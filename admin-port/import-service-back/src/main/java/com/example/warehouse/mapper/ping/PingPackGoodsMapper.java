package com.example.warehouse.mapper.ping;

import com.example.warehouse.entity.ping.PingPackGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PingPackGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingPackGoods record);

    int insertSelective(PingPackGoods record);

    PingPackGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingPackGoods record);

    int updateByPrimaryKey(PingPackGoods record);

    int insertList(@Param("pingPackGoodsList") List<PingPackGoods> pingPackGoodsList);

    List<PingPackGoods> selectByPPackId(int pPackId);

}