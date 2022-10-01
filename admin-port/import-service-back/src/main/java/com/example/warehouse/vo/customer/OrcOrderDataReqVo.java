package com.example.warehouse.vo.customer;

import lombok.Data;

@Data
public class OrcOrderDataReqVo {

    private String orderNumber;

    private Double weight;

    private Double length;
    private Double width;
    private Double height;

}
