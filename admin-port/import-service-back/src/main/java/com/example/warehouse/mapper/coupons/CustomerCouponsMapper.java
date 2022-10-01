package com.example.warehouse.mapper.coupons;

import com.example.warehouse.entity.coupons.CustomerCoupons;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.coupons.request.CustomerCouponsReqVo;
import com.example.warehouse.vo.coupons.response.CustomerCouponsRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 客户优惠券表(CustomerCoupons)表Mapper类
*
* @author LiangRongQing
* @version 1.0
* @since 2021/10/14 17:13
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

    void deleteByIdList(@Param("idList") List<String> idList);

	int removeByPrimaryKey(Integer id);

    int selectCount(CustomerCouponsReqVo customerCouponsReqVo);

    List<CustomerCouponsRespVo> selectCustomerCouponsList(CustomerCouponsReqVo customerCouponsReqVo);

    void deleteCustomerOverdueCoupons();

    void insertList(@Param("customerCouponsList") List<CustomerCoupons> customerCouponsList);

    List<CustomerCoupons> selectCustomerCouponsByCustomerId(int customerId);

}