package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.sys.LogRequest;
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
