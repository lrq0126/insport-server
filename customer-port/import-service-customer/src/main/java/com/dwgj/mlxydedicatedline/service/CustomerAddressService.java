package com.dwgj.mlxydedicatedline.service;

import com.dwgj.mlxydedicatedline.entity.CustomerAddress;

import java.util.List;
import java.util.Map;

public interface CustomerAddressService {

    List<CustomerAddress> findByCustomerId(Map<String,Object> paramMap);

    int countFindPageNumber(Map<String,Object> paramMap);

    int addNewAddress(Map<String,Object> map);

    int setDefaultAddress(int CustomerAddressId);

    int delteAddress(int customerAddressId);

    int updateAddress(Map<String,Object> map);

}
