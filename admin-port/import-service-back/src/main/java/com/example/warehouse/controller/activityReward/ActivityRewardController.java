package com.example.warehouse.controller.activityReward;

import com.example.warehouse.entity.activityReward.ActivityReward;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.activityReward.ActivityRewardService;
import com.example.warehouse.vo.activity.ActivityRewardReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activityReward")
public class ActivityRewardController {

    @Autowired
    private ActivityRewardService activityRewardService;

    @PostMapping("getActivityRewardList")
    public ResponseEntity<PageResultModel> getActivityRewardList(ActivityRewardReqVo activityRewardReqVo){
        return activityRewardService.getActivityRewardList(activityRewardReqVo);
    }

    @PostMapping("getActivityReward")
    public ResponseEntity<ResultModel> getActivityReward(int id){
        return activityRewardService.getActivityReward(id);
    }

    @PostMapping("saveActivityReward")
    public ResponseEntity<ResultModel> saveActivityReward(ActivityReward activityReward){
        return activityRewardService.saveActivityReward(activityReward);
    }

    @PostMapping("deleteActivityReward")
    public ResponseEntity<ResultModel> deleteActivityReward(int id){
        return activityRewardService.deleteActivityReward(id);
    }

    @PostMapping("enableActivityReward")
    public ResponseEntity<ResultModel> enableActivityReward(@RequestParam("id") int id, @RequestParam("enableType") int enableType){
        return activityRewardService.enableActivityReward(id, enableType);
    }

}
