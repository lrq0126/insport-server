package com.example.warehouse.controller.sys;

import com.example.warehouse.entity.sys.SysScheduleTask;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.SysScheduleTaskService;
import com.example.warehouse.vo.sys.SysScheduleTaskReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysScheduleTask")
public class SysScheduleTaskController {

    @Autowired
    private SysScheduleTaskService sysScheduleTaskService;

    @PostMapping("/getScheduleTaskList")
    public ResponseEntity<PageResultModel> getScheduleTaskList(SysScheduleTaskReqVo sysScheduleTaskReqVo){
        return sysScheduleTaskService.getScheduleTaskList(sysScheduleTaskReqVo);
    }

    @PostMapping("/getScheduleTaskDetail")
    public ResponseEntity<ResultModel> getScheduleTaskDetail(Integer id){
        return sysScheduleTaskService.getScheduleTaskDetail(id);
    }

    @PostMapping("/save")
    public ResponseEntity<ResultModel> save(SysScheduleTask sysScheduleTask){
        return sysScheduleTaskService.save(sysScheduleTask);
    }

    @PostMapping("/deleteTask")
    public ResponseEntity<ResultModel> deleteTask(Integer id){
        return sysScheduleTaskService.delete(id);
    }

    @PostMapping("/enableTask")
    public ResponseEntity<ResultModel> enableTask(Integer id, Integer isEnable){
        return sysScheduleTaskService.enableTask(id, isEnable);
    }

    @PostMapping("/executeTask")
    public ResponseEntity<ResultModel> executeTask(Integer id){
        return sysScheduleTaskService.execute(id);
    }
}
