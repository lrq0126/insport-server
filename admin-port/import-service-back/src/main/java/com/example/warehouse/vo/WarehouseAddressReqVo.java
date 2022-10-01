package com.example.warehouse.vo;

import lombok.Data;

@Data
public class WarehouseAddressReqVo extends PageVo {

    private String addressee;
    private String phoneNumber;
    private String abnormalRelation;
    private String postcode;
    private String address;
    private String companyName;
    private Integer isEnable;

}
