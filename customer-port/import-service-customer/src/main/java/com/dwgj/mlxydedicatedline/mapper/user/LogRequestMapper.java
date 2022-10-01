package com.dwgj.mlxydedicatedline.mapper.user;

import com.dwgj.mlxydedicatedline.entity.user.LogRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRequestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogRequest record);

    int insertSelective(LogRequest record);

    LogRequest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogRequest record);

    int updateByPrimaryKey(LogRequest record);
}