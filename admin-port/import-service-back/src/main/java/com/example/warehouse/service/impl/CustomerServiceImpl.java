package com.example.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.*;
import com.example.warehouse.controller.weChat.WXMessageBean;
import com.example.warehouse.entity.*;
import com.example.warehouse.entity.activityReward.ActivityPoster;
import com.example.warehouse.entity.activityReward.ActivityReward;
import com.example.warehouse.entity.coupons.CustomerCoupons;
import com.example.warehouse.entity.coupons.SysCoupons;
import com.example.warehouse.entity.customer.CustomerIdentity;
import com.example.warehouse.entity.customer.CustomerIntegral;
import com.example.warehouse.entity.customer.CustomerIntegralRule;
import com.example.warehouse.entity.customerGroup.CustomerGroupLink;
import com.example.warehouse.entity.image.ImageType;
import com.example.warehouse.entity.image.Images;
import com.example.warehouse.entity.sys.CommercialArea;
import com.example.warehouse.entity.wechat.WxAccessToken;
import com.example.warehouse.mapper.*;
import com.example.warehouse.mapper.activityReward.ActivityPosterMapper;
import com.example.warehouse.mapper.activityReward.ActivityRewardMapper;
import com.example.warehouse.mapper.coupons.CustomerCouponsMapper;
import com.example.warehouse.mapper.coupons.SysCouponsMapper;
import com.example.warehouse.mapper.customer.CustomerIntegralMapper;
import com.example.warehouse.mapper.customer.CustomerIntegralRuleMapper;
import com.example.warehouse.mapper.customerGroup.CustomerGroupLinkMapper;
import com.example.warehouse.mapper.customerMoney.CustomerMoneyMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.sys.CommercialAreaMapper;
import com.example.warehouse.mapper.user.WxAccessTokenMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.GoodsVo;

import com.example.warehouse.vo.customer.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.warehouse.entity.image.ImageType.CustomerIdentityPicture;
import static com.example.warehouse.enums.ResultStatus.*;

