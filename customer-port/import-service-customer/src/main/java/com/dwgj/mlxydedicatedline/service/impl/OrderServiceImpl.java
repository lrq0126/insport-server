package com.dwgj.mlxydedicatedline.service.impl;

import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.entity.Order;
import com.dwgj.mlxydedicatedline.mapper.GoodsMapper;
import com.dwgj.mlxydedicatedline.mapper.OrderMapper;
import com.dwgj.mlxydedicatedline.service.OrderService;
import com.dwgj.mlxydedicatedline.utils.GoodsUtil;
import com.dwgj.mlxydedicatedline.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsUtil goodsUtil;
    @Override
    public List<OrderVo> findOrder(Map<String, Object> map) {

        if(map.containsKey("orderTime") && !"".equals(map.get("orderTime").toString())) {
            timeChange(map);
        }
        List<OrderVo> orderList = orderMapper.findOrder(map);

        return orderList;
    }

    @Override
    public int countSelectAll(Map<String, Object> map) {
        return orderMapper.countSelectAll(map);
    }

    @Override
    public int updateByDeliveryOrderNo(Order order,Integer customerId) {
        Goods goods = goodsMapper.selectByPrimaryKey(order.getId());
        goods.setGoodsName(order.getGoodsName());
        goods.setPackageNum(order.getGoodsNumber());
        goods.setMessage(order.getRemark());
        goods.setDeliveryNo(order.getDeliveryNo());
        goods.setAddressId(order.getAddressId());
        goods.setDeliveryOrderNo(order.getDeliveryOrderNo());
        goodsUtil.goodsNameCheck(goods);
        int i = goodsMapper.updateByPrimaryKeySelective(goods);
        return i;
    }

    @Override
    public int updateByGoodsId(Goods g,Integer customerId) {
        log.info("goods:{}",g);
        Goods goods = goodsMapper.selectByPrimaryKey(g.getId());
        g.setVersion(goods.getVersion() + 1);
        int i = goodsMapper.updateByPrimary(g);
        return i;
    }

    @Override
    public int deleteByid(int id) {
        return goodsMapper.deleteById(id);
    }

    public Map timeChange(Map<String, Object> map){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String outStorageTime = (String) map.get("orderTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formatted = LocalDate.parse(outStorageTime,formatter);
        //设置零点
        LocalDateTime beginTime = LocalDateTime.of(formatted,LocalTime.MIN);
        String time1= beginTime.format(dtf);
        //设置当天的结束时间
        LocalDateTime endTime = LocalDateTime.of(formatted,LocalTime.MAX);
        String time2 =dtf.format(endTime);
        map.put("startTime",time1);
        map.put("endTime",time2);
        return map;
    }
}
