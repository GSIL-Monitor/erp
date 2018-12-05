package com.stosz.plat.utils;

import com.stosz.plat.service.ProjectConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.internet.MimeMessage;

@Component
public class MailSendUtils {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private ProjectConfigService configService;

    public  void sendTextMail(String to,String subject,String text){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(configService.mailUsername);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setSentDate(new Date());// 邮件发送时间
        mail.setText(text);
        mailSender.send(mail);
    }
    public  void sendHtmlMail(String to,String subject,String html) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(configService.mailUsername);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        mailSender.send(mimeMessage);
    }

    public  void sendFileMail(String to, String subject, String html, String contentId, Resource resource) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(configService.mailUsername);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        //FileSystemResource img = new FileSystemResource(new File("c:/350.jpg"));
        messageHelper.addInline(contentId, resource);
        // 发送
        mailSender.send(mimeMessage);
    }

}
