package com.example.warehouse.mapper.customer;

import com.example.warehouse.entity.customer.CustomerIntegralRule;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.integralRule.CustomerIntegralRuleReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 积分规则(CustomerIntegralRule)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/10 14:18
*/
@Repository
public interface CustomerIntegralRuleMapper extends MyBatisBaseDao<CustomerIntegralRule, Integer> {

    @Override
    int insert(CustomerIntegralRule record);

    @Override
    int insertSelective(CustomerIntegralRule record);

    @Override
    CustomerIntegralRule selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerIntegralRule record);

    @Override
    int updateByPrimaryKey(CustomerIntegralRule record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int selectIntegralRuleCount(CustomerIntegralRuleReqVo integralRuleReqVo);

    List<CustomerIntegralRule> getIntegralRuleByIds(@Param("integralIdList") List<Integer> integralIdList);

    List<CustomerIntegralRule> getIntegralRuleList(CustomerIntegralRuleReqVo integralRuleReqVo);

    CustomerIntegralRule getIntegralRuleByIntegralType(String popularize);

    List<CustomerIntegralRule> getAllIntegralRule();
}