package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.DeliveryCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DeliveryCompanyMapper继承基类
 */
@Repository
public interface DeliveryCompanyMapper extends MyBatisBaseDao<DeliveryCompany, Integer> {

    List<DeliveryCompany> findAll();

}