package com.example.warehouse.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@MapperScan("com.example.warehouse.mapper.**")
@Slf4j
public class MybatisConfig {

    /**
     * 注入mybatis拦截
     * @return
     */
    @Bean
    public MybatisSqlInterceptor mybatisSqlInterceptor() {
        log.info("注入mybatis拦截");
        return new MybatisSqlInterceptor();
    }
}
