package com.example.warehouse.service.activityReward;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.activityReward.ActivityReward;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.activityReward.ActivityRewardMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.activity.ActivityRewardReqVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
* 活动奖励(ActivityReward)表服务实现类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/18 17:50
*/
@Service("activityRewardService")
public class ActivityRewardServiceImpl  implements ActivityRewardService {
    /**
     * 活动奖励Mapper
     */
    @Autowired
    private ActivityRewardMapper activityRewardMapper;


    @Override
    public ResponseEntity<PageResultModel> getActivityRewardList(ActivityRewardReqVo activityRewardReqVo) {
        PageData pageData = PageHelp.editPage(activityRewardReqVo);
        int count = activityRewardMapper.selectActivityRewardCount(activityRewardReqVo);
        pageData.setTotal(count);
        if(count > 0){
            activityRewardReqVo.setPageNumber(pageData.getPageNumber());
            List<ActivityReward> activityRewardList = activityRewardMapper.getActivityRewardList(activityRewardReqVo);
            return new ResponseEntity<>(PageResultModel.ok(activityRewardList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getActivityReward(int id) {
        ActivityReward activityReward = activityRewardMapper.selectByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(activityReward), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveActivityReward(ActivityReward activityReward) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        // 检验活动名是否重复
        int nameCheck = activityRewardMapper.checkActivityName(activityReward);
        if(nameCheck > 0){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ACTIVITY_NAME_REPEAT), HttpStatus.OK);
        }
        // 检验活动类型和奖励是否已存在是否重复
        int rewardCheck = activityRewardMapper.checkActivityReward(activityReward);
        if(rewardCheck > 0){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ACTIVITY_TYPE_REWARD_REPEAT), HttpStatus.OK);
        }
        if(activityReward.getId() == null){
            activityReward.setCreateId(user.getId());
            activityReward.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            activityReward.setStatus(1);
            activityReward.setVersion(1);
            activityReward.setIsEnable(-1); // 默认不启动活动，后续自己启动
            activityRewardMapper.insertSelective(activityReward);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            activityReward.setUpdateId(user.getId());
            activityReward.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            activityReward.setStatus(1);
            activityReward.setVersion(activityReward.getVersion() + 1);
            if(StringUtils.isEmpty(activityReward.getStartTime())){
                activityReward.setStartTime(null);
                activityReward.setEndTime(null);
            }
            activityRewardMapper.updateByPrimaryKey(activityReward);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> deleteActivityReward(int id) {
        activityRewardMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> enableActivityReward(int id, int enableType) {
        activityRewardMapper.enableActivityReward(id, enableType);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
}