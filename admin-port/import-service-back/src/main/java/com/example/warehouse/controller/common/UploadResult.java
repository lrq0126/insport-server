package com.example.warehouse.controller.common;

import java.io.Serializable;
import java.util.List;

/**
 * 上传文件统一返回对象
 *
 * @author <a href="mailto:p.zhang@wunding.com">ZhangPan</a>
 * @since 4.15.0
 */
public class UploadResult implements Serializable {

    public UploadResult() {
        this.success = false;
    }

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 文件上传后的路径
     */
    private String path;

    /**
     * 多个图片上传时，保存多个图片地址
     */
    private List<String> paths;

    /**
     * 针对课件上传
     * 文件对应scorm的媒体类型 scorm/mimes.xml
     */
    private String mime;

    /**
     */
    private String type;

    /**
     * 针对课件上传
     * scorm 的版本 2004或1.2
     */
    private String version;

    /**
     * 音视频的时间长度，单位：秒
     */
    private String playtime;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlaytime() {
        return playtime;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
