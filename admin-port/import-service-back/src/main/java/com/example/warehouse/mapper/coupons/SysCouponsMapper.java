package com.example.warehouse.mapper.coupons;

import com.example.warehouse.entity.coupons.SysCoupons;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.coupons.request.SysCouponsReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 优惠券表(SysCoupons)表Mapper类
*
* @author LiangRongQing
* @version 1.0
* @since 2021/10/14 17:12
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

    List<SysCoupons> getCouponsByIds(@Param("idList") List<Integer> idList);
    /**
     * 查询优惠券总数
     * @param sysCouponsReqVo
     * @return
     */
    int selectCount(SysCouponsReqVo sysCouponsReqVo);

    List<SysCoupons> selectCouponsList(SysCouponsReqVo sysCouponsReqVo);

    List<SysCoupons> getCouponsListNotPage();

    void disableOverdueCoupons();

    List<SysCoupons> selectOverdueCouponsList();

    /**
     * 查询所有优惠券编码
     * @return
     */
    List<String> selectAllCouponsCode();

    int selectCouponsByCouponsName(SysCoupons sysCoupons);

    SysCoupons selectByCouponsCode(@Param("couponsCode") String couponsCode);
}