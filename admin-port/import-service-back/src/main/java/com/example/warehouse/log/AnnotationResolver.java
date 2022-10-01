package com.example.warehouse.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 获取注解的详细信息 注解的值
 * @author wenguosheng
 * @create 2021-03-23 5:59 PM
 */
public class AnnotationResolver {

    private static AnnotationResolver resolver;
    public static AnnotationResolver newInstance() {
        if (resolver == null) {
            return resolver = new AnnotationResolver();
        } else {
            return resolver;
        }
    }
    /**
     * 解析注解上的值
     *
     * @param joinPoint
     * @param str       需要解析的字符串
     * @return
     */
    public Object resolver(JoinPoint joinPoint, String str) {
        if (str == null) {
            return null;
        }
        Object value = null;
        // 如果name匹配上了#,则表示为map，使用map.get方法
        if (str.startsWith("Map.")) {
            String newStr = str.replaceAll("Map.", "").replaceAll("", "");
            if(newStr.contains(".")){
                try {
                    value = complexResolverMap(joinPoint, newStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            String newStr = str.replaceAll("", "");
            // 复杂类型 调用对象.get属性()方法
            if (newStr.contains(".")) {
                try {
                    value = complexResolver(joinPoint, newStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                value = simpleResolverPost(joinPoint, newStr);
            }
        }
        return value;
    }

    private Object complexResolver(JoinPoint joinPoint, String str) throws Exception {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        String[] strs = str.split("\\.");

        for (int i = 0; i < names.length; i++) {
            if (strs[0].equals(names[i])) {
                Object obj = args[i];
                Method dmethod = obj.getClass().getDeclaredMethod(getMethodName(strs[1]), null);
                Object value = dmethod.invoke(args[i]);
                return getValue(value, 1, strs);
            }
        }
        return null;
    }
    private Object complexResolverMap(JoinPoint joinPoint, String str) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        String[] strs = str.split("\\.");
        for (int i = 0; i < names.length; i++) {
            if (strs[0].equals(names[i])) {
                Map<String,Object> obj = (Map<String, Object>) args[i];
                return obj.get(strs[1]);
            }
        }
        return null;
    }

    private Object getValue(Object obj, int index, String[] strs) {
        try {
            if (obj != null && index < strs.length - 1) {
                Method method = obj.getClass().getDeclaredMethod(getMethodName(strs[index + 1]), null);
                obj = method.invoke(obj);
                getValue(obj, index + 1, strs);
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getMethodName(String name) {
        return "get" + name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
    }
    private Object simpleResolver(JoinPoint joinPoint, String str) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < names.length; i++) {
            if (str.equals(names[i])) {
                return args[i];
            }
        }
        return null;
    }

    private Object simpleResolverPost(JoinPoint joinPoint, String str) {
        return joinPoint.getArgs()[0];
    }
}
