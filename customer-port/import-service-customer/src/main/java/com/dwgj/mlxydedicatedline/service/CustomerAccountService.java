package com.dwgj.mlxydedicatedline.service;

import com.dwgj.mlxydedicatedline.entity.CustomerAccount;

import java.util.List;
import java.util.Map;

public interface CustomerAccountService {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerAccount record);

    int insertSelective(CustomerAccount record);

    CustomerAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerAccount record);

    int updateByPrimaryKey(CustomerAccount record);

    /**
     * 查询用户下的账单信息
     * @param map
     * @return
     */
    List<CustomerAccount> selectCustomerAccountList(Map<String,Object> map);

    int countSelectCustomerAccountList(Map<String,Object> map);

    /**
     * 统计余额
     * @param map
     * @return
     */
    float sumSingleCustomerYmt(Map<String,Object> map);
}
