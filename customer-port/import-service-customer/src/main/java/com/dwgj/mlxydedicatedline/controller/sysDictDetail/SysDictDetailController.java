package com.dwgj.mlxydedicatedline.controller.sysDictDetail;

import com.dwgj.mlxydedicatedline.entity.SysDictDetail;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.sysDictDetail.SysDictDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2020年03月23日14:42
 *
 * @author lrq
 */
@RestController
@RequestMapping("/sysDictDetail")
public class SysDictDetailController {

    @Autowired
    private SysDictDetailService sysDictDetailService;

    /**
     * 获取 国家列表
     * @return
     */
    @GetMapping("/find/country/list")
    public ResponseEntity<ResultModel> findCountry(@RequestParam Map<String, Object> paramMap){
        paramMap.put("sdmCode","country");
        List<SysDictDetail> sysDictDetails =  sysDictDetailService.selectCountry(paramMap);
        Map<String, Object> result = new HashMap<>(12);
        result.put("country",sysDictDetails);
        return new ResponseEntity<>(ResultModel.ok(result),HttpStatus.OK);
    }

    /**
     * 获取 国家列表
     * @return
     */
    @GetMapping("/findCountrySimpleList")
    public ResponseEntity<ResultModel> findCountrySimpleList(){
        return sysDictDetailService.findCountrySimpleList();
    }



}
