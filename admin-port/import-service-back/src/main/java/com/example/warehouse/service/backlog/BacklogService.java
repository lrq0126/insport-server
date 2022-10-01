package com.example.warehouse.service.backlog;


import com.example.warehouse.entity.backlog.Backlog;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.backlogVo.BacklogReqVo;
import org.springframework.http.ResponseEntity;

/**
* 待办任务表(Backlog)表服务类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/8 16:46
*/
public interface BacklogService{

    ResponseEntity<ResultModel> getHomeBacklogList(Integer userId);

    ResponseEntity<PageResultModel> getBacklogList(BacklogReqVo backlogReqVo);

    ResponseEntity<ResultModel> saveBacklog(Backlog backlog);

    ResponseEntity<ResultModel> getBacklogDetail(int id);

    ResponseEntity<ResultModel> upBacklogStatus(int id, int backlogStatus);

    ResponseEntity<ResultModel> deleteBacklog(int id);

}