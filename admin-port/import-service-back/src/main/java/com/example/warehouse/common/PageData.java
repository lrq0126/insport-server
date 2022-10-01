package com.example.warehouse.common;

/**
 * @des 分页记录
 * @author wenguosheng
 * @date 2019年4月24日20:14:59
 */
public class PageData {
    /**
     * 传入页码
     */
    private Integer number;
    /**
     * 当前分页码
     */
    private Integer pageNumber;
    /**
     * 每页显示条目
     */
    private Integer pageSize;
    /**
     * 总数
     */
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
