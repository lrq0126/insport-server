package com.example.warehouse.service.sys.impl;

import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.sys.UserPacked;
import com.example.warehouse.mapper.sys.UserPackedMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.service.sys.UserPackedService;
import com.example.warehouse.vo.sys.UserPackedReqVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.USER_NOT_COMMERCIAL_AREA;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

@Service("userPackedService")
public class UserPackedServiceImpl implements UserPackedService {

    @Autowired
    private UserPackedMapper userPackedMapper;

    @Override
    public ResponseEntity<PageResultModel> getUserPackedList(UserPackedReqVo userPackedReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
//            map.put("commercialAreaId", user.getCommercialAreaId());
            userPackedReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        if(!StringUtils.isEmpty(userPackedReqVo.getStartTime())){
            userPackedReqVo.setStartTime(userPackedReqVo.getStartTime() + " 00:00:00");
            userPackedReqVo.setEndTime(userPackedReqVo.getEndTime() + " 23:59:59");
        }
        PageData pageData = PageHelp.editPage(userPackedReqVo);
        int count = userPackedMapper.getUserPackedCount(userPackedReqVo);
        pageData.setTotal(count);
        List<UserPacked> userPackedList = new ArrayList<>();
        if(count > 0){
            userPackedReqVo.setPageNumber(pageData.getPageNumber());
            userPackedList = userPackedMapper.getUserPackedList(userPackedReqVo);
        }
        return new ResponseEntity<>(PageResultModel.ok(userPackedList, pageData), HttpStatus.OK);
    }
}
