package com.example.warehouse.service.sys;

import com.example.warehouse.entity.sys.LogOperation;
import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface LogOperationService {
    /**
     * 保存操作日志
     * @return
     */
    public boolean saveLogOperation(LogOperation logOperation);

    /**
     *
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> getLogList(Map<String,Object> map);
}
