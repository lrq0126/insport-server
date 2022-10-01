package com.example.warehouse.service.backlog;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.backlog.BacklogDetail;
import com.example.warehouse.mapper.backlog.BacklogDetailMapper;
import com.example.warehouse.model.ResultModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

/**
* 待办任务工作明细表(BacklogDetail)表服务实现类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/10 14:46
*/
@Service("backlogDetailService")
public class BacklogDetailServiceImpl implements BacklogDetailService {
    /**
     * 待办任务工作明细表Mapper
     */
    @Resource
    private BacklogDetailMapper backlogDetailMapper;


    @Override
    public ResponseEntity<ResultModel> getBacklogDetailByBacklogId(int backlogId) {
        List<BacklogDetail> backlogDetailList = backlogDetailMapper.getBacklogDetailByBacklogId(backlogId);
        return new ResponseEntity<>(ResultModel.ok(backlogDetailList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveBacklogDetail(BacklogDetail backlogDetail) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        int i;
        if(backlogDetail.getId() == null){
            backlogDetail.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            backlogDetail.setCreateId(user.getId());
            i = backlogDetailMapper.insertSelective(backlogDetail);
        }else {
            backlogDetail.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            backlogDetail.setUpdateId(user.getId());
            i = backlogDetailMapper.updateByPrimaryKeySelective(backlogDetail);
        }
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> deleteBacklogDetail(int backlogDetailId) {
        int i = backlogDetailMapper.deleteByPrimaryKey(backlogDetailId);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }
}