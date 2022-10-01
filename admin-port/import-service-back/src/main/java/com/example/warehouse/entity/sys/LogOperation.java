package com.example.warehouse.entity.sys;

import com.example.warehouse.log.SysLog;

import java.io.Serializable;
import java.util.Date;

/**
 * log_operation
 * @author
 */
public class LogOperation implements Serializable {
    private Integer id;

    /**
     * 操作类型：delete删除、add新增、update编辑
     */
    private SysLog.Type contentType;

    /**
     * 资源id,例如快递单号，例如发货订单号
     */
    private String contentId;

    /**
     * 操作员id
     */
    private Integer opertor;

    /**
     * 操作员名称
     */
    private String opertorName;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 请求接口
     */
    private String reqUrl;

    /**
     * 请求参数
     */
    private String reqParam;

    /**
     * 请求类型：post,get或其他
     */
    private String reqType;

    /**
     * 返回状态：100成功，500错误
     */
    private String respCode;

    /**
     * 返回信息
     */
    private String respMessage;

    private Integer createId;

    private String createTime;

    /**
     * 状态：0删除1有效
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SysLog.Type getContentType() {
        return contentType;
    }

    public void setContentType(SysLog.Type contentType) {
        this.contentType = contentType;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Integer getOpertor() {
        return opertor;
    }

    public void setOpertor(Integer opertor) {
        this.opertor = opertor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqParam() {
        return reqParam;
    }

    public void setReqParam(String reqParam) {
        this.reqParam = reqParam;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LogOperation other = (LogOperation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getContentType() == null ? other.getContentType() == null : this.getContentType().equals(other.getContentType()))
            && (this.getContentId() == null ? other.getContentId() == null : this.getContentId().equals(other.getContentId()))
            && (this.getOpertor() == null ? other.getOpertor() == null : this.getOpertor().equals(other.getOpertor()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getReqUrl() == null ? other.getReqUrl() == null : this.getReqUrl().equals(other.getReqUrl()))
            && (this.getReqParam() == null ? other.getReqParam() == null : this.getReqParam().equals(other.getReqParam()))
            && (this.getReqType() == null ? other.getReqType() == null : this.getReqType().equals(other.getReqType()))
            && (this.getRespCode() == null ? other.getRespCode() == null : this.getRespCode().equals(other.getRespCode()))
            && (this.getRespMessage() == null ? other.getRespMessage() == null : this.getRespMessage().equals(other.getRespMessage()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getContentType() == null) ? 0 : getContentType().hashCode());
        result = prime * result + ((getContentId() == null) ? 0 : getContentId().hashCode());
        result = prime * result + ((getOpertor() == null) ? 0 : getOpertor().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getReqUrl() == null) ? 0 : getReqUrl().hashCode());
        result = prime * result + ((getReqParam() == null) ? 0 : getReqParam().hashCode());
        result = prime * result + ((getReqType() == null) ? 0 : getReqType().hashCode());
        result = prime * result + ((getRespCode() == null) ? 0 : getRespCode().hashCode());
        result = prime * result + ((getRespMessage() == null) ? 0 : getRespMessage().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", contentType=").append(contentType);
        sb.append(", contentId=").append(contentId);
        sb.append(", opertor=").append(opertor);
        sb.append(", content=").append(content);
        sb.append(", reqUrl=").append(reqUrl);
        sb.append(", reqParam=").append(reqParam);
        sb.append(", reqType=").append(reqType);
        sb.append(", respCode=").append(respCode);
        sb.append(", respMessage=").append(respMessage);
        sb.append(", createId=").append(createId);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getOpertorName() {
        return opertorName;
    }

    public void setOpertorName(String opertorName) {
        this.opertorName = opertorName;
    }
}