package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.Order;
import com.dwgj.mlxydedicatedline.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * OrderMapper继承基类
 */
@Repository
public interface OrderMapper extends MyBatisBaseDao<Order, Integer> {

    List<OrderVo> findOrder(Map<String, Object> map);

    int countSelectAll(Map<String, Object> map);

    Order findByDeliveryOrderNo(@Param("deliveryOrderNo") String deliveryOrderNo, @Param("customerId") Integer customerId);

    int updateByDeliveryOrderNo(Order order);

    int deleteByid(int id);
}