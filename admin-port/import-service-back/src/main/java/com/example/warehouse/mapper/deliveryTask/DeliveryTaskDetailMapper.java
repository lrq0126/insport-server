package com.example.warehouse.mapper.deliveryTask;

import com.example.warehouse.entity.deliveryTask.DeliveryTaskDetail;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.deliveryTask.DeliveryTaskDetailRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 发货任务明细(DeliveryTaskDetail)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/12/22 16:56
*/
@Repository
public interface DeliveryTaskDetailMapper extends MyBatisBaseDao<DeliveryTaskDetail, Integer> {

    @Override
    int insert(DeliveryTaskDetail record);

    @Override
    int insertSelective(DeliveryTaskDetail record);

    @Override
    DeliveryTaskDetail selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(DeliveryTaskDetail record);

    @Override
    int updateByPrimaryKey(DeliveryTaskDetail record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

	List<DeliveryTaskDetailRespVo> selectDetailByTaskId(int taskId);

    int deleteByTaskId(Integer taskId);

    void insertList(@Param("deliveryTaskDetailList") List<DeliveryTaskDetail> deliveryTaskDetailList);
}