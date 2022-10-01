package com.example.warehouse.service.wechat.impl;

import com.alibaba.fastjson.JSON;
import com.baidu.speech.restapi.json.JSONObject;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.HttpUtilManager;
import com.example.warehouse.controller.weChat.WXMessageBean;
import com.example.warehouse.controller.weChat.WeChatUtil;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.wx.WeChatMaterialDo;
import com.example.warehouse.entity.wx.WechatOfficialAccountLog;
import com.example.warehouse.entity.wx.WechatReply;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.user.WxAccessTokenMapper;
import com.example.warehouse.mapper.wx.WechatOfficialAccountLogMapper;
import com.example.warehouse.mapper.wx.WechatReplyMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerService;
import com.example.warehouse.service.wechat.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    private String MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";
    private String returnTestMessage = "<xml>\n" +
            "  <ToUserName><![CDATA[FROMUSERNAME]]></ToUserName>\n" +
            "  <FromUserName><![CDATA[TOUSERNAME]]></FromUserName>\n" +
            "  <CreateTime>CREATETIME</CreateTime>\n" +
            "  <MsgType><![CDATA[text]]></MsgType>\n" +
            "  <Content><![CDATA[MESSAGE]]></Content>\n" +
            "</xml>";

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;

    @Autowired
    private WechatReplyMapper wechatReplyMapper;
    @Autowired
    private WechatOfficialAccountLogMapper wechatOfficialAccountLogMapper;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {

        // 调用parseXml方法解析请求消息
        try {
            request.setCharacterEncoding("UTF-8");
            String responseMsg = null;
            Map<String, String> weChatMap = WeChatUtil.parseXml(request);
            // 消息类型
            String msgType = weChatMap.get("MsgType");

            switch (msgType){
                // 关注公众号、扫描带参数二维码等事件推送消息
                case "event" :
                    responseMsg = eventResponse(weChatMap);
                    break;
                // 用户向公众账号发消息
                case "text" :
                    List<String> respStr = textResponse(weChatMap);
                    if(!CollectionUtils.isEmpty(respStr)){
                        for (String s : respStr) {
                            System.out.println("返回文本：" + s);
                            response.setCharacterEncoding("UTF-8");
                            PrintWriter out = response.getWriter();
                            out.print(s);
                        }
                    }
                    return;
                // 用户向公众账号发送图片消息
                case "image" :
                    responseMsg = defaultResponse(weChatMap);
                    break;
                // 用户向公众账号发送语音消息
                case "voice" :
                    responseMsg = defaultResponse(weChatMap);
                    break;
                default:
                    responseMsg = defaultResponse(weChatMap);
                    break;
            }

            if(!StringUtils.isEmpty(responseMsg)){
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(responseMsg);
            }


//            return "";
        } catch (Exception e) {
            e.printStackTrace();
//            return "";
        }
    }

    private List<String> subscribeResponse(Map<String,String> weChatMap) {
        List<String> responseMsgList = new ArrayList<>();
        WechatReply wechatReply = wechatReplyMapper.selectByReplyRule("客服二维码");
        long timeStamp = new Date().getTime();
        long createTime = Long.parseLong(String.valueOf(timeStamp).substring(0, String.valueOf(timeStamp).length()-3));
        if(wechatReply != null){
            String imgBuilder = "<xml>\n" +
                    "  <ToUserName><![CDATA[" + weChatMap.get("FromUserName") + "]]></ToUserName>\n" +
                    "  <FromUserName><![CDATA[" + weChatMap.get("ToUserName") + "]]></FromUserName>\n" +
                    "  <CreateTime>" + createTime + "</CreateTime>\n" +
                    "  <MsgType><![CDATA[image]]></MsgType>\n" +
                    "  <Image><MediaId><![CDATA["+wechatReply.getMediaId()+"]]></MediaId></Image>\n" +
                    "</xml>";
            responseMsgList.add(imgBuilder);

            String textBuilder = returnTestMessage.replaceAll("FROMUSERNAME", weChatMap.get("FromUserName"))
                            .replaceAll("TOUSERNAME", weChatMap.get("ToUserName"))
                            .replaceAll("CREATETIME", String.valueOf(createTime))
                            .replaceAll("MESSAGE", "请扫描二维码添加客服");
            responseMsgList.add(textBuilder);
        }
        return responseMsgList;
    }

    /**
     * 微信事件推送返回信息
     * @param weChatMap
     * @return
     */
    private String eventResponse(Map<String,String> weChatMap) {
        String event = weChatMap.get("Event");

        // 插入关注&取消关注日志
        if("subscribe".equals(event) || "unsubscribe".equals(event)){
            log.info("---------->>>>>>>插入微信关注&取消事件日志<<<<<<<<-----------");
            WechatOfficialAccountLog wechatLog = new WechatOfficialAccountLog();
            wechatLog.setMsgType(weChatMap.getOrDefault("MsgType", null));
            wechatLog.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            wechatLog.setFromUserName(weChatMap.getOrDefault("FromUserName", null));
            wechatLog.setEvent(weChatMap.getOrDefault("Event", null));
            wechatLog.setEventKey(weChatMap.getOrDefault("EventKey", null));
            wechatOfficialAccountLogMapper.insertSelective(wechatLog);
        }


        // Event: 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
        if("subscribe".equals(event)){
            WXMessageBean wxMessageBean = new WXMessageBean();
            wxMessageBean.setFromUserName(weChatMap.getOrDefault("FromUserName", null));
            wxMessageBean.setToUserName(weChatMap.getOrDefault("ToUserName", null));
            wxMessageBean.setMsgType(weChatMap.getOrDefault("MsgType", null));
            wxMessageBean.setEvent(weChatMap.getOrDefault("Event", null));
            wxMessageBean.setEventKey(weChatMap.getOrDefault("EventKey", null));
            wxMessageBean.setTicket(weChatMap.getOrDefault("Ticket", null));
            long timeStamp = new Date().getTime();
            int createTime = Integer.parseInt(String.valueOf(timeStamp).substring(0, String.valueOf(timeStamp).length()-3));

            // 扫描二维码后关注的事件
            System.out.println("关注公众号");
            Customer customer;
            // 如果是绑定微信的二维码
            if(wxMessageBean.getEventKey().contains("binding")){

                String openID = wxMessageBean.getFromUserName();
                customer = customerMapper.selectByOpenid(openID);
                String returnStr;
                if(customer == null){
                    System.out.println("当前用户未注册账号，将微信账号与系统账号进行绑定");
                    // 扫描客服生成的二维码，绑定相关微信号
                    customer = customerService.bindingCustomer(wxMessageBean);

                    WechatReply wechatReply = wechatReplyMapper.selectByReplyRule("扫码关注后自动回复");
                    String message = wechatReply == null ? "暂无自动回复的内容哦" : wechatReply.getReplyContent();
                    message = message.replaceAll("LOGIN_NAME", customer.getLoginName());
                    returnStr = returnTestMessage.replaceAll("FROMUSERNAME", weChatMap.get("FromUserName"))
                            .replaceAll("TOUSERNAME", weChatMap.get("ToUserName"))
                            .replaceAll("CREATETIME", String.valueOf(createTime))
                            .replaceAll("MESSAGE", message);
                }else {
                    System.out.println("当前用户已注册过账号，将提示用户用微信绑定的账号");

                    String loginName = wxMessageBean.getEventKey().split("qrscene_binding_")[1];
                    Customer sysCustomer = customerMapper.getCustomerByLoginName(loginName);

                    // 如果该账号的openid相同，则回复正常的关注提示
                    if(!StringUtils.isEmpty(sysCustomer.getOpenid()) && customer.getOpenid().equals(sysCustomer.getOpenid())){
                        WechatReply wechatReply = wechatReplyMapper.selectByReplyRule("扫码关注后自动回复");
                        String message = wechatReply == null ? "暂无自动回复的内容哦" : wechatReply.getReplyContent();
                        message = message.replaceAll("LOGIN_NAME", customer.getLoginName());
                        message = message.replaceAll("LOGIN_NAME", customer.getLoginName());
                        returnStr = returnTestMessage.replaceAll("FROMUSERNAME", weChatMap.get("FromUserName"))
                                .replaceAll("TOUSERNAME", weChatMap.get("ToUserName"))
                                .replaceAll("CREATETIME", String.valueOf(createTime))
                                .replaceAll("MESSAGE", message);
                    }else{
                        WechatReply wechatReply = wechatReplyMapper.selectByReplyRule("已经关注过的");
                        String message = wechatReply == null ? "暂无自动回复的内容哦" : wechatReply.getReplyContent();
                        message = message.replaceAll("LOGIN_NAME", customer.getLoginName());

                        returnStr = returnTestMessage.replaceAll("FROMUSERNAME", weChatMap.get("FromUserName"))
                                .replaceAll("TOUSERNAME", weChatMap.get("ToUserName"))
                                .replaceAll("CREATETIME", String.valueOf(createTime))
                                .replaceAll("MESSAGE", message);
                    }
                }

                return returnStr;

            }else { // 如果不是扫描微信绑定的二维码，则进行关注/扫码关注流程
                System.out.println("扫描客户分享的二维码进行关注");
                // 扫描客户分享的二维码，关注自动注册账户，绑定相关微信号
                customer = customerService.subscribe(wxMessageBean);
                WechatReply wechatReply = wechatReplyMapper.selectByReplyRule("扫码关注后自动回复");
                String message = wechatReply == null ? "暂无自动回复的内容哦" : wechatReply.getReplyContent();
                message = message.replaceAll("LOGIN_NAME", customer.getLoginName());
                String returnStr = returnTestMessage.replaceAll("FROMUSERNAME", weChatMap.get("FromUserName"))
                        .replaceAll("TOUSERNAME", weChatMap.get("ToUserName"))
                        .replaceAll("CREATETIME", String.valueOf(createTime))
                        .replaceAll("MESSAGE", message);

                return returnStr;
            }

        }else if("CLICK".equals(event) && "仓库地址".equals(weChatMap.get("EventKey"))){
            long timeStamp = new Date().getTime();
            int createTime = Integer.parseInt(String.valueOf(timeStamp).substring(0, String.valueOf(timeStamp).length() - 3));
            Customer customer = customerMapper.selectByOpenid(weChatMap.getOrDefault("FromUserName", null));

            if (customer == null) {
                return null;
            }
            WechatReply wechatReply = wechatReplyMapper.selectByReplyRule("仓库地址");
            String message = wechatReply == null ? "暂无自动回复的内容哦" : wechatReply.getReplyContent();
            message = message.replaceAll("LOGIN_NAME", customer.getLoginName());
            String returnStr = returnTestMessage.replaceAll("FROMUSERNAME", weChatMap.get("FromUserName"))
                    .replaceAll("TOUSERNAME", weChatMap.get("ToUserName"))
                    .replaceAll("CREATETIME", String.valueOf(createTime))
                    .replaceAll("MESSAGE", message);
//                    "<xml>\n" +
//                    "  <ToUserName><![CDATA[" + weChatMap.get("FromUserName") + "]]></ToUserName>\n" +
//                    "  <FromUserName><![CDATA[" + weChatMap.get("ToUserName") + "]]></FromUserName>\n" +
//                    "  <CreateTime>" + createTime + "</CreateTime>\n" +
//                    "  <MsgType><![CDATA[text]]></MsgType>\n" +
//                    "  <Content><![CDATA[" + message + "]]></Content>\n" +
//                    "</xml>";
            return returnStr;
        }
        return null;
    }


    /**
     * 客户发送信息 自动回复文本信息
     * @param weChatMap
     * @return
     */
    private List<String> textResponse(Map<String,String> weChatMap) {
        System.out.println("文字消息自动回复");
        List<String> returnStrList = new ArrayList<>();
        // 客户发送的文本信息
        String userRequestMessage = weChatMap.get("Content");
        List<WechatReply> wechatReplyList = wechatReplyMapper.selectByKeyWord(userRequestMessage);
        if(CollectionUtils.isEmpty(wechatReplyList)){
            returnStrList.add(defaultResponse(weChatMap));
        }else if(wechatReplyList.size() > 1){
            for (WechatReply wechatReply : wechatReplyList) {
                String responseMsg =  responseMessage(wechatReply, weChatMap);
                returnStrList.add(responseMsg);
            }
        }else {
            WechatReply wechatReply = wechatReplyList.get(0);
            String responseMsg =  responseMessage(wechatReply, weChatMap);
            returnStrList.add(responseMsg);
        }
        return returnStrList;
    }

    private String responseMessage(WechatReply wechatReply, Map<String,String> weChatMap) {

        String MsgType = wechatReply.getReplyType();
        String responseMessage = "";
        switch (MsgType){
            case "text":
                String replyContent = wechatReply.getReplyContent();
                if(replyContent.contains("LOGIN_NAME")){
                    Customer customer = customerMapper.selectByOpenid(weChatMap.get("FromUserName"));
                    if(customer != null){
                        replyContent = replyContent.replaceAll("LOGIN_NAME", customer.getLoginName());
                    }
                }
                responseMessage =
                        "  <Content><![CDATA[" + replyContent + "]]></Content>\n";
                break;
            case "image":
                responseMessage =
                        "  <Image>\n" +
                                "    <MediaId><![CDATA["+wechatReply.getMediaId()+"]]></MediaId>\n" +
                                "  </Image>\n";
                break;
            case "voice":
                responseMessage =
                        "  <Voice>\n" +
                                "    <MediaId><![CDATA["+wechatReply.getMediaId()+"]]></MediaId>\n" +
                                "  </Voice>\n";
                break;
            case "video":
                responseMessage =
                        "  <Video>\n" +
                                "    <MediaId><![CDATA["+wechatReply.getMediaId()+"]]></MediaId>\n" +
                                "  </Video>\n";
                break;
        }
        long timeStamp = new Date().getTime();
        int createTime = Integer.parseInt(String.valueOf(timeStamp).substring(0, String.valueOf(timeStamp).length()-3));
        String returnStr = "<xml>\n" +
                "  <ToUserName><![CDATA["+weChatMap.get("FromUserName")+"]]></ToUserName>\n" +
                "  <FromUserName><![CDATA["+weChatMap.get("ToUserName")+"]]></FromUserName>\n" +
                "  <CreateTime>"+ createTime +"</CreateTime>\n" +
                "  <MsgType><![CDATA["+MsgType+"]]></MsgType>\n" +
                responseMessage +
                "</xml>\n";
        return returnStr;
    }

    /**
     * 客户发送图片自动回复
     * @param weChatMap
     * @return
     */
    private String imageResponse(Map<String,String> weChatMap) {
        long timeStamp = new Date().getTime();
        int createTime = Integer.parseInt(String.valueOf(timeStamp).substring(0, String.valueOf(timeStamp).length()-3));
        String returnStr = "<xml>\n" +
                "  <ToUserName><![CDATA["+weChatMap.get("FromUserName")+"]]></ToUserName>\n" +
                "  <FromUserName><![CDATA["+weChatMap.get("ToUserName")+"]]></FromUserName>\n" +
                "  <CreateTime>"+ createTime +"</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[您好，暂未发现您的需求答案，请输入【关键字】查询]]></Content>\n" +
                "</xml>";
        return returnStr;
    }

    /**
     * 客户发送语音 自动回复
     * @param weChatMap
     * @return
     */
    private String voiceResponse(Map<String,String> weChatMap) {
        long timeStamp = new Date().getTime();
        int createTime = Integer.parseInt(String.valueOf(timeStamp).substring(0, String.valueOf(timeStamp).length()-3));
        String returnStr = "<xml>\n" +
                "  <ToUserName><![CDATA["+weChatMap.get("FromUserName")+"]]></ToUserName>\n" +
                "  <FromUserName><![CDATA["+weChatMap.get("ToUserName")+"]]></FromUserName>\n" +
                "  <CreateTime>"+ createTime +"</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[您好，暂未发现您的需求答案，请输入【关键字】查询]]></Content>\n" +
                "</xml>";
        return returnStr;
    }

    /**
     * 其他类型自动回复
     * @param weChatMap
     * @return
     */
    private String defaultResponse(Map<String,String> weChatMap) {
        long timeStamp = new Date().getTime();
        int createTime = Integer.parseInt(String.valueOf(timeStamp).substring(0, String.valueOf(timeStamp).length()-3));
        WechatReply wechatReply = wechatReplyMapper.selectByReplyRule("其他");
        String message = wechatReply == null ? "暂无提示" : wechatReply.getReplyContent();
        String returnStr = "<xml>\n" +
                "  <ToUserName><![CDATA["+weChatMap.get("FromUserName")+"]]></ToUserName>\n" +
                "  <FromUserName><![CDATA["+weChatMap.get("ToUserName")+"]]></FromUserName>\n" +
                "  <CreateTime>"+ createTime +"</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA["+message+"]]></Content>\n" +
                "</xml>";
        return returnStr;
    }



    /**
     * 获取微信素材
     * @param type
     * @param offset
     * @param count
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> getMaterial(String type, int offset, int count) {
        String accessToken = wxAccessTokenMapper.getAccessToken();
//        accessToken = "54_0nqkyd3aWOkG4D0X8nFq6cZgAl2tR-KLrx-J2oEXq5DoUsYIwCZ1bflIHk_YRGWShWybzS5orDC7NpR3Di1aDpEsXWMh-9WatrOy3hwELY80l7kBlkmrsQlbPoClLOTmEZk82c7rnIJtzfq7QUDaABAWAJ";
        String requestURL = MATERIAL_URL + accessToken;
        String json = "{\"type\": \""+type+"\", \"offset\": \""+offset+"\", \"count\":\""+count+"\"}";

        HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
        String responseResult = httpUtilManager.post(requestURL, json);
        System.out.println(responseResult);

        WeChatMaterialDo weChatMaterialDo = JSON.parseObject(responseResult, WeChatMaterialDo.class);

            List<Map<String, Object>> item = weChatMaterialDo.getItem();

            List<Map<String, Object>> newItem = new ArrayList<>();
            long timeStamp = new Date().getTime();
            for (Map<String, Object> map : item) {
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("media_id", map.get("media_id"));
                if("news".equals(type)){
                    Map<String, Object> content = (Map<String, Object>) map.get("content");
                    List<Map<String, Object>> newItems = (List<Map<String, Object>>) content.get("news_item");

                    if(!CollectionUtils.isEmpty(newItems)){
                        for (Map<String, Object> stringObjectMap : newItems) {
                            newMap = new HashMap<>();
                            String name = (String) stringObjectMap.get("title");
                            newMap.put("name", name);
                            String thumbUrl = (String) stringObjectMap.get("thumb_url");
                            newMap.put("thumb_url", thumbUrl + "&time=" + timeStamp);
                            String url = (String) stringObjectMap.get("url");
                            newMap.put("url", url);
                            String digest = (String) stringObjectMap.get("digest");
                            newMap.put("digest", digest);
                            newItem.add(newMap);
                        }
                    }
                }else if("image".equals(type)){
                    String url = (String) map.get("url");
                    newMap.put("url", url+"&time=" + timeStamp);
                    String name = (String) map.get("name");
                    newMap.put("name", name);
                    newItem.add(newMap);
                }
                timeStamp+= new Date().getTime() ;


            }
            weChatMaterialDo.setItem(newItem);
        return new ResponseEntity<>(ResultModel.ok(weChatMaterialDo), HttpStatus.OK);
    }
}
