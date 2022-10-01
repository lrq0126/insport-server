package com.example.warehouse.mapper;

import com.example.warehouse.entity.DeliveryCompany;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface DeliveryCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeliveryCompany record);

    DeliveryCompany selectByPrimaryKey(Integer id);

    List<DeliveryCompany> selectAll(Map<String,Object> map);

    int countSelectAll(Map<String,Object> map);

    int updateByPrimaryKey(DeliveryCompany record);

    int del(@Param("deliveryNo")String deliveryNo, @Param("userId")int userId);

    List<DeliveryCompany> find(Map<String,Object> map);
}