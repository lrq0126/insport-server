package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.sys.LogOperation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LogOperationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogOperation record);

    int insertSelective(LogOperation record);

    LogOperation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogOperation record);

    int updateByPrimaryKey(LogOperation record);

    List<LogOperation> getAllLogList(Map<String,Object> map);

    int countAllLogList(Map<String,Object> map);
}