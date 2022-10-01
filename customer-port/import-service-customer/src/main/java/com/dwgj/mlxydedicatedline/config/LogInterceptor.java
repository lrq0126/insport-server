package com.dwgj.mlxydedicatedline.config;

import com.dwgj.mlxydedicatedline.commons.DateUtil;
import com.dwgj.mlxydedicatedline.config.redis.RedisUtilNew;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.user.LogRequest;
import com.dwgj.mlxydedicatedline.mapper.user.LogRequestMapper;
import com.dwgj.mlxydedicatedline.mapper.user.LoginTokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @des 日志拦截器
 * @author wenguosheng
 * @date 2019年4月24日00:20:31
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtilNew redisUtilNew;

    @Resource
    private LoginTokenMapper loginTokenMapper;
    @Autowired
    private LogRequestMapper logRequestMapper;
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

        // 登录凭证失效则重新登录
        if(checkURI(httpServletRequest)){
            // 获取请求头的 Token 验证码信息
            String userToken = httpServletRequest.getHeader("authorization");
            Customer user = loginTokenMapper.getUser(userToken);
            log.info("当前用户信息：{}",user);
            if( null == user){
                httpServletResponse.setContentType("text/html;charset=utf-8");
                PrintWriter writer = httpServletResponse.getWriter();
                writer.print("{\n" +
                        " \"code\": 401,\n" +
                        " \"message\": \"用户登录失效，请重新登录！\"\n" +
                        "}");
                writer.close();
                log.error("请求失败：当前用户失效");
                return false;
            }
            else {
                UserThreadContext.setUser(user);
                return true;
            }

        }
        return true;

    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("---------------请求拦截完毕------------<<<<<<<<");
        // 移除当前用户栈
        UserThreadContext.remove();
    }

    /**
     * 过滤不需要拦截的uri请求
     * @param httpServletRequest
     * @return
     */
    private boolean checkURI(HttpServletRequest httpServletRequest) throws IOException {
        // 是否过滤请求uri
        boolean isPass = true;

        //需要过滤的uri，不对这些uri进行拦截
        Map<String,Object> uriMap = new HashMap<>();
        // 用户登录
        uriMap.put("/log/login","/log/login");
        // 用户登录
        uriMap.put("/log/checkLoginStatus","/log/checkLoginStatus");
        // 国家列表
        uriMap.put("/sysDictDetail/find/country/list","/sysDictDetail/find/country/list");
        uriMap.put("/sysDictDetail/findCountrySimpleList","/sysDictDetail/findCountrySimpleList");

        uriMap.put("/commercialArea/getCountryCommercialArea","/commercialArea/getCountryCommercialArea");
        // 用户注册
        uriMap.put("/log/register","/log/register");
        // 查询报价
        uriMap.put("/routePrice/price/quotation","/routePrice/price/quotation");
        // 微信授权
        uriMap.put("/weixin/verify","/weixin/verify");
        uriMap.put("/weixin/getUserInfoByCode","/weixin/getUserInfoByCode");
        uriMap.put("/weixin/getTicket","/weixin/getTicket");
        uriMap.put("/weixin/getSignature","/weixin/getSignature");
        // 首页拼团置顶列表接口
        uriMap.put("/pin/homeList","/pin/homeList");
        uriMap.put("/pin/confirm","/pin/confirm");
        // 接口文档
        uriMap.put("/swagger","/swagger-ui.html");
        uriMap.put("/webjars","/webjars");

        if("/routePrice/price/quotation".equalsIgnoreCase(httpServletRequest.getRequestURI())){
            LogRequest request = new LogRequest();
            request.setCreateTime(DateUtil.timestamp2String(new Date()));
            request.setMessage("报价查询");
            request.setRequestUrl(httpServletRequest.getRequestURI());
            request.setRequestIp(NetWorkUtil.getIpAddress(httpServletRequest));
            logRequestMapper.insert(request);
        }

        if(uriMap.containsKey(httpServletRequest.getRequestURI())){
            log.info("过滤不需要的拦截请求：{}",httpServletRequest.getRequestURI());
            isPass = false;
        }

        //过滤不需要的拦截请求,swagger-ui测试接口文档
        if ( getLikeByMap(httpServletRequest.getRequestURI()) ){
            isPass = false;
        }
        return isPass;
    }

    /**
     * 模糊匹配键
     * @param keyLike
     * @return
     */
    public static boolean getLikeByMap(String keyLike){
        if ( StringUtils.isBlank(keyLike) ){
            return false;
        }

        if ( keyLike.startsWith("/webjars") || keyLike.startsWith("/swagger") ){
            return true;
        }

        return false;
    }

}
