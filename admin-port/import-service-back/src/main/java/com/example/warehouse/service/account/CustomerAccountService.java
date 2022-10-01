package com.example.warehouse.service.account;

import com.example.warehouse.entity.AgentOrderAccount;
import com.example.warehouse.entity.CustomerAccount;
import com.example.warehouse.entity.CustomerAccountVo;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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
     * 客户充值-扣费统计列表
     * @param map
     * @return
     */
    ResponseEntity<PageResultModel> accountStatisticList(Map<String,Object> map);

    ResponseEntity<PageResultModel> selectSingleCustomerAccountList(Map<String,Object> map);
//    int countSelectSingleCustomerAccountList(Map<String,Object> map);
//    BigDecimal sumSingleYmt(Map<String,Object> map);

    int recharge(Map<String,Object> map);

    int rechargeSave(Map<String,Object> map);

    int rollbackRacharge(CustomerAccount account);
    /**
     * 查询已支付的订单
     * @param map
     * @return
     */
    ResponseEntity<PageResultModel> selectHavePaidList(Map<String,Object> map);
    int countHavePaidList(Map<String,Object> map);

    AgentOrderAccount getSingleSettleMentOrder(String orderNumber);

    AgentOrderAccount saveSingleSettleMentOrder(AgentOrderAccount agentOrderAccount);

    /**
     * 账户清零
     * @param customerId
     * @return
     */
    int accountReset(String customerId);

    void exportCustomerRecharge(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<PageResultModel> getCustomerTopUpHistory(Map<String, Object> requestMap);
}

