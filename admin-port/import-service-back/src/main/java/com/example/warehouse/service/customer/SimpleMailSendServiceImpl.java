package com.example.warehouse.service.customer;

import com.example.warehouse.common.MailAuthenticator;
import com.example.warehouse.common.MailSendUtils;
import org.springframework.stereotype.Service;


@Service("SimpleMailSendService")
public class SimpleMailSendServiceImpl implements SimpleMailSendService {



    public static void main(String[] args) {
        MailSendUtils.sendMail("978771653@qq.com", "测试内容", "测试邮件", "text");
    }

}
