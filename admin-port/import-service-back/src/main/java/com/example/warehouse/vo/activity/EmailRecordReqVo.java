package com.example.warehouse.vo.activity;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class EmailRecordReqVo extends PageVo {

    private Integer isSend;
    private String title;
    private String senderName;
    private String sendTime;
    private String sendObjectType;
    private String sendObjectName;
    private String contentType;
    private String content;

}
