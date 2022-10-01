package com.dwgj.mlxydedicatedline.mapper.coupons;

import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCouponsCenter;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 客户优惠券领券中心表(CustomerCouponsCenter)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/10/25 10:57
*/
@Repository
public interface CustomerCouponsCenterMapper extends MyBatisBaseDao<CustomerCouponsCenter, Integer> {

    @Override
    int insert(CustomerCouponsCenter record);

    @Override
    int insertSelective(CustomerCouponsCenter record);

    @Override
    CustomerCouponsCenter selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerCouponsCenter record);

    @Override
    int updateByPrimaryKey(CustomerCouponsCenter record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    List<CustomerCouponsCenter> getCouponsCenterList(@Param("couponsCenterIdList") List<Integer> couponsCenterIdList,
                              @Param("groupIdList") List<Integer> groupIdList);

    List<CustomerCouponsCenter> getCouponsCenterExchangeList();
}