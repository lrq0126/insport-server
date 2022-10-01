package com.dwgj.mlxydedicatedline.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerService;
import com.dwgj.mlxydedicatedline.vo.customer.RegisterDataVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;

/**
 * 登录验证页面
 */
@RestController
@RequestMapping("/log")
@Slf4j
public class UserLoginController {


    @Autowired
    CustomerService customerService;

    @RequestMapping("/to/login")
    public String toLogin() {
        return "login";
    }

    @GetMapping("checkLoginStatus")
    public ResponseEntity<ResultModel> checkLoginStatus(HttpServletRequest request){
        return customerService.checkLoginStatus(request);
    }

     /**
     *
     * @param paramMap
     * @param session
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ResultModel> login(@RequestParam("paramMap") String paramMap, HttpSession session) {
        Map<String, String> customerMap = (Map<String, String>) JSONObject.parse(paramMap);
        return customerService.login(customerMap.get("loginName"), customerMap.get("password"));
    }

    /**
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public String logout() {
        Subject subject= SecurityUtils.getSubject();
        if(subject != null){
            subject.logout();
        }
        return "login";
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResultModel> register(RegisterDataVo customer) {
        return customerService.register(customer);
    }
}
