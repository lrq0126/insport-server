package com.example.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.DeliveryCompany;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.DeliveryCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * @author lrq
 */
@RestController
@RequestMapping("/company")
@Slf4j
public class DeliveryCompanyController {

    @Autowired
    private DeliveryCompanyService deliveryCompanyService;

    @RequestMapping(value = "/find/all",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> findAll(@RequestParam Map<String, Object> map){
        log.info("查询所有快递公司信息-------->>>>>/find/all");
        PageData data = (PageData) PageHelp.initPage(map).get("page");
        int count = deliveryCompanyService.countFindAll(map);
        data.setTotal(count);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        return new ResponseEntity<>(PageResultModel.ok(deliveryCompanyService.findAll(map),result),HttpStatus.OK);
    }

    @RequiresPermissions("company:create")
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<ResultModel> create(@RequestParam Map<String, Object> params){
        log.info("创建快递公司，用于快递来源,参数{}",params);
        String str = JSON.toJSON(params).toString();
        DeliveryCompany company = JSON.parseObject(str,DeliveryCompany.class);
        int i = deliveryCompanyService.save(company);
        if(i == 0){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }
    @RequiresPermissions("company:update")
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<ResultModel> update(@RequestParam Map<String, Object> params){
        log.info("更新快递公司信息,参数{}",params);
        String str = JSON.toJSON(params).toString();
        DeliveryCompany company = JSON.parseObject(str,DeliveryCompany.class);
        int i = deliveryCompanyService.update(company);
        if(i == 0){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }
    @RequiresPermissions("company:delete")
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<ResultModel> del(@RequestParam Map<String, Object> params){

        log.info("删除快递公司信息,参数{},",params);
        int i = deliveryCompanyService.del(params.get("deliveryNo").toString(),params.get("usercode").toString());
        if(0 == i){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        if(500 == i){
            return new ResponseEntity<>(ResultModel.error(ERROR,"该快递公司被仓库列表引用，无法删除！"),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @PostMapping("/find")
    @ResponseBody
    public ResponseEntity<ResultModel> find(@Param("deliveryNo")String deliveryNo, @Param("deliveryName")String deliveryName,
                                            @Param("createName")String createName){
        log.info("查找快递公司信息,参数{}",deliveryNo,deliveryName,createName);
        Map<String, Object> map = new HashMap<>();
        map.put("deliveryNo",deliveryNo);
        map.put("deliveryName",deliveryName);
        map.put("createName",createName);
        return new ResponseEntity<>(ResultModel.ok(deliveryCompanyService.find(map)),HttpStatus.OK);
    }


}
