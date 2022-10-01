package com.example.warehouse.controller;

import com.example.warehouse.entity.sys.Trajectory;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.TrajectoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 内部轨迹管理
 */
@Controller
@Slf4j
@RequestMapping("/trajectory")
public class TrajectoryController {

    @Autowired
    private TrajectoryService trajectoryService;

    /**
     * 获取轨迹日志列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getTrajectoryLogList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> getTrajectoryLogList(@RequestParam Map<String,Object> map){
        return trajectoryService.getTrajectoryLog(map);
    }

    /**
     * 新增轨迹信息
     * @param trajectory
     * @return
     */
    @RequestMapping(value = "/saveTrajectory",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> saveTrajectory(Trajectory trajectory){
        return trajectoryService.insertTrajectory(trajectory);
    }

    /**
     * 查询轨迹详情
     * @param orderNumber
     * @param desc  1：降序  2：升序
     * @return
     */
    @RequestMapping(value = "/getTrackList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> getTrackListByOrderNumber(String orderNumber,int desc){
        List<Trajectory> list = trajectoryService.getTrackListByOrderNumber(orderNumber,desc);
        return new ResponseEntity<>(PageResultModel.ok(list), HttpStatus.OK);
    }

    /**
     * 查询单号是否有轨迹记录
     * @param orderNumber
     * @return
     */
    @RequestMapping(value = "/checkTrackHaveDatas",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> checkTrackHaveDatas(String orderNumber){
        int i = trajectoryService.checkTrackHaveDatas(orderNumber);
        return new ResponseEntity<>(PageResultModel.ok(i), HttpStatus.OK);
    }

}
