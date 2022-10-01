package com.example.warehouse.schedule;

import com.example.warehouse.common.TencentObjectMemory;
import com.example.warehouse.entity.coupons.SysCoupons;
import com.example.warehouse.entity.sys.SysScheduleTask;
import com.example.warehouse.mapper.sys.SysScheduleTaskMapper;
import com.example.warehouse.service.activityReward.ActivityPosterService;
import com.example.warehouse.service.coupons.SysCouponsService;
import com.example.warehouse.service.shelves.ShelvesAreaService;
import com.example.warehouse.service.sys.StatisticService;
import com.example.warehouse.service.sys.SysTokenService;
import com.example.warehouse.service.wechat.SendMessageServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ScheduleTask {

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

    @Autowired
    private SysScheduleTaskMapper sysScheduleTaskMapper;

    /**
     * 每日凌晨0点0分10秒统计仓库数据
     * “10 0 0 * * ?” 每日凌晨0点00分10秒触发任务
     */
    @Scheduled(cron="10 0 0 * * ?")
    public void warehouseStatisticalTask(){
        if(getTaskIsEnable("warehouseStatisticalTask")){
            log.info("--------------定时任务开始，每日凌晨1点30分统计仓库数据开始-----------");
            statisticService.everyDayAdd();
            log.info("--------------定时任务结束，每日凌晨1点30分统计仓库数据结束-----------");
        }
    }

    /**
     * 每日下午7点发送催款消息
     */
    @Scheduled(cron="0 0 19 * * ?")
    public void adviceCustomerToPayTask(){
        if(getTaskIsEnable("adviceCustomerToPayTask")){
            log.info("--------------定时任务开始，每日下午7点发送催款消息开始-----------");
            sendMessageServer.sendMessageForAdviceCustomerToPay();
            log.info("--------------定时任务开始，每日下午7点发送催款消息结束-----------");
        }
    }

    /**
     * 每日下午14点发送超时未出库消息
     */
    @Scheduled(cron="0 0 14 * * ?")
    public void adviceCustomerForGoodsNotOutTask(){
        if(getTaskIsEnable("adviceCustomerForGoodsNotOutTask")){
            log.info("--------------定时任务开始，每日下午14点发送超时未出库消息开始-----------");
            sendMessageServer.sendMessageForGoodsNotOut();
            log.info("--------------定时任务开始，每日下午14点发送超时未出库消息结束-----------");
        }

    }

    /**
     * 每日凌晨0点10分00秒 删除过期优惠券
     * “0 10 0 * * ?” 每日凌晨1点30分30秒触发任务
     */
    @Scheduled(cron="0 10 0 * * ?")
    public void checkCouponsTask(){
        if(getTaskIsEnable("checkCouponsTask")){
            log.info("--------------定时任务开始，删除过期优惠券 每日凌晨0点10分00秒开始-----------");
            sysCouponsService.deleteOverdueCoupons();
            log.info("--------------定时任务结束，删除过期优惠券 每日凌晨0点10分00秒结束-----------");
        }
    }

    /**
     * 每月2号凌晨0点10分00秒 删除过往海报内容
     * “0 10 0 2 * ?” 每日凌晨1点30分30秒触发任务
     */
    @Scheduled(cron="0 10 0 2 * ?")
    public void deletePosterTask(){
        if(getTaskIsEnable("deletePosterTask")){
            log.info("--------------定时任务开始，删除过往海报内容 开始-----------");
            activityPosterService.deletePosterTask();
            // 定期删除腾讯云数据存储对象的图片
            TencentObjectMemory.timingDeleteObject();
            log.info("--------------定时任务结束，删除过往海报内容 结束-----------");
        }
    }

    /**
     * 更新token信息
     * 每2个小时更新一次
     */
    @Scheduled(cron="55 59 0/2 * * ?")
    public void updateSysToken(){
        if(getTaskIsEnable("updateSysToken")){
            sysTokenService.generateSysToken();
        }
    }

    /**
     * 统计最优货架
     * 每20分钟更新一次
     */
    @Scheduled(cron="0 0/20 7-22 * * ?")
    public void updateOptimalShelves(){
        if(getTaskIsEnable("updateOptimalShelves")){
            shelvesAreaService.updateOptimalShelves();
        }
    }

    private Boolean getTaskIsEnable(String taskCode){
        SysScheduleTask sysScheduleTask = sysScheduleTaskMapper.selectTaskByTaskCode(taskCode);
        if(sysScheduleTask == null){
            return false;
        }
        return sysScheduleTask.getIsEnable() == 1;
    }

}
