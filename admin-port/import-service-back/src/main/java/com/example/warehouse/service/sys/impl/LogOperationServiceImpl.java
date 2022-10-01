package com.example.warehouse.service.sys.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.sys.LogOperation;
import com.example.warehouse.mapper.sys.LogOperationMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.LogOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogOperationServiceImpl implements LogOperationService {

    @Autowired
    private LogOperationMapper logOperationMapper;

    @Override public boolean saveLogOperation(LogOperation logOperation) {
        logOperation.setStatus(1);
        logOperation.setCreateTime(DateUtil.timestamp2String(new Date()));
        logOperationMapper.insert(logOperation);
        return true;
    }

    @Override public ResponseEntity<ResultModel> getLogList(Map<String, Object> map) {
        PageHelp.initPage(map);
        PageData data = (PageData) map.get("page");
        int total = logOperationMapper.countAllLogList(map);
        List<LogOperation> list = new ArrayList<>();
        if ( total > 0 ){
            list = logOperationMapper.getAllLogList(map);
        }
        Map<String,Object> resultMap = new HashMap<>();
        data.setTotal(total);
        resultMap.put("data",data);
        resultMap.put("logList", list);
        return new ResponseEntity<>(PageResultModel.ok(resultMap), HttpStatus.OK);
    }

}
