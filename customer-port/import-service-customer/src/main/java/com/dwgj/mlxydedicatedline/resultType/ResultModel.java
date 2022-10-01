package com.dwgj.mlxydedicatedline.resultType;

import com.dwgj.mlxydedicatedline.config.CodeEnum;
import com.dwgj.mlxydedicatedline.enums.ResultStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 自定义返回结果
 * @author lrq
 * @Date 2019/4/3
 */
@ApiModel(value = "ResultModel",description = "返回对象类型")
public class ResultModel {

    /**
     * 返回码
     */
    @ApiModelProperty(value = "状态码，100是成功",name = "code",example = "100")
    private int code;

    /**
     * 返回结果描述
     */
    @ApiModelProperty(value = "返回结果描述",name = "message",example = "操作成功")
    private String message;

    /**
     * 返回内容
     */
    @ApiModelProperty(value = "返回内容",name = "content",example = "返回实际的数据对象")
    private Object content;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public ResultModel() {

    }public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "";
    }

    public ResultModel(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public ResultModel(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = null;
    }
    public ResultModel(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
        this.content = null;
    }

    public ResultModel(ResultStatus status, Object content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public static ResultModel ok(Object content) {
        return new ResultModel(ResultStatus.SUCCESS, content);
    }

    public static ResultModel ok() {
        return new ResultModel(ResultStatus.SUCCESS);
    }

    public static ResultModel error(ResultStatus error) {
        return new ResultModel(error);
    }

    public static ResultModel error(ResultStatus error,Object content) {
        return new ResultModel(error,content);//返回错误
    }

    public static ResultModel error(CodeEnum error) {
        return new ResultModel(error);
    }

}
