package com.dwgj.mlxydedicatedline.service.impl;

import com.dwgj.mlxydedicatedline.entity.DeliveryCompany;
import com.dwgj.mlxydedicatedline.mapper.DeliveryCompanyMapper;
import com.dwgj.mlxydedicatedline.service.DeliveryCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryCompanyServiceImpl implements DeliveryCompanyService {

    @Autowired
    private DeliveryCompanyMapper deliveryCompanyMapper;

    @Override
    public List<DeliveryCompany> findAll() {
        return deliveryCompanyMapper.findAll();
    }
}
