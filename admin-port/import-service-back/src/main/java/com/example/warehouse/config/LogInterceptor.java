package com.example.warehouse.config;

import com.example.warehouse.common.NetWorkUtil;
import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 *  @des 日志拦截器
 * @author wenguosheng
 * @date 2019年4月24日00:20:31
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    /**
     * 进入controller层之前拦截请求
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("----开始进入请求地址拦截-------->>>>>>，请求方式：{}",httpServletRequest.getMethod());
        log.info("请求地址：{}",httpServletRequest.getRequestURI());
        log.info("客户端请求ip地址：{}",NetWorkUtil.getIpAddress(httpServletRequest));
        HttpSession session = httpServletRequest.getSession();
        if (null != session) {
            User user = (User) session.getAttribute("user");
            if ( null == user && !httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
                String authorization = httpServletRequest.getHeader("Authorization");
                if ( StringUtils.isNotBlank(authorization) ){
                    authorization = authorization.substring(authorization.indexOf("USER"));
                    user = userMapper.selectByUserCode(authorization);
                }
            }
            if (null != user) {
                MDC.put("user", user.getLoginName());
            } else {
                log.info("用户登录失效");
                MDC.put("user", "未登录");
            }
        }
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            log.info("请求参数：{}",name + ":" + httpServletRequest.getParameter(name));
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("---------------请求拦截完毕------------<<<<<<<<");
    }
}
