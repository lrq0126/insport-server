package com.example.warehouse.service.wechat.impl;

import com.alibaba.fastjson.JSON;
import com.example.warehouse.common.HttpUtilManager;
import com.example.warehouse.entity.wechat.WxAccessToken;
import com.example.warehouse.mapper.user.WxAccessTokenMapper;
import com.example.warehouse.service.wechat.WxAccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

/**
 *  服务实现类
 *
 * @author LRQ
 * @since 2023-04-24
 */
@Slf4j
@Service
public class WxAccessTokenServiceImpl implements WxAccessTokenService {
    private static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    @Value("${wechat.appid}")
    private String WECHAT_APPID;
    @Value("${wechat.appsecret}")
    private String WECHAT_APP_SECRET;
    @Value("${wechat.token}")
    private String WECHAT_TOKEN;
    @Value("${wechat.aeskey}")
    private String WECHAT_AESKEY;

    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;

    @Override
    public void updateWxAccessToken() {

        try {
            WxAccessToken wxAccessToken = new WxAccessToken();
            HttpUtilManager http = HttpUtilManager.getInstance();

            // 获取AccessToken
            String url = GET_ACCESS_TOKEN_URL.replace("APPID", WECHAT_APPID).replace("APPSECRET", WECHAT_APP_SECRET);
            String accessTokenStr = http.requestHttpGet(url);
            Map<String, String> accessTokenMap = JSON.parseObject(accessTokenStr, Map.class);
            System.out.println("----->>>>> resultTokenMap：" + accessTokenMap);
            String accessToken = accessTokenMap.get("access_token");
            String expires = String.valueOf(accessTokenMap.get("expires_in"));
            System.out.println("----->>>>> accessToken：" + accessToken);
            // 获取jsApiTicket
            url = GET_JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken);
            String jsApiTicketStr = http.requestHttpGet(url);
            Map<String, String> jsApiTicketMap = JSON.parseObject(jsApiTicketStr, Map.class);
            String jsApiTicket = jsApiTicketMap.get("ticket");
            System.out.println("----->>>>> jsApiTicket：" + jsApiTicket);

            wxAccessToken.setAccessToken(accessToken);
            wxAccessToken.setExpire(expires);
            wxAccessToken.setJsapiTicket(jsApiTicket);
            wxAccessToken.setStatus("1");
            wxAccessToken.setCreateTime(new Date());
            this.wxAccessTokenMapper.insert(wxAccessToken);
        } catch (HttpException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
