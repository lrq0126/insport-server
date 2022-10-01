package com.example.warehouse.service.sys;

import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface SysTokenService {

    void generateSysToken();

    ResponseEntity<ResultModel> getSysToken();

    Boolean checkToken(String token);

}
