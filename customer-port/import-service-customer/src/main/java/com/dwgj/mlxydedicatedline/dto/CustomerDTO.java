package com.dwgj.mlxydedicatedline.dto;

import com.dwgj.mlxydedicatedline.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO extends Customer {

    private List<Goods> goodsList;

    private List<CustomerPack> customerPackList;

    private List<CustomerAccount> customerAccountList;

    private CustomerMoney customerMoney;

}
