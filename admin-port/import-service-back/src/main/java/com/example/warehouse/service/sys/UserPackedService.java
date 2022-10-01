package com.example.warehouse.service.sys;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.vo.sys.UserPackedReqVo;
import org.springframework.http.ResponseEntity;

public interface UserPackedService {
    ResponseEntity<PageResultModel> getUserPackedList(UserPackedReqVo userPackedReqVo);
}
