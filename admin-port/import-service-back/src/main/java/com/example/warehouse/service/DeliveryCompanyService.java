package com.example.warehouse.service;

import com.example.warehouse.entity.DeliveryCompany;

import java.util.List;
import java.util.Map;

public interface DeliveryCompanyService {

    int save(DeliveryCompany deliveryCompany);

    int update(DeliveryCompany deliveryCompany);

    int del(String goodsNo, String usercode);

    List<DeliveryCompany> findAll(Map<String,Object> map);

    int countFindAll(Map<String,Object> map);

    List<DeliveryCompany> find(Map<String, Object> map);
}
