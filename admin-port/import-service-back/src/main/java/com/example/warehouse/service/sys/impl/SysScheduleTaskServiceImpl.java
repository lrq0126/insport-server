package com.example.warehouse.service.sys.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.common.TencentObjectMemory;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.sys.SysScheduleTask;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.sys.SysScheduleTaskMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.activityReward.ActivityPosterService;
import com.example.warehouse.service.coupons.SysCouponsService;
import com.example.warehouse.service.shelves.ShelvesAreaService;
import com.example.warehouse.service.sys.StatisticService;
import com.example.warehouse.service.sys.SysScheduleTaskService;
import com.example.warehouse.service.sys.SysTokenService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.sys.SysScheduleTaskReqVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("scheduleTaskService")
public class SysScheduleTaskServiceImpl implements SysScheduleTaskService {

    @Autowired
    private SysScheduleTaskMapper sysScheduleTaskMapper;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private SendMessageServer sendMessageServer;

    @Autowired
    private SysCouponsService sysCouponsService;

    @Autowired
    private ActivityPosterService activityPosterService;

    @Autowired
    private SysTokenService sysTokenService;

    @Autowired
    private ShelvesAreaService shelvesAreaService;

    @Override
    public ResponseEntity<PageResultModel> getScheduleTaskList(SysScheduleTaskReqVo sysScheduleTaskReqVo) {
        PageData pageData = PageHelp.editPage(sysScheduleTaskReqVo);
        int count = sysScheduleTaskMapper.selectScheduleTaskCount(sysScheduleTaskReqVo);
        List<SysScheduleTask> sysScheduleTaskList = new ArrayList<>();
        pageData.setTotal(count);
        if(count > 0){
            sysScheduleTaskReqVo.setPageNumber(pageData.getPageNumber());
            sysScheduleTaskList = sysScheduleTaskMapper.selectScheduleTask(sysScheduleTaskReqVo);
        }
        return new ResponseEntity<>(PageResultModel.ok(sysScheduleTaskList, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getScheduleTaskDetail(Integer id) {
        SysScheduleTask sysScheduleTask = sysScheduleTaskMapper.selectByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(sysScheduleTask), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> save(SysScheduleTask sysScheduleTask) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_REPEAT_ERROR), HttpStatus.OK);
        }

        if(sysScheduleTask.getId() == null){
            sysScheduleTask.setCreateId(user.getId());
            sysScheduleTask.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

            sysScheduleTaskMapper.insertSelective(sysScheduleTask);
        }else{
            sysScheduleTask.setUpdateId(user.getId());
            sysScheduleTask.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

            sysScheduleTaskMapper.updateByPrimaryKey(sysScheduleTask);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> delete(Integer id) {
        int i = sysScheduleTaskMapper.deleteByPrimaryKey(id);
        if(i != 1){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> execute(Integer id) {
        SysScheduleTask sysScheduleTask = sysScheduleTaskMapper.selectByPrimaryKey(id);
        if(sysScheduleTask == null || sysScheduleTask.getIsEnable() != 1){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.SYS_SCHEDULE_NULL_ERROR), HttpStatus.OK);
        }
        System.out.println("手动执行任务：【"+sysScheduleTask.getTaskName()+"】");
        switch (sysScheduleTask.getTaskCode()){
            case "warehouseStatisticalTask":
                // 统计仓库数据
                statisticService.everyDayAdd();
                break;

            case "adviceCustomerToPayTask":
                // 发送催款消息
                sendMessageServer.sendMessageForAdviceCustomerToPay();
                break;

            case "adviceCustomerForGoodsNotOutTask":
                // 超时未发货订单信息
                sendMessageServer.sendMessageForGoodsNotOut();
                break;

            case "checkCouponsTask":
                // 删除过期优惠券
                sysCouponsService.deleteOverdueCoupons();
                break;

            case "deletePosterTask":
                // 删除过往海报内容
                activityPosterService.deletePosterTask();
                // 定期删除腾讯云数据存储对象的图片
                TencentObjectMemory.timingDeleteObject();
                break;

            case "updateSysToken":
                // 更新token信息
                sysTokenService.generateSysToken();
                break;

            case "updateOptimalShelves":
                // 统计最优货架
                shelvesAreaService.updateOptimalShelves();
                break;
        }
        sysScheduleTask.setManualExecutionTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        sysScheduleTaskMapper.updateByPrimaryKeySelective(sysScheduleTask);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> enableTask(Integer id, Integer isEnable) {
        sysScheduleTaskMapper.updateTaskEnable(id, isEnable);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
}
