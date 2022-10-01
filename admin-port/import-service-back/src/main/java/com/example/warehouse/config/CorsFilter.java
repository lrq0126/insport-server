package com.example.warehouse.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@Slf4j
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.info("----------CorsFilter----------开始");
        HttpServletResponse response = (HttpServletResponse) res;

        HttpServletRequest reqs = (HttpServletRequest) req;
        String allowOrigin = reqs.getHeader("Origin");
        log.info("----------CorsFilter----------allowOrigin:{}",allowOrigin);
        String allowMethods = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
        String allowHeaders = "Origin,No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified,Cache-Control, Expires, Content-Type, X-E4M-With";
        if (reqs.getMethod().equals(RequestMethod.OPTIONS.name())) {
            log.info("----------OPTIONS----------OPTIONS:{}",RequestMethod.OPTIONS.name());
        }
        if(reqs.getHeader("Origin")!=null&&!"".equals(reqs.getHeader("Origin"))){
            log.info("------origin1:{},",reqs.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Origin", reqs.getHeader("Origin"));
        }
        else
            response.setHeader("Access-Control-Allow-Origin","*");

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Headers", "token,user,authorization,access-control-allow-origin, authority, content-type, version-info, X-Requested-With");
        response.setStatus(HttpStatus.OK.value());
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("----------CorsFilter----------初始化");
    }

    @Override
    public void destroy() {
    }

}
