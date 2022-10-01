package com.example.warehouse.service.activityReward;


import com.example.warehouse.entity.activityReward.ActivityPoster;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.activity.ActivityPosterReqVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
* 活动海报(ActivityPoster)表服务类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/23 11:18
*/
public interface ActivityPosterService{

    ResponseEntity<PageResultModel> getActivityPosterList(ActivityPosterReqVo activityPosterReqVo);

    ResponseEntity<ResultModel> getActivityPoster(int id);

    ResponseEntity<ResultModel> saveActivityPoster(ActivityPoster activityPoster);

    ResponseEntity<ResultModel> checkPosterModel(String posterType);

    ResponseEntity<ResultModel> generatePoster(int id);

    ResponseEntity<ResultModel> deleteActivityPoster(int id);

    ResponseEntity<ResultModel> enableActivityPoster(int id);

    ResponseEntity<ResultModel> unEnableActivityPoster(int id);

    void deletePosterTask();
}