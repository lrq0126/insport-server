package com.dwgj.mlxydedicatedline.mapper.coupons;

import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCoupons;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 客户优惠券表(CustomerCoupons)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/10/25 10:57
*/
@Repository
public interface CustomerCouponsMapper extends MyBatisBaseDao<CustomerCoupons, Integer> {

    @Override
    int insert(CustomerCoupons record);

    @Override
    int insertSelective(CustomerCoupons record);

    @Override
    CustomerCoupons selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerCoupons record);

    @Override
    int updateByPrimaryKey(CustomerCoupons record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int insertList(@Param("customerCouponsList") List<CustomerCoupons> customerCouponsList);

    List<CustomerCoupons> getMyCouponsList(int customerId);

    List<CustomerCoupons> getHaveToReceiveCouponsList(int customerId);

    CustomerCoupons selectByCenterId(@Param("customerId") int customerId, @Param("centerCouponsId") int centerCouponsId);


    CustomerCoupons findByOrderNumber(@Param("customerId") int customerId, @Param("orderNumber") String orderNumber);
}