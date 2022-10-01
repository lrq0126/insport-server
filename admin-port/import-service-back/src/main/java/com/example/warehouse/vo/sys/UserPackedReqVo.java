package com.example.warehouse.vo.sys;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class UserPackedReqVo extends PageVo {

    private String createName;

    private String customerName;

    private String loginName;

    private String orderNumber;

    private String routeName;

    private String destination;

    private Integer routeType;

    private Integer packType;

    private String startTime;
    private String endTime;

}
