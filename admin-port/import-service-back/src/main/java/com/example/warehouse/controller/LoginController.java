package com.example.warehouse.controller;

import com.example.warehouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;


}
