package com.example.warehouse.mapper.customerMoney;

import com.example.warehouse.entity.CustomerMoney;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
 * CustomerMoneyMapper继承基类
 */
@Repository
public interface CustomerMoneyMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerMoney customerMoney);

    int insertSelective(CustomerMoney customerMoney);

    CustomerMoney selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(CustomerMoney customerMoney);

    int updateByPrimaryKeySelective(CustomerMoney customerMoney);

    int deleteByCustomerId(String customerId);

    CustomerMoney selectByCustomerNo(String customerNo);

    int amountReset(String customerNo);
}
