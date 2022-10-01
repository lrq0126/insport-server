package com.example.warehouse.vo.wechat;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class WechatLogReqVo extends PageVo {

    private String startTime;
    private String endTime;

    private String customerID;

    private String event;

    private String eventKey;

    private String msgType;

}
