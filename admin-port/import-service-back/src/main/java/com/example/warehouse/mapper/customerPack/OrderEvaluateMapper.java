package com.example.warehouse.mapper.customerPack;

import com.example.warehouse.entity.customerPack.OrderEvaluate;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.packVo.OrderEvaluateReqVo;
import com.example.warehouse.vo.packVo.OrderEvaluateRespVo;
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

    int selectCount(OrderEvaluateReqVo orderEvaluateReqVo);

    List<OrderEvaluateRespVo> getEvaluateList(OrderEvaluateReqVo orderEvaluateReqVo);

    List<OrderEvaluateRespVo> getEvaluateListNotPage(OrderEvaluateReqVo orderEvaluateReqVo);
}