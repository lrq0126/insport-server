package com.example.warehouse.controller.activityReward;

import com.example.warehouse.entity.activityReward.ActivityPoster;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.activityReward.ActivityPosterService;
import com.example.warehouse.vo.activity.ActivityPosterReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ActivityPoster")
public class ActivityPosterController {
    @Autowired
    private ActivityPosterService activityPosterService;

    @PostMapping("getActivityPosterList")
    public ResponseEntity<PageResultModel> getActivityPosterList(ActivityPosterReqVo activityPosterReqVo){
        return activityPosterService.getActivityPosterList(activityPosterReqVo);
    }

    @PostMapping("getActivityPoster")
    public ResponseEntity<ResultModel> getActivityPoster(int id){
        return activityPosterService.getActivityPoster(id);
    }

    @PostMapping("saveActivityPoster")
    public ResponseEntity<ResultModel> saveActivityPoster(ActivityPoster activityPoster){
        return activityPosterService.saveActivityPoster(activityPoster);
    }
    // 生成海报
    @PostMapping("generatePoster")
    public ResponseEntity<ResultModel> generatePoster(int id){
        return activityPosterService.generatePoster(id);
    }

    // 检验海报
    @GetMapping("checkPosterModel")
    public ResponseEntity<ResultModel> checkPosterModel(String posterType){
        return activityPosterService.checkPosterModel(posterType);
    }

    @PostMapping("deleteActivityPoster")
    public ResponseEntity<ResultModel> deleteActivityPoster(int id){
        return activityPosterService.deleteActivityPoster(id);
    }

    @PostMapping("enableActivityPoster")
    public ResponseEntity<ResultModel> enableActivityPoster(@RequestParam("id") int id){
        return activityPosterService.enableActivityPoster(id);
    }

    @PostMapping("unEnableActivityPoster")
    public ResponseEntity<ResultModel> unEnableActivityPoster(@RequestParam("id") int id){
        return activityPosterService.unEnableActivityPoster(id);
    }
}
