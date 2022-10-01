package com.dwgj.mlxydedicatedline.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author guoshengwen
 */
@Component
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    private static String UPLOAD_LOCATION;

    /**
     * 图片上传最大的尺寸
     */
    public static Long IMAGE_MAX_SIZE = 2 * 1024 * 1024L;

    /**
     * 上传文件路径
     */
    @Value("${upload.location}")
    public void setUploadLocation(String uploadLocation) {
        FileUtil.UPLOAD_LOCATION = uploadLocation;
    }


    /**
     * 文件路径分隔符，全部使用"/"，因为 windows 上兼容 linux 的路径,
     * 出现错误的情况主要出现在开发人员环境是 windows。其它服务器环境都是 linux，不会出现此问题
     */
    public static final String separator = "/";

    private FileUtil() {
    }

    public static String buildNewName(String name, String newName) {
        String[] arr = name.split("\\.");
        if (arr.length > 1) {
            return newName + "." + arr[arr.length - 1].toLowerCase();
        }
        return name;
    }

    /**
     * 创建文件夹
     * @param fileType  文件类型
     * @param fileName  文件名
     * @return
     */
    public static File buildFileName(String fileType , String fileName){

        StringBuilder strBuilder = new StringBuilder(UPLOAD_LOCATION + separator);
        if ( StringUtils.isNotBlank(fileType) ){
            strBuilder.append(fileType);
            strBuilder.append(separator);
        }
        strBuilder.append(DateUtil.getYMStr());
        strBuilder.append(separator);
        if (StringUtils.isNotBlank(fileName)) {
            strBuilder.append(fileName);
        }
        // 创建路径
        File file = new File(strBuilder.toString());
        exitDirs(strBuilder.toString());

        return file;
    }

    public static String getSuffix(String name) {
        String[] arr = name.split("\\.");
        if (arr.length > 1) {
            return "." + arr[arr.length - 1];
        }
        return null;
    }

    public static String getSuffix2(String name) {
        if (StringUtils.isNotBlank(name) && ( name.indexOf(".") > 0 )){
            return name.substring(name.lastIndexOf("."));
        }
        return null;
    }

    public static String getRealPath(String type, String name) {
        StringBuilder strBuilder = new StringBuilder(UPLOAD_LOCATION + separator);
        strBuilder.append(type);
        strBuilder.append(separator);
        strBuilder.append(DateUtil.getYMStr());
        strBuilder.append(separator);
        if (StringUtils.isNotBlank(name)) {
            strBuilder.append(name);
        }
        return strBuilder.toString();
    }


    public static String getRelativePath(String type, String category, String name) {
        StringBuilder strBuilder = new StringBuilder(separator);
        strBuilder.append(type);
        strBuilder.append(separator);
        strBuilder.append(DateUtil.getYMStr());
        strBuilder.append(separator);
        strBuilder.append(category);
        strBuilder.append(separator);
        if (StringUtils.isNotBlank(name)) {
            strBuilder.append(name);
        }
        return strBuilder.toString();
    }

    /**
     * @param name
     * @return
     * @description 获取临时路径
     * @author wlq
     * @date 2015年5月7日上午9:04:28
     */
    public static String getTempPath(String name) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(separator).append(UPLOAD_LOCATION);
        strBuilder.append(separator).append(DateUtil.getYMStr()).append(separator);
        if (StringUtils.isNotBlank(name)) {
            strBuilder.append(name).append(separator);
        }
        return strBuilder.toString();
    }

    public static String getPhysicalPath(String path) {
        return UPLOAD_LOCATION + path;
    }

    /**
     * 检查是否存在文件或者目录，不存在就直接创建
     * @param path
     * @return
     */
    public static boolean exitDirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }

    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        // file.renameTo(file);//后面考虑使用

        try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
            BufferedInputStream inBuff = new BufferedInputStream(fileInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            BufferedOutputStream outBuff = new BufferedOutputStream(fileOutputStream)) {
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len = 0;
            while ((len = inBuff.read(b)) > 0) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param srcPath    String 原文件路径 如：c:/fqf
     * @param targetPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String srcPath, String targetPath) {
        try {
            // 如果文件夹不存在 则建立新文件夹
            (new File(targetPath)).mkdirs();
            File a = new File(srcPath);
            String[] file = a.list();
            File temp;
            for (int i = 0; i < file.length; i++) {
                if (srcPath.endsWith(separator)) {
                    temp = new File(srcPath + file[i]);
                } else {
                    temp = new File(srcPath + separator + file[i]);
                }
                if (temp.isFile()) {
                    try (FileInputStream input = new FileInputStream(temp);
                        FileOutputStream output = new FileOutputStream(
                            targetPath + separator + (temp.getName()).toString())) {
                        byte[] b = new byte[1024 * 5];
                        int len;
                        while ((len = input.read(b)) != -1) {
                            output.write(b, 0, len);
                        }
                        output.flush();
                    }
                }
                if (temp.isDirectory()) {
                    // 如果是子文件夹
                    copyFolder(srcPath + separator + file[i], targetPath + separator + file[i]);
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static Map<String, Integer> getImgWH(byte[] bytes) {
        InputStream is = new ByteArrayInputStream(bytes);
        Map<String, Integer> map = new HashMap<>(8);
        // 原始图片宽
        int width = 0;
        // 原始图片高
        int height = 0;
        try {
            BufferedImage bufimg = ImageIO.read(is);
            // 只有图片才获取高宽
            if (bufimg != null) {
                width = bufimg.getWidth();
                height = bufimg.getHeight();
            }
            is.close();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            map.put("width", width);
            map.put("height", height);
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return map;
    }

    /**
     * @param currPath 相对路径
     * @return
     * @description 删除文件
     * @author wlq
     * @date 2015年4月18日下午5:45:14
     */
    public static void deleteFile(String currPath) {
        File file = new File(currPath);
        if (file.isDirectory()) {
            File[] ff = file.listFiles();
            for (int i = 0; i < ff.length; i++) {
                deleteFile(ff[i].getPath());
            }
        }
        file.delete();
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param filepath 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a deletion fails, the method
     * stops attempting to delete and returns "false".
     */
    public static void deleteDir(String filepath) throws IOException {
        // 定义文件路径
        File f = new File(filepath);
        // 判断是文件还是目录
        if (f.exists() && f.isDirectory()) {
            // 若目录下没有文件则直接删除
            if (f.listFiles().length == 0) {
                f.delete();
            } else {
                // 若有则把文件放进数组，并判断是否有下级目录
                File[] delFile = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        // 递归调用del方法并取得子目录路径
                        deleteDir(delFile[j].getAbsolutePath());
                    }
                    // 删除文件
                    delFile[j].delete();
                }
            }
        }
    }

    /**
     * 将String数据存为文件
     */
    public static File getFileFromString(String content, String path) {
        File file = null;
        try {
            file = new File(path);
            try (FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);) {
                byte[] b = content.getBytes(StandardCharsets.UTF_8.name());
                bos.write(b);
            }
        } catch (FileNotFoundException e) {
            log.error("文件找不到", e);
        } catch (IOException e) {
            log.error("IO操作异常", e);
        }
        return file;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            log.error("文件找不到", e);
        } catch (IOException e) {
            log.error("IO操作异常", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException ioe) {
            }
        }
        return buffer;
    }



    /**
     * @param sourceDir
     * @param targetDir
     * @throws IOException
     * @Description 拷贝文件夹
     * @author lzp
     * @data 2016年7月6日下午3:41:17
     */
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        if (file == null || file.length == 0) {
            return;
        } else {
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    // 源文件
                    File sourceFile = file[i];
                    // 目标文件
                    File targetFile =
                        new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                    copyFile(sourceFile, targetFile);
                }
                if (file[i].isDirectory()) {
                    // 准备复制的源文件夹
                    String dir1 = sourceDir + File.separator + file[i].getName();
                    // 准备复制的目标文件夹
                    String dir2 = targetDir + File.separator + file[i].getName();
                    copyDirectiory(dir1, dir2);
                }
            }
        }
    }





    public static String[] buildParams(String sourceDir) throws IOException {
        // 获取源文件夹当下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        String[] result = new String[file.length];
        for (int i = 0; i < file.length; i++) {
            if (file[i].exists()) {
                // 源文件
                File sourceFile = file[i];
                result[i] = sourceFile.getPath();
            }
        }
        return result;
    }


    public static void getFile(String fileurl, HttpServletResponse response) throws IOException {
        if (null != fileurl && !"".equals(fileurl)) {
            // 第一步:根据文件路径获取文件
            File file = new File(fileurl);
            if (file.exists()) {

                byte[] buffer = null;
                try (FileInputStream fileInputStream = new FileInputStream(file);
                    InputStream inputStream = new BufferedInputStream(fileInputStream);) {
                    buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                }

                String fileName = file.getName();
                response.reset();
                response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8.name()),
                        StandardCharsets.ISO_8859_1.name()));
                response.addHeader("Content-Length", "" + file.length());

                try (OutputStream out = response.getOutputStream();
                    OutputStream outputStream = new BufferedOutputStream(out)) {
                    response.setContentType("application/octet-stream");
                    outputStream.write(buffer);
                    outputStream.flush();
                }

            }
        } else {
            return;
        }
    }



}
