package com.stosz.plat.rabbitmq;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component("sysMailSender")
public class MailSender implements InitializingBean {


    private static Session session = null;
    private static final String fromMailAddress = "itnotitfysercices@stosz.com";
    private  final Logger logger = LoggerFactory.getLogger(getClass());

    public void sendEmail(String subject, String content)
    {
        sendEmail(fromMailAddress,subject,content);
    }

    public void sendEmail(String toEmailAddress,String subject, String content)
    {

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(fromMailAddress));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(content,"text/html;charset=utf-8");
            mimeMessage.setRecipients(Message.RecipientType.TO,toEmailAddress);
            Transport.send(mimeMessage);
            logger.info("邮件发送成功，目标地址：{} ,主题:{} , 内容：",toEmailAddress , subject , StringUtils.abbreviate(content,120) );
        } catch (MessagingException e) {
            logger.error("邮件发送失败，目标地址：{} ,主题:{} , 内容：",toEmailAddress , subject , StringUtils.abbreviate(content,120) ,e);
        }




    }



    @Override
    public void afterPropertiesSet() throws Exception {

        Properties properties = new Properties();

        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.host","smtp.qiye.163.com");


         session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromMailAddress, "Love2017!");
            }
        });





    }
}