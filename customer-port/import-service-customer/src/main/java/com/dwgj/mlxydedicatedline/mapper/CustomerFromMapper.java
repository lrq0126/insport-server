package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.CustomerFrom;
import com.dwgj.mlxydedicatedline.vo.customer.PopularizeDetailReqVo;
import com.dwgj.mlxydedicatedline.vo.customer.PopularizeDetailRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 客户来源表(CustomerFrom)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/17 10:0
*/
@Repository
public interface CustomerFromMapper extends MyBatisBaseDao<CustomerFrom, Integer> {

    @Override
    int insert(CustomerFrom record);

    @Override
    int insertSelective(CustomerFrom record);

    @Override
    CustomerFrom selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerFrom record);

    @Override
    int updateByPrimaryKey(CustomerFrom record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int selectPopularizeDetailCount(PopularizeDetailReqVo popularizeDetailReqVo);

    List<PopularizeDetailRespVo> selectPopularizeDetailByCustomerId(PopularizeDetailReqVo popularizeDetailReqVo);

    List<PopularizeDetailRespVo> selectChildren(@Param("customerId") int customerId);
}