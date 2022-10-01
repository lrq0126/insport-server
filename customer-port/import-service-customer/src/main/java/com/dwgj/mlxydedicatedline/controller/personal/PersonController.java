package com.dwgj.mlxydedicatedline.controller.personal;

import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心 -- 个人信息
 * @author guoshengwen
 * @date 2019年11月1日23:25:31
 */
@Controller
@RequestMapping("/personal/person")
@Slf4j
public class PersonController {

    @Autowired
    private CustomerService customerService;
    /**
     * 获取当前登录用户信息--个人中心
     * @param session
     * @return
     */
    @RequestMapping(value = "/getSingleCustomer",method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getSingleCustomer(HttpSession session){
        Customer customer = UserThreadContext.getUser();
        customer = customerService.selectByPrimaryKey(customer.getId());
        Map<String, Object> resultMap = new HashMap<>();
        return new ResponseEntity<>(ResultModel.ok(customer), HttpStatus.OK);
    }
}