@Service("customerService")
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    /**
     * ??????????????????id
     */
    @Value("${wechat.appid}")
    private String APPID;
    /**
     * ????????????????????????
     */
    @Value("${wechat.appsecret}")
    private String APPSECRET;
    /**
     * ??????access_token??????????????????GET???
     */
    @Value("${wechat.access_token_url}")
    private String TOKEN_URL;
    /**
     * ??????????????????????????????????????????GET???
     */
    @Value("${wechat.userinfo_url}")
    private String USERINFO_URL;

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private CommercialAreaMapper commercialAreaMapper;
    @Autowired
    private CustomerMoneyMapper moneyMapper;

    @Autowired
    private CustomerGroupLinkMapper customerGroupLinkMapper;

    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;
    @Autowired
    private ActivityPosterMapper activityPosterMapper;

    @Autowired
    private CustomerFromMapper customerFromMapper;

    @Autowired
    private ActivityRewardMapper activityRewardMapper; // ??????????????????
    @Autowired
    private SysCouponsMapper sysCouponsMapper; // ?????????
    @Autowired
    private CustomerCouponsMapper customerCouponsMapper; // ???????????????
    @Autowired
    private CustomerIntegralMapper customerIntegralMapper; // ???????????????

    @Autowired
    private CustomerIntegralRuleMapper customerIntegralRuleMapper; // ??????????????????
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

    public static String toSerialCode() {
        // ????????????????????????????????????pc??????????????????
        String sources = "0123456789";
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        List<String> sysCode = new ArrayList<>();
        sysCode.add("510000");
        sysCode.add("510440");
        sysCode.add("000000");
        for (int j = 0; j < 6; j++) {
            flag.append(sources.charAt(rand.nextInt(10)) + "");
        }

        while (sysCode.contains(flag.toString())){
            System.out.print(flag + "??????????????????????????????");
            flag = new StringBuffer();
            for (int j = 0; j < 6; j++) {
                flag.append(sources.charAt(rand.nextInt(10)) + "");
            }
            System.out.println("?????????????????????" +flag+"???" );
        }

        return flag.toString();
    }

    @Override
    public ResponseEntity<PageResultModel> getGoodsListByCustomerNo(CustomerOrderReqVo customerOrderReqVo) {
        PageData pageData = PageHelp.editPage(customerOrderReqVo);
        int count = goodsMapper.getCustomerGoodsCount(customerOrderReqVo);
        pageData.setTotal(count);
        if(count > 0){
            customerOrderReqVo.setPageNumber(pageData.getPageNumber());
            List<GoodsVo> goodsVoList = goodsMapper.getGoodsListByCustomerNo(customerOrderReqVo);
            if(!CollectionUtils.isEmpty(goodsVoList)){
                goodsVoList.forEach(goodsVo -> {
                    if(!StringUtils.isEmpty(goodsVo.getLocation())){
                        if(goodsVo.getLocation().equals(",")){
                            goodsVo.setLocation("?????????");
                        }else {
                            String area = goodsVo.getLocation().split(",")[0];
                            String row = goodsVo.getLocation().split(",")[1];
                            goodsVo.setLocation(area + "???" + row + "???");
                        }
                    }
                });
            }
            return new ResponseEntity<>(PageResultModel.ok(goodsVoList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getOrderListByCustomerId(CustomerOrderReqVo customerOrderReqVo) {
        PageData pageData = PageHelp.editPage(customerOrderReqVo);
        int count = customerPackMapper.getCustomerOrderCount(customerOrderReqVo);
        pageData.setTotal(count);
        if(count > 0){
            customerOrderReqVo.setPageNumber(pageData.getPageNumber());
            List<Map> mapList = customerPackMapper.getOrderListByCustomerId(customerOrderReqVo);
//            List<CustomerPackVo> customerPackVoList = ;
            return new ResponseEntity<>(PageResultModel.ok(mapList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    /**
     * ????????????
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> save(Map<String, Object> params) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        CommercialArea commercialArea = commercialAreaMapper.selectByPrimaryKey(user.getCommercialAreaId());

        log.info("??????????????????------->>>>>?????????{}", params);
        String str = JSON.toJSON(params).toString();
        Customer customer = JSON.parseObject(str, Customer.class);
        customer.setCreateTime(DateUtil.timestamp2String(new Date()));
        try {
            List<Customer> list = customerMapper.findByName(customer.getCustomerName(), customer.getPhoneNumber(), customer.getLoginName());
            if (list.size() > 0) {
                // ???????????????
//                return 1002;
                log.error("???????????????????????????????????????");
                return new ResponseEntity<>(ResultModel.error(USER_REPEAT_ERROR), HttpStatus.OK);
            }
            // TODO ??????customer_code
            String code = SequenceCode.gainSerialNo("CUM");
            customer.setCustomerNo(code);

            if (customer.getPassword() == null) {
                BASE64Encoder encoder = new BASE64Encoder();
                customer.setPassword(encoder.encode("000000".getBytes()));
            }
            if (customer.getLoginName() == null || "".equals(customer.getLoginName())) {
//                List<Customer> customerList = customerMapper.getAllCustomer();

                List<String> loginNameList = customerMapper.getAllCustomerLoginName();

                String loginCode = toSerialCode();
                String loginName = loginCode;
                boolean b = true;
                while (b) {
                    for (String loginNameCode : loginNameList) {
                        if (!StringUtils.isEmpty(loginNameCode) && loginNameCode.equals(loginCode)) {
                            loginCode = toSerialCode();
                            break;
                        }
                    }
                    if (loginName.equals(loginCode)) {
                        b = false;
                    }
                }

                customer.setCountry(commercialArea.getCountry());
                customer.setCreateId(user.getId());
                customer.setLoginName(loginName);
                if (StringUtils.isEmpty(customer.getPhoneNumber())) {
                    customer.setPhoneNumber(loginName);
                }
            }
            customerMapper.insert(customer);

            // TODO ????????????????????????????????????????????????
            CustomerMoney customerMoney = new CustomerMoney();
            customerMoney.setCustomerId(code);
            customerMoney.setCustomerName(customer.getCustomerName() == null ?
                    customer.getLoginName() : customer.getCustomerName());
            moneyMapper.insertSelective(customerMoney);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("customer", customer);

            WarehouseAddress warehouseAddress = warehouseAddressMapper.getIsEnableAddress(commercialArea.getId());
            if(warehouseAddress == null){
                resultMap.put("warehouseAddress", "");
                log.info("??????????????????, ????????????????????????????????? -------<<<<<<<");
                return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
            }

            String addressee = warehouseAddress.getAddressee().replaceAll("LOGIN_NAME", customer.getLoginName());
            String address = warehouseAddress.getAddress().replaceAll("LOGIN_NAME", customer.getLoginName());

            warehouseAddress.setAddressee(addressee);
            warehouseAddress.setAddress(address);

            resultMap.put("warehouseAddress", warehouseAddress);

            log.info("??????????????????-------<<<<<<<");
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            log.error("???????????????????????????:", e);
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }

    @Override
    public int update(Customer customer) {

        return customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    @Transactional
    public int del(String customerNo, String usercode) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("??????????????????");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("customerNo", customerNo);
            List<GoodsVo> goodsList = goodsMapper.find(map);
            //??????????????????????????????????????????????????????????????????
            if (!goodsList.isEmpty()) {
                return -1;
            }
            User user = userMapper.findByCode(usercode);

            // ??????????????????
            Customer customer = customerMapper.findByCustomerNo(customerNo);
            // ????????????id??????????????????
            if (customer != null) {
                moneyMapper.deleteByCustomerId(customer.getCustomerNo());
            }
            customerMapper.del(customerNo, user.getId());

            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return 0;
        }


    }

    @Override
    public ResponseEntity<PageResultModel> findAll(CustomerReqVo customerReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }

        // ????????????????????????????????????????????????????????????
        if(user.getCommercialAreaId() != 1){
            customerReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        PageData pageData = PageHelp.editPage(customerReqVo);
        int count = customerMapper.countSelectAll(customerReqVo);
        pageData.setTotal(count);
        if (count > 0) {
            customerReqVo.setPageNumber(pageData.getPageNumber());
            List<Customer> customerList = customerMapper.selectAll(customerReqVo);
            return new ResponseEntity<>(PageResultModel.ok(customerList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public List<Customer> find(Map<String, Object> map) {
        return customerMapper.find(map);
    }

    @Override
    public Customer findById(int id) {
        return customerMapper.findById(id);
    }

    @Override
    public int resetPassword(int customerId) {
        Customer customer = customerMapper.findById(customerId);
        BASE64Encoder encoder = new BASE64Encoder();
        customer.setPassword(encoder.encode("000000".getBytes()));
        return customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public Customer getCustomerByLoginName(String loginName) {
        return customerMapper.getCustomerByLoginName(loginName);
    }

    @Override
    public ResponseEntity<PageResultModel> getNotInGroupCustomerList(Map<String, Object> map) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // ????????????????????????????????????????????????????????????
        if(user.getCommercialAreaId() != 1){
            map.put("commercialAreaId", user.getCommercialAreaId());
//            sysCouponsReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        long startTime = new Date().getTime();
        PageData pageData = PageHelp.editPage(map.get("pageNumber").toString(), map.get("pageSize").toString());
        List<CustomerGroupLink> customerGroupLinkList = customerGroupLinkMapper.selectByGroupId(map.get("groupId").toString());
        List<Integer> inGroupCustomerIdList = null;
        if (customerGroupLinkList != null && !customerGroupLinkList.isEmpty()) {
            inGroupCustomerIdList = customerGroupLinkList.stream().map(CustomerGroupLink::getCustomerId).collect(Collectors.toList());
        }
        int count = customerMapper.getNotInGroupCustomerCount(map, inGroupCustomerIdList);
        pageData.setTotal(count);
        if (count > 0) {
            List<Customer> customerList = customerMapper.getNotInGroupCustomerList(map, inGroupCustomerIdList,
                    pageData.getPageNumber(), Integer.valueOf(map.get("pageSize").toString()));
            System.out.println("???????????????" + (new Date().getTime() - startTime));
            return new ResponseEntity<>(PageResultModel.ok(customerList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }


    @Override
    public void generateCustomerPoster(int id, String posterType,HttpServletRequest request, HttpServletResponse response) {
        String posterPath = "";
        String qrcodePath = "";
        try {

            ActivityPoster activityPoster = activityPosterMapper.selectEnablePoster(posterType);
            if(activityPoster == null){
                return;
            }

            String dateStr = DateUtil.getDateFormate(new Date(), "yyyy-MM"); // ?????????????????????

            String timeStr = DateUtil.getDateFormate(new Date(), "ddHHmmss"); // ?????????????????????

            Customer customer = customerMapper.findById(id);

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
                String content = "";
                // ????????????????????????????????????????????????
                switch (posterType){
                    case "sharePoster" :
                        content = "share_" + customer.getLoginName();
                        break;
                    case "bindingPoster" :
                        content = "binding_" + customer.getLoginName();
                        break;
                }
                ImgUtil.generateWeChatQrcode(QRPath, accessToken, content, qrcodePath, multiple);
            }

            String fileName = customer.getLoginName()+ "_" +  timeStr + ".jpg";

            posterPath = fileLocation + "/" + fileName;
            // ????????????
            ImgUtil.posterImage(activityPoster, qrcodePath, posterPath);

            // ??????????????????
            // posterPath = DOMAIN + "/" + posterPath.substring(4, posterPath.length());
            FileUtil.downloadPoster(fileName, posterPath, request, response);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            File qrFile = new File(qrcodePath);
            qrFile.delete();
            File posterFile = new File(posterPath);
            posterFile.delete();
        }
    }

    @Override
    public ResponseEntity<PageResultModel> getPopularizeDetailByCustomerId(PopularizeDetailReqVo popularizeDetailReqVo) {
        PageData pageData = PageHelp.editPage(popularizeDetailReqVo);
        int count = customerFromMapper.selectPopularizeDetailCount(popularizeDetailReqVo);
        pageData.setTotal(count);
        if (count > 0) {
            popularizeDetailReqVo.setPageNumber(pageData.getPageNumber());

            List<PopularizeDetailRespVo> popularizeDetailRespVoList = customerFromMapper.selectPopularizeDetailByCustomerId(popularizeDetailReqVo);

            return new ResponseEntity<>(PageResultModel.ok(popularizeDetailRespVoList, pageData), HttpStatus.OK);
        }

        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer subscribe(WXMessageBean wxMessageBean) {
        String openID = wxMessageBean.getFromUserName();
        Customer customer = customerMapper.selectByOpenid(openID);

        if (customer == null) {
            System.out.println("??????????????????????????????");
            String access_token = wxAccessTokenMapper.getAccessToken();
            customer = getWeChatUserMessage(openID, access_token);
            customer.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:dd"));
            insertCustomerInfo(customer);

            customer = customerMapper.selectByOpenid(openID);
            insertCustomerMoney(customer);
            // ?????????????????????????????????????????????????????????
            // ???????????????????????????????????????????????????????????????,?????????????????????????????????
            if (wxMessageBean.getEventKey().contains("qrscene_share_") || wxMessageBean.getEventKey().contains("share_")) {
                insertCustomerFrom(wxMessageBean, customer);
            }else{
                // ????????????
                List<ActivityReward> subscribeRewardList = activityRewardMapper.selectByActivityType("subscribeGiving");
                if(!CollectionUtils.isEmpty(subscribeRewardList)){
                    sendReward(customer, subscribeRewardList, null);
                }
            }
        }
        return customer;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer bindingCustomer(WXMessageBean wxMessageBean) {
        String openID = wxMessageBean.getFromUserName();

        String loginName = wxMessageBean.getEventKey().split("qrscene_binding_")[1];
        Customer customer = customerMapper.getCustomerByLoginName(loginName);

        // ???????????????????????????
        customer.setOpenid(openID);
        customerMapper.updateByPrimaryKey(customer);

        List<ActivityReward> subscribeRewardList = activityRewardMapper.selectByActivityType("subscribeGiving");
        if(!CollectionUtils.isEmpty(subscribeRewardList)){
            sendReward(customer, subscribeRewardList, null);
        }

        return customer;
    }



    @Override
    public ResponseEntity<PageResultModel> getIntegralsDetailByCustomerId(CustomerReqVo customerReqVo) {
        PageData pageData = PageHelp.editPage(customerReqVo);
        int count = customerIntegralMapper.getIntegralsCountByCustomerId(customerReqVo);
        HashMap<String, Object> resultMap = new HashMap<>();
        List<CustomerIntegral>  customerIntegralList = new ArrayList<>();
        double haveObtained = customerIntegralMapper.getIntegralsHaveObtainedByCustomerId(customerReqVo);

        double usedIntegrals = customerIntegralMapper.getIntegralsUsedByCustomerId(customerReqVo);

        double nowIntegrals = customerIntegralMapper.selectIntegralCount(customerReqVo.getId());
        resultMap.put("nowIntegrals", nowIntegrals);
        resultMap.put("haveObtained", haveObtained);
        resultMap.put("usedIntegrals", usedIntegrals);
        resultMap.put("customerIntegrals", customerIntegralList);
        pageData.setTotal(count);
        if (count > 0) {
            customerReqVo.setPageNumber(pageData.getPageNumber());
            customerIntegralList = customerIntegralMapper.getIntegralsDetailByCustomerId(customerReqVo);
            resultMap.put("customerIntegrals", customerIntegralList);
            return new ResponseEntity<>(PageResultModel.ok(resultMap, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(resultMap, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCustomerIntegrals(int customerId) {
        double integrals = customerIntegralMapper.selectIntegralCount(customerId);
        CustomerIntegralRule deductibleRoute = customerIntegralRuleMapper.getIntegralRuleByIntegralType("deductible");

        return new ResponseEntity<>(ResultModel.ok(integrals), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> copyWareAddress(int customerId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        Integer commercialAreaId = user.getCommercialAreaId();
        WarehouseAddress warehouseAddress = warehouseAddressMapper.getIsEnableAddress(commercialAreaId);
        if(warehouseAddress == null){
            return new ResponseEntity<>(ResultModel.error(ENABLE_WAREHOUSE_ADDRESS_NULL_ERROR), HttpStatus.OK);
        }
        Customer customer = customerMapper.findById(customerId);
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(CUSTOMER_NULL_ERROR), HttpStatus.OK);
        }
        String addressee = warehouseAddress.getAddressee().replaceAll("LOGIN_NAME", customer.getLoginName());
        String address = warehouseAddress.getAddress().replaceAll("LOGIN_NAME", customer.getLoginName());
        String content = "????????????" + addressee
                + "\n\t ?????????" + warehouseAddress.getPhoneNumber()
                + "\n\t ?????????" + warehouseAddress.getPostcode()
                + "\n\t ?????????" + address;
        return new ResponseEntity<>(ResultModel.ok(content), HttpStatus.OK);
    }

    private void insertCustomerFrom(WXMessageBean wxMessageBean, Customer customer) {
        String customerLoginName;
        if(wxMessageBean.getEventKey().contains("qrscene_share_")){
            customerLoginName = wxMessageBean.getEventKey().split("qrscene_share_")[1];
        }else {
            customerLoginName = wxMessageBean.getEventKey().split("share_")[1];
        }
        Customer fromCustomer = customerMapper.getCustomerByLoginName(customerLoginName);

        CustomerFrom customerFrom = new CustomerFrom();
        customerFrom.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        customerFrom.setCustomerId(customer.getId());
        customerFrom.setFromCustomerId(fromCustomer.getId());
        // ??????????????????????????????????????????WeChat
        customerFrom.setFromType("WeChat");
        customerFrom.setStatus(1);
        customerFrom.setVersion(1);
        customerFromMapper.insertSelective(customerFrom);

        // ??????????????????
        // ??????????????????
        List<ActivityReward> scanCodeRewardList = activityRewardMapper.selectByActivityType("scanCode");
        if (!CollectionUtils.isEmpty(scanCodeRewardList)) {
            sendReward(customer, scanCodeRewardList, null);
        }

        // ????????????????????????
        List<ActivityReward> toPromoteReward = activityRewardMapper.selectByActivityType("toPromote");
        if (!CollectionUtils.isEmpty(toPromoteReward)) {
            sendReward(fromCustomer, toPromoteReward, customer.getId());
        }

    }

    /**
     * ????????????????????????
     *
     * @param customer           ??????
     * @param activityRewardList ????????????
     * @param formId             ????????????ID
     */
    private void sendReward(Customer customer, List<ActivityReward> activityRewardList, Integer formId) {
        List<ActivityReward> couponsRewardList = new ArrayList<>();
        List<ActivityReward> integralRewardList = new ArrayList<>();
        for (ActivityReward activityReward : activityRewardList) {
            if (activityReward.getRewardType().equals("coupons")) {
                couponsRewardList.add(activityReward);
            } else {
                integralRewardList.add(activityReward);
            }
        }

        if (!CollectionUtils.isEmpty(couponsRewardList)) {
            List<Integer> couponsIdList = couponsRewardList.stream().map(ActivityReward::getRewardContentId).collect(Collectors.toList());
            // ???????????????
            List<SysCoupons> sysCouponsList = sysCouponsMapper.getCouponsByIds(couponsIdList);
            if (!CollectionUtils.isEmpty(sysCouponsList)) {
                List<CustomerCoupons> customerCouponsList = new ArrayList<>();
                for (SysCoupons sysCoupons : sysCouponsList) {
                    CustomerCoupons customerCoupons = new CustomerCoupons();
                    customerCoupons.setStatus(1);
                    customerCoupons.setVersion(1);
                    customerCoupons.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                    customerCoupons.setGiveId(-1);
                    customerCoupons.setCustomerId(customer.getId());
                    customerCoupons.setCenterCouponsId(-1);
                    customerCoupons.setCustomerGroupId(-1); //??????????????????????????????????????????-1
                    customerCoupons.setSysCouponsId(sysCoupons.getId());
                    customerCoupons.setCouponsName(sysCoupons.getCouponsName());
                    customerCoupons.setCouponsStatus(0);
                    customerCoupons.setCouponsType(sysCoupons.getCouponsType());
                    customerCoupons.setCouponsAmount(sysCoupons.getCouponsAmount());
                    customerCoupons.setMinimumAmount(sysCoupons.getMinimumAmount());
                    if (sysCoupons.getOverdueType() == 1) {
                        customerCoupons.setTimeOverdue(sysCoupons.getTimeOverdue());
                    } else {
                        String overdueTime =
                                DateUtil.FormatDate(DateUtil.getSpecifiedDayAfter(new Date(), sysCoupons.getTimeLimit()), "yyyy-MM-dd");
                        customerCoupons.setTimeOverdue(overdueTime);
                    }
                    customerCouponsList.add(customerCoupons);
                }

                if (!CollectionUtils.isEmpty(customerCouponsList)) {
                    customerCouponsMapper.insertList(customerCouponsList);
                }

                // ????????????????????????
                List<Customer> customerList = new ArrayList<>();
                customerList.add(customer);
                for (SysCoupons sysCoupons : sysCouponsList) {
                    // ??????????????????????????????
                    sendMessageServer.sengMeesageForCustomerSendCoupons(customerList, sysCoupons, 1);
                }
            }
        }

        if (!CollectionUtils.isEmpty(integralRewardList)) {
            // ????????????
            List<Integer> integralIdList = couponsRewardList.stream().map(ActivityReward::getRewardContentId).collect(Collectors.toList());
            List<CustomerIntegralRule> customerIntegralRuleList = customerIntegralRuleMapper.getIntegralRuleByIds(integralIdList);
            // ?????????????????????????????????????????????????????????
            if (!CollectionUtils.isEmpty(customerIntegralRuleList)) {
                List<CustomerIntegral> customerIntegralList = new ArrayList<>();

                for (CustomerIntegralRule customerIntegralRule : customerIntegralRuleList) {
                    double integrals = customerIntegralRule.getIntegrals();
                    CustomerIntegral customerIntegral = new CustomerIntegral();
                    customerIntegral.setCustomerId(customer.getId());
                    customerIntegral.setFromId(formId);
                    customerIntegral.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    customerIntegral.setStatus(1);
                    customerIntegral.setIntegralType("popularize");
                    customerIntegral.setIntegrals(integrals);
                    customerIntegralList.add(customerIntegral);
                }

                if (!CollectionUtils.isEmpty(customerIntegralList)) {
                    customerIntegralMapper.insertList(customerIntegralList);
                }

            }

        }
    }


    private Customer getWeChatUserMessage(String openid, String access_token) {

        JSONObject jsonObject = getUserInfoByOpenid(openid, access_token);
        log.info("?????????????????????{}", jsonObject);
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
     *
     * @param customer
     */
    private void insertCustomerInfo(Customer customer) {
//        List<Customer> customerList = customerMapper.getAllCustomer();
        List<String> loginNameList = customerMapper.getAllCustomerLoginName();
        String loginCode = toSerialCode();
        String loginName = loginCode;
        boolean b = true;
        while (b) {
            for (String loginNameCode : loginNameList) {
                if (!StringUtils.isEmpty(loginNameCode) && loginNameCode.equals(loginCode)) {
                    loginCode = toSerialCode();
                    break;
                }
            }
            if (loginName.equals(loginCode)) {
                b = false;
            }
        }
        // ???????????????????????????????????????
        if (StringUtils.isEmpty(customer.getCustomerName())) {
            customer.setCustomerName(loginName);
        }

        for (String loginNameCode : loginNameList) {
            // ??????
            if (!StringUtils.isEmpty(loginNameCode) && loginNameCode.equals(customer.getCustomerName())) {
                customer.setCustomerName(customer.getCustomerName() + "_1");
            }
        }

        customer.setLoginName(loginName);
        customer.setStatus(1);
        customer.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

        // ??????????????????????????????????????????????????????????????????
        customer.setPhoneNumber(loginName);

        customerMapper.insert(customer);
    }

    /**
     * ????????????????????????
     *
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

    /**
     * ??????openid??????????????????
     *
     * @param openid
     * @param token
     * @return
     */
    public JSONObject getUserInfoByOpenid(String openid, String token) {
        String requestUrl = USERINFO_URL.replace("OPENID", openid).replace("ACCESS_TOKEN", token);
        String jsonObject = loadJSON(requestUrl);
        JSONObject object = JSONObject.parseObject(jsonObject);
        if (object.containsKey("errcode")) {
            if ("40001".equals(object.get("errcode").toString()) || "42001".equals(object.get("errcode").toString())) {
                log.error("access_token?????????????????????????????????");
                String token_result = loadJSON(TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET));
                JSONObject result = JSONObject.parseObject(token_result);
                WxAccessToken wxAccessToken = new WxAccessToken();
                wxAccessToken.setAccessToken(result.get("access_token").toString());
                wxAccessToken.setExpire(result.get("expires_in").toString());

                wxAccessTokenMapper.insert(wxAccessToken);
                requestUrl = USERINFO_URL.replace("OPENID", openid).replace("ACCESS_TOKEN", result.get("access_token").toString());
                jsonObject = loadJSON(requestUrl);
                object = JSONObject.parseObject(jsonObject);
            }
        }
        return object;
    }

    /**
     * ??????JSONObject.get ( key)???????????????json??????????????????
     *
     * @param url ????????????
     * @return
     */
   public String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

}
