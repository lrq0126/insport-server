package com.example.warehouse.service;

import com.example.warehouse.entity.CustomerAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerAddressService {
    int save(CustomerAddress record);

    List<CustomerAddress> findAll(Map<String,Object> map);

    int countFindAll(Map<String,Object> map);

    int update(CustomerAddress record);

    int del(Integer addressId);

    List<CustomerAddress> find(Map<String,Object> map);

    List<CustomerAddress> findByName(String name);

    List<CustomerAddress> findByCustomerId(int customerId);
}
