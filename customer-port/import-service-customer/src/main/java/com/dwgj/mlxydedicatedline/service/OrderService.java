package com.dwgj.mlxydedicatedline.service;

import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.entity.Order;
import com.dwgj.mlxydedicatedline.vo.OrderVo;

import java.util.List;
import java.util.Map;

public interface OrderService {

    List<OrderVo> findOrder(Map<String,Object> map);

    int countSelectAll(Map<String,Object> map);

    int updateByDeliveryOrderNo(Order order,Integer customerId);

    int updateByGoodsId(Goods g,Integer customerId);

    int deleteByid(int id);

}
