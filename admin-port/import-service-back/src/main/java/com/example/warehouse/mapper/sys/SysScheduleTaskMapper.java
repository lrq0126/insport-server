package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.sys.SysScheduleTask;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.sys.SysScheduleTaskReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 定时任务表(SysScheduleTask)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/8/8 10:57
*/
@Repository
public interface SysScheduleTaskMapper extends MyBatisBaseDao<SysScheduleTask, Integer> {

    @Override
    int insert(SysScheduleTask record);

    @Override
    int insertSelective(SysScheduleTask record);

    @Override
    SysScheduleTask selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(SysScheduleTask record);

    @Override
    int updateByPrimaryKey(SysScheduleTask record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int selectScheduleTaskCount(SysScheduleTaskReqVo sysScheduleTaskReqVo);

    List<SysScheduleTask> selectScheduleTask(SysScheduleTaskReqVo sysScheduleTaskReqVo);

    SysScheduleTask selectTaskByTaskCode(String taskCode);

    void updateTaskEnable(@Param("id") Integer id, @Param("isEnable") Integer isEnable);
}