package com.dwgj.mlxydedicatedline.resultType;

/**
 * 对象返回类型
 * @date 2019年9月26日23:46
 * @author guoshengwen
 */
public class ObjectResponse<T> extends BaseResponse {
    T data;

    public ObjectResponse() {
    }

    public ObjectResponse data(T data) {
        this.setData(data);
        return this;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
