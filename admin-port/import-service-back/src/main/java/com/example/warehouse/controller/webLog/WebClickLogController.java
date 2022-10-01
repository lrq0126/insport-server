package com.example.warehouse.controller.webLog;

import com.example.warehouse.entity.webLog.WebClickLog;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.service.webLog.WebClickLogService;
import com.example.warehouse.vo.webLog.WebClickReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("webClickLog")
public class WebClickLogController {

    @Autowired
    private WebClickLogService webClickLogService;

    @PostMapping("create")
    public void create(WebClickLog webClickLog, HttpServletRequest httpServletRequest) throws IOException {
        webClickLogService.create(webClickLog, httpServletRequest);
    }

    @PostMapping("getWebClickList")
    public ResponseEntity<PageResultModel> getWebClickList(WebClickReqVo webClickReqVo){
        return webClickLogService.get(webClickReqVo);
    }
}
