package com.dwgj.mlxydedicatedline.controller.wx;

import lombok.Data;

@Data
public class WXMessageBean {
    private String FromUserName;
    private String ToUserName;
    private long CreateTime;
    private String MsgType;
    private String Event;
    private String EventKey;
    private String Ticket;
}
