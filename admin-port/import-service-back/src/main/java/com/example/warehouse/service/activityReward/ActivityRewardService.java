package com.example.warehouse.service.activityReward;

import com.example.warehouse.entity.activityReward.ActivityReward;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.activity.ActivityRewardReqVo;
import org.springframework.http.ResponseEntity;

/**
* 活动奖励(ActivityReward)表服务类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/18 17:50
*/
public interface ActivityRewardService {

    ResponseEntity<PageResultModel> getActivityRewardList(ActivityRewardReqVo activityRewardReqVo);

    ResponseEntity<ResultModel> getActivityReward(int id);

    ResponseEntity<ResultModel> saveActivityReward(ActivityReward activityReward);

    ResponseEntity<ResultModel> deleteActivityReward(int id);

    ResponseEntity<ResultModel> enableActivityReward(int id, int enableType);
}