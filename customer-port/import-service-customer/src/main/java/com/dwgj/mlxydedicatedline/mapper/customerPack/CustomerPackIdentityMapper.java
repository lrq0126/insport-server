package com.dwgj.mlxydedicatedline.mapper.customerPack;

import com.dwgj.mlxydedicatedline.entity.customerPack.CustomerPackIdentity;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
* 客户订单身份证信息(CustomerPackIdentity)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/10/12 14:10
*/
@Repository
public interface CustomerPackIdentityMapper extends MyBatisBaseDao<CustomerPackIdentity, Integer> {

    @Override
    int insert(CustomerPackIdentity record);

    @Override
    int insertSelective(CustomerPackIdentity record);

    @Override
    CustomerPackIdentity selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerPackIdentity record);

    @Override
    int updateByPrimaryKey(CustomerPackIdentity record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    void deleteByCustomerPackId(Integer customerPackId);

    CustomerPackIdentity selectIdentityByPackId(Integer packId);
}