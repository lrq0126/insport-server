package com.example.warehouse.common;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Number;
import jxl.write.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel文件工具类，list列表中必须装的是实体或者String对象。 如果为实体对象，则会按照实体属性定义的顺序一一放入Excel表单元格中
 * 如果为String对象，则放入Excel的第一单元格中
 *
 * @author FJB
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class ExcelUtil {

    /**
     * excel单元格宽度，默认为20
     */
    public static int width = 20;
    public static final int EXCEL_MAX_SIZE = 65536 - 1; // excel支持的最大行数，除去表头

    /**
     * 导出excel 文件
     *
     * @param type     1导出list（根据实体类的属性导出） 2导出二维数组
     * @param response 当前输出的response
     * @param arrs     二维数组
     * @param list     要导出的数据列表
     * @param columns  实体对象中需要导出的属性（和列标题的顺序要一一对应）
     * @param fileName 文件名
     * @param title    excel单元格内的标题
     * @throws Exception
     */
    private static void dispose(int type, HttpServletResponse response,
                                String[][] arrs, List list, String[] columns, String fileName, String[] title) {
        try {

            if (response == null) {
                log.info("导出excel时没有获取到当前response");
                return;
            }
            if (type == 1) {
                if (list == null || list.size() == 0) {
                    log.info("没有导出的数据");
                    return;
                }
                if (columns == null || columns.length == 0) {
                    log.info("没有指定实体类中的属性");
                    return;
                }
            }
            if (type == 2) {
                if (arrs == null || arrs.length == 0) {
                    log.info("没有导出的数据");
                    return;
                }
            }
            if (fileName == null) {
                log.info("没有指定导出的文件名");
                return;
            }
            if (title == null || title.length == 0) {
                log.info("没有指定数据的列名");
                return;
            }


            // 处理中文的文件名
            /*String fileNameCN = new String(fileName.getBytes("utf-8"),
            		"ISO-8859-1");*/

            // 设置输出流
            OutputStream os = response.getOutputStream();
            /*response.reset();
            response.setContentType("application/vnd.ms-excel"); // 保存为excel
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ fileNameCN);*/

            // 判断导出列表长度是否大小Excel所支持的最大行数
            int size = 0;
            if (type == 1) {
                size = list.size();
            }
            if (type == 2) {
                size = arrs.length;
            }

            int count = 1; // Excel的sheet数
            if (size > EXCEL_MAX_SIZE) {
                count = size % EXCEL_MAX_SIZE == 0 ? size / EXCEL_MAX_SIZE
                        : size / EXCEL_MAX_SIZE + 1;
                size = EXCEL_MAX_SIZE;
            }

            // 创建Excel文件流
            WritableWorkbook wbook = Workbook.createWorkbook(os);
            // 设置Excel字体
            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10,
                    WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);
            WritableCellFormat titleFormat = new WritableCellFormat(wfont);
            titleFormat.setAlignment(Alignment.LEFT);
            // String[] tempNames = StringTool.convert(fileName).split("\\.");
            // Excel支持最大行数有限，超过最大行数的只能分多个sheet来写入
            int fileNameLen = fileName.length() >= 25 ? 25 : fileName.length();
            for (int c = 0; c < count; c++) {
                // 创建工作表
                WritableSheet wsheet = wbook.createSheet(fileName.substring(0, fileNameLen) + (c + 1), c); // 工作表名
                // 设置Excel表头
                for (int i = 0; i < title.length; i++) {
                    Label excelTitle = new Label(i, 0, title[i], titleFormat);
                    wsheet.addCell(excelTitle);
                    wsheet.setColumnView(i, width); // 设置列宽
                }

                // 把数据写入Excel单元格中
                if (type == 1) {// 根据list导出
                    for (int i = 0; i < size; i++) {
                        Object object = list.get(i + c * EXCEL_MAX_SIZE);
                        if (object instanceof String) { // 如果实体为String对象，则直接放入第一单元格
                            Label content1 = new Label(0, i + 1, object.toString());
                            wsheet.addCell(content1);
                        } else {
                            Class entityClass = object.getClass();
                            Field[] fields = entityClass.getDeclaredFields(); // 获取对象的字段
                            int cellNum = 0; // 实际显示时的列编号
                            for (String column : columns) {
                                for (int j = 0; j < fields.length; j++) {
                                    if (column.equals(fields[j].getName())) {
                                        fields[j].setAccessible(true); // 去除字段访问权限限制
                                        Object result = fields[j].get(object);
                                        if (result == null) {
                                            result = "";
                                        }
                                        Label content1 = new Label(cellNum, i + 1,
                                                String.valueOf(result));
                                        wsheet.addCell(content1);
                                        cellNum++;
                                        break;
                                    }
                                }

                            }
                        }
                    }
                    size = list.size() - (c + 1) * EXCEL_MAX_SIZE;
                } else if (type == 2) {// 根据二维数组导出
                    // 设置Excel表头
                    // for (int i = 0; i < title.length; i++) {
                    // Label excelTitle = new Label(i, 0, title[i], titleFormat);
                    // wsheet.addCell(excelTitle);
                    // wsheet.setColumnView(i, width); //设置列宽
                    // }
                    // 把数据写入Excel单元格中
                    for (int i = 0; i < size; i++) {
                        String[] arr = arrs[i + c * EXCEL_MAX_SIZE];
                        int cellNum = 0;
                        for (String str : arr) {
                            Label content1 = new Label(cellNum, i + 1, str == null ? "" : str);
                            boolean isNum = false;
                            if(StringUtils.isNotBlank(str)){
                                isNum = str.matches("^(-?\\d+)(\\.\\d+)?$");
                            }
                            if( isNum ){ // 如果数值是数字，就填充数字的格式，方便用函数统计
                                NumberFormat numberFormat = new NumberFormat("#0");
                                if ( cellNum == 8 ){ // 综合星际是有小数的
                                    numberFormat = new NumberFormat("#0.0");
                                }
                                WritableCellFormat cellFormat = new WritableCellFormat(numberFormat);
                                cellFormat.setAlignment(Alignment.LEFT);
                                Number number = new Number(cellNum,i + 1 ,Double.parseDouble(str),cellFormat);
                                wsheet.addCell(number);
                                cellNum++;
                                continue;
                            }
                            wsheet.addCell(content1);
                            cellNum++;
                        }
                    }
                    size = arrs.length - (c + 1) * EXCEL_MAX_SIZE;
                }

                if (size > EXCEL_MAX_SIZE) {
                    size = EXCEL_MAX_SIZE;
                }
            }

            wbook.write(); // 写入到os流中
            wbook.close();

            os.flush();
            os.close();
            log.info("log4j export finish");
            log.info("common logging export finish");
            System.out.println("System export finish");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 大标题生成实现方法
     *
     * @param type
     * @param response
     * @param arrs
     * @param list
     * @param columns
     * @param fileName
     * @param title
     * @param title1
     */
    private static void dispose1(int type, HttpServletResponse response,
                                 String[][] arrs, List list, String[] columns, String fileName, String[] title, String[] title1) {
        try {

            if (response == null) {
                log.info("导出excel时没有获取到当前response");
                return;
            }
            if (type == 1) {
                if (list == null || list.size() == 0) {
                    log.info("没有导出的数据");
                    return;
                }
                if (columns == null || columns.length == 0) {
                    log.info("没有指定实体类中的属性");
                    return;
                }
            }
            if (type == 2) {
                if (arrs == null || arrs.length == 0) {
                    log.info("没有导出的数据");
                    return;
                }
            }
            if (fileName == null) {
                log.info("没有指定导出的文件名");
                return;
            }
            if (title == null || title.length == 0) {
                log.info("没有指定数据的列名");
                return;
            }
            // 处理中文的文件名
            /*String fileNameCN = new String(fileName.getBytes("utf-8"),
            		"ISO-8859-1");*/
            // 设置输出流
            OutputStream os = response.getOutputStream();
            /*response.reset();
            response.setContentType("application/vnd.ms-excel"); // 保存为excel
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ fileNameCN);*/
            // 判断导出列表长度是否大小Excel所支持的最大行数
            int size = 0;
            if (type == 1) {
                size = list.size();
            }
            if (type == 2) {
                size = arrs.length;
            }
            int count = 1; // Excel的sheet数
            if (size > EXCEL_MAX_SIZE) {
                count = size % EXCEL_MAX_SIZE == 0 ? size / EXCEL_MAX_SIZE
                        : size / EXCEL_MAX_SIZE + 1;
                size = EXCEL_MAX_SIZE;
            }
            // 创建Excel文件流
            WritableWorkbook wbook = Workbook.createWorkbook(os);
            // 设置Excel字体
            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10,
                    WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);
            WritableCellFormat titleFormat = new WritableCellFormat(wfont);
            // String[] tempNames = StringTool.convert(fileName).split("\\.");
            // Excel支持最大行数有限，超过最大行数的只能分多个sheet来写入
            int fileNameLen = fileName.length() >= 25 ? 25 : fileName.length();
            for (int c = 0; c < count; c++) {
                // 创建工作表
                WritableSheet wsheet = wbook.createSheet(fileName.substring(0, fileNameLen) + (c + 1), c); // 工作表名
                // 设置Excel表头大标题
                for (int i = 0; i < title1.length; i++) {
                    if (title1[i] == "") {
                        Label excelTitle = new Label(i, 0, title1[i], titleFormat);
                        wsheet.addCell(excelTitle);
                        wsheet.setColumnView(i, width); // 设置列宽
                    }
                    if (title1[i] == "基本信息") {
                        Label excelTitle = new Label(i, 0, title1[i], titleFormat);
                        // titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
                        wsheet.addCell(excelTitle);
                        wsheet.mergeCells(i, 0, i + 1, 0);// 合并垮了2列.
                        wsheet.setColumnView(i, width); // 设置列宽
                    }
                    if (title1[i] == "学习") {
                        Label excelTitle = new Label(i + 1, 0, title1[i], titleFormat);
                        // titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
                        wsheet.addCell(excelTitle);
                        wsheet.mergeCells(i + 1, 0, i + 5, 0);// 合并垮了6列.
                        wsheet.setColumnView(i, width); // 设置列宽
                    }
                    if (title1[i] == "互动") {
                        Label excelTitle = new Label(i + 6, 0, title1[i], titleFormat);
                        // titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
                        wsheet.addCell(excelTitle);
                        wsheet.mergeCells(i + 6, 0, i + 9, 0);// 合并垮了4列.
                        wsheet.setColumnView(i, width); // 设置列宽
                    }
                }
                // 设置Excel表头小标题
                for (int i = 0; i < title.length; i++) {
                    Label excelTitle = new Label(i, 1, title[i], titleFormat);
                    wsheet.addCell(excelTitle);
                    wsheet.setColumnView(i, width); // 设置列宽
                }
                // 把数据写入Excel单元格中
                if (type == 1) {// 根据list导出
                    for (int i = 0; i < size; i++) {
                        Object object = list.get(i + c * EXCEL_MAX_SIZE);
                        if (object instanceof String) { // 如果实体为String对象，则直接放入第一单元格
                            Label content1 = new Label(0, i + 2, object.toString());
                            wsheet.addCell(content1);
                        } else {
                            Class entityClass = object.getClass();
                            Field[] fields = entityClass.getDeclaredFields(); // 获取对象的字段
                            int cellNum = 0; // 实际显示时的列编号
                            for (String column : columns) {
                                for (int j = 0; j < fields.length; j++) {
                                    if (column.equals(fields[j].getName())) {
                                        fields[j].setAccessible(true); // 去除字段访问权限限制
                                        Object result = fields[j].get(object);
                                        if (result == null) {
                                            result = "";
                                        }
                                        Label content1 = new Label(cellNum, i + 2,
                                                String.valueOf(result));
                                        wsheet.addCell(content1);
                                        cellNum++;
                                        break;
                                    }
                                }

                            }
                        }
                    }
                    size = list.size() - (c + 1) * EXCEL_MAX_SIZE;
                } else if (type == 2) {// 根据二维数组导出
                    // 设置Excel表头
                    // for (int i = 0; i < title.length; i++) {
                    // Label excelTitle = new Label(i, 0, title[i], titleFormat);
                    // wsheet.addCell(excelTitle);
                    // wsheet.setColumnView(i, width); //设置列宽
                    // }
                    // 把数据写入Excel单元格中
                    for (int i = 0; i < size; i++) {
                        String[] arr = arrs[i + c * EXCEL_MAX_SIZE];
                        int cellNum = 0;
                        for (String str : arr) {
                            Label content1 = new Label(cellNum, i + 2, str == null ? "" : str);
                            wsheet.addCell(content1);
                            cellNum++;
                        }
                    }
                    size = arrs.length - (c + 1) * EXCEL_MAX_SIZE;
                }

                if (size > EXCEL_MAX_SIZE) {
                    size = EXCEL_MAX_SIZE;
                }
            }

            wbook.write(); // 写入到os流中
            wbook.close();

            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }


    /**
     * 导出excel 文件
     *
     * @param response 当前输出的response
     * @param list     要导出的数据列表
     * @param fileName 文件名
     * @param title    excel单元格内的标题
     * @param columns  实体对象中需要导出的属性（和列标题的顺序要一一对应）
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, HttpServletRequest request,
                                   List list, String fileName, String[] title, String[] columns) {
        geneHead(response, request, fileName);
        dispose(1, response, null, list, columns, fileName, title);
    }

    /**
     * @throws UnsupportedEncodingException 通过自己传递数据集合区生成excel，不通过反射得到值，以便处理一些要得到对象中对象属性值等特殊情况 @Title:
     *                                      exportExcel1 @param @param response @param @param arrs 二维数组 @param @param fileName
     *                                      文件名 @param @param title 列名数组 @return void 返回类型 @throws
     */
    public static void exportExcel1(HttpServletResponse response, HttpServletRequest request, String[][] arrs, String fileName, String[] title)
            throws Exception {
        geneHead(response, request, fileName);
        dispose(2, response, arrs, null, null, fileName, title);
    }

    /**
     * 为生成大标题新建的方法
     *
     * @param response
     * @param request
     * @param arrs
     * @param fileName
     * @param title
     * @param title1
     * @throws Exception
     */
    public static void exportExcel2(HttpServletResponse response, HttpServletRequest request, String[][] arrs, String fileName, String[] title, String[] title1)
            throws Exception {
        geneHead(response, request, fileName);
        dispose1(2, response, arrs, null, null, fileName, title, title1);
    }

    /**
     * 提取公共部分
     *
     * @param response
     * @param request
     * @param fileName
     */
    public static void geneHead(HttpServletResponse response, HttpServletRequest request, String fileName) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Disposition", "attachment;" + FileUtil.getFileName(request, fileName));
    }

    /**
     * excel转换成二维数组
     *
     * @param excelFile excel文件对象
     * @return 二维数组(返回所有行 ， 包括第一行 ， 和从第二行开始的数据)
     */
    public static String[][] excelToArray(File excelFile, int columnCount) {
        String[][] result = null;
        int totalRowCount = 0; // 实际总行数
        boolean isNomarlExists = true; // 是否正常退出下面的循环,如果在rowCount范围内的每行第一个单元格都不是空值，则是正常退出
        try {
            InputStream in = new FileInputStream(excelFile);
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("ISO-8859-1");
            ws.setCellValidationDisabled(true);
            Workbook book = Workbook.getWorkbook(in, ws);
            // 得到第一个工作表对象
            Sheet sheet = book.getSheet(0);
            // 得到第一个工作表中的总行数 和总列数
            int rowCount = sheet.getRows();

            // 确定二维数组的行数和列数
            result = new String[rowCount][columnCount];

            // 循环取出excel的数据
            for (int i = 0; i < rowCount; i++) {
                Cell[] cells = sheet.getRow(i);
                // 如果一行的第一个单元是空值，则退出整个循环
                // if(null != cells[0] && null != cells[0].getContents() &&
                // !"".equals(cells[0].getContents().trim())){
                if (isNotBlank(cells)) {// 验证当前这条excel数据是否都为null或者空字符串 addby lzp
                    for (int j = 0; j < columnCount; j++) {
                        if (j < cells.length) {
                            result[i][j] = cells[j].getContents().trim();
                        } else {
                            break;
                        }
                    }
                } else {
                    totalRowCount = i;
                    isNomarlExists = false;
                    break;
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }

        // 重新声明一个数组，去掉原数组中的空值
        String[][] result2;
        if (!isNomarlExists) {
            result2 = new String[totalRowCount][columnCount];
            System.arraycopy(result, 0, result2, 0, totalRowCount);
            result = null; // 释放数组的引用
            return result2;
        }
        return result;
    }

    /**
     * @param cells
     * @return
     * @Description Excel检测一行数据是否都为NULL
     * @author lzp
     * @data 2016年4月26日下午4:09:49
     */
    private static boolean isNotBlank(Cell[] cells) {
        boolean result = false;
        if (null != cells) {
            String str = "";
            for (Cell cell : cells) {
                str += cell.getContents().trim();
            }
            result = StringUtils.isNotBlank(str);
        }
        return result;
    }

    /**
     * excel转换成二维数组
     *
     * @param excelFile
     * @param columnCount
     * @return
     */
    public static Map<String, Object> excelToArray2(File excelFile, int columnCount) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        String message = null;
        String[][] result = null;
        try {
            InputStream in = new FileInputStream(excelFile);
            WorkbookSettings ws = new WorkbookSettings();
            ws.setCellValidationDisabled(true);
            Workbook book = Workbook.getWorkbook(in, ws);
            // 得到第一个工作表对象
            Sheet sheet = book.getSheet(0);
            // 得到第一个工作表中的总行数 和总列数
            int rowCount = sheet.getRows();

            // 确定二维数组的行数和列数
            result = new String[rowCount][columnCount];

            // 循环取出excel的数据
            for (int i = 0; i < rowCount; i++) {
                Cell[] cells = sheet.getRow(i);
                if (cells.length <= 0) {// 本行数据为空
                    if (i == 0) {
                        message = "请下载正确的模版进行导入!";
                    } else {
                        message = "第" + (i + 1) + "行为空";
                    }
                    break;
                }
                if (cells.length < columnCount) { // 考虑到扩展
                    if (i == 0) {
                        message = "请下载正确的模版进行导入!";
                    } else {
                        message = "第" + (i + 1) + "行有空数据";
                    }
                    break;
                }
                // 如果一行的第一个单元是空值，则退出整个循环
                if (null != cells[0] && null != cells[0].getContents() && !"".equals(cells[0].getContents().trim())) {
                    result[i][0] = cells[0].getContents().trim();
                } else {
                    if (i == 0) {
                        message = "请下载正确的模版进行导入!";
                    } else {
                        message = "第" + (i + 1) + "行,第1列为空";
                    }
                    break;
                }
                if (null != cells[1] && null != cells[1].getContents() && !"".equals(cells[1].getContents().trim())) {
                    result[i][1] = cells[1].getContents().trim();
                } else {
                    if (i == 0) {
                        message = "请下载正确的模版进行导入!";
                    } else {
                        message = "第" + (i + 1) + "行,第2列为空";
                    }
                    break;
                }
            }
        } catch (Exception e) {
            resMap.put("mess", "请下载正确的模版进行导入!");
            return resMap;
        }

        if (message != null) {
            result = null;
        }
        resMap.put("mess", message);
        resMap.put("result", result);
        return resMap;
    }

//    public static void exportExcel(String[][] arrs, String fileName, String[] titles) throws Exception {
//        String path = ApplicationPropertiesUtil.getProperties().getUpload().getLocation() + FileUtil.separator + ConfigUtil.EXCEL_DOWNLOADPATH;
//        //判断文件夹是否存在
//        File file = new File(path);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//
//        path = path + FileUtil.separator + fileName;
//        // 创建Excel的工作书册 Workbook,对应到一个excel文档
//        HSSFWorkbook wb = new HSSFWorkbook();
//        // 创建Excel的工作sheet,对应到一个excel文档的tab
//        HSSFSheet sheet = wb.createSheet("sheet1");
//        // 设置excel每列宽度
//        sheet.setColumnWidth(0, 4000);
//        sheet.setColumnWidth(1, 3500);
//        // 创建字体样式
//        HSSFFont font = wb.createFont();
//        font.setFontName("Verdana");
//        font.setBoldweight((short) 100);
//        font.setFontHeight((short) 300);
//        font.setColor(HSSFColor.BLUE.index);
//        // 创建单元格样式
//        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        // 设置边框
//        style.setBottomBorderColor(HSSFColor.RED.index);
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style.setFont(font);// 设置字体
//        // 生成头部信息
//        HSSFRow row = sheet.createRow(0);
//        if (titles.length > 0) {
//            for (int i = 0; i < titles.length; i++) {
//                HSSFCell cell = row.createCell(i);
//                cell.setCellValue(titles[i]);
//            }
//        }
//        // 生成内容信息
//        //  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        for (int i = 0; i < arrs.length; i++) {
//            HSSFRow r = sheet.createRow(1 + i);
//            String[] temp = arrs[i];
//            for (int j = 0; j < temp.length; j++) {
//                r.createCell(j).setCellValue(temp[j]);// 序号
//            }
//
//        }
//
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(path);
//            wb.write(os);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
}
