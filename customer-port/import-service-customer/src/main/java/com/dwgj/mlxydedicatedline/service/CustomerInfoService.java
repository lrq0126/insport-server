package com.dwgj.mlxydedicatedline.service;

import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.ResponseEntity;

public interface CustomerInfoService {

    ResponseEntity<ResultModel> selectBaseInfoById();

}
