package com.dwgj.mlxydedicatedline.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.commons.DateUtil;
import com.dwgj.mlxydedicatedline.commons.SequenceCode;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.controller.wx.WeiXinController;
import com.dwgj.mlxydedicatedline.dto.CustomerDTO;
import com.dwgj.mlxydedicatedline.entity.*;
import com.dwgj.mlxydedicatedline.entity.activityReward.ActivityPoster;
import com.dwgj.mlxydedicatedline.entity.activityReward.ActivityReward;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCoupons;
import com.dwgj.mlxydedicatedline.entity.coupons.SysCoupons;
import com.dwgj.mlxydedicatedline.entity.customer.CustomerIntegral;
import com.dwgj.mlxydedicatedline.entity.customer.CustomerIntegralRule;
import com.dwgj.mlxydedicatedline.entity.user.LoginToken;
import com.dwgj.mlxydedicatedline.entity.user.User;
import com.dwgj.mlxydedicatedline.mapper.*;
import com.dwgj.mlxydedicatedline.mapper.activityReward.ActivityPosterMapper;
import com.dwgj.mlxydedicatedline.mapper.activityReward.ActivityRewardMapper;
import com.dwgj.mlxydedicatedline.mapper.coupons.CustomerCouponsMapper;
import com.dwgj.mlxydedicatedline.mapper.coupons.SysCouponsMapper;
import com.dwgj.mlxydedicatedline.mapper.customer.CustomerIntegralMapper;
import com.dwgj.mlxydedicatedline.mapper.customer.CustomerIntegralRuleMapper;
import com.dwgj.mlxydedicatedline.mapper.customerMoney.CustomerMoneyMapper;
import com.dwgj.mlxydedicatedline.mapper.user.LoginTokenMapper;
import com.dwgj.mlxydedicatedline.mapper.user.UserMapper;
import com.dwgj.mlxydedicatedline.mapper.user.WxAccessTokenMapper;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerService;
import com.dwgj.mlxydedicatedline.service.wechat.SendMessageServer;
import com.dwgj.mlxydedicatedline.utils.HttpUtilManager;
import com.dwgj.mlxydedicatedline.utils.ImgUtil;
import com.dwgj.mlxydedicatedline.vo.customer.CustomerReqVo;
import com.dwgj.mlxydedicatedline.vo.customer.PopularizeDetailReqVo;
import com.dwgj.mlxydedicatedline.vo.customer.PopularizeDetailRespVo;
import com.dwgj.mlxydedicatedline.vo.customer.RegisterDataVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.dwgj.mlxydedicatedline.controller.wx.WeiXinController.loadJSON;
import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;
import static com.dwgj.mlxydedicatedline.enums.ResultStatus.SYS_ERROR;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private LoginTokenMapper loginTokenMapper;

    @Autowired
    private CustomerMoneyMapper moneyMapper;
    @Autowired
    private CustomerAccountMapper customerAccountMapper;

    @Autowired
    private CustomerFromMapper customerFromMapper;
    @Autowired
    private CustomerIntegralMapper customerIntegralMapper;
    @Autowired
    private CustomerIntegralRuleMapper customerIntegralRuleMapper;
    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ActivityRewardMapper activityRewardMapper;
    @Autowired
    private ActivityPosterMapper activityPosterMapper;

    @Autowired
    private SysCouponsMapper sysCouponsMapper;
    @Autowired
    private CustomerCouponsMapper customerCouponsMapper;

    @Autowired
    private WarehouseAddressMapper warehouseAddressMapper;

    @Autowired
    private SendMessageServer sendMessageServer;

    /**
     * ??????????????????
     */
    @Value("${upload.location}")
    private String UPLOAD_LOCATION;

    /**
     * ????????????
     */
    @Value("${server.domain}")
    private String DOMAIN;

    /**
     * ????????????
     */
    @Value("${server.url}")
    private String SERVER_URL;
    @Value("${server.prefix}")
    private String SERVER_PREFIX;

    @Value("${wechat.appid}")
    private String APPID;
    @Value("${wechat.appsecret}")
    private String APPSECRET;
    @Value("${wechat.openid_url}")
    private String OPENID_URL;

    private String REFRESH_GET_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    private String VALIDATION_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
    private String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    @Override
    public ResponseEntity<ResultModel> authorizationLogin(Map<String,Object> param) {
        try {
            String code = (String) param.get("code");
            if(StringUtils.isEmpty(code)){
                return new ResponseEntity<>(ResultModel.error(WECHAT_USER_CODE_NULL_ERROR), HttpStatus.OK);
            }
            HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
            String url = OPENID_URL.replaceAll("APPID", APPID).replaceAll("APPSECRET", APPSECRET).replaceAll("CODE", code);

            // ??????????????????openid???access_token???refresh_token
            String getTokenResponseStr = httpUtilManager.requestHttpGet(url);

            JSONObject getTokenResponse = JSON.parseObject(getTokenResponseStr);
            if(!StringUtils.isEmpty(getTokenResponse.getString("errmsg"))){
                String error = "?????????????????????" + getTokenResponse.getString("errcode") +"??? ?????????????????????" + getTokenResponse.getString("errmsg");
                return new ResponseEntity<>(ResultModel.error(WECHAT_ERROR, error), HttpStatus.OK);
            }
            String openid = getTokenResponse.getString("openid");
            String accessToken = getTokenResponse.getString("access_token");
            String refreshToken = getTokenResponse.getString("refresh_token");

            Map<String, String> weChatMap = new HashMap<>();
            weChatMap.put("openid", openid);
            weChatMap.put("access_token", accessToken);
            weChatMap.put("refresh_token", refreshToken);

            log.info("???????????????openid???{}",openid);
            // ??????openID???????????????????????????????????????????????????????????????
            checkOpenID(openid);
            Customer customer = customerMapper.selectByOpenid(openid);

            // ?????????????????????????????????????????????????????????
            if ( customer == null ){
                log.info("???????????????????????????????????????");
                customer = this.customerSetting();

                ResponseEntity<ResultModel> responseEntity = wxAuthorization(weChatMap, customer);
                ResultModel resultModel = responseEntity.getBody();
                if(resultModel.getCode() != 200){
                    return responseEntity;
                }
                customer = (Customer) resultModel.getContent();
                insertCustomerMoney(customer);
                int i = customerMapper.insert(customer);
                if(i < 1){
                    throw new RuntimeException();
                }
            }

            Map<String,Object> map = new HashMap<>(3);
            map.put("customerNo",customer.getCustomerNo());
            LoginToken loginToken = loginTokenMapper.getLoginTokenByParam(map);
            log.info("loginToken:{}",loginToken);
            // ?????????????????????
            if(StringUtils.isEmpty(loginToken)){
                log.info("?????????????????????????????????????????????");
                ResponseEntity<ResultModel> responseEntity = wxAuthorization(weChatMap, customer);
                ResultModel resultModel = responseEntity.getBody();
                if(resultModel.getCode() != 200){
                    return responseEntity;
                }
                customer = (Customer) resultModel.getContent();
                int i = customerMapper.updateByPrimaryKey(customer);
                if(i < 1){
                    throw new RuntimeException();
                }
            }
            String token = DigestUtils.md5Hex("customer_user_"+ LocalDateTime.now()+"_"+customer.getId());
            boolean isFirstLogin = saveOrUpdateLoginToken(token+ "_" + customer.getCustomerNo(),customer, loginToken);
            param.put("user",customer);
            param.put("isFirstLogin",isFirstLogin);
            param.put("token",token);
            return new ResponseEntity<>(ResultModel.ok(param), HttpStatus.OK);
        } catch (HttpException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    private void checkOpenID(String openid) {
        int count = customerMapper.findCustomerCountByOpenid(openid);
        if(count > 1){
            List<CustomerDTO> customerDTOList = customerMapper.findCustomerListByOpenid(openid);

            log.info("------------->>>>>>>>>>>>>  ?????????????????????????????????????????????????????????   <<<<<<<<<<<<<<<<---------------");
            List<Customer> customerList = new ArrayList<>();
            // ???????????????????????????????????????
            for (CustomerDTO customerDTO : customerDTOList) {
                if(!CollectionUtils.isEmpty(customerDTO.getGoodsList())){
                    customerList.add(customerDTO);
                }
            }
            Customer customer;
            List<Integer> customerIdList = customerDTOList.stream().map(CustomerDTO::getId).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(customerList)){
                // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
                customer = customerDTOList.get(0);
                log.info("?????????????????????????????????????????????????????????{}", customer.getLoginName());
            }else if(customerList.size() == 1){
                // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
                customer = customerList.get(0);
                log.info("?????????????????????????????????????????????????????????????????????{}", customer.getLoginName());
            }else {
                // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                customer = customerDTOList.get(0);
                CustomerMoney customerMoney = customerDTOList.get(0).getCustomerMoney();
                List<CustomerPack> updateCustomerPackList = new ArrayList<>();
                List<Goods> updateGoodsList = new ArrayList<>();
                List<CustomerAccount> updateCustomerAccountList = new ArrayList<>();
                List<CustomerMoney> updateCustomerMoneyList = new ArrayList<>();
                BigDecimal money = new BigDecimal(0);
                for (CustomerDTO customerDTO : customerDTOList) {
                    updateCustomerPackList.addAll(customerDTO.getCustomerPackList());
                    updateGoodsList.addAll(customerDTO.getGoodsList());
                    updateCustomerAccountList.addAll(customerDTO.getCustomerAccountList());
                    updateCustomerMoneyList.add(customerDTO.getCustomerMoney());
                    if(customerDTO.getCustomerMoney() != null){
                        money = money.add(customerDTO.getCustomerMoney().getAmount());
                    }
                }
                if(!CollectionUtils.isEmpty(updateCustomerPackList)){
                    // ????????????????????????
                    customerPackMapper.updateCustomerId(updateCustomerPackList, customer.getId());
                }
                if(!CollectionUtils.isEmpty(updateGoodsList)){
                    goodsMapper.updateCustomerNo(updateGoodsList, customer.getCustomerNo());
                }
                if(!CollectionUtils.isEmpty(updateCustomerAccountList)){
                    customerAccountMapper.updateCustomerId(updateCustomerAccountList, customer);
                }
                if(!CollectionUtils.isEmpty(updateCustomerMoneyList)){
                    moneyMapper.updateCustomerNo(updateCustomerMoneyList, customer.getCustomerNo());
                }
                if(customerMoney != null){
                    customerMoney.setAmount(money);
                    moneyMapper.updateMoneyByPrimaryKeySelective(customerMoney);
                }
                log.info("??????????????????????????????????????????????????????????????????{}", customer.getLoginName());
            }
            customerMapper.updateRepeatedCustomer(customerIdList, customer.getId());
        }
    }

    @Override
    public ResponseEntity<ResultModel> checkLoginStatus(HttpServletRequest request) {
        // ?????????????????? Token ???????????????
        String userToken = request.getHeader("authorization");
        Customer user = loginTokenMapper.getUser(userToken);
        log.info("?????????????????????{}",user);
        if(user != null){
            System.out.println("????????????????????????");
            return new ResponseEntity<>(ResultModel.ok(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCustomerWareAddress(int commercialAreaId, HttpServletRequest request) {
        String userToken = request.getHeader("authorization");
        Customer customer = loginTokenMapper.getUser(userToken);
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
        }

        WarehouseAddress warehouseAddress = warehouseAddressMapper.getIsEnableAddress(commercialAreaId);
        if(warehouseAddress == null){
            return new ResponseEntity<>(ResultModel.error(ENABLE_WAREHOUSE_ADDRESS_NULL_ERROR), HttpStatus.OK);
        }
        String addressee = warehouseAddress.getAddressee().replaceAll("LOGIN_NAME", customer.getLoginName());
        String address = warehouseAddress.getAddress().replaceAll("LOGIN_NAME", customer.getLoginName());
        warehouseAddress.setAddressee(addressee);
        warehouseAddress.setAddress(address);
        return new ResponseEntity<>(ResultModel.ok(warehouseAddress), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> updateCustomerEmail(Integer id, String email) {
        List<Customer> customerList = customerMapper.selectByEmail(email);
        if(!CollectionUtils.isEmpty(customerList)){
            if(customerList.size() > 1){
                return new ResponseEntity<>(ResultModel.error(EMAIL_REPEAT_ERROR), HttpStatus.OK);
            }else if (!customerList.get(0).getId().equals(id)){
                return new ResponseEntity<>(ResultModel.error(EMAIL_REPEAT_ERROR), HttpStatus.OK);
            }
        }
        customerMapper.updateEmail(id, email);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    private Customer customerSetting() {

        List<Customer> customerList = customerMapper.findAll();

        List<String> loginNameList = customerList.stream().map(Customer::getLoginName).collect(Collectors.toList());
        String thisLoginName = "";
        boolean b = true;
        while (b) {
            thisLoginName = toSerialCode();
            if (!loginNameList.contains(thisLoginName)) {
                b = false;
            }
        }
        Customer customer = new Customer();

        customer.setStatus(1);
        customer.setCreateTime(new Date());

        customer.setLoginName(thisLoginName);
        customer.setPassword("000000");

        String customerNo = SequenceCode.gainSerialNo("CUM");
        customer.setCustomerNo(customerNo);
        BASE64Encoder encoder = new BASE64Encoder();
        customer.setPassword(encoder.encode(customer.getPassword().getBytes()));

        customer.setPhoneNumber(thisLoginName);

        return customer;
    }

    private ResponseEntity<ResultModel> wxAuthorization(Map<String, String> weChatMap, Customer customer) throws IOException, HttpException {
        HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
        String accessToken = weChatMap.get("access_token");
        String refreshToken = weChatMap.get("refresh_token");
        String openid = weChatMap.get("openid");

        // ?????? access_token????????????
        String validationUrl
                = VALIDATION_ACCESS_TOKEN_URL.replaceAll("ACCESS_TOKEN", accessToken)
                .replaceAll("OPENID", openid);
        String validationStr = httpUtilManager.requestHttpGet(validationUrl);
        JSONObject validationResponse = JSON.parseObject(validationStr);
        // ????????????
        if(!"0".equals(validationResponse.getString("errcode"))){
            // ???????????????access_token ???????????????access_token??????????????????
            String refreshTokenUrl = REFRESH_GET_TOKEN_URL.replaceAll("ACCESS_TOKEN", accessToken)
                    .replaceAll("REFRESH_TOKEN", refreshToken);
            String refreshTokenStr = httpUtilManager.requestHttpGet(refreshTokenUrl);
            JSONObject refreshTokenResponse = JSON.parseObject(refreshTokenStr);
            // ??????????????????????????????
            if(!"0".equals(refreshTokenResponse.getString("errcode"))){
                String error = "?????????????????????" + refreshTokenResponse.getString("errcode") +"??? ?????????????????????" + refreshTokenResponse.getString("errmsg");
                return new ResponseEntity<>(ResultModel.error(WECHAT_ERROR, error), HttpStatus.OK);
            }
        }
        // ??????????????????????????????(???scope??? snsapi_userinfo)
        String getUserInfoURL
                = GET_USER_INFO_URL.replaceAll("ACCESS_TOKEN", accessToken)
                .replaceAll("OPENID", openid);

        URL url1 = new URL(getUserInfoURL);
        HttpURLConnection urlConnection = (HttpURLConnection)url1.openConnection();


        // ???????????????????????????????????????
        InputStream inputStream = urlConnection.getInputStream();

        // ??????????????????
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
        BufferedReader in = new BufferedReader(inputStreamReader);
        String getUserInfoStr =in.readLine().toString();
        System.out.println("jsonUserStr = "+getUserInfoStr);

        // ????????????
        inputStream.close();
        inputStream = null;
        urlConnection.disconnect();

//        String getUserInfoStr = httpUtilManager.requestHttpGet(getUserInfoURL);
        JSONObject getUserInfoStrResponse = JSON.parseObject(getUserInfoStr);
        log.info("?????????????????????????????????{}",getUserInfoStrResponse);
        if(!StringUtils.isEmpty(getUserInfoStrResponse.getString("errmsg"))){
            String error = "?????????????????????" + getUserInfoStrResponse.getString("errcode") +"??? ?????????????????????" + getUserInfoStrResponse.getString("errmsg");
            return new ResponseEntity<>(ResultModel.error(WECHAT_ERROR, error), HttpStatus.OK);
        }

        openid = getUserInfoStrResponse.getString("openid");
        String nickname = getUserInfoStrResponse.getString("nickname");
        String sex = getUserInfoStrResponse.getString("sex");
        String headimgurl = getUserInfoStrResponse.getString("headimgurl");
        customer.setHeadimgurl(headimgurl);
        customer.setCustomerName(nickname);
        customer.setGender(sex);
        customer.setOpenid(openid);

        return new ResponseEntity<>(ResultModel.ok(customer), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> login(String loginName, String password) {
        Map<String, Object> resultMap = new HashMap<>();
        Customer customer = customerMapper.login(loginName);
        if(customer == null ){
            return new ResponseEntity<>(ResultModel.error(USERNAME_ERROR), HttpStatus.OK);
        }
        BASE64Decoder decoder = new BASE64Decoder();
        String decoderPassword = customer.getPassword();
        try {
            byte[] b = decoder.decodeBuffer(decoderPassword);
            decoderPassword = new String(b, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!decoderPassword.equals(password)){
            return new ResponseEntity<>(ResultModel.error(PASSWORD_ERROR), HttpStatus.OK);
        }
        String token = saveCustomerToken(customer, loginName, password);
        resultMap.put("customer",customer);
        resultMap.put("token",token);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    private String saveCustomerToken(Customer customer, String loginName, String password) {
        String token = DigestUtils.md5Hex("customer_user_"+ LocalDateTime.now()+"_"+customer.getId());

        // ????????????????????????
        Map<String,Object> map = new HashMap<>(3);
        map.put("customerNo",customer.getCustomerNo());
        LoginToken loginToken = loginTokenMapper.getLoginTokenByParam(map);
        log.info("loginToken:{}",loginToken);

        saveOrUpdateLoginToken(token+ "_" + customer.getCustomerNo(),customer, loginToken);
        UsernamePasswordToken token1 = new UsernamePasswordToken(loginName, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token1);
        Customer customer2 = (Customer) subject.getPrincipal();
        subject.getSession().setAttribute("customer", customer2);
        return token;
    }

    private boolean saveOrUpdateLoginToken(String userToken, Customer customer, LoginToken loginToken){
        if( null != loginToken ){
            // ?????????????????????
            loginToken.setCustomerId(customer.getId().toString());
            loginToken.setCustomerNo(customer.getCustomerNo());
            loginToken.setCustomerToken(userToken);
            loginToken.setLoginTime(DateUtil.timestamp2String(new Date()));
            log.info("loginToken.getLoginCount():{}",loginToken.getLoginCount());
            loginToken.setLoginCount(loginToken.getLoginCount() + 1);
            loginTokenMapper.updateByPrimaryKey(loginToken);
            return false;
        }else {
            // ???????????????
            loginToken = new LoginToken();
            loginToken.setLoginCount(1);
            loginToken.setLoginTime(DateUtil.timestamp2String(new Date()));
            loginToken.setCustomerId(customer.getId().toString());
            loginToken.setCustomerNo(customer.getCustomerNo());
            loginToken.setCustomerToken(userToken);
            loginToken.setFirstLoginTime(DateUtil.timestamp2String(new Date()));
            loginTokenMapper.insert(loginToken);
            return true;
        }
    }

    @Override
    public Customer getUserInfoByOpenid(String openid) {
        // ?????????????????????????????????openid???????????????????????????????????????????????????????????????????????????????????????
        Customer user = customerMapper.selectByOpenid(openid);
        if( null == user ){
            String access_token = wxAccessTokenMapper.getAccessToken();
//            String access_token = "35_n715omgmUcIgze9Atz7kUDaksvh-p-KzFsjjnXhihUI-dBhPDam6E0itDAMThvkQ5mFowwqGdkAd0uq_5zDFhFE325nYWiIkSwmreNVEsjqvlMu-tW_wrT0-qmQYhi8wTPXCUR2w_BhDla6_YQSbACANFH";
            try {
                user = getWeChatUserMessage(openid, access_token);

                insertCustomerInfo(user);

                user = customerMapper.selectByOpenid(openid);

                insertCustomerMoney(user);


                }
            catch (Exception e) {
                e.printStackTrace();
                log.error("???????????????");
                return null;
            }
        }
        return user;
    }

    @Override
    public ResponseEntity<ResultModel> generateCustomerPoster(int id, HttpServletRequest request, HttpServletResponse response) {
        String posterPath = "";
        String qrcodePath = "";
        try {
            ActivityPoster activityPoster = activityPosterMapper.selectEnablePoster();

            String dateStr = DateUtil.getDateFormate(new Date(), "yyyy-MM"); // ?????????????????????

            String timeStr = DateUtil.getDateFormate(new Date(), "ddHHmmss"); // ?????????????????????


            Customer customer = customerMapper.selectByPrimaryKey(id);

            String fileLocation = UPLOAD_LOCATION + "/template/poster/" + dateStr;
            File file = new File(fileLocation);
            if (!file.exists()) {
                file.mkdirs();
            }

            // ???????????????????????????????????????????????????
            if(activityPoster.getIsPlaceQrcode() == 1){
                // ?????????????????????
                String QRPath = UPLOAD_LOCATION + "/template/poster/qrcode/"+dateStr;
                qrcodePath = QRPath + "QR" + customer.getLoginName() + "_" + timeStr + ".jpg";
                File qrcode = new File(qrcodePath);
                if(qrcode.exists()){
                    qrcode.delete();
                }
                double multiple = activityPoster.getQrcodeScaling();
                String accessToken = wxAccessTokenMapper.getAccessToken();
                ImgUtil.generateWeChatQrcode(QRPath, accessToken, "share_" + customer.getLoginName(), qrcodePath, multiple);
            }

            String fileName = customer.getLoginName()+ "_" +  timeStr + ".jpg";

            posterPath = fileLocation + "/" + fileName;
            // ????????????
            ImgUtil.posterImage(activityPoster, qrcodePath, posterPath);

            // ??????????????????
            posterPath = DOMAIN + posterPath.substring(4, posterPath.length());

            return new ResponseEntity<>(ResultModel.ok(posterPath), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }finally {
            // ?????????????????????
            File qrFile = new File(qrcodePath);
            qrFile.delete();
        }
    }

    @Override
    public ResponseEntity<ResultModel> generateShareLink(String loginName) {
        String url = SERVER_PREFIX + SERVER_URL + "/client/jiyun/#/pages/login/register?CustID="+loginName+"&FromType=shareLink";
        return new ResponseEntity<>(ResultModel.ok(url), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getPopularizeDetailByCustomerId(PopularizeDetailReqVo popularize) {
        PageData pageData = PageHelp.editPage(String.valueOf(popularize.getPageNumber()), String.valueOf(popularize.getPageSize()));
        int count = customerFromMapper.selectPopularizeDetailCount(popularize);
        pageData.setTotal(count);
        Map<String, Object> resultMap = new HashMap<>();
        int totalPages = 1;
        resultMap.put("number", pageData.getNumber());
        resultMap.put("total", pageData.getTotal());
        resultMap.put("totalPages", totalPages);
        if(count > 0){

            double ceil = (double) count / (double) popularize.getPageSize();
            totalPages = (int) Math.ceil(ceil);
            resultMap.put("totalPages", totalPages);
            popularize.setPageNumber(pageData.getPageNumber());
            List<PopularizeDetailRespVo> popularizeDetailRespVoList =  customerFromMapper.selectPopularizeDetailByCustomerId(popularize);
            return new ResponseEntity<>(PageResultModel.ok(popularizeDetailRespVoList, resultMap), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), resultMap), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getIntegralsSum(int id) {
        double integrals = customerIntegralMapper.selectIntegralSum(id);
        return new ResponseEntity<>(ResultModel.ok(integrals), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getIntegralsDetail(CustomerReqVo customerReqVo) {
        PageData pageData = PageHelp.editPage(String.valueOf(customerReqVo.getPageNumber()), String.valueOf(customerReqVo.getPageSize()));
        Map<String, Object> resultMap = new HashMap<>();
        int count = customerIntegralMapper.selectIntegralCount(customerReqVo);
        resultMap.put("number", pageData.getNumber());
        resultMap.put("total", count);
        int totalPages = 1;
        resultMap.put("totalPages", totalPages);
        if(count > 0){
            double ceil = (double) count / (double) customerReqVo.getPageSize();
            totalPages = (int) Math.ceil(ceil);
            resultMap.put("totalPages", totalPages);
            customerReqVo.setPageNumber(pageData.getPageNumber());
            List<CustomerIntegral> customerIntegrals = customerIntegralMapper.getIntegralsDetail(customerReqVo);
            return new ResponseEntity<>(PageResultModel.ok(customerIntegrals, resultMap), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), resultMap), HttpStatus.OK);
    }

    public static String toSerialCode() {
        // ????????????????????????????????????pc??????????????????
        String sources = "0123456789";
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 6; j++) {
            flag.append(sources.charAt(rand.nextInt(9)) + "");
        }
        return flag.toString();
    }

    @Override
    public List<Customer> findAll() {
        return customerMapper.findAll();
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> register(RegisterDataVo registerData) {
        List<Customer> customerList = customerMapper.findAll();
        // ????????????????????????
        List<String> emailList = customerList.stream().map(Customer::getEmail).collect(Collectors.toList());
        if (emailList.contains(registerData.getEmail())) {
            return new ResponseEntity<>(ResultModel.error(EMAIL_REPEAT_ERROR), HttpStatus.OK);
        }
        // ??????????????????????????????
        List<String> phoneList = customerList.stream().map(Customer::getPhoneNumber).collect(Collectors.toList());
        if (phoneList.contains(registerData.getPhoneNumber())) {
            return new ResponseEntity<>(ResultModel.error(PHONE_REPEAT_ERROR), HttpStatus.OK);
        }

        List<String> loginNameList = customerList.stream().map(Customer::getLoginName).collect(Collectors.toList());
        String thisLoginName = "";
        boolean b = true;
        while (b) {
            thisLoginName = toSerialCode();
            if (!loginNameList.contains(thisLoginName)) {
                b = false;
            }
        }
        Customer customer = new Customer();

        customer.setLoginName(thisLoginName);
        customer.setCustomerName(registerData.getCustomerName());
        customer.setPhoneNumber(registerData.getPhoneNumber());
        customer.setEmail(registerData.getEmail());
        customer.setPassword(registerData.getPassword());


        String code = SequenceCode.gainSerialNo("CUM");
        customer.setCustomerNo(code);
        BASE64Encoder encoder = new BASE64Encoder();
        customer.setPassword(encoder.encode(customer.getPassword().getBytes()));

        insertCustomerMoney(customer);

        int i = customerMapper.insert(customer);

        if (i < 1) {
            log.info("1SYS_ERROR:{}",SYS_ERROR);
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }

//      ??????????????????????????????????????????????????????????????????????????????
        if(!StringUtils.isEmpty(registerData.getServerID()) || !StringUtils.isEmpty(registerData.getCustID())){
            insertCustomerForm(customer, registerData);
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        BASE64Decoder decoder = new BASE64Decoder();
        String decoderPassword = customer.getPassword();
        try {
            byte[] bytes = decoder.decodeBuffer(decoderPassword);
            decoderPassword = new String(bytes, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String token = saveCustomerToken(customer, customer.getPhoneNumber(), decoderPassword);
        resultMap.put("customer", customer);
        resultMap.put("token", token);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    private void insertCustomerForm(Customer customer, RegisterDataVo registerData) {
        CustomerFrom customerFrom = new CustomerFrom();
        String activityType = "register";  // ????????????????????? ??????????????????
        // ?????????????????????
        if(!StringUtils.isEmpty(registerData.getServerID())){
            User user = userMapper.selectByPrimaryKey(Integer.parseInt(registerData.getServerID()));
            if(user != null){
                customerFrom.setFromUserId(user.getId());
            }
            activityType = "shareLinkRegister";
        }

        Customer formCustomer = new Customer();
        // ?????????????????????
        if(!StringUtils.isEmpty(registerData.getCustID())){
            formCustomer = customerMapper.selectByLoginName(registerData.getCustID());
            if(formCustomer != null){
                customerFrom.setFromCustomerId(formCustomer.getId());
                // ???????????????????????????
                insertActivityRewards(formCustomer, customer, null, registerData.getFromType());
            }
            activityType = "shareLinkRegister";
        }

        insertActivityRewards(customer, formCustomer, registerData.getSysCC(), activityType);

        customerFrom.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        customerFrom.setCustomerId(customer.getId());

        customerFrom.setFromType(registerData.getFromType());
        customerFrom.setStatus(1);
        customerFrom.setVersion(1);
        customerFromMapper.insertSelective(customerFrom);
    }


    private void insertActivityRewards(Customer customer, Customer fromCustomer, String sysCC, String formType) {
        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if(!StringUtils.isEmpty(sysCC)){
            SysCoupons sysCoupons = sysCouponsMapper.selectByCouponsCode(sysCC);

            CustomerCoupons customerCoupons = settingCustomerCoupons(sysCoupons);
            customerCoupons.setCustomerId(customer.getId());
            System.out.println(customer.getCustomerName() + ": ???????????????????????????"+sysCoupons.getCouponsName()+"???????????????");

            customerCouponsMapper.insertSelective(customerCoupons);
        }else {
            List<ActivityReward> activityRewardList = activityRewardMapper.selectByActivityType(formType);
            List<Integer> integralIdList = activityRewardList.stream()
                    .filter(activityReward -> "integral".equals(activityReward.getRewardType()))
                    .map(ActivityReward::getRewardContentId).collect(Collectors.toList());
            // ???????????? ?????????????????? ?????????????????????????????????????????????
            if(!CollectionUtils.isEmpty(integralIdList)){
                List<CustomerIntegralRule> customerIntegralRuleList
                        = customerIntegralRuleMapper.selectEnableListByIntegralRuleIds(integralIdList);
                if(!CollectionUtils.isEmpty(customerIntegralRuleList)){
                    List<CustomerIntegral> customerIntegralList = new ArrayList<>();
                    for (CustomerIntegralRule customerIntegralRule : customerIntegralRuleList) {
                        CustomerIntegral customerIntegral = new CustomerIntegral();
                        customerIntegral.setStatus(1);
                        customerIntegral.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));

                        customerIntegral.setIntegralType(customerIntegralRule.getIntegralType());

                        customerIntegral.setCustomerId(customer.getId());
                        customerIntegral.setFromId(fromCustomer.getId());
                        customerIntegral.setIntegrals(customerIntegralRule.getIntegrals());
                        customerIntegralList.add(customerIntegral);
                        System.out.println(customer.getCustomerName() + ": ?????????"+customerIntegralRule.getIntegrals()+"????????????");
                    }
                    customerIntegralMapper.insertList(customerIntegralList);
                }
            }

            List<Integer> sysCouponsIdList = activityRewardList.stream()
                    .filter(activityReward -> "coupons".equals(activityReward.getRewardType()))
                    .map(ActivityReward::getRewardContentId).collect(Collectors.toList());

            // ???????????? ????????????????????? ????????????????????????????????????????????????
            if(!CollectionUtils.isEmpty(sysCouponsIdList)){
                List<SysCoupons> sysCouponsList = sysCouponsMapper.selectEnableListBySysCouponsIds(sysCouponsIdList);
                if(!CollectionUtils.isEmpty(sysCouponsList)){
                    List<CustomerCoupons> customerCouponsList = new ArrayList<>();
                    for (SysCoupons sysCoupons : sysCouponsList) {
                        CustomerCoupons customerCoupons = settingCustomerCoupons(sysCoupons);
                        customerCoupons.setCustomerId(customer.getId());
                        customerCouponsList.add(customerCoupons);
                        System.out.println(customer.getCustomerName() + ": ?????????"+sysCoupons.getCouponsName()+"???????????????");
                    }
                    customerCouponsMapper.insertList(customerCouponsList);
                }

            }
        }
    }

    private CustomerCoupons settingCustomerCoupons(SysCoupons sysCoupons) {
        CustomerCoupons customerCoupons = new CustomerCoupons();
        customerCoupons.setStatus(1);
        customerCoupons.setVersion(1);
        customerCoupons.setSysCouponsId(sysCoupons.getId());
        customerCoupons.setCouponsName(sysCoupons.getCouponsName());
        customerCoupons.setCouponsType(sysCoupons.getCouponsType());
        customerCoupons.setCouponsAmount(sysCoupons.getCouponsAmount());
        customerCoupons.setMinimumAmount(sysCoupons.getMinimumAmount());
        if(sysCoupons.getOverdueType() == 1){
            customerCoupons.setTimeOverdue(sysCoupons.getTimeOverdue());
        }else {
            String overdueTime =
                    DateUtil.FormatDate(DateUtil.getSpecifiedDayAfter(new Date(), sysCoupons.getTimeLimit()), "yyyy-MM-dd");
            customerCoupons.setTimeOverdue(overdueTime);
        }
        customerCoupons.setCouponsStatus(0);
        customerCoupons.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        customerCoupons.setGiveId(-1);
        customerCoupons.setCustomerGroupId(-1);
        customerCoupons.setCenterCouponsId(-1);
        return customerCoupons;

    }

    @Override
    public ResponseEntity<ResultModel> update(Customer customer) {
        Customer oldCustomer;
        if(!StringUtils.isEmpty(customer.getPhoneNumber())){
            oldCustomer = customerMapper.login(customer.getPhoneNumber());
            if (oldCustomer != null && !customer.getId().equals(oldCustomer.getId())) {
                return new ResponseEntity<>(ResultModel.error(PHONE_REPEAT_ERROR),HttpStatus.OK);
            }
        }

        if(!StringUtils.isEmpty(customer.getEmail())){
            oldCustomer = customerMapper.login(customer.getEmail());
            if (oldCustomer != null && !customer.getId().equals(oldCustomer.getId())) {
                return new ResponseEntity<>(ResultModel.error(EMAIL_REPEAT_ERROR),HttpStatus.OK);
            }
        }

        try {
            int i = customerMapper.updateByPrimaryKeySelective(customer);
            if (i < 1) {
                return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR),HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<ResultModel> updatePassword(Map<String, Object> paramMap) {
        if(!paramMap.containsKey("id")){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR),HttpStatus.OK);
        }
        if(!paramMap.containsKey("password")){
            return new ResponseEntity<>(ResultModel.error(PASSWORD_NULL_ERROR),HttpStatus.OK);
        }
        if(!paramMap.containsKey("newPassword")){
            return new ResponseEntity<>(ResultModel.error(NEW_PASSWORD_NULL_ERROR),HttpStatus.OK);
        }
        int id = Integer.parseInt(String.valueOf(paramMap.get("id")));
        String password = String.valueOf(paramMap.get("password"));
        String newPassword = String.valueOf(paramMap.get("newPassword"));

        Customer customer = customerMapper.selectByPrimaryKey(id);
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(USER_NULL_ERROR),HttpStatus.OK);
        }

        BASE64Encoder encoder = new BASE64Encoder();
        password = encoder.encode(password.getBytes());
        if(!password.equals(customer.getPassword())){
            return new ResponseEntity<>(ResultModel.error(EDIT_TIME_PASSWORD_ERROR),HttpStatus.OK);
        }

        customer.setPassword(encoder.encode(newPassword.getBytes()));

        int i = customerMapper.updatePassword(customer);
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public List<Goods> matchingOrder(Map map, String customerNo) {
        return goodsMapper.findDeliveryOrderNoAndCustomerId(String.valueOf(map.get("deliveryOrderNo")),customerNo);
    }


    /**
     * @param map        ???????????????
     *                   deliveryNo ?????????????????????deliveryOrderNo  ??????????????? goodsName  ??????????????? goodsNumber  ??????????????? remark  ????????? addressId  ????????????
     * @param customerId
     * @return
     */
    @Override
    @Transactional
    public int downOrder(Map map, int customerId) {
        AtomicInteger result = new AtomicInteger(1);
        try {
            String str = (String) map.get("paramMap");
            List lists = JSONObject.parseArray(str);
            List<Goods> insertGoodsList = new ArrayList<>();
            lists.forEach(list -> {
                Order order = JSON.parseObject(JSONObject.toJSONString(list), Order.class);

                String goodsName = order.getGoodsName();
                Integer goodsNum = order.getGoodsNumber() == null ? 1 : order.getGoodsNumber();
                String remark = order.getRemark();
                String deliveryOrderNo = order.getDeliveryOrderNo().toUpperCase().replaceAll(" ", "");
                String customerNo = UserThreadContext.getUser().getCustomerNo();
                // ??????????????????????????????????????????????????????????????????
                List<Goods> goodsList = goodsMapper.findDeliveryOrderNoAndCustomerId(deliveryOrderNo,customerNo);
                if ( goodsList.size() > 0 ){
                    result.set(2);
                    return;
                }
                Goods goods = new Goods();
                goods.setGoodsNo(SequenceCode.gainSerialNo("GOODS"));
                goods.setGoodsType("3");
                goods.setCreateId(customerId);
                goods.setCustomerNo(customerNo);
                goods.setGoodsName(goodsName);
                goods.setPackageNum(goodsNum);
                goods.setMessage(remark);
                goods.setDeliveryOrderNo(deliveryOrderNo);
                goods.setCommercialAreaId(order.getCommercialAreaId());
                goods.setCommercialAreaName(order.getCommercialAreaName());
                insertGoodsList.add(goods);
            });
            goodsMapper.insertList(insertGoodsList);
        } catch (Exception e) {
            e.printStackTrace();
            result.set(-1);
        }
        return result.get();
    }

    @Override
    public Customer selectByPrimaryKey(int id) {
        return customerMapper.selectByPrimaryKey(id);
    }


    private Customer getWeChatUserMessage(String openid, String access_token){

        JSONObject jsonObject = WeiXinController.getUserInfoByOpenid(openid,access_token);
        log.info("?????????????????????{}",jsonObject);
        //??????????????????????????????
        Customer user = new Customer();

        String code = SequenceCode.gainSerialNo("CUM");
        user.setCustomerNo(code);

        // ???????????????
        user.setOpenid(jsonObject.getString("openid"));
        // ??????
        user.setCustomerName(jsonObject.getString("nickname"));
        // ??????????????????1????????????2????????????0????????????
        user.setGender(jsonObject.get("sex").toString());
        // ??????????????????
        user.setCountry(jsonObject.getString("country"));
        // ??????????????????
        user.setProvince(jsonObject.getString("province"));
        // ??????????????????
        user.setCity(jsonObject.getString("city"));
        // ????????????
        user.setHeadimgurl(jsonObject.getString("headimgurl"));

        user.setPhoneNumber(jsonObject.getString("phoneNumber"));

        BASE64Encoder encoder = new BASE64Encoder();
        user.setPassword(encoder.encode("000000".getBytes()));
        return user;
    }

    /**
     * ????????????????????????
     * @param customer
     */
    private void insertCustomerInfo(Customer customer){
        List<Customer> customerList = customerMapper.findAll();
        String loginCode = toSerialCode();
        String loginName = loginCode;
        boolean b = true;
        while (b) {
            for (Customer cus : customerList) {
                if (cus.getLoginName() != null && cus.getLoginName().equals(loginCode)) {
                    loginCode = toSerialCode();
                    break;
                }
            }
            if (loginName.equals(loginCode)) {
                b = false;
            }
        }
        for (Customer cus : customerList) {
            // ??????
            if (cus.getCustomerName() != null && cus.getCustomerName().equals(customer.getCustomerName())) {
                customer.setCustomerName(customer.getCustomerName()+"_1");
            }
        }
        customer.setLoginName(loginName);
        // ??????????????????????????????????????????????????????????????????
        customer.setPhoneNumber(loginName);
        customerMapper.insert(customer);
    }

    /**
     * ????????????????????????
     * @param customer
     */
    private void insertCustomerMoney(Customer customer) {
        CustomerMoney money = new CustomerMoney();
        money.setCustomerId(customer.getCustomerNo());
        money.setStatus(1);
        money.setVersion(1);
        money.setCustomerName(customer.getCustomerName());
        money.setAmount(new BigDecimal(0));

        moneyMapper.insert(money);
    }

}
