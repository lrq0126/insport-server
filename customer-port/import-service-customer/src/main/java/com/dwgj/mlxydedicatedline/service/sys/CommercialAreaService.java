package com.dwgj.mlxydedicatedline.service.sys;

import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface CommercialAreaService {


    ResponseEntity<ResultModel> getCountryCommercialArea();

}
