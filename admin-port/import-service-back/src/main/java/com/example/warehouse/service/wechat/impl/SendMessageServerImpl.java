package com.example.warehouse.service.wechat.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.HttpUtilManager;
import com.example.warehouse.entity.*;
import com.example.warehouse.entity.annualReport.AnnualReport;
import com.example.warehouse.entity.coupons.CustomerCoupons;
import com.example.warehouse.entity.coupons.SysCoupons;
import com.example.warehouse.entity.ping.PingMain;
import com.example.warehouse.entity.sys.TrajectoryLog;
import com.example.warehouse.entity.wechat.WechatMessageTemplate;
import com.example.warehouse.entity.wx.WechatMessage;
import com.example.warehouse.mapper.CustomerAccountMapper;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.annualReport.AnnualReportMapper;
import com.example.warehouse.mapper.coupons.CustomerCouponsMapper;
import com.example.warehouse.mapper.coupons.SysCouponsMapper;
import com.example.warehouse.mapper.customerMoney.CustomerMoneyMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.transportationRoute.TransportationRouteMapper;
import com.example.warehouse.mapper.user.WxAccessTokenMapper;
import com.example.warehouse.mapper.wx.WechatMessageMapper;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.route.RouteVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SendMessageServerImpl implements SendMessageServer {

    @Value("${wechat.appid}")
    private String APPID;

    @Value("${wechat.appsecret}")
    private String APPSECRET;

    @Value("${wechat.template_url}")
    private String TEMPLATE_URL;

    /**
     * ???????????????id
     */
    @Value("${wechat.payment_id}")
    private String PAYMENT_ID;

    /**
     * ????????????????????????
     */
    @Value("${wechat.apply_pack_id}")
    private String APPLE_PACK_ID;

    /**
     *  ????????????id
     */
    @Value("${wechat.pacakge_in_id}")
    private String PACKAGE_IN_ID;

    /**
     * ???????????????url
     */
    @Value("${wechat.payment_url}")
    private String PAYMENT_URL;

    /**
     * ??????????????????id
     */
    @Value("${wechat.pin_type_chanage_id}")
    private String PINTYPECHANGEID;

    /**
     * ??????????????????
     */
    @Value("${wechat.after_sales}")
    private String AFTERSALES;

    /**
     * ???????????????????????????
     */
    @Value("${wechat.pin_detail_url}")
    private String PINDETAILURL;

    // ??????????????????
    @Value("${wechat.trajectory_url}")
    private String TRAJECTORYURL;

    /**
     * ????????????url
     */
    @Value("${wechat.package_pack_url}")
    private String PACKAGE_PACK_URL;

    /**
     * ?????????????????????id
     */
    @Value("${wechat.topup_success_id}")
    private String YOPUP_SUCCESS_ID;

    /**
     * ????????????
     */
    @Value("${wechat.warehouse_name}")
    private String WAREHOUSE_NAME;

    /**
     * ????????????????????????
     */
    @Value("${wechat.goods_overtime}")
    private String GOODS_OVERTIME;

    /**
     * ???????????????
     */
    @Value("${wechat.name}")
    private String WECHAT_NAME;

    @Autowired
    private WxAccessTokenMapper accessTokenMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerMoneyMapper moneyMapper;
    @Autowired
    private CustomerMoneyMapper customerMoneyMapper;
    @Autowired
    private CustomerAccountMapper customerAccountMapper;
    @Autowired
    private WechatMessageMapper wechatMessageMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private AnnualReportMapper annualReportMapper;

    @Autowired
    private TransportationRouteMapper routeMapper;

    @Autowired
    private SysCouponsMapper sysCouponsMapper;
    @Autowired
    private CustomerCouponsMapper customerCouponsMapper;

    @Override
    public void sendMessageForPayment() {
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        httpUtil.post(TEMPLATE_URL + "token=" + httpUtil,"");
    }

    /**
     *
     * @param customerPack ????????????
     * @param packNum ????????????
     */
    @Override
    public void sendMessageForPackage(CustomerPack customerPack,int packNum) {
        Customer customer = customerMapper.findById(customerPack.getCustomerId());
        if ( customer.getOpenid() == null || "".equals(customer.getOpenid()) ){
            return;
        }
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        messageTemplate.setTouser(customer.getOpenid());
        messageTemplate.setTemplate_id(PAYMENT_ID);
        messageTemplate.setTopcolor("#FF0000");
        messageTemplate.setUrl(PAYMENT_URL);

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject keyword3 = new JSONObject();
        JSONObject keyword4 = new JSONObject();
        JSONObject remark = new JSONObject();

        first.put("value","?????????????????????\n?????????????????????????????????????????????????????????");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",customerPack.getOrderNumber());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        BigDecimal b = new BigDecimal(customerPack.getActualWeight());
        keyword2.put("value",b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "KG");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);
        String actualPrice = String.valueOf(customerPack.getActualPrice());
        if(customerPack.getTariffsPrice() != null && customerPack.getTariffsPrice().compareTo(new BigDecimal(0)) > 0){
            // ?????????????????????????????????????????????????????????
            // BigDecimal price = customerPack.getActualPrice().add(customerPack.getTariffsPrice());
            actualPrice = actualPrice + "(????????????)";
        }
        keyword3.put("value","???"+actualPrice);
        keyword3.put("color","#173177");
        object.put("keyword3",keyword3);

        keyword4.put("value","??????????????????");
        keyword4.put("color","#173177");
        object.put("keyword4",keyword4);
        remark.put("value","?????????"+ customerPack.getGoodsSum() +"?????????,?????????" + packNum +"???/??? " + customerPack.getRemarks() );

        remark.put("color","#fc1303");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        System.out.println("jsonString???");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("???????????????????????????????????????");
        System.out.println(result);
    }

    @Override
    public void sendMessageForShipped() {
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        httpUtil.post(TEMPLATE_URL + "token=" + httpUtil,"");
    }

    @Override
    public void sendMessageForWareIn(Goods goods) {
        Customer customer = customerMapper.findByCustomerNo(goods.getCustomerNo());
        if (customer == null || customer.getOpenid() == null || "".equals(customer.getOpenid()) ){
            return;
        }
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        messageTemplate.setTouser(customer.getOpenid());
        messageTemplate.setTemplate_id(PACKAGE_IN_ID);
        messageTemplate.setTopcolor("#FF0000");
        messageTemplate.setUrl(PACKAGE_PACK_URL);

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();

        first.put("value","????????????????????????????????????????????????\n????????????:" + goods.getGoodsName() + "\n????????????:"+ goods.getDeliveryOrderNo() +
            "\n??????:" + goods.getKg() + " kg" +
            "\n??????:" + goods.getLength() + "cm * " + goods.getWidth() + "cm * " + goods.getHeight() + "cm" +
            "\n?????????????????????????????????????????????????????????");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",WAREHOUSE_NAME);
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        keyword2.put("value",DateUtil.timestamp2String(new Date()));
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);


        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        System.out.println("jsonString???");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("???????????????????????????????????????");
        System.out.println(result);
    }
    /**
     * ????????????????????????
     * @param pin ????????????
     * @param isPassed ?????????????????????1??? 2???
     */
    @Override public void sendMessageForCheckPin(PingMain pin, int isPassed) {
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        Customer customer = customerMapper.selectByPrimaryKey(pin.getLeaderId());
        if ( customer.getOpenid() == null ){
            System.out.println("????????????openid");
            return;
        }
        messageTemplate.setTouser(customer.getOpenid());
        messageTemplate.setTemplate_id(PINTYPECHANGEID);
        messageTemplate.setTopcolor("#FF0000");
        messageTemplate.setUrl(PINDETAILURL);

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject remark = new JSONObject();

        first.put("value","?????????????????????\n???????????????" + pin.getPinName() + "???????????????");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",pin.getOrderNumber());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        String passmessage = "????????????";
        String checkMessage = "????????????????????????????????????????????????";
        if ( isPassed == 1 ){
            passmessage = "???????????????";
            checkMessage = "?????????";
        }

        keyword2.put("value",passmessage);
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        remark.put("value",checkMessage +  pin.getCheckMessage());
        remark.put("color","#173177");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        System.out.println("jsonString???");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("?????????"+result);
    }

    @Override public void sendMessageForTopUp(CustomerAccount customerAccount, String message) {
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        Customer customer = customerMapper.selectByPrimaryKey(Integer.valueOf(customerAccount.getCustomerNo()));
        if ( customer.getOpenid() == null ){
            System.out.println("????????????openid");
            return;
        }
        messageTemplate.setTouser(customer.getOpenid());
        messageTemplate.setTemplate_id(YOPUP_SUCCESS_ID);
        messageTemplate.setTopcolor("#FF0000");

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject keyword3 = new JSONObject();
        JSONObject keyword4 = new JSONObject();
        JSONObject remark = new JSONObject();

        CustomerMoney money = moneyMapper.selectByCustomerNo(customer.getCustomerNo());
        first.put("value","????????????????????????");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",customerAccount.getAmount().setScale(2, BigDecimal.ROUND_CEILING) + "");
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        keyword2.put("value","0.00");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        keyword3.put("value",money.getAmount().setScale(2, BigDecimal.ROUND_CEILING) + "");
        keyword3.put("color","#173177");
        object.put("keyword3",keyword3);

        keyword4.put("value",DateUtil.timestamp2String(new Date()));
        keyword4.put("color","#173177");
        object.put("keyword4",keyword4);

        remark.put("value",message);
        remark.put("color","#fc1303");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        System.out.println("jsonString???");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("?????????"+result);

    }



    @Override public void sendMessageForNoOrderCustomer() {
        List<Customer> customerList = customerMapper.getNoOrderCustomer();
//        Customer c = customerMapper.findByCustomerNo("CUM2021010822220100000");
        // ???????????????????????????(???????????????????????????)
//        SysCoupons sysCoupons = sysCouponsMapper.selectByCouponsCode("sysc1060");
        for ( Customer c : customerList ){
            WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

            if ( c.getOpenid() == null ){
                System.out.println("????????????openid");
                continue;
            }
            messageTemplate.setTouser(c.getOpenid());
            messageTemplate.setTemplate_id(YOPUP_SUCCESS_ID);
            messageTemplate.setTopcolor("#FF0000");

            JSONObject object = new JSONObject();
            JSONObject first = new JSONObject();
            JSONObject keyword1 = new JSONObject();
            JSONObject keyword2 = new JSONObject();
            JSONObject keyword3 = new JSONObject();
            JSONObject keyword4 = new JSONObject();
            JSONObject remark = new JSONObject();

//            CustomerMoney money = moneyMapper.selectByCustomerNo(c.getCustomerNo());

            // ???????????????
//            CustomerAccount customerAccount = new CustomerAccount();
//            customerAccount.setCustomerNo(c.getId().toString());
//            customerAccount.setCustomerName(c.getCustomerName());
//            customerAccount.setCostType("1");//??????
//            customerAccount.setCreateName("??????");
//            customerAccount.setAmount(new BigDecimal(20));
//            customerAccount.setCreateTime(DateUtil.timestamp2String(new Date()));
//            customerAccount.setStatus(1);
//            customerAccount.setLoginName(c.getLoginName());
//            customerAccount.setChannel("6");//????????????  ???????????????????????? 1?????????   2????????????   3???????????????   4?????????  5????????? 6???????????? 7:????????? 8?????????
//            String message = "--????????????--";
//            customerAccount.setMessage(message);
//            customerAccount.setCurrency("?????????");//?????????
//            customerAccount.setVersion("1");

            // ??????????????? sysCoupons
//            CustomerCoupons customerCoupons = new CustomerCoupons();
//            customerCoupons.setVersion(1);
//            customerCoupons.setStatus(1);
//            customerCoupons.setCreateTime(DateUtil.timestamp2String(new Date()));
//
//            customerCoupons.setGiveId(-1); // ?????????
//            customerCoupons.setCustomerGroupId(-1); // ????????????id
//            customerCoupons.setCenterCouponsId(-1); // ????????????id
//
//            // ??????????????? 0???????????? 1???????????? 2????????????
//            customerCoupons.setCouponsStatus(0);
//
//            customerCoupons.setCustomerId(c.getId());
//            customerCoupons.setSysCouponsId(sysCoupons.getId());
//            customerCoupons.setCouponsName(sysCoupons.getCouponsName());
//            customerCoupons.setCouponsAmount(sysCoupons.getCouponsAmount());
//            customerCoupons.setMinimumAmount(sysCoupons.getMinimumAmount());
//            customerCoupons.setCouponsType(sysCoupons.getCouponsType());
//            // ??????????????? 1???????????????????????? 2?????????????????????
//            if(sysCoupons.getOverdueType() == 1){
//                customerCoupons.setTimeOverdue(sysCoupons.getTimeOverdue());
//            }else{
//                String overdueTime =
//                    DateUtil.FormatDate(DateUtil.getSpecifiedDayAfter(new Date(), sysCoupons.getTimeLimit()), "yyyy-MM-dd");
//                customerCoupons.setTimeOverdue(overdueTime);
//            }
//            customerCouponsMapper.insertSelective(customerCoupons);
//            // ?????????????????????
//
//
//            // ???????????????
            CustomerMoney customerMoney = new CustomerMoney();
            customerMoney = customerMoneyMapper.selectByCustomerNo(c.getCustomerNo());
            if ( customerMoney == null ){
                // ???????????????????????????????????????????????????????????????????????????????????????????????????
                customerMoney = new CustomerMoney();
                customerMoney.setStatus(1);
                customerMoney.setVersion(1);
                customerMoney.setCustomerId(c.getCustomerNo());
                customerMoney.setCustomerName(c.getCustomerName());
                customerMoney.setAmount(new BigDecimal(0));
                customerMoneyMapper.insert(customerMoney);
            }
//            customerMoney.setAmount(customerMoney.getAmount() == null ? new BigDecimal(20) : customerMoney.getAmount().add(new BigDecimal(20)));
//            customerMoney.setLastUpdateTime((DateUtil.timestamp2String(new Date())));
//
//            customerAccountMapper.insert(customerAccount);
//            customerMoneyMapper.updateByPrimaryKeySelective(customerMoney);

            first.put("value","??????????????????618?????????");
            first.put("color","#173177");
            object.put("first",first);

            keyword1.put("value","20.00(?????????)7???31????????????");
            keyword1.put("color","#173177");
            object.put("keyword1",keyword1);

            keyword2.put("value","0.00");
            keyword2.put("color","#173177");
            object.put("keyword2",keyword2);

            keyword3.put("value",customerMoney.getAmount().setScale(2, BigDecimal.ROUND_CEILING) + "");
            keyword3.put("color","#173177");
            object.put("keyword3",keyword3);

            keyword4.put("value",DateUtil.timestamp2String(new Date()));
            keyword4.put("color","#173177");
            object.put("keyword4",keyword4);

            remark.put("value","???????????????????????????618?????????");
            remark.put("color","#fc1303");
            object.put("remark",remark);

            messageTemplate.setData(object);

            String jsonString = JSONObject.toJSONString(messageTemplate);
            System.out.println("jsonString???");
            System.out.println(jsonString);
            messageTemplate.setData(object);
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String token = accessTokenMapper.getAccessToken();
            String url = TEMPLATE_URL + token;
            String result = httpUtil.post(url,jsonString);

            WechatMessage wechatMessage = new WechatMessage();
            wechatMessage.setCreateTime(new Date());
            wechatMessage.setCustomerNo(c.getCustomerNo());
            wechatMessage.setStatus(1);
            wechatMessage.setRequestParam(result);
            wechatMessage.setType("no_order_20220530_ad");
            wechatMessage.setMessage("20220530?????????????????????");
            wechatMessageMapper.insert(wechatMessage);

            System.out.println("?????????"+result);
        }
    }

    /**
     * ???????????????
     * @param customerList
     * @param sysCoupons
     * @param isSend
     */
    @Override public void sengMeesageForCustomerSendCoupons(List<Customer> customerList, SysCoupons sysCoupons,int isSend) {
//        List<Customer> customerList = customerMapper.getMonthyNoOrderCustomers();
//        List<Customer> customerList = customerMapper.getCusomterToSend30();
//                Customer c = customerMapper.findByCustomerNo("CUM2021010822220100000");

        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;

        List<WechatMessage> wechatMessageList = new ArrayList<>();
        String message = "??????????????????-???????????????????????????";
        if ( isSend == 2 ){
            message = "??????????????????-????????????????????????";
        }
        for ( Customer c : customerList ) {
            WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

            if (c.getOpenid() == null) {
                System.out.println("????????????openid");
                continue;
            }
            messageTemplate.setTouser(c.getOpenid());
            messageTemplate.setTemplate_id(YOPUP_SUCCESS_ID);
            messageTemplate.setTopcolor("#FF0000");

            JSONObject object = new JSONObject();
            JSONObject first = new JSONObject();
            JSONObject keyword1 = new JSONObject();
            JSONObject keyword2 = new JSONObject();
            JSONObject keyword3 = new JSONObject();
            JSONObject keyword4 = new JSONObject();
            JSONObject remark = new JSONObject();

            first.put("value", "???????????????");
            first.put("color", "#173177");
            object.put("first", first);

            keyword1.put("value", sysCoupons.getCouponsAmount() + "???????????????");
            keyword1.put("color", "#173177");
            object.put("keyword1", keyword1);

            keyword2.put("value", "0.00");
            keyword2.put("color", "#173177");
            object.put("keyword2", keyword2);

            keyword3.put("value", "0.00");
            keyword3.put("color", "#173177");
            object.put("keyword3", keyword3);

            keyword4.put("value", DateUtil.timestamp2String(new Date()));
            keyword4.put("color", "#173177");
            object.put("keyword4", keyword4);

            remark.put("value", WECHAT_NAME + "????????????" + sysCoupons.getCouponsName() + sysCoupons.getCouponsAmount() +"??????????????????????????????~\n?????????" + message);
            remark.put("color", "#fc1303");
            object.put("remark", remark);

            messageTemplate.setData(object);

            String jsonString = JSONObject.toJSONString(messageTemplate);
//            System.out.println("jsonString???");
//            System.out.println(jsonString);
            messageTemplate.setData(object);
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String result = httpUtil.post(url, jsonString);

            WechatMessage wechatMessage = new WechatMessage();
            wechatMessage.setCreateTime(new Date());
            wechatMessage.setCustomerNo(c.getCustomerNo());
            wechatMessage.setStatus(1);
            wechatMessage.setRequestParam(result);
            wechatMessage.setType("sysCoupons");
            wechatMessage.setMessage(sysCoupons.getCouponsName());

            wechatMessageList.add(wechatMessage);
            System.out.println("?????????" + result);
        }
        if(!CollectionUtils.isEmpty(wechatMessageList)){
            wechatMessageMapper.insertList(wechatMessageList);
        }
    }

    @Override public void sengMeesageForCustomerNoOrderBefore() {
        List<AnnualReport> annualReportList = annualReportMapper.getAnnualReportList();
//                Customer c = customerMapper.findByCustomerNo("CUM2021010822220100000");
        for ( AnnualReport report : annualReportList ) {
            WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

            if (report.getOpenid() == null) {
                System.out.println("????????????openid");
                continue;
            }
            messageTemplate.setTouser(report.getOpenid());
            // ????????????????????????id
            messageTemplate.setTemplate_id("chn6MRRVeRj-h730SAuoNUjU_41N4XUBl9122V_pRpk");
            messageTemplate.setTopcolor("#FF0000");
            messageTemplate.setUrl("http://jiyun.flycloudstorage.com/client/jiyun/#/pages/mine/my-report/annual-repoet");

            JSONObject object = new JSONObject();
            JSONObject first = new JSONObject();
            JSONObject keyword1 = new JSONObject();
            JSONObject keyword2 = new JSONObject();
            JSONObject keyword3 = new JSONObject();
            JSONObject remark = new JSONObject();

            first.put("value", "????????????2021??????????????????????????????????????????");
            first.put("color", "#173177");
            object.put("first", first);

            keyword1.put("value", "2021???12???31???");
            keyword1.put("color", "#173177");
            object.put("keyword1", keyword1);

            keyword2.put("value", report.getOrderSum());
            keyword2.put("color", "#173177");
            object.put("keyword2", keyword2);

            keyword3.put("value", String.format("%.2f",report.getConsumptionAmountCount()));
            keyword3.put("color", "#173177");
            object.put("keyword3", keyword3);

            remark.put("value", "??????????????????");
            remark.put("color", "#fc1303");
            object.put("remark", remark);

            messageTemplate.setData(object);

            String jsonString = JSONObject.toJSONString(messageTemplate);
            System.out.println("jsonString???");
            System.out.println(jsonString);
            messageTemplate.setData(object);
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String token = accessTokenMapper.getAccessToken();
            String url = TEMPLATE_URL + token;
            String result = httpUtil.post(url, jsonString);

            WechatMessage wechatMessage = new WechatMessage();
            wechatMessage.setCreateTime(new Date());
            wechatMessage.setCustomerNo(report.getCustomerId()+"");
            wechatMessage.setStatus(1);
            wechatMessage.setRequestParam(result);
            wechatMessage.setType("yearOrder2021");
            wechatMessage.setMessage("??????????????????");
            wechatMessageMapper.insert(wechatMessage);

            System.out.println("?????????" + result);
        }
    }

    @Override
    public void sendMessageForApplyPack(CustomerPack customerPack) {
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        Customer user = customerMapper.selectByPrimaryKey(customerPack.getCustomerId());
        if ( user.getOpenid() == null || "".equals(user.getOpenid()) ){
            return;
        }
        messageTemplate.setTouser(user.getOpenid());
        messageTemplate.setTemplate_id(APPLE_PACK_ID);
        messageTemplate.setTopcolor("#FF0000");
        messageTemplate.setUrl(null);

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject remark = new JSONObject();

        first.put("value","????????????");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",customerPack.getBusinessNumber());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        RouteVo routeVo = routeMapper.findById(customerPack.getTransportationRouteId());

        List<RoutePrice> priceList = routeVo.getRoutePrices();
        String routeMessage = "???????????????????????????(???*???*???)/" ;
        if ( priceList != null && priceList.size() > 0 ){
            double limitVolConversion = 8000;
            if ( routeVo.getVolConversion() == null || routeVo.getVolConversion() > limitVolConversion ){
                routeMessage = "??????????????????";
            }else {
                routeMessage = routeMessage + routeVo.getVolConversion() + ",??????????????????????????????,????????????";
            }
        }

        keyword2.put("value","????????????????????????????????????????????????,????????????????????????" + routeVo.getRouteName() +"???\n"
                + "?????????" + routeMessage);

        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        String weekendMessage = "";
        try {
            if ( DateUtil.isWeekendToday() ){
                weekendMessage = "?????????????????????,???????????????????????????~";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        remark.put("value","??????????????????????????????,?????????????????? " + weekendMessage + "\n?????????????????????????????????????????????????????????????????????????????????????????????" );
        remark.put("color","#173177");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        System.out.println("jsonString???");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("?????????"+result);
    }

    /**
     * ??????????????????????????????
     */
    @Override public void sendMessageForAdviceCustomerToPay() {
        // ??????????????????????????????????????????????????????????????????
//        List<CustomerPack> oneDayPackList = customerPackMapper.getOrdersToPay("1");
        // ??????????????????????????????????????????????????????????????????
        List<CustomerPack> twoDayPackList = customerPackMapper.getOrdersToPay("2");
        // ??????????????????????????????????????????????????????????????????
        List<CustomerPack> sevenDayPackList = customerPackMapper.getOrdersToPay("7");

        // ?????????????????????????????????
//        for ( CustomerPack onePack : oneDayPackList ){
//            Customer customer = customerMapper.findById(onePack.getCustomerId());
//            String paramUrl = sendMessageForCustomerNotPay(onePack,1,customer);
//            saveWechatMessageForCustomerNotPay(onePack,1,paramUrl,customer.getCustomerNo());
//        }
        // ?????????????????????????????????
        for ( CustomerPack twoPack : twoDayPackList ){
            Customer customer = customerMapper.findById(twoPack.getCustomerId());
            String paramUrl = sendMessageForCustomerNotPay(twoPack,2,customer);
            saveWechatMessageForCustomerNotPay(twoPack,2,paramUrl,customer.getCustomerNo());
        }
//        // ?????????????????????????????????
        for ( CustomerPack sevenPack : sevenDayPackList ){
            Customer customer = customerMapper.findById(sevenPack.getCustomerId());
            String paramUrl = sendMessageForCustomerNotPay(sevenPack,7,customer);
            saveWechatMessageForCustomerNotPay(sevenPack,7,paramUrl,customer.getCustomerNo());
        }
    }

    @Override public void sendMessageForGoodsNotOut() {
        // ?????????????????????????????????
        List<Goods> twoMonthList = goodsMapper.getOverTimeInWarehouse("two");
        // ?????????????????????????????????
        List<Goods> fourMonthList = goodsMapper.getOverTimeInWarehouse("four");
        // ?????????????????????????????????
        List<Goods> sixMonthList = goodsMapper.getOverTimeInWarehouse("six");

        for ( Goods goods : twoMonthList ){
            Customer customer = customerMapper.findByCustomerNo(goods.getCustomerNo());
            String paramUrl = sendMessageForGoodsNotOutCustomer(goods,2,customer);
            saveWechatMessageForGoodsNotOut(goods.getGoodsNo(),"tow",customer.getCustomerNo(),paramUrl);
        }

        for ( Goods goods : fourMonthList ){
            Customer customer = customerMapper.findByCustomerNo(goods.getCustomerNo());
            String paramUrl = sendMessageForGoodsNotOutCustomer(goods,2,customer);
            saveWechatMessageForGoodsNotOut(goods.getGoodsNo(),"four",customer.getCustomerNo(),paramUrl);
        }

        for ( Goods goods : sixMonthList ){
            Customer customer = customerMapper.findByCustomerNo(goods.getCustomerNo());
            String paramUrl = sendMessageForGoodsNotOutCustomer(goods,2,customer);
            saveWechatMessageForGoodsNotOut(goods.getGoodsNo(),"six",customer.getCustomerNo(),paramUrl);
        }
    }

    /**
     * ??????????????????????????????--?????????????????????????????????
     * @param customerPack
     * @param i
     * @param paramUrl
     * @param customerNo
     */
    void saveWechatMessageForCustomerNotPay(CustomerPack customerPack,int i,String paramUrl,String customerNo){
        WechatMessage message = new WechatMessage();
        message.setMessage("??????" + i + "????????????");
        String type = "oneDayNotPay";
        if ( i == 2 ){
            type = "twoDayNotPay";
        }
        if ( i == 7 ){
            type = "sevenDayNotPay";
        }
        message.setType(type);
        message.setStatus(1);
        message.setCreateTime(new Date());
        message.setOrderNumber(customerPack.getOrderNumber());
        message.setCustomerNo(customerNo);
        message.setRequestParam(paramUrl);
        wechatMessageMapper.insert(message);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????
     * @param customerPack
     */
    String sendMessageForCustomerNotPay(CustomerPack customerPack,int day,Customer customer){
        if ( customer.getOpenid() == null || "".equals(customer.getOpenid()) ){
            return "";
        }
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        messageTemplate.setTouser(customer.getOpenid());
        messageTemplate.setTemplate_id(PAYMENT_ID);
        messageTemplate.setTopcolor("#FF0000");
        messageTemplate.setUrl(PAYMENT_URL);

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject keyword3 = new JSONObject();
        JSONObject keyword4 = new JSONObject();
        JSONObject remark = new JSONObject();

        first.put("value","?????????????????????\n?????????????????????????????????????????????????????????");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",customerPack.getOrderNumber());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        BigDecimal b = new BigDecimal(customerPack.getActualWeight());
        keyword2.put("value",b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "KG");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        keyword3.put("value","???"+customerPack.getActualPrice());
        keyword3.put("color","#173177");
        object.put("keyword3",keyword3);

        keyword4.put("value","??????????????????");
        keyword4.put("color","#173177");
        object.put("keyword4",keyword4);
        remark.put("value","?????????????????????????????????" + day + "????????????????????????????????????????????????????????????????????????");

        remark.put("color","#fc1303");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);

        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("???????????????????????????????????????");
        System.out.println(result);
        return result;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     * @param goodsNo
     * @param goodsType
     * @param customerNo
     */
    void saveWechatMessageForGoodsNotOut(String goodsNo,String goodsType,String customerNo,String paramUrl){
        WechatMessage message = new WechatMessage();
        String day = "?????????";
        String type = "towmonth_inwarehouse";
        if ( "four".equals(goodsType) ){
            day = "?????????";
            type = "fourmonth_inwarehouse";
        }
        if ( "six".equals(goodsType) ){
            day = "?????????";
            type = "sixmonth_inwarehouse";
        }
        message.setMessage("????????????" + day + "?????????");
        message.setType(type);
        message.setStatus(1);
        message.setCreateTime(new Date());
        message.setOrderNumber(goodsNo);
        message.setCustomerNo(customerNo);
        message.setRequestParam(paramUrl);
        wechatMessageMapper.insert(message);
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????
     * @param goods
     */
    String sendMessageForGoodsNotOutCustomer(Goods goods,int month,Customer customer){
        if ( customer.getOpenid() == null || "".equals(customer.getOpenid()) ){
            return "";
        }
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        messageTemplate.setTouser(customer.getOpenid());
        messageTemplate.setTemplate_id(GOODS_OVERTIME);
        messageTemplate.setTopcolor("#FF0000");

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject remark = new JSONObject();

        int time = 0;
        try {
            time = (int)DateUtil.getDifTwoTime(DateUtil.getSpecifiedDayAfter(DateUtil.string2Timestamp(goods.getInStorageTime()),180),new Date(),"D");
        } catch (Exception e) {
            e.printStackTrace();
        }

        first.put("value","??????????????????" + time + "????????????????????????????????????????????????????????????????????????????????????");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",goods.getDeliveryOrderNo());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        keyword2.put("value",goods.getGoodsName() + "");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        remark.put("value","???????????????" + goods.getInStorageTime()+ "\n????????????????????????180????????????~");
        remark.put("color","#fc1303");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);

        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("???????????????????????????????????????");
        System.out.println(result);
        return result;
    }


    /**
     * ????????????????????????
     * @param singleTrackLog
     * @param trackingMessage ??????????????????
     */
    @Override
    public void sendMessageForTrackUpdate(TrajectoryLog singleTrackLog, String trackingMessage, String orderStatus) {
        List<CustomerPack> customerPackList = customerPackMapper.findByTransshipmentNo(singleTrackLog.getOrderNumber());
        if(customerPackList.isEmpty()){
            log.info("???????????????????????????{}???????????????", singleTrackLog.getOrderNumber());
            return;
        }
        log.info("??????????????????");
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        List<WechatMessage> wechatMessageList = new ArrayList<>();
        for (CustomerPack customerPack : customerPackList) {
            String orderNumber = customerPack.getOrderNumber();

            Customer customer = customerMapper.findById(customerPack.getCustomerId());
            if(customer == null){
                log.info("???????????????????????????{}?????????????????????????????????????????????", singleTrackLog.getOrderNumber());
                continue;
            }
            String openId = customer.getOpenid();

            WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

            if (StringUtils.isEmpty(openId)){
                System.out.println("????????????openid");
                continue;
            }

            messageTemplate.setTouser(openId);
            messageTemplate.setTemplate_id(PINTYPECHANGEID);
            messageTemplate.setTopcolor("#FF0000");
            messageTemplate.setUrl(TRAJECTORYURL + "?orderNumber=" + orderNumber
                    + "&transitNumber=" + singleTrackLog.getOrderNumber());

            JSONObject object = new JSONObject();
            JSONObject first = new JSONObject();
            JSONObject keyword1 = new JSONObject();
            JSONObject keyword2 = new JSONObject();
            JSONObject remark = new JSONObject();

            first.put("value","?????????????????????:\n???????????? " + orderNumber + " ?????????????????????????????????");
            first.put("color","#173177");
            object.put("first",first);

            keyword1.put("value",orderNumber);
            keyword1.put("color","#173177");
            object.put("keyword1",keyword1);

//        String passmessage = "????????????????????????";

            keyword2.put("value",trackingMessage);
            keyword2.put("color","#173177");
            object.put("keyword2",keyword2);

            remark.put("value", "????????????????????????????????????????????????-???????????????????????????");
            remark.put("color","#173177");
            object.put("remark",remark);

            messageTemplate.setData(object);

            String jsonString = JSONObject.toJSONString(messageTemplate);
            messageTemplate.setData(object);
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String result = httpUtil.post(url,jsonString);
            System.out.println("?????????"+result);

            WechatMessage message = new WechatMessage();
            message.setMessage("???"+singleTrackLog.getOrderNumber()+"???????????????:" + trackingMessage);
            String type = "trackingUpdate";
            message.setType(type);
            message.setStatus(1);
            message.setCreateTime(new Date());
            message.setOrderNumber(customerPack.getOrderNumber());
            message.setOrderStatus(orderStatus);
            message.setCustomerNo(customer.getCustomerNo());
            message.setRequestParam(singleTrackLog.getOrderNumber());
            wechatMessageList.add(message);
        }
        if(!CollectionUtils.isEmpty(wechatMessageList)){
            wechatMessageMapper.insertList(wechatMessageList);
        }
    }

    @Override
    public void afterSaleReturn(String orderNumber, Customer customer, String payReturnMessage, String remarks) {
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        if (StringUtils.isEmpty(customer.getOpenid())){
            System.out.println("????????????openid");
            return;
        }

        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;

        messageTemplate.setTouser(customer.getOpenid());
        messageTemplate.setTemplate_id(AFTERSALES);
        messageTemplate.setTopcolor("#FF0000");

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject keyword3 = new JSONObject();
        JSONObject remark = new JSONObject();

        first.put("value","???????????????????????????");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",orderNumber);
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

//        String passmessage = "????????????????????????";

        keyword2.put("value","?????????");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        keyword3.put("value", payReturnMessage);
        keyword3.put("color","#173177");
        object.put("keyword3",keyword3);

        remark.put("value", remarks);
        remark.put("color","#173177");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = httpUtil.post(url,jsonString);
        System.out.println("?????????"+result);

        WechatMessage message = new WechatMessage();
        message.setMessage("???"+orderNumber+"???????????????????????????:" + payReturnMessage);
        String type = "afterSaleReturn";
        message.setType(type);
        message.setStatus(1);
        message.setCreateTime(new Date());
        message.setOrderNumber(orderNumber);
        message.setCustomerNo(customer.getCustomerNo());
        message.setRequestParam(result);
        wechatMessageMapper.insert(message);
    }

    public static void main(String[] args) {
        BigDecimal b = new BigDecimal(246.123123);
        System.out.println(b);
        System.out.println(b.setScale(2, BigDecimal.ROUND_CEILING));
    }
}
