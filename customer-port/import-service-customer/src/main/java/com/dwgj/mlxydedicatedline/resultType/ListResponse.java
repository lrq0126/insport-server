package com.dwgj.mlxydedicatedline.resultType;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * 数据对象类型
 * @date 2019年9月26日23:54:44
 * @author guoshengwen
 */
public class ListResponse<T> extends BaseResponse {
    ListResponse<T>.ListData<T> data;

    public ListResponse(PageData pageData, List<T> list) {
        this.data = new ListResponse.ListData(pageData, list);
    }

    public ListResponse<T>.ListData<T> getData() {
        return this.data;
    }

    public void setData(ListResponse<T>.ListData<T> data) {
        this.data = data;
    }

    class ListData<T> {
        PageData pageData;
        List<T> list;

        public ListData(PageData pageData, List<T> list) {
            this.pageData = pageData;
            this.list = list;
        }

        public PageData getTotal() {
            return this.pageData;
        }

        public void setTotal(PageData pageData) {
            this.pageData = pageData;
        }

        public List<T> getList() {
            return this.list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }
    }
}
