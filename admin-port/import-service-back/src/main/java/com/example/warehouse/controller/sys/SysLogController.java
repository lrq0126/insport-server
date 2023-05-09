package com.example.warehouse.controller.sys;

import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.LogOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/log")
public class SysLogController {
    @Autowired
    private LogOperationService logOperationService;

    /**
     * 查询日志列表
     * @param param
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> findAll(@RequestParam Map<String, Object> param) {
        return logOperationService.getLogList(param);
    }
}
