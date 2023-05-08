package com.example.warehouse.service.webLog;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.webLog.CustomerContactLog;
import com.example.warehouse.mapper.webLog.CustomerContactLogMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.webLog.CustomerContactLogReqVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

@Service
public class CustomerContactLogServiceImpl implements CustomerContactLogService {
    @Autowired
    private CustomerContactLogMapper customerContactLogMapper;
    @Override
    public ResponseEntity<ResultModel> create(CustomerContactLog customerContactLog) {
        if(StringUtils.isEmpty(customerContactLog.getCustomerName())){
            return new ResponseEntity<>(ResultModel.error(ERROR,"请输入您的姓名"),HttpStatus.OK);
        }
        if(StringUtils.isEmpty(customerContactLog.getPhoneNumber())){
            return new ResponseEntity<>(ResultModel.error(ERROR,"请输入您的联系方式"),HttpStatus.OK);
        }
        if(StringUtils.isEmpty(customerContactLog.getTxtContent())){
            return new ResponseEntity<>(ResultModel.error(ERROR,"请输入您要咨询的问题"),HttpStatus.OK);
        }
        customerContactLog.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        int i = customerContactLogMapper.insertSelective(customerContactLog);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR,"提交出现问题了，请扫描二维码添加客服咨询"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> get(CustomerContactLogReqVo customerContactLogReqVo) {
        PageData pageData = PageHelp.editPage(customerContactLogReqVo);
        int count = customerContactLogMapper.getCount(customerContactLogReqVo);
        pageData.setTotal(count);
        if(count > 0){
            customerContactLogReqVo.setPageNumber(pageData.getPageNumber());
            List<CustomerContactLog> customerContactLogList = customerContactLogMapper.getLogList(customerContactLogReqVo);
            return new ResponseEntity<>(PageResultModel.ok(customerContactLogList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> update(CustomerContactLog customerContactLog) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        customerContactLog.setCompleteId(user.getId());
        customerContactLog.setCompleteName(user.getUsername());
        customerContactLog.setCompleteTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        int i = customerContactLogMapper.updateByPrimaryKeySelective(customerContactLog);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }
}
