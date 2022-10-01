package com.dwgj.mlxydedicatedline.mapper.customerPack;

import com.dwgj.mlxydedicatedline.entity.customerPack.OrderEvaluate;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import com.dwgj.mlxydedicatedline.vo.orderEvaluate.OrderEvaluateReqVo;
import com.dwgj.mlxydedicatedline.vo.orderEvaluate.OrderEvaluateRespVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 订单评价(OrderEvaluate)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/12/30 11:23
*/
@Repository
public interface OrderEvaluateMapper extends MyBatisBaseDao<OrderEvaluate, Integer> {

    @Override
    int insert(OrderEvaluate record);

    @Override
    int insertSelective(OrderEvaluate record);

    @Override
    OrderEvaluate selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(OrderEvaluate record);

    @Override
    int updateByPrimaryKey(OrderEvaluate record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int getEvaluateCount(OrderEvaluateReqVo orderEvaluateReqVo);

    List<OrderEvaluateRespVo> getEvaluateList(OrderEvaluateReqVo orderEvaluateReqVo);

    OrderEvaluateRespVo selectEvaluateByPackId(int packId);
}