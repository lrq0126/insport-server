package com.example.warehouse.model;

import com.example.warehouse.common.PageData;
import com.example.warehouse.config.CodeEnum;
import com.example.warehouse.enums.ResultStatus;

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

    public PageResultModel(int code, String message) {
        super(code,message);
    }

    public PageResultModel(ResultStatus status, Object content) {
        super(status,content);
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

    public PageResultModel(ResultStatus status, Object content,Map<String,Object> map) {
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
        return new PageResultModel(ResultStatus.SUCCESS,object,data);
    }

    public static PageResultModel error(ResultStatus error, Object content) {
        return new PageResultModel(error,content);//返回错误
    }

    public static PageResultModel error(CodeEnum error) {
        return new PageResultModel(error);
    }
    public static PageResultModel error(int code,String message) {
        return new PageResultModel(code,message);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public PageData getData() {
        return data;
    }

    public void setData(PageData data) {
        this.data = data;
    }
}
