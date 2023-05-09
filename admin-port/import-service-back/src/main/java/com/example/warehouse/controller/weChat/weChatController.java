package com.example.warehouse.controller.weChat;

import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.mapper.user.WxAccessTokenMapper;
import com.example.warehouse.service.wechat.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("weChat")
@Slf4j
public class weChatController {

    private static String APPID;
    private static String CUSTOMER_URL;
    private static String TOKEN;
    private static String EncodingAESKey;

    /**
     * 微信token
     */
    @Value("${wechat.token}")
    public void setTOKEN(String TOKEN) {
        weChatController.TOKEN = TOKEN;
    }

    /**
     * aeskey
     */
    @Value("${wechat.aeskey}")
    public void setEncodingAESKey(String encodingAESKey) {
        weChatController.EncodingAESKey = encodingAESKey;
    }


    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;

    @Autowired
    private WeChatService weChatService;

    /**
     * signature:e55ec939d287c14640653118ae3c60238c45fc97
     * echostr:8219228656660642517
     * timestamp:1645673044
     * nonce:78657751
     * @return
     */
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public String validate(HttpServletRequest request) throws AesException{
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("msgSignature：{}",msgSignature);
        log.info("msgTimestamp：{}",msgTimestamp);
        log.info("msgNonce：{}",msgNonce);
        log.info("echostr：{}",echostr);
        boolean flag = SHA1.checkSignature(TOKEN,msgSignature,msgTimestamp,msgNonce);
        return echostr;
    }

    /**
     * 此处是处理微信服务器的消息转发的
     */
    @PostMapping(value = "validate")
    @ResponseBody
    public void processMsg(HttpServletRequest request, HttpServletResponse response) {
        // 调用核心服务类接收处理请求

          weChatService.processRequest(request, response);
    }

    /**
     * 获取验签，分享链接
     * @param url
     * @return
     * @throws Exception
     */
    @RequestMapping("/getSignature")
    @ResponseBody
    public JSONObject getSignature(String url) throws Exception {
        String ticket = wxAccessTokenMapper.getJsapiTicket();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String noncestr = WXVerify.getRandomStr();
        JSONObject a = new JSONObject();
        System.out.println("url:"+url);
        if ( StringUtils.isBlank(url) ){
            url = CUSTOMER_URL;
        }
        String sign = buildJSSDKSignature(ticket,timestamp,noncestr,url);
        String signature = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sign.getBytes("UTF-8"));
            signature = WeChatUtil.byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }


        log.info("生成最终签名如下：{}",signature);
        a.put("appid",APPID);
        a.put("signature",signature);
        a.put("timestamp",timestamp);
        a.put("noncestr",noncestr);
        a.put("url",url);
        return a;
    }

    public static String buildJSSDKSignature(String ticket,String timestamp,String noncestr ,String url) throws Exception {
        String orderedString = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr
                +  "&timestamp=" + timestamp
                + "&url=" + url;
        log.info("生成签名链接如下：{}",orderedString);
//        return sha1(orderedString);
        return orderedString;
    }



}
