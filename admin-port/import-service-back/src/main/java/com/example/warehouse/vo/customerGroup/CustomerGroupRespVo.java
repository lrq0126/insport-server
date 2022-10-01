package com.example.warehouse.vo.customerGroup;

import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.customerGroup.CustomerGroup;
import lombok.Data;

import java.util.List;

@Data
public class CustomerGroupRespVo extends CustomerGroup {

    private Integer id;

    private String createName;

    private Integer groupCustomerNum;

    private List<Customer> customer;

}
