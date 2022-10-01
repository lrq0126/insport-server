package com.example.warehouse.mapper.activityReward;

import com.example.warehouse.entity.activityReward.ActivityPoster;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.activity.ActivityPosterReqVo;
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

    int selectPosterCount(ActivityPosterReqVo activityPosterReqVo);

    List<ActivityPoster> getActivityPosterList(ActivityPosterReqVo activityPosterReqVo);

    int enableActivityPoster(int id);

    int unEnableActivityPoster(int id);

    int unEnableAll(String posterType);

    ActivityPoster selectEnablePoster(String posterType);
}