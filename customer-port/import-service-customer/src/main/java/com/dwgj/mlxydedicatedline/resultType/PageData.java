package com.dwgj.mlxydedicatedline.resultType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @des 分页记录
 * @author wenguosheng
 * @date 2019年4月24日20:14:59
 */
@ApiModel(value = "PageData",description = "分页页码返回")
public class PageData {
    /**
     * 传入页码
     */
    @ApiModelProperty(value = "前端的页码",name = "number",example = "1")
    private Integer number;
    /**
     * 当前分页码
     */
    @ApiModelProperty(value = "当前分页码（后端mysql的，前端不用理会）",name = "pageNumber",example = "10")
    private Integer pageNumber;
    /**
     * 每页显示条目
     */
    @ApiModelProperty(value = "每页显示条目",name = "pageSize",example = "10")
    private Integer pageSize;
    /**
     * 总数
     */
    @ApiModelProperty(value = "总数",name = "total",example = "10")
    private Integer total;
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
