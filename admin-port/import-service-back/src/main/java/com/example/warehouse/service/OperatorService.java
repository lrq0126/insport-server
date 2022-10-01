package com.example.warehouse.service;

import com.example.warehouse.entity.Operator;

import java.util.List;
import java.util.Map;

public interface OperatorService {

    int save(Operator operator);

    int update(Operator operator);

    int del(String goodsNo, String usercode);

    List<Operator> findAll();

    List<Operator> find(Map<String, Object> map);
}
