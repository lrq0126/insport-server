package com.dwgj.mlxydedicatedline.mapper.customer;

import com.dwgj.mlxydedicatedline.entity.customer.CustomerIdentity;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import com.dwgj.mlxydedicatedline.vo.customer.CustomerIdentityRespVo;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

/**
* 客户身份证信息(CustomerIdentity)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/9/28 14:49
*/
@Repository
public interface CustomerIdentityMapper extends MyBatisBaseDao<CustomerIdentity, Integer> {

    @Override
    int insert(CustomerIdentity record);

    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertSelective(CustomerIdentity record);

    @Override
    CustomerIdentity selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerIdentity record);

    @Override
    int updateByPrimaryKey(CustomerIdentity record);
	
	@Override
    int deleteByPrimaryKey(Integer id);



    CustomerIdentityRespVo getIdentityInfo(int id);
}