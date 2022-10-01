package com.example.warehouse.mapper;

import com.example.warehouse.entity.AgentOrderAccount;
import com.example.warehouse.entity.CustomerAccount;
import com.example.warehouse.entity.CustomerAccountVo;
import com.example.warehouse.vo.account.customerAccount.AccountDeductionVo;
import com.example.warehouse.vo.account.customerAccount.AccountTopVo;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
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

    /**
     * 财务 -- 客户账单列表
     * @param map
     * @return
     */
    List<CustomerAccountVo> accountStatisticList(Map<String,Object> map);

    AccountTopVo selectAccountTop(Integer id);
    AccountDeductionVo selectAccountDeduction(Integer id);

    int countAccountStatisticList(Map<String,Object> map);

    float countCustomerAmount(Map<String,Object> map);

    /**
     * 查询某个客户充值、扣费记录
     * @param map
     * @return
     */
    List<CustomerAccount> selectSingleCustomerAccountList(Map<String,Object> map);
    int countSelectSingleCustomerAccountList(Map<String,Object> map);

    BigDecimal sumSingleYmt(Map<String,Object> map); // 查询总余额

    /**
     * 查询已支付的订单
     * @param map
     * @return
     */
    List<CustomerAccount> selectHavePaidList(Map<String,Object> map);
    int countHavePaidList(Map<String,Object> map);

    /**
     * 根据订单号获取结算成本的信息
     * @param orderNumber
     * @return
     */
    AgentOrderAccount getSingleSettleMentOrder(String orderNumber);

    /**
     * 查询用户余额：用于数据余额清零
     * @param loginName
     * @return
     */
    float getAmountByLoginName(String loginName);
}
