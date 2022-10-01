package com.dwgj.mlxydedicatedline.mapper.coupons;

import com.dwgj.mlxydedicatedline.entity.coupons.SysCoupons;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 优惠券表(SysCoupons)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/10/25 13:38
*/
@Repository
public interface SysCouponsMapper extends MyBatisBaseDao<SysCoupons, Integer> {

    @Override
    int insert(SysCoupons record);

    @Override
    int insertSelective(SysCoupons record);

    @Override
    SysCoupons selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(SysCoupons record);

    @Override
    int updateByPrimaryKey(SysCoupons record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

	SysCoupons selectByCouponsCode(@Param("couponsCode")String sysCoupons);

    List<SysCoupons> selectEnableListBySysCouponsIds(@Param("sysCouponsIdList") List<Integer> sysCouponsIdList);
}