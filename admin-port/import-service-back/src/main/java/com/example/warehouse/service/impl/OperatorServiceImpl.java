package com.example.warehouse.service.impl;

import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.Operator;
import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.OperatorMapper;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorMapper operatorMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(Operator operator) {
        // TODO  添加operator_code
        String code = SequenceCode.gainSerialNo("OPR");
        operator.setOperatorCode(code);
        return operatorMapper.insert(operator);
    }

    @Override
    public int update(Operator operator) {
        return operatorMapper.updateByPrimaryKey(operator);
    }

    @Override
    public int del(String operatorNo, String usercode) {
        User user = userMapper.findByCode(usercode);
        return operatorMapper.del(operatorNo, user.getId());
    }

    @Override
    public List<Operator> findAll() {
        return operatorMapper.selectAll();
    }

    @Override
    public List<Operator> find(Map<String, Object> map) {
        return operatorMapper.find(map);
    }
}
