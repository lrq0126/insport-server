package com.dwgj.mlxydedicatedline.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.entity.user.WxAccessToken;
import com.dwgj.mlxydedicatedline.enums.ResultStatus;
import com.dwgj.mlxydedicatedline.mapper.user.WxAccessTokenMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.Map;

@RequestMapping("/weixin")
@Controller
@Slf4j
public class WeiXinController {

    private static String APPID;
    private static String APPSECRET;
    private static String TOKEN;
    private static String EncodingAESKey;
    private static String TOKEN_URL;
    private static String OPENID_URL;
    private static String USERINFO_URL;
    private static String JSAPI_TICKET_URL;
    private static String CUSTOMER_URL;
    /**
     * 微信唯一账户id
     */
    @Value("${wechat.appid}")
    public void setAPPID(String APPID) {
        WeiXinController.APPID = APPID;
    }

    @Value("${wechat.appid}")
    private String setAPPID;

    /**
     * 微信唯一账户密钥
     */
    @Value("${wechat.appsecret}")
    public void setAPPSECRET(String APPSECRET) {
        WeiXinController.APPSECRET = APPSECRET;
    }

    /**
     * 微信token
     */
    @Value("${wechat.token}")
    public void setTOKEN(String TOKEN) {
        WeiXinController.TOKEN = TOKEN;
    }

    /**
     * aeskey
     */
    @Value("${wechat.aeskey}")
    public void setEncodingAESKey(String encodingAESKey) {
        WeiXinController.EncodingAESKey = encodingAESKey;
    }

    /**
     * 获取access_token的接口地址（GET）
     */
    @Value("${wechat.access_token_url}")
    public void setTokenUrl(String tokenUrl) {
        WeiXinController.TOKEN_URL = tokenUrl;
    }

    /**
     * 获取openid_url的接口地址（GET）
     */
    @Value("${wechat.openid_url}")
    public void setOpenidUrl(String openidUrl) {
        WeiXinController.OPENID_URL = openidUrl;
    }

    /**
     * 获取微信用户信息的接口地址（GET）
     */
    @Value("${wechat.userinfo_url}")
    public void setUserinfoUrl(String userinfoUrl) {
        WeiXinController.USERINFO_URL = userinfoUrl;
    }
    /**
     * 获取jsapi_ticket的接口地址（GET）
     */
    @Value("${wechat.jsapi_ticket_url}")
    public void setJsapiTicketUrl(String jsapiTicketUrl) {
        WeiXinController.JSAPI_TICKET_URL = jsapiTicketUrl;
    }

    /**
     * 获取客户端的url
     */
    @Value("${wechat.url}")
    public void setCUSTOMER_URL(String CUSTOMER_URL) {
        WeiXinController.CUSTOMER_URL = CUSTOMER_URL;
    }

    private static WxAccessTokenMapper wxAccessTokenMapper;

    @Autowired
    private WeiXinController(WxAccessTokenMapper tokenMapper){
        WeiXinController.wxAccessTokenMapper = tokenMapper;
    }

    @Autowired @Lazy
    private CustomerService customerService;



    @RequestMapping("/verify")
    @ResponseBody
    public String verify(HttpServletRequest request) throws AesException{
        WXVerify wxVerify = new WXVerify(TOKEN,EncodingAESKey,APPID);
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("msgSignature：{}",msgSignature);
        log.info("msgTimestamp：{}",msgTimestamp);
        log.info("msgNonce：{}",msgNonce);
        log.info("echostr：{}",echostr);
        String a = SHA1.getSHA1(TOKEN,msgTimestamp,msgNonce,echostr);
        log.info("加密：{}",a);
        return wxVerify.verifyUrl(msgSignature,msgTimestamp,msgNonce,echostr);
    }

    /**
     * 传入code，然后获取用户openid，再获取用户信息
     * @param param
     * @return
     */
    @RequestMapping("/getUserInfoByCode")
    @ResponseBody
    public ResponseEntity<ResultModel> getUserInfoByCode(@RequestParam Map<String,Object> param){
        return customerService.authorizationLogin(param);
    }

