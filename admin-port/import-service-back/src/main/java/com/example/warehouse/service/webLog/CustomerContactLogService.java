package com.example.warehouse.service.webLog;

import com.example.warehouse.entity.webLog.CustomerContactLog;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.webLog.CustomerContactLogReqVo;
import org.springframework.http.ResponseEntity;

public interface CustomerContactLogService {

    ResponseEntity<ResultModel> create(CustomerContactLog customerContactLog);

    ResponseEntity<PageResultModel> get(CustomerContactLogReqVo customerContactLogReqVo);

    ResponseEntity<ResultModel> update(CustomerContactLog customerContactLog);
}
