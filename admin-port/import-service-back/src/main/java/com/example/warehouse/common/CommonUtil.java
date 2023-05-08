package com.example.warehouse.common;

import com.example.warehouse.entity.wechat.WxAccessToken;
import com.example.warehouse.mapper.user.WxAccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.hutool.crypto.SecureUtil.sha1;

@Configuration
public class CommonUtil {

    @Value("${wechat.appid}")
    private String WECHAT_APPID;

    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;

    public Map getJsApi(String requestUrl) {
        WxAccessToken wxAccessToken = wxAccessTokenMapper.getNewAccessToken();

        String noncestr = SequenceCode.generateSigningKey(); // 签名的随机字符串
        String timestamp = String.valueOf(new Date().getTime() / 1000);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("jsapi_ticket=").append(wxAccessToken.getJsapiTicket())
                .append("&noncestr=").append(noncestr)
                .append("&timestamp=").append(timestamp)
                .append("&url=").append(requestUrl);

        String signature = sha1(String.valueOf(stringBuffer));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("appId", WECHAT_APPID);
        resultMap.put("timestamp", timestamp);
        resultMap.put("noncestr", noncestr);
        resultMap.put("signature", signature);
        resultMap.put("url", requestUrl);
        System.out.println("加密结果：" + resultMap);
        return resultMap;
    }

}
