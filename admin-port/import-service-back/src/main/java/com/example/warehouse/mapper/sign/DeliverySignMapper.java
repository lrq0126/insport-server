package com.example.warehouse.mapper.sign;

import com.example.warehouse.entity.sign.DeliverySign;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeliverySignMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeliverySign record);

    int insertSelective(DeliverySign record);

    DeliverySign selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeliverySign record);

    int updateByPrimaryKey(DeliverySign record);

    List<DeliverySign> getList(Map<String,Object> map);

    int countList(Map<String,Object> map);
}
