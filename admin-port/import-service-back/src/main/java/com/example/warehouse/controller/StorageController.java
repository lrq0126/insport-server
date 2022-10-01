package com.example.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Storage;
import com.example.warehouse.entity.User;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.StorageService;
import com.example.warehouse.vo.storage.StorageCodeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.SYS_ERROR;

@RestController
@RequestMapping("/storage")
@Slf4j
public class StorageController {

    @Autowired
    private StorageService storageService;

    @RequestMapping(value = "/find/all",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> findAll(@RequestParam Map<String, Object> map){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        log.info("查询所有仓库信息-------->>>>>/find/all");
        log.info("user:{}",user);
        PageData data = (PageData) PageHelp.initPage(map).get("page");
        int count = storageService.countFindAll(map);
        data.setTotal(count);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        List<Storage> storages = new ArrayList<>();
        try {
            storages = storageService.findAll(map);
        }catch (Exception e){
            e.printStackTrace();
            new ResponseEntity<>(PageResultModel.error(SYS_ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(storages,result),HttpStatus.OK);
    }

    @RequestMapping(value = "getWareLocations", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getWareLocations(){
        return storageService.getWareLocations();
    }

    @RequiresPermissions("storage:create")
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<ResultModel> create(@RequestParam Map<String, Object> params){
        log.info("创建仓库开始------------->>>>>>>>，参数{}",params);
        String str = JSON.toJSON(params).toString();
        Storage storage = JSON.parseObject(str,Storage.class);
        int i = storageService.save(storage);
        if(i == 0){
            log.info("创建仓库错误");
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        log.info("创建仓库成功，结束---------<<<<<<");
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @RequiresPermissions("storage:update")
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<ResultModel> update(@RequestParam Map<String, Object> params){
        String str = JSON.toJSON(params).toString();
        Storage storage = JSON.parseObject(str,Storage.class);
        int i = storageService.update(storage);
        if(i == 0){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @RequiresPermissions("storage:delete")
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<ResultModel> del(@RequestParam Map<String, Object> params){
        int i = storageService.del(params.get("storageCode").toString(),params.get("usercode").toString());
        if(0 == i){
            return new ResponseEntity<>(ResultModel.error(ERROR,"删除出错！"),HttpStatus.OK);
        }
//        if(500 == i){
//            return new ResponseEntity<>(ResultModel.error(ERROR,"该仓库地址被货物列表引用，无法删除！"),HttpStatus.OK);
//        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }


    @ResponseBody
    @PostMapping(value = "/code/print")
    public ResponseEntity<ResultModel> codePrint(StorageCodeVo storageCodeVo){
        return storageService.codePrint(storageCodeVo);
    }

}
