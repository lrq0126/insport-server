package com.dwgj.mlxydedicatedline.schedul;

import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.controller.wx.WeiXinController;
import com.dwgj.mlxydedicatedline.entity.ping.PingMain;
import com.dwgj.mlxydedicatedline.entity.user.WxAccessToken;
import com.dwgj.mlxydedicatedline.mapper.ping.PingMainMapper;
import com.dwgj.mlxydedicatedline.mapper.user.WxAccessTokenMapper;
import com.dwgj.mlxydedicatedline.service.wechat.SendMessageServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
/**
 * @author guoshengwen
 * @date 2020年4月26日
 * @desc 一个低端定时任务类，此类不需要同步pc端，一端就够
 */
public class SchedulTask {
    @Autowired
    WxAccessTokenMapper wxAccessTokenMapper;

    @Autowired
    private PingMainMapper pingMainMapper;

    @Autowired
    private SendMessageServer sendMessageServer;

    /**
     * 微信唯一账户id
     */
    @Value("${wechat.appid}")
    private String APPID;

    /**
     * 微信唯一账户密钥
     */
    @Value("${wechat.appsecret}")
    private String SECRET;

    /**
     * 获取access_token的接口地址（GET）
     */
    @Value("${wechat.access_token_url}")
    private String TOKEN_URL;

    /**
     * 获取jsapi_ticket的接口地址（GET）
     */
    @Value("${wechat.jsapi_ticket_url}")
    private String JSAPI_TICKET_URL;


    /**
     * 每90分钟取一次微信access_token,一天最多取2000次
     */
//    @Scheduled(cron="0 */90 * * * ? ")
    public void getNewAccessToken(){
        log.info("--------------微信定时任务开始，获取access_token-----------");
        JSONObject result=  getAccessToken();
        WxAccessToken wxAccessToken = new WxAccessToken();
        wxAccessToken.setAccessToken(result.get("access_token").toString());
        wxAccessToken.setExpire(result.get("expires_in").toString());
        String tikcet = getJsapiTicket(result.get("access_token").toString());
        wxAccessToken.setJsapiTicket(tikcet);
        wxAccessTokenMapper.insert(wxAccessToken);
        log.info("--------------微信定时任务结束，获取access_token：{}",result.get("access_token").toString());
    }

    /**
     * 每30分钟取执行一次：截止日期已到的拼团，需要把状态改为已过期
     */
    @Scheduled(cron="0 */30 * * * ? ")
    public void changeCutOffTimeForPin(){
        log.info("--------------拼团定时任务开始，更改已过期的拼团开始-----------");
        List<PingMain> list = pingMainMapper.selectIsCutOffTimePinList();
        // 先发送消息
        sendMessageServer.sendMessageForCutOffTime(list);
        // 更改状态
        pingMainMapper.updateIsCutOffTimePin();
        log.info("--------------拼团定时任务开始，更改已过期的拼团结束-----------");
    }

    public JSONObject getAccessToken(){
        log.info("--------------获取access_token-----------");
        String requestUrl = TOKEN_URL.replace("APPID",APPID).replace("SECRET",SECRET);
        log.info("--------------获取access_tokend的url{}-----------",requestUrl);
        String result = WeiXinController.loadJSON(requestUrl);
        JSONObject object = JSONObject.parseObject(result);
        return  object;
    }

    public String getJsapiTicket(String accessToken) {
        log.info("accessToken:{}",accessToken);
        String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken);
        log.info("getJsapiTicketURL:{}",requestUrl);
        String jsonObject = WeiXinController.loadJSON(requestUrl);
        JSONObject json = JSONObject.parseObject(jsonObject);
        log.info("json:{}",json.toJSONString());
        Object openId = json.get("ticket");
        if (null != openId) {
            return openId.toString();
        }
        return null;
    }

}
