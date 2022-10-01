package com.example.warehouse.controller;

import com.example.warehouse.entity.WarehouseAddress;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.WarehouseAddressService;
import com.example.warehouse.vo.WarehouseAddressReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouseAddress")
public class WarehouseAddressController {

    @Autowired
    private WarehouseAddressService warehouseAddressService;

    /**
     * 查询列表数据
     * @param warehouseAddressReqVo
     * @return
     */
    @PostMapping("/getWarehouseAddressList")
    public ResponseEntity<PageResultModel> getWarehouseAddressList(WarehouseAddressReqVo warehouseAddressReqVo){
        return warehouseAddressService.getWarehouseAddressList(warehouseAddressReqVo);
    }

    /**
     * 保存
     * @param warehouseAddress
     * @return
     */
    @PostMapping("/saveWarehouseAddress")
    public ResponseEntity<ResultModel> saveWarehouseAddress(WarehouseAddress warehouseAddress){
        return warehouseAddressService.saveWarehouseAddress(warehouseAddress);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/deleteWarehouseAddress")
    public ResponseEntity<ResultModel> deleteWarehouseAddress(int id){
        return warehouseAddressService.deleteWarehouseAddress(id);
    }

    /**
     * 启用仓库地址
     * @param id
     * @return
     */
    @PostMapping("/enableWarehouseAddress")
    public ResponseEntity<ResultModel> enableWarehouseAddress(int id){
        return warehouseAddressService.enableWarehouseAddress(id);
    }

    /**
     * 停用仓库地址
     * @param id
     * @return
     */
    @PostMapping("/unableWarehouseAddress")
    public ResponseEntity<ResultModel> unableWarehouseAddress(int id){
        return warehouseAddressService.unableWarehouseAddress(id);
    }

}
