package com.example.warehouse.vo.customer;

import com.example.warehouse.entity.Customer;
import lombok.Data;

@Data
public class CustomerRespVo extends Customer {

    private Double integrals;
    private String fromType;
    private String fromUserName;

    private String fromCustomerId;
    private String fromLoginName;
    private String fromCustomerName;
    private String fromCustomerNo;

}
