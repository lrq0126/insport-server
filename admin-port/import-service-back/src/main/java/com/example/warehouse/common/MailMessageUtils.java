package com.example.warehouse.common;

import org.apache.commons.lang.StringUtils;
import org.thymeleaf.util.MapUtils;

import java.util.Map;

public class MailMessageUtils {


    private static String setMailMessage(String title, String message){
        return"<H1>"+title+"</H1>\n"
                + "<span style='color: 222020; margin-bottom: 20px; '>"+message+"</span> \n";
    }

    /**
     * 设置定义格式的邮件信息
     * @param title     标题
     * @param message   文字内容
     * @param keyWord1  (非必填)name：名称；value：内容；color：字体颜色；
     * @param keyWord2  (非必填)name：名称；value：内容；color：字体颜色；
     * @param keyWord3  (非必填)name：名称；value：内容；color：字体颜色；
     * @param keyWord4  (非必填)name：名称；value：内容；color：字体颜色；
     * @param remarks   (非必填)
     * @return
     */
    public static String setDefineFormatEmail(String title, String message,
                                              Map<String, String> keyWord1, Map<String, String> keyWord2,
                                              Map<String, String> keyWord3, Map<String, String> keyWord4,
                                              Map<String, String> remarks){
        String content = setMailMessage(title, message);

        if(!MapUtils.isEmpty(keyWord1) && keyWord1.containsKey("name")){
            String color = !keyWord1.containsKey("name") || StringUtils.isEmpty(keyWord1.get("color"))
                    ? "#284ccd" : keyWord1.get("color");
            String keyWord1Str = "<p><span style='width: 150px;'>" + keyWord1.get("name") + "：</span>"
                    + "<span style='color: "+color+"'>" + keyWord1.get("value") + "</span></p> \t\n";
            content += keyWord1Str;
        }
        if(!MapUtils.isEmpty(keyWord2) && keyWord2.containsKey("name")){
            String color = !keyWord2.containsKey("name") || StringUtils.isEmpty(keyWord2.get("color"))
                    ? "#284ccd" : keyWord2.get("color");
            String keyWord2Str = "<p><span style='width: 150px;'>" + keyWord2.get("name") + "：</span>"
                    + "<span style='color: "+color+"'>" + keyWord2.get("value") + "</span></p> \t\n";
            content += keyWord2Str;
        }
        if(!MapUtils.isEmpty(keyWord3) && keyWord3.containsKey("name")){
            String color = !keyWord3.containsKey("name") || StringUtils.isEmpty(keyWord3.get("color"))
                    ? "#284ccd" : keyWord3.get("color");
            String keyWord3Str = "<p><span style='width: 150px;'>" + keyWord3.get("name") + "：</span>"
                    + "<span style='color: "+color+"'>" + keyWord3.get("value") + "</span></p> \t\n";
//            String keyWord3Str = "<p><img src=\"https://flycloud-1259586164.cos.ap-guangzhou.myqcloud.com/GoodsInWarePicture/2022-08/Futari%20No%20Hanabi.jpg\"/></p> \n";
            content += keyWord3Str;
        }

        if(!MapUtils.isEmpty(keyWord4) && keyWord4.containsKey("name")){
            String color = !keyWord4.containsKey("name") || StringUtils.isEmpty(keyWord4.get("color"))
                    ? "#284ccd" : keyWord4.get("color");
            String keyWord4Str = "<p><span style='width: 150px;'>" + keyWord4.get("name") + "：</span>"
                    + "<span style='color: "+color+"'>" + keyWord4.get("value") + "</span></p> \t\n";
            content += keyWord4Str;
        }

        if(!MapUtils.isEmpty(remarks) && remarks.containsKey("value")){
            String color = StringUtils.isEmpty(remarks.get("color")) ? "#284ccd" : remarks.get("color");
            String remarksStr = "<p><span style='width: 150px;'>" + "备注：</span>"
                    + "<span style='color: "+color+"'>" + remarks.get("value") + "</span></p> \t\n";
            content += remarksStr;
        }
        return content;
    }

}
