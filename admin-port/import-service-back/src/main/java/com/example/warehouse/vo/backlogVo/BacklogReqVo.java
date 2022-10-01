package com.example.warehouse.vo.backlogVo;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class BacklogReqVo extends PageVo {

    private String title;

    private String content;

    private Integer backloggerId;

    private String backlogger;

    private Integer backlogType;

    private Integer backlogStatus;

    private String associatedOrder;

    private String startTime;

    private String endTime;

}
