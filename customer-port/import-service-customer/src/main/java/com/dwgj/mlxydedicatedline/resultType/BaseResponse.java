package com.dwgj.mlxydedicatedline.resultType;

/**
 * 基本返回类型
 * @date 2019年9月26日23:45:35
 * @author guoshengwen
 */
public class BaseResponse {
    private int resultCode = 0;
    private String resultMsg;

    public BaseResponse(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public BaseResponse() {
    }

    public String getResultMsg() {
        return this.resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
