package com.dwgj.mlxydedicatedline.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Slf4j
public class CorsConfig {
    /**
     允许任何域名使用
     允许任何头
     允许任何方法（post、get等）
     */
    private CorsConfiguration buildConfig() {
        log.info("---跨域请求允许buildConfig---");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // addAllowedOrigin 不能设置为* 因为与 allowCredential 冲突
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        // allowCredential 需设置为true
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        log.info("---跨域请求允许corsFilter");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
