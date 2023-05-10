package com.example.warehouse.vo.customerPack;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

import java.util.List;

@Data
public class RouteCustomerPackReqVo extends PageVo {

    private String customerName;
    private String loginName;
    private String businessNumber;
    private Integer routeId;
    private String customerPackIds;

}
