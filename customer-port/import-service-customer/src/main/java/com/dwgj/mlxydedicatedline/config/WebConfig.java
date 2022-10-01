package com.dwgj.mlxydedicatedline.config;

import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.entity.user.WxAccessToken;
import com.dwgj.mlxydedicatedline.mapper.user.WxAccessTokenMapper;
import com.dwgj.mlxydedicatedline.schedul.SchedulTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Date;

/**
 * @des  执行拦截器
 * @author wenguosheng
 * @date 2019年4月24日00:27:19
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    WxAccessTokenMapper wxAccessTokenMapper;
    @Autowired
    SchedulTask schedulTask;

    @Bean
    public LogInterceptor getLogInterceptor(){
        return new LogInterceptor();
    }

    @Bean
    public TaskScheduler taskScheduler(){
        System.out.println("系统启用定时任务------>");
//        JSONObject result=  schedulTask.getAccessToken();
//        WxAccessToken wxAccessToken = new WxAccessToken();
//        wxAccessToken.setAccessToken(result.get("access_token").toString());
//        wxAccessToken.setExpire(result.get("expires_in").toString());
//        wxAccessToken.setJsapiTicket(schedulTask.getJsapiTicket(result.get("access_token").toString()));
//        wxAccessTokenMapper.insert(wxAccessToken);
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("springboot-mobile-task");
        return scheduler;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLogInterceptor()).addPathPatterns("/**")//拦截所有请求
        //需要配置2：----------- 告知拦截器：/static/** 与 /static/** 不需要拦截 （配置的是 路径）
                .excludePathPatterns("/static/**", "/static/**");
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
