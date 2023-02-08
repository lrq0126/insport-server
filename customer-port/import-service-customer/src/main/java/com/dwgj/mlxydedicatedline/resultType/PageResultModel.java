package com.dwgj.mlxydedicatedline.resultType;


import com.dwgj.mlxydedicatedline.config.CodeEnum;
import com.dwgj.mlxydedicatedline.enums.ResultStatus;

import java.util.Map;

/**
 * @des 返回接口增强，添加Map，加入分页信息
 * @author wenguosheng
 * @date 2019年4月24日00:51:55
 */
public class PageResultModel extends ResultModel {
    protected Map<String,Object> map;//分页信息

    protected PageData data; //分页信息实体类

    public PageResultModel(){ super();}

    public PageResultModel(int code, String message, Object content, Map<String,Object> map){
        super(code,message,content);
        this.map = map;
    }
    public PageResultModel(int code, String message, Object content, PageData pageData){
        super(code,message,content);
        this.data = pageData;
    }

    public PageResultModel(int code, String message) {
        super(code,message);
    }

    public PageResultModel(int code, String message, Object content) {
        super(code,message,content);
    }

    public PageResultModel(ResultStatus status) {
        super(status);
    }
    public PageResultModel(CodeEnum codeEnum) {
        super(codeEnum);
    }

    public PageResultModel(ResultStatus status, Object content, Map<String,Object> map) {
        super(status,content);
        this.map = map;
    }

    public PageResultModel(ResultStatus status, Object content, PageData data) {
        super(status,content);
        this.data = data;
    }

    public static PageResultModel ok(){
        return new PageResultModel(ResultStatus.SUCCESS);
    }

    public static PageResultModel ok(Object object,Map<String,Object> map){
        return new PageResultModel(ResultStatus.SUCCESS,object,map);
    }

    public static PageResultModel ok(Object object, PageData data){
        return new PageResultModel(ResultStatus.SUCCESS, object, data);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
