package com.dwgj.mlxydedicatedline.mapper.customerMoney;

import com.dwgj.mlxydedicatedline.entity.CustomerMoney;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CustomerMoneyMapper继承基类
 */
@Repository
public interface CustomerMoneyMapper extends MyBatisBaseDao<CustomerMoney, Integer> {
    int deleteByCustomerId(int customerId);

    /**
     * 根据客户代码查询数据
     * @param customerNo
     * @return
     */
    CustomerMoney selectByCustomerNo(String customerNo);

    /**
     * 修改余额
     * @param customerMoney
     */
    void updateMoneyByPrimaryKeySelective(CustomerMoney customerMoney);
    /**
     * 更改客户金额的状态（用于微信客户多次点击关注，多个账号绑定同一个微信号的时候）
     * @param customerMoneyList
     * @param customerNo
     */
    void updateCustomerNo(@Param("customerMoneyList") List<CustomerMoney> customerMoneyList,
                          @Param("customerNo") String customerNo);
}