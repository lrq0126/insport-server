package com.dwgj.mlxydedicatedline.mapper.customerGroup;

import com.dwgj.mlxydedicatedline.entity.customerGroup.CustomerGroup;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 客户组表(CustomerGroup)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/10/25 11:35
*/
@Repository
public interface CustomerGroupMapper extends MyBatisBaseDao<CustomerGroup, Integer> {

    @Override
    int insert(CustomerGroup record);

    @Override
    int insertSelective(CustomerGroup record);

    @Override
    CustomerGroup selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerGroup record);

    @Override
    int updateByPrimaryKey(CustomerGroup record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    List<Integer> selectGroupIdByCustomerId(int customerId);
}