package com.dwgj.mlxydedicatedline.mapper.customer;

import com.dwgj.mlxydedicatedline.entity.customer.CustomerIntegralRule;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
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

    List<CustomerIntegralRule> getIntegralRuleList(@Param("integralType") String integralType);

    // 查询启动中的积分规则
    List<CustomerIntegralRule> selectEnableListByIntegralRuleIds(@Param("integralIdList") List<Integer> integralIdList);
}