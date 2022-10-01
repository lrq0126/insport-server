package com.example.warehouse.mapper.customer;

import com.example.warehouse.entity.customer.CustomerIdentity;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.PageVo;
import com.example.warehouse.vo.customer.CustomerIdentityRespVo;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
	
	int removeByPrimaryKey(Integer id);

    int getIdentityCount(@Param("customerId") int customerId);

    List<CustomerIdentityRespVo> getIdentityList(@Param("customerId") int customerId, @Param("page") PageVo pageVo);

    CustomerIdentityRespVo getIdentityInfo(int id);
}