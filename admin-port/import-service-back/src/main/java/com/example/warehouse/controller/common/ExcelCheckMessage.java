package com.example.warehouse.controller.common;

import java.util.List;

/**
 * 模板校验结果消息封装类
 */
public class ExcelCheckMessage {

    private List<String> message;

    private List<?> objects;

    private String[][] excel;

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<?> getObjects() {
        return objects;
    }

    public void setObjects(List<?> objects) {
        this.objects = objects;
    }

    public String[][] getExcel() {
        return excel;
    }

    public void setExcel(String[][] excel) {
        this.excel = excel;
    }
}
