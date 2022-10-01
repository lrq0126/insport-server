package com.dwgj.mlxydedicatedline.service.impl;

import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.CustomerMoney;
import com.dwgj.mlxydedicatedline.mapper.customerMoney.CustomerMoneyMapper;
import com.dwgj.mlxydedicatedline.mapper.sys.SysNoticeMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerInfoService;
import com.dwgj.mlxydedicatedline.vo.customer.CustomerInfoRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.NOT_LOGIN_ERROR;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    @Autowired
    private CustomerMoneyMapper customerMoneyMapper;


    @Override
    public ResponseEntity<ResultModel> selectBaseInfoById() {
        Customer customer = UserThreadContext.getUser();
        if (customer == null) {
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
        }
        CustomerInfoRespVo customerInfoRespVo = new CustomerInfoRespVo();
        Map<String, Object> noteMap = new HashMap<>();
        noteMap.put("customerId", customer.getId());
        noteMap.put("isRead", "0");
        int count = sysNoticeMapper.countSelectNoticeListByCustomerId(noteMap);

        customerInfoRespVo.setIsHaveNotRead(count > 0 ? 1 : 0);
        customerInfoRespVo.setNotReadNum(count);

        CustomerMoney customerMoney = customerMoneyMapper.selectByCustomerNo(customer.getCustomerNo());
        customerInfoRespVo.setAmount(customerMoney == null ? new BigDecimal(0) : customerMoney.getAmount());

        return new ResponseEntity<>(ResultModel.ok(customerInfoRespVo), HttpStatus.OK);
    }
}
