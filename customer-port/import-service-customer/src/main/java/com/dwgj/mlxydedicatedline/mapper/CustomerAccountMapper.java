package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.CustomerAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerAccountMapper {
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
    float sumSingleCustomerYmt(Map<String,Object> map);

    /**
     * 查询用户账单信息
     * @param customerId
     * @return
     */
    List<CustomerAccount> selectCustomerAccountByCustomerId(Integer customerId);

    /**
     * 更改金额明细的绑定客户（用于微信客户多次点击关注，多个账号绑定同一个微信号的时候）
     * @param updateCustomerAccountList
     * @param customer
     */
    void updateCustomerId(@Param("accountList") List<CustomerAccount> updateCustomerAccountList,
                          @Param("customer") Customer customer);
}