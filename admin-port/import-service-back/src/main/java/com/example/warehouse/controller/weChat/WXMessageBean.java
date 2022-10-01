package com.example.warehouse.controller.weChat;

import lombok.Data;

@Data
public class WXMessageBean {
    private String fromUserName;
    private String toUserName;
    private long createTime;
    private String msgType;
    private String event;
    private String eventKey;
    private String ticket;
}
