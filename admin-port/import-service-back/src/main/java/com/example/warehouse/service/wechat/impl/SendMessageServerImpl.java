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
     * 待付款提示id
     */
    @Value("${wechat.payment_id}")
    private String PAYMENT_ID;

    /**
     * 申请打包成功提醒
     */
    @Value("${wechat.apply_pack_id}")
    private String APPLE_PACK_ID;

    /**
     *  入库提醒id
     */
    @Value("${wechat.pacakge_in_id}")
    private String PACKAGE_IN_ID;

    /**
     * 待付款页面url
     */
    @Value("${wechat.payment_url}")
    private String PAYMENT_URL;

    /**
     * 拼团变更通知id
     */
    @Value("${wechat.pin_type_chanage_id}")
    private String PINTYPECHANGEID;

    /**
     * 售后回复通知
     */
    @Value("${wechat.after_sales}")
    private String AFTERSALES;

    /**
     * 拼团审核详情页直达
     */
    @Value("${wechat.pin_detail_url}")
    private String PINDETAILURL;

    // 轨迹详情路径
    @Value("${wechat.trajectory_url}")
    private String TRAJECTORYURL;

    /**
     * 申请打包url
     */
    @Value("${wechat.package_pack_url}")
    private String PACKAGE_PACK_URL;

    /**
     * 处理成功的通知id
     */
    @Value("${wechat.topup_success_id}")
    private String YOPUP_SUCCESS_ID;

    /**
     * 仓库名称
     */
    @Value("${wechat.warehouse_name}")
    private String WAREHOUSE_NAME;

    /**
     * 包裹即将到期提醒
     */
    @Value("${wechat.goods_overtime}")
    private String GOODS_OVERTIME;

    /**
     * 公众号名称
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
     * @param customerPack 包裹信息
     * @param packNum 箱子数量
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

        first.put("value","尊敬的客户您好\n您的包裹已经打包好，请及时查看付款发货");
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
            // 预付关税的价格已经计算过，不用再次计算
            // BigDecimal price = customerPack.getActualPrice().add(customerPack.getTariffsPrice());
            actualPrice = actualPrice + "(包含关税)";
        }
        keyword3.put("value","￥"+actualPrice);
        keyword3.put("color","#173177");
        object.put("keyword3",keyword3);

        keyword4.put("value","先付款后发货");
        keyword4.put("color","#173177");
        object.put("keyword4",keyword4);
        remark.put("value","总共有"+ customerPack.getGoodsSum() +"个包裹,打包成" + packNum +"箱/件 " + customerPack.getRemarks() );

        remark.put("color","#fc1303");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        System.out.println("jsonString：");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("打印消息模板通知消息如下：");
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

        first.put("value","尊敬的客户您好，您的包裹已入库：\n包裹名称:" + goods.getGoodsName() + "\n快递单号:"+ goods.getDeliveryOrderNo() +
            "\n重量:" + goods.getKg() + " kg" +
            "\n尺寸:" + goods.getLength() + "cm * " + goods.getWidth() + "cm * " + goods.getHeight() + "cm" +
            "\n可以等待其他包裹到齐后再一并申请打包哦");
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
        System.out.println("jsonString：");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("打印消息模板通知消息如下：");
        System.out.println(result);
    }
    /**
     * 拼团审核进度通知
     * @param pin 拼团详情
     * @param isPassed 是否审核通过：1否 2是
     */
    @Override public void sendMessageForCheckPin(PingMain pin, int isPassed) {
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        Customer customer = customerMapper.selectByPrimaryKey(pin.getLeaderId());
        if ( customer.getOpenid() == null ){
            System.out.println("用户没有openid");
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

        first.put("value","尊敬的客户您好\n您的拼团《" + pin.getPinName() + "》审核进度");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",pin.getOrderNumber());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        String passmessage = "审核通过";
        String checkMessage = "赶紧分享给您的小伙伴一起拼邮吧！";
        if ( isPassed == 1 ){
            passmessage = "审核不通过";
            checkMessage = "原因：";
        }

        keyword2.put("value",passmessage);
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        remark.put("value",checkMessage +  pin.getCheckMessage());
        remark.put("color","#173177");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        System.out.println("jsonString：");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("结果："+result);
    }

    @Override public void sendMessageForTopUp(CustomerAccount customerAccount, String message) {
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        Customer customer = customerMapper.selectByPrimaryKey(Integer.valueOf(customerAccount.getCustomerNo()));
        if ( customer.getOpenid() == null ){
            System.out.println("用户没有openid");
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
        first.put("value","您的账户充值成功");
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
        System.out.println("jsonString：");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("结果："+result);

    }



    @Override public void sendMessageForNoOrderCustomer() {
        List<Customer> customerList = customerMapper.getNoOrderCustomer();
//        Customer c = customerMapper.findByCustomerNo("CUM2021010822220100000");
        // 获取要赠送的优惠券(根据优惠券编码查询)
//        SysCoupons sysCoupons = sysCouponsMapper.selectByCouponsCode("sysc1060");
        for ( Customer c : customerList ){
            WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

            if ( c.getOpenid() == null ){
                System.out.println("用户没有openid");
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

            // 账单明细表
//            CustomerAccount customerAccount = new CustomerAccount();
//            customerAccount.setCustomerNo(c.getId().toString());
//            customerAccount.setCustomerName(c.getCustomerName());
//            customerAccount.setCostType("1");//充值
//            customerAccount.setCreateName("系统");
//            customerAccount.setAmount(new BigDecimal(20));
//            customerAccount.setCreateTime(DateUtil.timestamp2String(new Date()));
//            customerAccount.setStatus(1);
//            customerAccount.setLoginName(c.getLoginName());
//            customerAccount.setChannel("6");//充值渠道  （充值才需填入） 1：微信   2：支付宝   3：银行转账   4：现金  5：淘宝 6：优惠券 7:补运费 8：其他
//            String message = "--运费赠送--";
//            customerAccount.setMessage(message);
//            customerAccount.setCurrency("人民币");//先写死
//            customerAccount.setVersion("1");

            // 赠送优惠券 sysCoupons
//            CustomerCoupons customerCoupons = new CustomerCoupons();
//            customerCoupons.setVersion(1);
//            customerCoupons.setStatus(1);
//            customerCoupons.setCreateTime(DateUtil.timestamp2String(new Date()));
//
//            customerCoupons.setGiveId(-1); // 发放人
//            customerCoupons.setCustomerGroupId(-1); // 客户分组id
//            customerCoupons.setCenterCouponsId(-1); // 领券中心id
//
//            // 优惠券状态 0、未使用 1、已使用 2、已过期
//            customerCoupons.setCouponsStatus(0);
//
//            customerCoupons.setCustomerId(c.getId());
//            customerCoupons.setSysCouponsId(sysCoupons.getId());
//            customerCoupons.setCouponsName(sysCoupons.getCouponsName());
//            customerCoupons.setCouponsAmount(sysCoupons.getCouponsAmount());
//            customerCoupons.setMinimumAmount(sysCoupons.getMinimumAmount());
//            customerCoupons.setCouponsType(sysCoupons.getCouponsType());
//            // 过期类型， 1、固定日期过期， 2、固定天数过期
//            if(sysCoupons.getOverdueType() == 1){
//                customerCoupons.setTimeOverdue(sysCoupons.getTimeOverdue());
//            }else{
//                String overdueTime =
//                    DateUtil.FormatDate(DateUtil.getSpecifiedDayAfter(new Date(), sysCoupons.getTimeLimit()), "yyyy-MM-dd");
//                customerCoupons.setTimeOverdue(overdueTime);
//            }
//            customerCouponsMapper.insertSelective(customerCoupons);
//            // 优惠券赠送完毕
//
//
//            // 账户余额表
            CustomerMoney customerMoney = new CustomerMoney();
            customerMoney = customerMoneyMapper.selectByCustomerNo(c.getCustomerNo());
            if ( customerMoney == null ){
                // 如果客户没有账户，可能是系统冲突造成没有插入数据，重新给她申请账户
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

            first.put("value","您有未使用的618优惠券");
            first.put("color","#173177");
            object.put("first",first);

            keyword1.put("value","20.00(优惠券)7月31日前有效");
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

            remark.put("value","您还有一张未使用的618优惠券");
            remark.put("color","#fc1303");
            object.put("remark",remark);

            messageTemplate.setData(object);

            String jsonString = JSONObject.toJSONString(messageTemplate);
            System.out.println("jsonString：");
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
            wechatMessage.setMessage("20220530优惠券赠送提醒");
            wechatMessageMapper.insert(wechatMessage);

            System.out.println("结果："+result);
        }
    }

    /**
     * 发放优惠券
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
        String message = "【个人中心】-【我的优惠券】查看";
        if ( isSend == 2 ){
            message = "【个人中心】-【领券中心】领取";
        }
        for ( Customer c : customerList ) {
            WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

            if (c.getOpenid() == null) {
                System.out.println("用户没有openid");
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

            first.put("value", "优惠券赠送");
            first.put("color", "#173177");
            object.put("first", first);

            keyword1.put("value", sysCoupons.getCouponsAmount() + "（优惠券）");
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

            remark.put("value", WECHAT_NAME + "为您赠送" + sysCoupons.getCouponsName() + sysCoupons.getCouponsAmount() +"元，可直接下单抵扣哦~\n可进入" + message);
            remark.put("color", "#fc1303");
            object.put("remark", remark);

            messageTemplate.setData(object);

            String jsonString = JSONObject.toJSONString(messageTemplate);
//            System.out.println("jsonString：");
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
            System.out.println("结果：" + result);
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
                System.out.println("用户没有openid");
                continue;
            }
            messageTemplate.setTouser(report.getOpenid());
            // 年度账单微信模板id
            messageTemplate.setTemplate_id("chn6MRRVeRj-h730SAuoNUjU_41N4XUBl9122V_pRpk");
            messageTemplate.setTopcolor("#FF0000");
            messageTemplate.setUrl("http://jiyun.flycloudstorage.com/client/jiyun/#/pages/mine/my-report/annual-repoet");

            JSONObject object = new JSONObject();
            JSONObject first = new JSONObject();
            JSONObject keyword1 = new JSONObject();
            JSONObject keyword2 = new JSONObject();
            JSONObject keyword3 = new JSONObject();
            JSONObject remark = new JSONObject();

            first.put("value", "您有一份2021年的年度账单已送达，请查收！");
            first.put("color", "#173177");
            object.put("first", first);

            keyword1.put("value", "2021年12月31日");
            keyword1.put("color", "#173177");
            object.put("keyword1", keyword1);

            keyword2.put("value", report.getOrderSum());
            keyword2.put("color", "#173177");
            object.put("keyword2", keyword2);

            keyword3.put("value", String.format("%.2f",report.getConsumptionAmountCount()));
            keyword3.put("color", "#173177");
            object.put("keyword3", keyword3);

            remark.put("value", "点击查看详情");
            remark.put("color", "#fc1303");
            object.put("remark", remark);

            messageTemplate.setData(object);

            String jsonString = JSONObject.toJSONString(messageTemplate);
            System.out.println("jsonString：");
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
            wechatMessage.setMessage("年度账单通知");
            wechatMessageMapper.insert(wechatMessage);

            System.out.println("结果：" + result);
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

        first.put("value","订单提醒");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",customerPack.getBusinessNumber());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        RouteVo routeVo = routeMapper.findById(customerPack.getTransportationRouteId());

        List<RoutePrice> priceList = routeVo.getRoutePrices();
        String routeMessage = "体积重计算方式为：(长*宽*高)/" ;
        if ( priceList != null && priceList.size() > 0 ){
            double limitVolConversion = 8000;
            if ( routeVo.getVolConversion() == null || routeVo.getVolConversion() > limitVolConversion ){
                routeMessage = "不计算体积重";
            }else {
                routeMessage = routeMessage + routeVo.getVolConversion() + ",实际重量与体积重对比,取较大者";
            }
        }

        keyword2.put("value","您好，您申请的打包订单已成功提交,所选路线名称：《" + routeVo.getRouteName() +"》\n"
                + "该渠道" + routeMessage);

        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        String weekendMessage = "";
        try {
            if ( DateUtil.isWeekendToday() ){
                weekendMessage = "由于今天是周末,仓库打包会相对较慢~";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        remark.put("value","请耐心等待仓库打包哦,打包后再付款 " + weekendMessage + "\n付款方式有：微信、支付宝、淘宝，可联系微信客服充值后再付款发货" );
        remark.put("color","#173177");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        System.out.println("jsonString：");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("结果："+result);
    }

    /**
     * 发送超时未支付的订单
     */
    @Override public void sendMessageForAdviceCustomerToPay() {
        // 先获取超过一天并且不超过两天的客户未支付订单
//        List<CustomerPack> oneDayPackList = customerPackMapper.getOrdersToPay("1");
        // 先获取超过一天并且不超过两天的客户未支付订单
        List<CustomerPack> twoDayPackList = customerPackMapper.getOrdersToPay("2");
        // 先获取超过一天并且不超过两天的客户未支付订单
        List<CustomerPack> sevenDayPackList = customerPackMapper.getOrdersToPay("7");

        // 发送超过一天的催款消息
//        for ( CustomerPack onePack : oneDayPackList ){
//            Customer customer = customerMapper.findById(onePack.getCustomerId());
//            String paramUrl = sendMessageForCustomerNotPay(onePack,1,customer);
//            saveWechatMessageForCustomerNotPay(onePack,1,paramUrl,customer.getCustomerNo());
//        }
        // 发送超过两天的催款消息
        for ( CustomerPack twoPack : twoDayPackList ){
            Customer customer = customerMapper.findById(twoPack.getCustomerId());
            String paramUrl = sendMessageForCustomerNotPay(twoPack,2,customer);
            saveWechatMessageForCustomerNotPay(twoPack,2,paramUrl,customer.getCustomerNo());
        }
//        // 发送超过七天的催款消息
        for ( CustomerPack sevenPack : sevenDayPackList ){
            Customer customer = customerMapper.findById(sevenPack.getCustomerId());
            String paramUrl = sendMessageForCustomerNotPay(sevenPack,7,customer);
            saveWechatMessageForCustomerNotPay(sevenPack,7,paramUrl,customer.getCustomerNo());
        }
    }

    @Override public void sendMessageForGoodsNotOut() {
        // 两个月未出库的用户提醒
        List<Goods> twoMonthList = goodsMapper.getOverTimeInWarehouse("two");
        // 四个月未出库的用户提醒
        List<Goods> fourMonthList = goodsMapper.getOverTimeInWarehouse("four");
        // 六个月未出库的用户提醒
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
     * 保存发送过的模板消息--专用于订单未支付的客户
     * @param customerPack
     * @param i
     * @param paramUrl
     * @param customerNo
     */
    void saveWechatMessageForCustomerNotPay(CustomerPack customerPack,int i,String paramUrl,String customerNo){
        WechatMessage message = new WechatMessage();
        message.setMessage("超过" + i + "天未支付");
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
     * 给超时未支付的客户发送订单未支付的消息，最多发三次
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

        first.put("value","尊敬的客户您好\n您的包裹已经打包好，请及时查看付款发货");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",customerPack.getOrderNumber());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        BigDecimal b = new BigDecimal(customerPack.getActualWeight());
        keyword2.put("value",b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "KG");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        keyword3.put("value","￥"+customerPack.getActualPrice());
        keyword3.put("color","#173177");
        object.put("keyword3",keyword3);

        keyword4.put("value","先付款后发货");
        keyword4.put("color","#173177");
        object.put("keyword4",keyword4);
        remark.put("value","您好，该订单已打包好超" + day + "天，为了方便您及时收到货物，麻烦亲尽快付款发货哦");

        remark.put("color","#fc1303");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);

        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("打印消息模板通知消息如下：");
        System.out.println(result);
        return result;
    }

    /**
     * 超过两个月或者四个月或者六个月未出库的包裹通知提醒发货
     * @param goodsNo
     * @param goodsType
     * @param customerNo
     */
    void saveWechatMessageForGoodsNotOut(String goodsNo,String goodsType,String customerNo,String paramUrl){
        WechatMessage message = new WechatMessage();
        String day = "两个月";
        String type = "towmonth_inwarehouse";
        if ( "four".equals(goodsType) ){
            day = "四个月";
            type = "fourmonth_inwarehouse";
        }
        if ( "six".equals(goodsType) ){
            day = "六个月";
            type = "sixmonth_inwarehouse";
        }
        message.setMessage("包裹超过" + day + "未出库");
        message.setType(type);
        message.setStatus(1);
        message.setCreateTime(new Date());
        message.setOrderNumber(goodsNo);
        message.setCustomerNo(customerNo);
        message.setRequestParam(paramUrl);
        wechatMessageMapper.insert(message);
    }

    /**
     * 给超时未出库的客户发送消息，两个月发一次，四个月发一次，六个月发一次
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

        first.put("value","您的包裹还有" + time + "天即将满免费仓储期，逾期将不做保管处理，请尽快下达出库哦");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",goods.getDeliveryOrderNo());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        keyword2.put("value",goods.getGoodsName() + "");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        remark.put("value","入库时间：" + goods.getInStorageTime()+ "\n仓库提供免费仓储180天存放哦~");
        remark.put("color","#fc1303");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);

        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        String result = httpUtil.post(url,jsonString);
        System.out.println("打印消息模板通知消息如下：");
        System.out.println(result);
        return result;
    }


    /**
     * 订单轨迹变更通知
     * @param singleTrackLog
     * @param trackingMessage 轨迹更新信息
     */
    @Override
    public void sendMessageForTrackUpdate(TrajectoryLog singleTrackLog, String trackingMessage, String orderStatus) {
        List<CustomerPack> customerPackList = customerPackMapper.findByTransshipmentNo(singleTrackLog.getOrderNumber());
        if(customerPackList.isEmpty()){
            log.info("暂未发现转单号为：{}的订单信息", singleTrackLog.getOrderNumber());
            return;
        }
        log.info("微信通知开始");
        String token = accessTokenMapper.getAccessToken();
        String url = TEMPLATE_URL + token;
        List<WechatMessage> wechatMessageList = new ArrayList<>();
        for (CustomerPack customerPack : customerPackList) {
            String orderNumber = customerPack.getOrderNumber();

            Customer customer = customerMapper.findById(customerPack.getCustomerId());
            if(customer == null){
                log.info("暂未发现转单号为：{}的订单的用户信息，请联系管理员", singleTrackLog.getOrderNumber());
                continue;
            }
            String openId = customer.getOpenid();

            WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

            if (StringUtils.isEmpty(openId)){
                System.out.println("用户没有openid");
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

            first.put("value","尊敬的客户您好:\n您的订单 " + orderNumber + " 有新的物流轨迹信息变更");
            first.put("color","#173177");
            object.put("first",first);

            keyword1.put("value",orderNumber);
            keyword1.put("color","#173177");
            object.put("keyword1",keyword1);

//        String passmessage = "物流轨迹信息变更";

            keyword2.put("value",trackingMessage);
            keyword2.put("color","#173177");
            object.put("keyword2",keyword2);

            remark.put("value", "如需查看轨迹详情，可在【个人中心-待收货】中查看轨迹");
            remark.put("color","#173177");
            object.put("remark",remark);

            messageTemplate.setData(object);

            String jsonString = JSONObject.toJSONString(messageTemplate);
            messageTemplate.setData(object);
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String result = httpUtil.post(url,jsonString);
            System.out.println("结果："+result);

            WechatMessage message = new WechatMessage();
            message.setMessage("【"+singleTrackLog.getOrderNumber()+"】轨迹更新:" + trackingMessage);
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
            System.out.println("用户没有openid");
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

        first.put("value","尊敬的客户，您好：");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",orderNumber);
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

//        String passmessage = "物流轨迹信息变更";

        keyword2.put("value","已完成");
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
        System.out.println("结果："+result);

        WechatMessage message = new WechatMessage();
        message.setMessage("【"+orderNumber+"】发送售后通知信息:" + payReturnMessage);
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
