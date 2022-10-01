package com.dwgj.mlxydedicatedline.mapper.ping;

import com.dwgj.mlxydedicatedline.entity.ping.PingPackGoods;
import org.springframework.stereotype.Repository;

@Repository
public interface PingPackGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingPackGoods record);

    int insertSelective(PingPackGoods record);

    PingPackGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingPackGoods record);

    int updateByPrimaryKey(PingPackGoods record);
}