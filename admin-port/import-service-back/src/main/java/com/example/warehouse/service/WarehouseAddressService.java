package com.example.warehouse.service;

import com.example.warehouse.entity.WarehouseAddress;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.WarehouseAddressReqVo;
import org.springframework.http.ResponseEntity;

/**
* 仓库地址(WarehouseAddress)表服务类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/7/5 16:24
*/
public interface WarehouseAddressService {

    ResponseEntity<PageResultModel> getWarehouseAddressList(WarehouseAddressReqVo warehouseAddressReqVo);

    ResponseEntity<ResultModel> saveWarehouseAddress(WarehouseAddress warehouseAddress);

    ResponseEntity<ResultModel> deleteWarehouseAddress(int id);

    ResponseEntity<ResultModel> enableWarehouseAddress(int id);

    ResponseEntity<ResultModel> unableWarehouseAddress(int id);
}