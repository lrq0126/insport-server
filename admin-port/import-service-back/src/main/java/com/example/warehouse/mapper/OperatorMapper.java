package com.example.warehouse.mapper;

import com.example.warehouse.entity.Operator;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface OperatorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Operator record);

    Operator selectByPrimaryKey(Integer id);

    List<Operator> selectAll();

    int updateByPrimaryKey(Operator record);

    int del(String operatorCode, int userId);

    List<Operator> find(Map<String,Object> map);

    Operator findByName(String operatorName);
}