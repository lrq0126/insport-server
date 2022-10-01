package com.example.warehouse.service;

import com.example.warehouse.entity.Operator;
import com.example.warehouse.entity.Storage;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.storage.StorageCodeVo;
import com.itextpdf.text.DocumentException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StorageService {

    int save(Storage storage);

    int update(Storage storage);

    int del(String goodsNo, String usercode);

    List<Storage> findAll(Map<String,Object> map);

    int countFindAll(Map<String,Object> map);

    List<Storage> find(Map<String, Object> map);

    ResponseEntity<ResultModel> codePrint(StorageCodeVo storageCodeVo);

    ResponseEntity<ResultModel> getWareLocations();
}
