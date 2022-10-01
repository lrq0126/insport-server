package com.example.warehouse.controller.sys;

import com.example.warehouse.entity.sys.UserPacked;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.service.sys.UserPackedService;
import com.example.warehouse.vo.sys.UserPackedReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userPacked")
public class UserPackedController {

    @Autowired
    private UserPackedService userPackedService;

    @RequestMapping("/getUserPackedList")
    public ResponseEntity<PageResultModel> getUserPackedList(UserPackedReqVo userPackedReqVo){
        return userPackedService.getUserPackedList(userPackedReqVo);
    }
}
