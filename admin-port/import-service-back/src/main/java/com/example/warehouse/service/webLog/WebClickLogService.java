package com.example.warehouse.service.webLog;

import com.example.warehouse.entity.webLog.WebClickLog;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.vo.webLog.WebClickReqVo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface WebClickLogService {
    void create(WebClickLog webClickLog, HttpServletRequest httpServletRequest) throws IOException;

    ResponseEntity<PageResultModel> get(WebClickReqVo webClickReqVo);
}
