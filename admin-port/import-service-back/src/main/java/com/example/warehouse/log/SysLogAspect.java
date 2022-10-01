package com.example.warehouse.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.sys.LogOperation;
import com.example.warehouse.service.UserService;
import com.example.warehouse.service.sys.LogOperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect  // 使用@Aspect注解声明一个切面
@Component
@Slf4j
@ComponentScan("com.example.warehouse.log")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SysLogAspect {
    @Autowired
    private LogOperationService logOperationService;

    @Autowired
    private UserService userService;

    // 切入需要拦截的声明日志类
    @Pointcut("@annotation(com.example.warehouse.log.SysLog)")
    public void logPointCut() {}

    /**
     * 环绕通知 @Around  ， 当然也可以使用 @Before (前置通知)  @After (后置通知)
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint  point) throws Throwable {
        Object result = point.proceed();
        try {
            saveLog(point,result);
        }catch (NullPointerException e){
            e.printStackTrace();
            log.error("[操作日志时，发现用户未登录，请重新登录]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保存日志
     * @param joinPoint
     */
    private void saveLog(ProceedingJoinPoint joinPoint,Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogOperation logOperation = new LogOperation();

        // 通过AnnotationResolve解析注解属性参数
        AnnotationResolver annotationResolver = AnnotationResolver.newInstance();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        Object paramObj = annotationResolver.resolver(joinPoint, sysLog.contentId());
        if( sysLog != null ){
            //注解上的描述
            logOperation.setContent(sysLog.value());
            logOperation.setContentType(sysLog.type());
            logOperation.setContentId(paramObj + "");
        }

        // 获取接口返回状态
        JSONObject resultObject = (JSONObject)JSONObject.toJSON(result);
        JSONObject bodyObject = resultObject.getJSONObject("body");
        logOperation.setRespCode(bodyObject.get("code").toString());
        logOperation.setRespMessage(bodyObject.get("message").toString());

        // 获取当前用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        User user = (User) session.getAttribute("user");
        if ( null == user ){
            //接口请求成功
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String authorization = request.getHeader("Authorization");
            if ( StringUtils.isNotBlank(authorization) ){
                authorization = authorization.substring(authorization.indexOf("USER"));
                user = userService.getUserByUserCode(authorization);
            }
        }
        logOperation.setOpertor(user.getId());
        logOperation.setCreateId(user.getId());
        logOperation.setOpertorName(user.getUsername());

        //请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        logOperation.setReqUrl(className + "." + methodName);
        //请求的参数
        Object[] args = joinPoint.getArgs();
        try{
            List<String> list = new ArrayList<String>();
            for (Object o : args) {
                list.add(JSON.toJSONString(o));
            }
            logOperation.setReqParam(list.toString());
        }catch (Exception e){

        }

        try {
            logOperationService.saveLogOperation(logOperation);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
