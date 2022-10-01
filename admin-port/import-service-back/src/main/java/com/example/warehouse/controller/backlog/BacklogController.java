package com.example.warehouse.controller.backlog;

import com.example.warehouse.entity.backlog.Backlog;
import com.example.warehouse.entity.backlog.BacklogDetail;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.backlog.BacklogDetailService;
import com.example.warehouse.service.backlog.BacklogService;
import com.example.warehouse.vo.backlogVo.BacklogReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "backlog")
public class BacklogController {

    @Autowired
    private BacklogService backlogService;
    @Autowired
    private BacklogDetailService backlogDetailService;

    @PostMapping("getHomeBacklogList")
    public ResponseEntity<ResultModel> getHomeBacklogList(Integer userId){
        return backlogService.getHomeBacklogList(userId);
    }

    @PostMapping("getBacklogList")
    public ResponseEntity<PageResultModel> getBacklogList(BacklogReqVo backlogReqVo){
        return backlogService.getBacklogList(backlogReqVo);
    }

    @PostMapping("saveBacklog")
    public ResponseEntity<ResultModel> saveBacklog(Backlog Backlog){
        return backlogService.saveBacklog(Backlog);
    }

    @PostMapping("getBacklogDetail")
    public ResponseEntity<ResultModel> getBacklogDetail(int id){
        return backlogService.getBacklogDetail(id);
    }

    @PostMapping("upBacklogStatus")
    public ResponseEntity<ResultModel> upBacklogStatus(int id, int backlogStatus){
        return backlogService.upBacklogStatus(id, backlogStatus);
    }

    @PostMapping("deleteBacklog")
    public ResponseEntity<ResultModel> deleteBacklog(int id){
        return backlogService.deleteBacklog(id);
    }


    /**
     * 任务明细内容接口
     */

    @PostMapping("getBacklogDetailByBacklogId")
    public ResponseEntity<ResultModel> getBacklogDetailByBacklogId(int backlogId){
        return backlogDetailService.getBacklogDetailByBacklogId(backlogId);
    }
    /**
     * 创建
     * @param backlogDetail
     * @return
     */
    @PostMapping("saveBacklogDetail")
    public ResponseEntity<ResultModel> saveBacklogDetail(BacklogDetail backlogDetail){
        return backlogDetailService.saveBacklogDetail(backlogDetail);
    }

    /**
     * 删除
     * @param backlogDetailId
     * @return
     */
    @PostMapping("deleteBacklogDetail")
    public ResponseEntity<ResultModel> deleteBacklogDetail(int backlogDetailId){
        return backlogDetailService.deleteBacklogDetail(backlogDetailId);
    }
}
