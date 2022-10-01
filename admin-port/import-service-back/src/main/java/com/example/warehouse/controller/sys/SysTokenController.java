package com.example.warehouse.controller.sys;

import com.example.warehouse.entity.sys.SysToken;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.SysTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysToken")
public class SysTokenController {

    @Autowired
    private SysTokenService sysTokenService;

    @GetMapping("/gainSystemToken")
    public ResponseEntity<ResultModel> gainSystemToken(){
        return sysTokenService.getSysToken();
    }

    @RequestMapping("/resetSystemToken")
    public void resetSystemToken(){
        sysTokenService.generateSysToken();
    }

}
