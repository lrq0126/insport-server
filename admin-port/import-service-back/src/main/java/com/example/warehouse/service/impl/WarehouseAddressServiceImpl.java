package com.example.warehouse.service.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.WarehouseAddress;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.WarehouseAddressMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.WarehouseAddressService;
import com.example.warehouse.vo.WarehouseAddressReqVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.*;

/**
* 仓库地址(WarehouseAddress)表服务实现类
*
* @version 1.0
* @since 2022/7/5 16:24
*/
@Service("warehouseAddressService")
public class WarehouseAddressServiceImpl implements WarehouseAddressService {
    /**
     * 仓库地址Mapper
     */
    @Resource
    private WarehouseAddressMapper warehouseAddressMapper;

    @Override
    public ResponseEntity<PageResultModel> getWarehouseAddressList(WarehouseAddressReqVo warehouseAddressReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
//            map.put("commercialAreaId", user.getCommercialAreaId());
            warehouseAddressReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageData pageData = PageHelp.editPage(warehouseAddressReqVo);
        int count = warehouseAddressMapper.getWarehouseAddressCount(warehouseAddressReqVo);
        pageData.setTotal(count);
        List<WarehouseAddress> warehouseAddressList = new ArrayList<>();
        if(count > 0){
            warehouseAddressReqVo.setPageNumber(pageData.getPageNumber());
            warehouseAddressList = warehouseAddressMapper.getWarehouseAddressList(warehouseAddressReqVo);
        }

        return new ResponseEntity<>(PageResultModel.ok(warehouseAddressList, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveWarehouseAddress(WarehouseAddress warehouseAddress) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(!warehouseAddress.getAddress().contains("LOGIN_NAME")){
            return new ResponseEntity<>(ResultModel.error(WAREHOUSE_ADDRESS_LOGIN_NAME_NULL_ERROR), HttpStatus.OK);
        }
        if(!warehouseAddress.getAddressee().contains("LOGIN_NAME")){
            return new ResponseEntity<>(ResultModel.error(WAREHOUSE_ADDRESS_LOGIN_NAME_NULL_ERROR), HttpStatus.OK);
        }
        if(warehouseAddress.getId() != null){
            warehouseAddress.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            warehouseAddressMapper.updateByPrimaryKeySelective(warehouseAddress);
        }else {
            if(warehouseAddress.getIsEnable() == 1){
                int commercialAreaId = user.getCommercialAreaId();
                warehouseAddressMapper.unableWarehouseAddressAll(commercialAreaId);
            }
            warehouseAddress.setCreateId(user.getId());
            warehouseAddress.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            warehouseAddressMapper.insertSelective(warehouseAddress);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deleteWarehouseAddress(int id) {
        int i = warehouseAddressMapper.deleteByPrimaryKey(id);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ResultModel> enableWarehouseAddress(int id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        int commercialAreaId = user.getCommercialAreaId();
        warehouseAddressMapper.unableWarehouseAddressAll(commercialAreaId);
        warehouseAddressMapper.enableWarehouseAddress(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> unableWarehouseAddress(int id) {
        warehouseAddressMapper.unableWarehouseAddress(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
}