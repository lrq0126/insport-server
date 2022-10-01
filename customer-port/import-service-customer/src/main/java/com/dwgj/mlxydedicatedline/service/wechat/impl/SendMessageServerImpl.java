package com.dwgj.mlxydedicatedline.service.wechat.impl;

import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.CustomerPack;
import com.dwgj.mlxydedicatedline.entity.RoutePrice;
import com.dwgj.mlxydedicatedline.entity.ping.PingMain;
import com.dwgj.mlxydedicatedline.entity.ping.PingMember;
import com.dwgj.mlxydedicatedline.entity.user.WechatMessageTemplate;
import com.dwgj.mlxydedicatedline.mapper.CustomerMapper;
import com.dwgj.mlxydedicatedline.mapper.RoutePriceMapper;
import com.dwgj.mlxydedicatedline.mapper.TransportationRouteMapper;
import com.dwgj.mlxydedicatedline.mapper.ping.PingMemberMapper;
import com.dwgj.mlxydedicatedline.mapper.user.WxAccessTokenMapper;
import com.dwgj.mlxydedicatedline.service.wechat.SendMessageServer;
import com.dwgj.mlxydedicatedline.utils.DateUtil;
import com.dwgj.mlxydedicatedline.utils.HttpUtilManager;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.appender.routing.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
     * 拼团成员变更通知团长
     */
    @Value("${wechat.pin_type_chanage_id}")
    private String PINTYPECHANGEID;

    /**
     * 申请打包成功提醒
     */
    @Value("${wechat.apply_pack_id}")
    private String APPLE_PACK_ID;

    /**
     * 待付款页面url
     */
    @Value("${wechat.payment_url}")
    private String PAYMENT_URL;

    @Autowired
    private WxAccessTokenMapper accessTokenMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PingMemberMapper memberMapper;

    @Autowired
    private TransportationRouteMapper routeMapper;

    @Autowired
    private RoutePriceMapper priceMapper;

    @Override
    public void sendMessageForPayment() {

    }


    @Override
    public void sendMessageForShipped() {
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String token = accessTokenMapper.getAccessToken();
        httpUtil.post(TEMPLATE_URL + "token=" + httpUtil,"");
    }

    /**
     * 提醒团长有成员加入或者退出
     * @param pin
     * @param userId
     * @param packageNum
     * @param type
     */
    @Override public void sendMessageForPinLeader(PingMain pin, String userId, int packageNum, String type) {
        Customer leaderId = customerMapper.selectByPrimaryKey(pin.getLeaderId());
        Customer user = customerMapper.selectByPrimaryKey(Integer.parseInt(userId));
        if ( leaderId.getOpenid() == null || "".equals(leaderId.getOpenid()) ){
            return;
        }
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        messageTemplate.setTouser(leaderId.getOpenid());
//        messageTemplate.setTouser("oJIqW6I3G_mrV4upvlec9VBN1JuU");
        messageTemplate.setTemplate_id(PINTYPECHANGEID);
        messageTemplate.setTopcolor("#FF0000");
        messageTemplate.setUrl(null);

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject remark = new JSONObject();

        first.put("value","尊敬的客户您好\n您的拼团有新的进展");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",pin.getOrderNumber());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        String typeMsg = "已加入";
        if ( "delete".equals(type) ){
            typeMsg = "已退出";
        }
        keyword2.put("value","您的好友 " + user.getCustomerName() + " " + typeMsg + "您的拼团《"+ pin.getPinName() +"》");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        remark.put("value",typeMsg + packageNum +"个包裹," + "当前拼团总实际重量：" + pin.getPackageWeight() );
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

    @Override public void sendMessageForCutOffTime(List<PingMain> list) {
        for ( PingMain pin : list ){
            WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

            Customer leaderId = customerMapper.selectByPrimaryKey(pin.getLeaderId());
            if ( leaderId.getOpenid() == null || "".equals(leaderId.getOpenid()) ){
                break;
            }
            messageTemplate.setTouser(leaderId.getOpenid());
            messageTemplate.setTemplate_id(PINTYPECHANGEID);
            messageTemplate.setTopcolor("#FF0000");
            messageTemplate.setUrl(null);

            JSONObject object = new JSONObject();
            JSONObject first = new JSONObject();
            JSONObject keyword1 = new JSONObject();
            JSONObject keyword2 = new JSONObject();
            JSONObject remark = new JSONObject();

            first.put("value","尊敬的客户您好\n您的拼团已过期");
            first.put("color","#173177");
            object.put("first",first);

            keyword1.put("value",pin.getOrderNumber());
            keyword1.put("color","#173177");
            object.put("keyword1",keyword1);

            keyword2.put("value","拼团实重未达到预期值，已过期");
            keyword2.put("color","#173177");
            object.put("keyword2",keyword2);

            remark.put("value","您的拼团已过期，请及时确认,你有以下两个选择\n1.解散该团\n2.重新激活并设置更长的截止时间" );
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
    }

    /**
     * 发消息给成员，需要加快速度拼团
     * @param pin
     * @param memberId
     */
    @Override public void sendMessageForPinMember(PingMain pin, String memberId) {
        Customer leaderId = customerMapper.selectByPrimaryKey(pin.getLeaderId());
        Customer user = customerMapper.selectByPrimaryKey(Integer.parseInt(memberId));
        if ( leaderId.getOpenid() == null || "".equals(leaderId.getOpenid()) ){
            return;
        }
        if ( user.getOpenid() == null || "".equals(user.getOpenid()) ){
            return;
        }
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        //        messageTemplate.setTouser(leaderId.getOpenid());
        messageTemplate.setTouser(user.getOpenid());
        messageTemplate.setTemplate_id(PINTYPECHANGEID);
        messageTemplate.setTopcolor("#FF0000");
        messageTemplate.setUrl(null);

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject remark = new JSONObject();

        first.put("value","尊敬的拼团客户您好\n您的加入的拼团《"+ pin.getPinName() +"》团长给您的温馨提示：");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value",pin.getOrderNumber());
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);


        keyword2.put("value","拼团还未成团，大家互相努力哦");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        remark.put("value","该团有" + pin.getPackageSum() +"个包裹," + "当前拼团总实际重量：" + pin.getPackageWeight() +
            "目标重量：" + pin.getTargetWeight());
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

    @Override public void sendMessageForConfirmPin(PingMain pin) {
        List<PingMember> list = memberMapper.getAllMembersGoodsSumExceptLeaderByPinId(pin.getId().toString(),pin.getLeaderId().toString());
        for ( PingMember member : list ){
            WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

            Customer customer = customerMapper.selectByPrimaryKey(member.getCustomerId());
            messageTemplate.setTouser(customer.getOpenid());
            messageTemplate.setTemplate_id(PINTYPECHANGEID);
            messageTemplate.setTopcolor("#FF0000");
            messageTemplate.setUrl(null);

            JSONObject object = new JSONObject();
            JSONObject first = new JSONObject();
            JSONObject keyword1 = new JSONObject();
            JSONObject keyword2 = new JSONObject();
            JSONObject remark = new JSONObject();

            first.put("value","尊敬的客户您好\n您的拼团已成团");
            first.put("color","#173177");
            object.put("first",first);

            keyword1.put("value",pin.getOrderNumber());
            keyword1.put("color","#173177");
            object.put("keyword1",keyword1);

            keyword2.put("value","拼团成功");
            keyword2.put("color","#173177");
            object.put("keyword2",keyword2);

            remark.put("value","仓库小哥会马上处理打包哦，请耐心关注打包状态。" );
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
    }

    @Override public void sendMessageForApplyPack(CustomerPack customerPack) {
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

        RouteVo routeVo = routeMapper.findRouteVoByRouteId(customerPack.getTransportationRouteId());

        List<RoutePrice> priceList = priceMapper.findByRouteId(routeVo.getId(),routeVo.getRouteType());
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

    public static void main(String[] args) {
        WechatMessageTemplate messageTemplate = new WechatMessageTemplate();

        //        messageTemplate.setTouser(leaderId.getOpenid());
        messageTemplate.setTouser("oJIqW6JaVxefKS9TpD5P8sgHq8Hg");
        messageTemplate.setTemplate_id("xztU4UDnSngYxdAAkZtwJETphyjaEcc5KnL1PkBqxPs");
        messageTemplate.setTopcolor("#FF0000");
        messageTemplate.setUrl(null);

        JSONObject object = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();
        JSONObject remark = new JSONObject();

        first.put("value","尊敬的客户您好\n您的拼团有新的进展");
        first.put("color","#173177");
        object.put("first",first);

        keyword1.put("value","P-0484080123");
        keyword1.put("color","#173177");
        object.put("keyword1",keyword1);

        String typeMsg = "已加入";

        keyword2.put("value","您的好友 " +  "Never " + typeMsg + "您的拼团《"+ 222 +"》");
        keyword2.put("color","#173177");
        object.put("keyword2",keyword2);

        remark.put("value",typeMsg + 1 +"个包裹," + "当前拼团总实际重量：" + 20.25 );
        remark.put("color","#173177");
        object.put("remark",remark);

        messageTemplate.setData(object);

        String jsonString = JSONObject.toJSONString(messageTemplate);
        System.out.println("jsonString：");
        System.out.println(jsonString);
        messageTemplate.setData(object);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" +
            "40_55Ji9hop-AecxUKwik3nNixn30H-NzuTz68anXpU6vxMVnqlqx17b5hjcTTA59QDNk1cgjI29M791YO3zfUFy7CQyKjwBeFiMxoRqohlBL8-2Jx6hS8D0wfgQSTahbk2s_F05tNvtJCF5I2yOIVgAJAKER";
        String result = httpUtil.post(url,jsonString);
        System.out.println("结果："+result);
    }
}
