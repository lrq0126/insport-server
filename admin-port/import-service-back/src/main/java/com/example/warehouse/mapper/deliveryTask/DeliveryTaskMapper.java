package com.example.warehouse.mapper.deliveryTask;

import com.example.warehouse.entity.deliveryTask.DeliveryTask;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.deliveryTask.DeliveryTaskReqVo;
import com.example.warehouse.vo.deliveryTask.DeliveryTaskRespVo;
import com.example.warehouse.vo.deliveryTask.DeliveryTaskSaveReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 发货任务中心(DeliveryTask)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/12/22 16:56
*/
@Repository
public interface DeliveryTaskMapper extends MyBatisBaseDao<DeliveryTask, Integer> {

    @Override
    int insert(DeliveryTask record);

    @Override
    int insertSelective(DeliveryTask record);

    @Override
    DeliveryTask selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(DeliveryTask record);

    @Override
    int updateByPrimaryKey(DeliveryTask record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    /**
     * 查询当天的任务数量
     * @return
     */
    int selectTodayCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int selectCount(DeliveryTaskReqVo deliveryTaskReqVo);

    DeliveryTaskSaveReqVo selectByTaskOrder(String taskOrder);

    List<DeliveryTask> selectDeliveryTaskList(DeliveryTaskReqVo deliveryTaskReqVo);

    List<DeliveryTask> selectDeliveryTaskAllList(DeliveryTaskReqVo deliveryTaskReqVo);

    DeliveryTaskRespVo selectDeliveryTaskById(Integer id);
}