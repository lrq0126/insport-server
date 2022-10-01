package com.example.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @des  执行拦截器
 * @author wenguosheng
 * @date 2019年4月24日00:27:19
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    @Bean
    public LogInterceptor getLogInterceptor(){
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLogInterceptor()).addPathPatterns("/**")//拦截所有请求
        //需要配置2：----------- 告知拦截器：/static/** 与 /static/** 不需要拦截 （配置的是 路径）
                .excludePathPatterns("/static/**", "/static/**")
                .excludePathPatterns("/AppDataService/getOrderData","/AppDataService/getOrderData");
    }
    /**
     * 添加静态资源文件，外部可以直接访问地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置----------- 告知系统，被当成静态文件的
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
