package com.example.warehouse.service.backlog;


import com.example.warehouse.entity.backlog.BacklogDetail;
import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

/**
* 待办任务工作明细表(BacklogDetail)表服务类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/10 14:46
*/
public interface BacklogDetailService{

    ResponseEntity<ResultModel> getBacklogDetailByBacklogId(int backlogId);

    ResponseEntity<ResultModel> saveBacklogDetail(BacklogDetail backlogDetail);

    ResponseEntity<ResultModel> deleteBacklogDetail(int backlogDetailId);
}