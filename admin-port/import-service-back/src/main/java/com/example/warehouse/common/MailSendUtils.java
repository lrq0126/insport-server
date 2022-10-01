package com.example.warehouse.common;

import com.example.warehouse.enums.customer.MailTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.example.warehouse.enums.customer.MailTypeEnum.*;
@Configuration
public class MailSendUtils {
    private static String SENDER_ADDRESS; // 发件人称号，同邮箱地址
    private static String SENDER_NAME; // 发件人称号，同邮箱地址
    private static String AUTHORIZATION_CODE; // 可以使户端授权码

    @Value("${mail.sender-address}")
    public void setSenderAddress(String senderAddress){
        SENDER_ADDRESS = senderAddress;
    }
    @Value("${mail.sender-name}")
    public void setSenderName(String senderName){
        SENDER_NAME = senderName;
    }
    @Value("${mail.authorization-code}")
    public void setAuthorizationCode(String authorizationCode){
        AUTHORIZATION_CODE = authorizationCode;
    }


    /**
     *
     * @param toAddressEmail 收件人邮箱
     * @param text 邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String toAddressEmail, String text, String title, String type) {
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");

            // 发件人的账号
            props.put("mail.user", SENDER_ADDRESS);
            //发件人的密码(授权码)
            props.put("mail.password", AUTHORIZATION_CODE);

            // 构建授权信息，用于进行SMTP进行身份验证
            MailAuthenticator authenticator = new MailAuthenticator(SENDER_ADDRESS, AUTHORIZATION_CODE);
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            form.setPersonal(SENDER_NAME);
            message.setFrom(form);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(toAddressEmail);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            if(type.equals("text")){
                // 设置邮件的内容体
                message.setContent(text, "text/html;charset=UTF-8");
            }else {
                // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
                Multipart mainPart = new MimeMultipart();
                // 创建一个包含HTML内容的MimeBodyPart
                BodyPart html = new MimeBodyPart();

                message.setContent(text, "text/html;charset=UTF-8");
                // 设置HTML内容
                html.setContent(message.getContent(), "text/html; charset=utf-8");
                mainPart.addBodyPart(html);
                // 将MiniMultipart对象设置为邮件内容
                message.setContent(mainPart);
            }
            // 发送邮件
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, String> keyWord1 = new HashMap<>();
        keyWord1.put("name", "订单号");
        keyWord1.put("value", "FL1563608025963");
        keyWord1.put("color", "#283bcd");

        Map<String, String> keyWord2 = new HashMap<>();
        keyWord2.put("name", "重量");
        keyWord2.put("value", "8KG");
//        keyWord2.put("color", "#FFFFFF");

        Map<String, String> keyWord3 = new HashMap<>();
        keyWord3.put("name", "价格");
        keyWord3.put("value", "￥500");
//        keyWord3.put("color", "#FFFFFF");

        Map<String, String> keyWord4 = new HashMap<>();
//        keyWord4.put("name", "付款类型");
//        keyWord4.put("value", "先付款后发货");
//        keyWord4.put("color", "#FFFFFF");

        Map<String, String> remarks = new HashMap<>();
        remarks.put("name", "备注");
        remarks.put("value", "\n" +
                "【尺寸：1: 3.5kg (30.0*19.0*21.0)cm 】\n" +
                "；额外扣款：1.00元;额外扣款：30.00元;额外扣款：1.00元;额外扣款：8.77元；");
        remarks.put("color", "#da1616");

        String title = "测试内容";
        String message = "尊敬的客户，您好:\n您的包裹已经打包好，请及时查看付款发货\n";

        String content = MailMessageUtils.setDefineFormatEmail(title, message, keyWord1, keyWord2, keyWord3, keyWord4, remarks);
        MailSendUtils.sendMail("13676216939@163.com",content,"测试邮件", ORDER_UPDATE.getName());
        System.out.println("发送成功");
    }
}
