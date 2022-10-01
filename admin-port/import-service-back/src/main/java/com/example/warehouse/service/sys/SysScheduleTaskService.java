package com.example.warehouse.service.sys;

import com.example.warehouse.entity.sys.SysScheduleTask;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.sys.SysScheduleTaskReqVo;
import org.springframework.http.ResponseEntity;

public interface SysScheduleTaskService {
    ResponseEntity<PageResultModel> getScheduleTaskList(SysScheduleTaskReqVo sysScheduleTaskReqVo);

    ResponseEntity<ResultModel> getScheduleTaskDetail(Integer id);

    ResponseEntity<ResultModel> save(SysScheduleTask sysScheduleTask);

    ResponseEntity<ResultModel> delete(Integer id);

    ResponseEntity<ResultModel> execute(Integer id);

    ResponseEntity<ResultModel> enableTask(Integer id, Integer isEnable);
}
