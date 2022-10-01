package com.dwgj.mlxydedicatedline.mapper.ping;

import com.dwgj.mlxydedicatedline.entity.ping.PingPack;
import org.springframework.stereotype.Repository;

@Repository
public interface PingPackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingPack record);

    int insertSelective(PingPack record);

    PingPack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingPack record);

    int updateByPrimaryKey(PingPack record);
}