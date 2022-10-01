package com.dwgj.mlxydedicatedline.controller;

import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.CustomerAddress;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerAddressService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.ERROR;
import static com.dwgj.mlxydedicatedline.enums.ResultStatus.NOT_LOGIN_ERROR;

@RestController
@RequestMapping("/customer/address")
public class CustomerAddressController {

    @Autowired
    private CustomerAddressService addressService;

    /**
     * 查询当前登录人的所有收货地址
     * @param session
     * @return
     */
    @GetMapping
    public ResponseEntity<ResultModel> findByCustomerId(HttpSession session,String pageNumber,String pageSize,String addressee){
        Customer customer = UserThreadContext.getUser();
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR),HttpStatus.OK);
        }
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isEmpty(pageNumber)){
            paramMap.put("pageNumber",0);
        }else {
            paramMap.put("pageNumber",pageNumber);
        }

        if(StringUtils.isEmpty(pageSize)){
            paramMap.put("pageSize",10);
        }else {
            paramMap.put("pageSize",pageSize);
        }
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(),paramMap.get("pageSize").toString());
        paramMap.put("customerId",customer.getId());
        paramMap.put("pageNumber",data.getPageNumber());
        paramMap.put("pageSize",data.getPageSize());
        paramMap.put("addressee",addressee);
        int count = addressService.countFindPageNumber(paramMap);
        if(count < 1){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        }
        data.setTotal(count);
        List<CustomerAddress> customerAddressList = addressService.findByCustomerId(paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("customerAddress",customerAddressList);
        resultMap.put("page",data);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    /**
     * 新增收货地址
     * @param paramMap
     * 详细参数  addressee  收货人   gender  性别  receiverAddress  收货人地址  phoneNumber   手机号码    callNumber   电话号码    message   备注
     * @param session
     * @return
     */
    @PostMapping
    public ResponseEntity<ResultModel> addNewAddress(@RequestParam Map<String, Object> paramMap, HttpSession session){
        Customer customer = UserThreadContext.getUser();
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR),HttpStatus.OK);
        }
        paramMap.put("customerId", customer.getId());
        int result = addressService.addNewAddress(paramMap);
        if(result == 1){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
    }

    /**
     * 设置默认收货地址
     * @param addressId
     * @return
     */
    @PutMapping("/set/default/address")
    public ResponseEntity<ResultModel> setDefaultAddress(@RequestParam int addressId){
        int result = addressService.setDefaultAddress(addressId);
        if(result == 1){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
    }

    /**
     * 修改收货地址
     * @param paramMap
     * 详细参数  addressee    收货人 ；     gender    性别 ；  receiverAddress  收货人地址 ； phoneNumber   手机号码  ；
     *           callNumber   电话号码 ；   message   备注 ；  id        收货地址id
     * @param session
     * @return
     */
    @PutMapping
    public ResponseEntity<ResultModel> updateAddress(@RequestParam Map<String,Object> paramMap, HttpSession session){
        Customer customer = UserThreadContext.getUser();
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR),HttpStatus.OK);
        }
        paramMap.put("customerId", customer.getId());
        int result = addressService.updateAddress(paramMap);
        if(result == 1){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
    }

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    @DeleteMapping
    public ResponseEntity<ResultModel> delteAddress(@RequestParam int id){
        int result = addressService.delteAddress(id);
        if(result == 1){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
    }
}
