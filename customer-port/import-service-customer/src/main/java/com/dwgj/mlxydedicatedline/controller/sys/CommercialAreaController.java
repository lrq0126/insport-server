package com.dwgj.mlxydedicatedline.controller.sys;

import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.sys.CommercialAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/commercialArea")
public class CommercialAreaController {

    @Autowired
    private CommercialAreaService commercialAreaService;

    /**
     * 获取 国家-仓库列表
     * @return
     */
    @GetMapping("/getCountryCommercialArea")
    public ResponseEntity<ResultModel> getCountryCommercialArea(){
        return commercialAreaService.getCountryCommercialArea();
    }
}
