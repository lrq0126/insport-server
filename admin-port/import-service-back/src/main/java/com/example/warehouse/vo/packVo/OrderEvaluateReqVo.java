package com.example.warehouse.vo.packVo;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class OrderEvaluateReqVo extends PageVo {

    private String orderNumber;

    private String customerId;
    private String customerName;

    private String loginName;

    private String routeName;

    private String transportType;

    private String startTime;

    private String endTime;

}
