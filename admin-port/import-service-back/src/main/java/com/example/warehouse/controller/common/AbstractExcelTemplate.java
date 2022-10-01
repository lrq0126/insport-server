package com.example.warehouse.controller.common;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractExcelTemplate {

    private String[] titles;

    AbstractExcelTemplate(String[] titles) {
        this.titles = titles;
    }

    public ExcelCheckMessage check(String[][] excel) {
        return check(excel, titles);
    }

    /**
     * 默认excel基础校验方法
     *
     * @param excel  excel解析后的二维数组
     * @param titles 模板的标题列表
     * @return {@link ExcelCheckMessage}
     */
    private ExcelCheckMessage baseCheck(String[][] excel, String[] titles) {
        ExcelCheckMessage excelCheckMessage = new ExcelCheckMessage();
        List<String> messages = new LinkedList<>();

        if (null == excel) {
            messages.add("请下载正确的模版进行导入!");
        } else if (excel.length == 0) {
            messages.add("请下载正确的模版进行导入!");
        } else if (excel[0].length != titles.length) {
            messages.add("请下载正确的模版进行导入!");
        } else {
            for (int i = 0; i < titles.length; i++) {
                if (!titles[i].equals(excel[0][i])) {
                    messages.add("请下载正确的模版进行导入!");
                    break;
                }
            }
            if (messages.isEmpty() && excel.length == 1) {
                messages.add("导入的文件中没有数据!");
            }
        }

        excelCheckMessage.setMessage(messages);
        return excelCheckMessage;
    }

    /**
     * 校验excel文件
     *
     * @param excel  excel解析后的二维数组
     * @param titles 模板的标题列表
     * @return {@link ExcelCheckMessage}
     */
    private ExcelCheckMessage check(String[][] excel, String[] titles) {
        ExcelCheckMessage excelCheckMessage = baseCheck(excel, titles);
        if (excelCheckMessage.getMessage().isEmpty()) {
            excelCheckMessage.setExcel(excel);
            return doCheck(excelCheckMessage);
        }
        return excelCheckMessage;
    }

    /**
     * 返回为空的错误信息
     *
     * @param rowNum 行号
     * @param title  标题
     * @return 错误信息
     */
    String emptyMessage(int rowNum, String title) {
        return "第" + rowNum + "行" + title + "不能为空!";
    }

    /**
     * 返回超过超度的错误信息
     *
     * @param rowNum 行号
     * @param title  标题
     * @param len    限制长度
     * @return 错误信息
     */
    String longMessage(int rowNum, String title, int len) {
        return "第" + rowNum + "行" + title + "长度不能超过" + len + "位!";
    }

    /**
     * 返回值错误的错误信息
     *
     * @param rowNum 行号
     * @param title  标题
     * @return 错误信息
     */
    String errorMessage(int rowNum, String title) {
        return "第" + rowNum + "行" + title + "填写错误!";
    }

    /**
     * 查找重复信息，并返回错误信息列表
     *
     * @param values 信息列表
     * @param title  信息标题
     * @return 错误信息列表
     */
    List<String> duplicateValue(List<String> values, String title) {
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < values.size() - 1; i++) {
            if (!StringUtils.hasText(values.get(i))) {
                continue;
            }
            for (int j = i + 1; j < values.size(); j++) {
                if (!StringUtils.hasText(values.get(j))) {
                    continue;
                }
                if (values.get(i).equals(values.get(j))) {
                    messages.add("第" + (i + 2) + "行和第" + (j + 2) + "行" + title + "重复!");
                }
            }
        }
        return messages;
    }


    /**
     * 有效选项编号验证
     *
     * @param rowNum
     * @param title
     * @return
     */
    String validNumMessage(int rowNum, String title) {
        return "第" + rowNum + "行" + title + "只能是A-Z!";
    }

    /**
     * 验证编号是否从A开始
     *
     * @param rowNum
     * @param title
     * @return
     */
    String validStartMessage(int rowNum, String title) {
        return "第" + rowNum + "行" + title + "必须从A开始!";
    }

    /**
     * 验证选项编号是否连续
     *
     * @param rowNum
     * @param title
     * @return
     */
    String continuousMessage(int rowNum, String title) {
        return "第" + rowNum + "行" + title + "必须连续!";
    }

    /**
     * 选项至少两个
     *
     * @param rowNum
     * @param title
     * @return
     */
    String minNumMessage(int rowNum, String title) {
        return "第" + rowNum + "行" + title + "至少有2个!";
    }

    /**
     * 判断题只能有2个选项
     *
     * @param rowNum
     * @param
     * @return
     */
    String maxNumMessage(int rowNum) {
        return "第" + rowNum + "行判断题只能有2个选项!";
    }

    /**
     * 子类自己去实现自己的校验规则
     *
     * @param excelCheckMessage {@link ExcelCheckMessage}
     * @return {@link ExcelCheckMessage}
     */
    abstract ExcelCheckMessage doCheck(ExcelCheckMessage excelCheckMessage);

}

