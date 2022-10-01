package com.example.warehouse.common;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;

@Configuration
@Slf4j
public class FileUtil {
    /**
     * 下载excel模版文件
     *
     * @param downloadFileName 下载的文件名称
     * @param dowloadFileUrl   下载路径[从resource/download/的子目录开始]
     * @param request
     * @param response
     * @throws Exception
     */
    public static void downloadExcel(String downloadFileName, String dowloadFileUrl, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;" + getFileName(request, downloadFileName));
        response.setContentType("application/vnd.ms-excel"); // 保存为excel
        // 获取下载文件的绝对路径
        String url = PdfUtils.getSystemPath() + "template/download/" + dowloadFileUrl;

        InputStream in = new FileInputStream(new File(url));
        OutputStream out = response.getOutputStream();

        int len = 1024;
        byte[] b = new byte[len];
        while ((len = in.read(b)) > 0) {
            out.write(b, 0, len);
        }
        in.close();
        out.flush();
        out.close();
    }


    public static void downloadPostExcel(String downloadFileName, String downloadFileUrl, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;" + getFileName(request, downloadFileName));
        response.setContentType("application/vnd.ms-excel"); // 保存为excel

        InputStream in = new FileInputStream(new File(downloadFileUrl));
        OutputStream out = response.getOutputStream();

        int len = 1024;
        byte[] b = new byte[len];
        while ((len = in.read(b)) > 0) {
            out.write(b, 0, len);
        }
        in.close();
        out.flush();
        out.close();
    }

    public static void downloadPoster(String downloadFileName, String downloadFileUrl,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;" + getFileName(request, downloadFileName));
        response.setContentType("image/jpeg");

        InputStream in = new FileInputStream(new File(downloadFileUrl));
        OutputStream out = response.getOutputStream();

        int len = 1024;
        byte[] b = new byte[len];

        while ((len = in.read(b)) > 0) {
            out.write(b, 0, len);
        }

        in.close();
        out.flush();
        out.close();
    }

    /**
     * 将文件名转译成不同浏览器能识别的字符编码
     *
     * @return
     */
    public static String getFileName(HttpServletRequest request, String filename) {
        String userAgent = request.getHeader("user-agent");
        String new_filename, rtn = "filename=\"" + filename + "\"";
        try {
            new_filename = URLEncoder.encode(filename, "UTF8");

            // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
            rtn = "filename=\"" + new_filename + "\"";
            if (userAgent != null) {
                userAgent = userAgent.toLowerCase();
                // IE浏览器，只能采用URLEncoder编码
                if (userAgent.indexOf("msie") != -1 || userAgent.indexOf("edge") != -1) {
                    rtn = "filename=\"" + new_filename + "\"";
                }
                // Opera浏览器只能采用filename*
                else if (userAgent.indexOf("opera") != -1) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
                // Safari浏览器，只能采用ISO编码的中文输出
                else if (userAgent.indexOf("safari") != -1) {
                    rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
                }
                // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                else if (userAgent.indexOf("applewebkit") != -1) {
                    new_filename = MimeUtility.encodeText(filename, "UTF8", "B");
                    rtn = "filename=\"" + new_filename + "\"";
                }
                // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                else if (userAgent.indexOf("mozilla") != -1) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }

        return rtn;
    }

    public static String getSuffix(String name) {
        String[] arr = name.split("\\.");
        if (arr.length > 1) {
            return arr[arr.length - 1];
        }
        return null;
    }

    /**
     * excel转换成二维数组
     *
     * @param excelFile excel文件对象
     * @return 二维数组(返回所有行 ， 包括第一行 ， 和从第二行开始的数据)
     */
    public static String[][] excelToArray(MultipartFile excelFile, int columnCount) {
        String[][] result = null;
        int totalRowCount = 0; // 实际总行数
        boolean isNomarlExists = true; // 是否正常退出下面的循环,如果在rowCount范围内的每行第一个单元格都不是空值，则是正常退出
        try {
            InputStream in = excelFile.getInputStream();
            WorkbookSettings ws = new WorkbookSettings();
            ws.setCellValidationDisabled(true);
            Workbook book = Workbook.getWorkbook(in,ws);
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

    public static void main(String[] args) {

        convertCharset("C:\\Users\\10375\\Documents\\WeChat Files\\wxid_3qo7ja9c5rbp22\\FileStorage\\File\\2020-11\\123",Charset.forName("GBK"),Charset.forName("UTF-8"),null);

    }

    /**
     * 转换文件编码格式
     * @param path 需要转换的文件或文件夹路径
     * @param fromCharset 原编码格式
     * @param toCharset   目标编码格式
     * @param expansion      需要转换的文件扩展名,如需全部转换则传 null
     */
    private static void convertCharset(String path, Charset fromCharset,Charset toCharset,String expansion ) {
        if (StrUtil.isBlank(path)) {
            return;
        }
        File file = cn.hutool.core.io.FileUtil.file(path);
        File[] listFiles = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (StrUtil.isBlank(expansion)) {
                    return true;
                }
                if (cn.hutool.core.io.FileUtil.isDirectory(pathname)||cn.hutool.core.io.FileUtil.extName(pathname).equals("java")) {
                    return true;
                }
                return false;
            }
        });
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isDirectory()) {
                String canonicalPath = cn.hutool.core.io.FileUtil.getCanonicalPath(listFiles[i]);
                //每个文件夹分个线程处理,提高点儿效率
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        convertCharset(canonicalPath,fromCharset,toCharset,expansion);
                    }
                }).start();
            }else {
                cn.hutool.core.io.FileUtil.convertCharset(listFiles[i], fromCharset,  toCharset);
                Console.log("转换完成文件名:{}",listFiles[i].getName());
            }
        }
    }

    public static String getSuffix2(String name) {
        if (StringUtils.isNotBlank(name) && ( name.indexOf(".") > 0 )){
        return name.substring(name.lastIndexOf("."));
    }
        return null;
    }

    /**
     * 创建文件夹
     * @param filePath
     * @param fileName  文件名
     * @return
     */
    public static File buildFileName(String filePath , String fileName){

        File files = new File(filePath);
        if(!files.exists()){
            files.mkdirs();
        }
        // 创建路径
        File file = new File(filePath+ "/" + fileName);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }
}
