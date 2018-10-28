package com.school.biz.service;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/10/26 23:03
 */
@Service
public class MailServiceImpl implements MailService {

    @Override
    public void sendMail(String email,String subject,String msg) {
        //创建邮件发送服务器
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("81673211@qq.com");
        mailSender.setPassword("pyxjxerfozxabhhc");
        //加认证机制
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.timeout", 5000);
        mailSender.setJavaMailProperties(javaMailProperties);
        //创建邮件内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("81673211@qq.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(msg);
        //发送邮件
        mailSender.send(message);
    }

}