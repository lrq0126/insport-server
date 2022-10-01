package com.dwgj.mlxydedicatedline.entity.user;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 微信模板
 */
@Data
public class WechatMessageTemplate {

    private String touser; // 用户openid

    private String template_id; // 模板id

    private String url; // 需要跳转的url

    private String topcolor; // 主题的文字颜色

    private JSONObject data; // 封装的数据主体
}
