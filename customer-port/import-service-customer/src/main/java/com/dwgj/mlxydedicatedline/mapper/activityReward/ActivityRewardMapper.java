package com.dwgj.mlxydedicatedline.mapper.activityReward;

import com.dwgj.mlxydedicatedline.entity.activityReward.ActivityReward;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 活动奖励(ActivityReward)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/18 17:50
*/
@Repository
public interface ActivityRewardMapper extends MyBatisBaseDao<ActivityReward, Integer> {

    @Override
    int insert(ActivityReward record);

    @Override
    int insertSelective(ActivityReward record);

    @Override
    ActivityReward selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(ActivityReward record);

    @Override
    int updateByPrimaryKey(ActivityReward record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    List<ActivityReward> selectByActivityType(@Param("activityType") String activityType);
}