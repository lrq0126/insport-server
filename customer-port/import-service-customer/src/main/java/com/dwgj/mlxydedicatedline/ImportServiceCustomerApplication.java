package com.dwgj.mlxydedicatedline;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.dwgj.mlxydedicatedline.mapper")
@EnableScheduling
public class ImportServiceCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportServiceCustomerApplication.class, args);
    }
}
