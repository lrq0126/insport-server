package com.example.warehouse.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
/**
 * 微信发送模板消息工具
 */
public class WeChatTemplateMsgUtils {

    @Value("${wechat.appid}")
    private String APPID;

    @Value("${wechat.appsecret}")
    private String APPSECRET;

    @Value("${wechat.template_url}")
    private String TEMPLATE_URL;


}