    public String getWxOpenId(String code) {
        log.info("code:{}",code);
        String requestUrl = OPENID_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET).replace("CODE", code);
        log.info("getWXOpenURL:{}",requestUrl);
        String jsonObject = loadJSON(requestUrl);
        JSONObject json = JSONObject.parseObject(jsonObject);
        log.info("json:{}",json.toJSONString());
        Object openId = json.get("openid");
        if (null != openId) {
            return openId.toString();
        }
        return null;
    }

    public String getJsapiTicket(String accessToken) {
        log.info("accessToken:{}",accessToken);
        String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken);
        log.info("getJsapiTicketURL:{}",requestUrl);
        String jsonObject = loadJSON(requestUrl);
        JSONObject json = JSONObject.parseObject(jsonObject);
        log.info("json:{}",json.toJSONString());
        Object openId = json.get("ticket");
        if (null != openId) {
            return openId.toString();
        }
        return null;
    }

    /**
     * 根据openid获取用户信息
     * @param openid
     * @param token
     * @return
     */
    public static JSONObject getUserInfoByOpenid(String openid,String token){
        String requestUrl = USERINFO_URL.replace("OPENID", openid).replace("ACCESS_TOKEN",token);
        String jsonObject = loadJSON(requestUrl);
        JSONObject object = JSONObject.parseObject(jsonObject);
        if( object.containsKey("errcode") ){
            if ( "40001".equals(object.get("errcode").toString()) || "42001".equals(object.get("errcode").toString())){
                log.error("access_token已过期，正重新获取新的");
                String token_result = WeiXinController.loadJSON(TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET));
                JSONObject result = JSONObject.parseObject(token_result);
                WxAccessToken wxAccessToken = new WxAccessToken();
                wxAccessToken.setAccessToken(result.get("access_token").toString());
                wxAccessToken.setExpire(result.get("expires_in").toString());
                wxAccessTokenMapper.insert(wxAccessToken);
                requestUrl = USERINFO_URL.replace("OPENID", openid).replace("ACCESS_TOKEN",result.get("access_token").toString());
                jsonObject = loadJSON(requestUrl);
                object = JSONObject.parseObject(jsonObject);
            }
        }
        return object;
    }
    /**
     * 通过JSONObject.get ( key)的方式获取json对象的属性值
     *
     * @param url 请求地址
     * @return
     */
    public static String loadJSON(String url) {
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
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

    @RequestMapping("/getTicket")
    @ResponseBody
    public JSONObject getTicket(){
        String ticket = wxAccessTokenMapper.getJsapiTicket();
        JSONObject a = new JSONObject();
        a.put("ticket",ticket);
        return a;
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
            signature = byteToHex(crypt.digest());
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

    /**
     * sha1 加密JSSDK微信配置参数获取签名。
     *
     * @return
     */
    public static String sha1(String orderedString) throws Exception {
        String ciphertext = null;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(orderedString.getBytes());
        ciphertext = byteToStr(digest);
        return ciphertext.toLowerCase();
    }
    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }
    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    public static void main(String[] args) throws Exception {

        //
        // 第三方回复公众平台
        //

        // 需要加密的明文
        String encodingAesKey = "e0EDCnGhDjbWegrS8n3xLGeIy1gIBuN391uQ16YO8J3";
        String token = "wenguosheng";
        String timestamp = "1586312856";
        String nonce = "xxxxxx";
        String appId = "wxbfe4daa53af9e4de";
        String replyMsg = " 中文<xml><ToUserName><![CDATA[oia2TjjewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";

        WXVerify pc = new WXVerify(token, encodingAesKey, appId);
        String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
        System.out.println("加密后: " + mingwen);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(mingwen);
        org.xml.sax.InputSource is = new org.xml.sax.InputSource(sr);
        Document document = db.parse(is);

        Element root = document.getDocumentElement();
        NodeList nodelist1 = root.getElementsByTagName("Encrypt");
        NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

        String encrypt = nodelist1.item(0).getTextContent();
        String msgSignature = nodelist2.item(0).getTextContent();

        String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
        String fromXML = String.format(format, encrypt);

        //
        // 公众平台发送消息给第三方，第三方处理
        //

        // 第三方收到公众号平台发送的消息
        String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
        System.out.println("解密后明文: " + result2);

        //pc.verifyUrl(null, null, null, null);
    }

}
