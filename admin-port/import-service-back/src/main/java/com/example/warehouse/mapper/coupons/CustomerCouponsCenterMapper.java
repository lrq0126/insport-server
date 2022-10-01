package com.example.warehouse.mapper.coupons;

import com.example.warehouse.entity.coupons.CustomerCouponsCenter;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.coupons.request.CustomerCouponsReqVo;
import com.example.warehouse.vo.coupons.response.CustomerCouponsCenterRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 客户优惠券领券中心表(CustomerCouponsCenter)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/10/21 11:14
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

    void insertList(@Param("customerCouponsCenterList") List<CustomerCouponsCenter> customerCouponsCenterList);

    int selectIssuedCouponsCount(CustomerCouponsReqVo customerCouponsReqVo);

    List<CustomerCouponsCenterRespVo> getIssuedCouponsList(CustomerCouponsReqVo customerCouponsReqVo);

    void deleteCenterOverdueCoupons();
}