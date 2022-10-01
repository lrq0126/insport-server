package com.example.warehouse.vo.customer;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class CustomerReqVo extends PageVo {

    private Integer id;
    private String customerName;
    private String englishName;
    private String loginName;
    private String phoneNumber;
    private String callNumber;
    private String email;
    private String fullAddress;
    private String fromType;
    private String fromUserName;
    private String fromCustomerLoginName;
}
