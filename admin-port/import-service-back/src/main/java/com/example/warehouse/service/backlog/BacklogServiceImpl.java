package com.example.warehouse.service.backlog;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.backlog.Backlog;
import com.example.warehouse.mapper.backlog.BacklogMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.backlogVo.BacklogReqVo;
import com.example.warehouse.vo.backlogVo.BacklogRespVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

/**
* 待办任务表(Backlog)表服务实现类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/8 16:46
*/
@Service("backlogService")
public class BacklogServiceImpl implements BacklogService {
    /**
     * 待办任务表Mapper
     */
    @Resource
    private BacklogMapper backlogMapper;

    @Override
    public ResponseEntity<ResultModel> getHomeBacklogList(Integer userId) {
        if(userId == null){
            return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>()), HttpStatus.OK);
        }else{
            List<Backlog> backlogList = backlogMapper.getHomeBacklogList(userId);
            return new ResponseEntity<>(PageResultModel.ok(backlogList), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<PageResultModel> getBacklogList(BacklogReqVo backlogReqVo) {
        PageData pageData = PageHelp.editPage(backlogReqVo.getPageNumber().toString(), backlogReqVo.getPageSize().toString());
        int count = backlogMapper.getBacklogCount(backlogReqVo);
        pageData.setTotal(count);
        if(count > 0){
            backlogReqVo.setPageNumber(pageData.getPageNumber());
            List<BacklogRespVo> backlogList = backlogMapper.getBacklogList(backlogReqVo);
            return new ResponseEntity<>(PageResultModel.ok(backlogList,pageData), HttpStatus.OK);

        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(),pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveBacklog(Backlog backlog) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        if(backlog.getBackloggerId() == null){
            backlog.setBackloggerId(user.getId());
            backlog.setBacklogger(user.getUsername());
        }
        int i;
        if(backlog.getId() == null){
            backlog.setCreateId(user.getId());
            backlog.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            backlog.setBacklogStatus(0);
            i = backlogMapper.insertSelective(backlog);

        }else {
            backlog.setUpdateId(user.getId());
            backlog.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            i = backlogMapper.updateByPrimaryKeySelective(backlog);
        }

        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> getBacklogDetail(int id) {
        BacklogRespVo backlog = backlogMapper.getBacklogDetailById(id);

        return new ResponseEntity<>(ResultModel.ok(backlog), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> upBacklogStatus(int id, int backlogStatus) {
        int i = backlogMapper.upBacklogStatus(id, backlogStatus);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> deleteBacklog(int id) {
        int i = backlogMapper.deleteByPrimaryKey(id);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }
}