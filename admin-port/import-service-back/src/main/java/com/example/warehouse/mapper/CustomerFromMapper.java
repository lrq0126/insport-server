package com.example.warehouse.mapper;

import com.example.warehouse.entity.CustomerFrom;
import com.example.warehouse.vo.customer.PopularizeDetailReqVo;
import com.example.warehouse.vo.customer.PopularizeDetailRespVo;
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

    int selectPopularizeSum(@Param("customerId") int customerId);

    Integer selectFromIdByCustomerId(@Param("customerId") Integer customerId);
}