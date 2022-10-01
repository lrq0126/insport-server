package com.example.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.CustomerAddress;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerAddressService;
import com.example.warehouse.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.SUCCESS;

@RestController
@RequestMapping("/customerAddress")
@Slf4j
public class CustomerAddressController {

    @Autowired CustomerAddressService customerAddressService;

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/find/all",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> findAll(@RequestParam Map<String, Object> map){
        log.info("查询所有客户地址列表------>>>>>>");
        PageData data = (PageData) PageHelp.initPage(map).get("page");
        int count = customerAddressService.countFindAll(map);
        data.setTotal(count);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        return new ResponseEntity<>(PageResultModel.ok(customerAddressService.findAll(map),result), HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<ResultModel> create(@RequestParam Map<String, Object> params){
        log.info("新增客户地址开始------->>>>>，参数{}",params);
        String str = JSON.toJSON(params).toString();
        CustomerAddress address = JSON.parseObject(str,CustomerAddress.class);
        address.setCreateTime(DateUtil.timestamp2String(new Date()));//设置创建时间
        int i = 0;
        try{
            if (params.containsKey("loginName")){
                Customer customer = customerService.getCustomerByLoginName(params.get("loginName").toString());
                address.setCustomerId(customer.getId());
                address.setCustomerNo(customer.getCustomerNo());
            }
            i = customerAddressService.save(address);
        }catch (Exception e){
            log.error("新增客户地址失败，原因:",e);
            return new ResponseEntity<>(ResultModel.error(ERROR,e.getMessage()),HttpStatus.OK);
        }
        if(i == 0){
            log.error("新增客户地址失败");
            return new ResponseEntity<>(ResultModel.error(ERROR,"新增客户地址失败"),HttpStatus.OK);
        }
        log.info("新增客户地址成功-------<<<<<<<");
        return new ResponseEntity<>(ResultModel.ok(SUCCESS),HttpStatus.OK);
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<ResultModel> update(@RequestParam Map<String, Object> params){
        log.info("修改客户地址开始----------->>>>>>>>>>>，参数：{}",params);
        String str = JSON.toJSON(params).toString();
        CustomerAddress address = JSON.parseObject(str,CustomerAddress.class);
        address.setStatus(1);
        int i = 0;
        try{
            i = customerAddressService.update(address);
        }catch (Exception e){
            log.error("系统错误----------->>>>>>>>>>>，参数：{}",e);
            return new ResponseEntity<>(ResultModel.error(ERROR,e.getMessage()),HttpStatus.OK);
        }
        if(i == 0){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        log.info("修改客户地址完成-----------<<<<<<<<");
        return new ResponseEntity<>(ResultModel.ok(SUCCESS),HttpStatus.OK);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<ResultModel> del(int addressId){
        int i = customerAddressService.del(addressId);
        if(0 == i){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(SUCCESS),HttpStatus.OK);
    }

    @RequestMapping(value = "/getAddressByCustomerId",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> getAddressByCustomerId(int customerId){
        List<CustomerAddress> list = customerAddressService.findByCustomerId(customerId);
        return new ResponseEntity<>(PageResultModel.ok(list), HttpStatus.OK);
    }

}
