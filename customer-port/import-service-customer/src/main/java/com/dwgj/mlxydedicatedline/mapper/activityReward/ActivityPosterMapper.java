package com.dwgj.mlxydedicatedline.mapper.activityReward;

import com.dwgj.mlxydedicatedline.entity.activityReward.ActivityPoster;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 活动海报(ActivityPoster)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/23 11:18
*/
@Repository
public interface ActivityPosterMapper extends MyBatisBaseDao<ActivityPoster, Integer> {

    @Override
    int insert(ActivityPoster record);

    @Override
    int insertSelective(ActivityPoster record);

    @Override
    ActivityPoster selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(ActivityPoster record);

    @Override
    int updateByPrimaryKey(ActivityPoster record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    ActivityPoster selectEnablePoster();

    ActivityPoster selectEnableAdvertisingPoster();
}