package com.dwgj.mlxydedicatedline.service.impl;

import com.dwgj.mlxydedicatedline.entity.CustomerAccount;
import com.dwgj.mlxydedicatedline.mapper.CustomerAccountMapper;
import com.dwgj.mlxydedicatedline.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomerAccountServiceImpl implements CustomerAccountService {
    @Autowired
    private CustomerAccountMapper customerAccountMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return customerAccountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CustomerAccount record) {
        return customerAccountMapper.insert(record);
    }

    @Override
    public int insertSelective(CustomerAccount record) {
        return customerAccountMapper.insertSelective(record);
    }

    @Override
    public CustomerAccount selectByPrimaryKey(Integer id) {
        return customerAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CustomerAccount record) {
        return customerAccountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CustomerAccount record) {
        return customerAccountMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CustomerAccount> selectCustomerAccountList(Map<String, Object> map) {
        return customerAccountMapper.selectCustomerAccountList(map);
    }

    @Override
    public int countSelectCustomerAccountList(Map<String, Object> map) {
        return customerAccountMapper.countSelectCustomerAccountList(map);
    }

    @Override
    public float sumSingleCustomerYmt(Map<String, Object> map) {
        return customerAccountMapper.sumSingleCustomerYmt(map);
    }
}
