package com.stosz.product.email;

import com.stosz.product.ext.model.ExcludeRepeat;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author he_guitang
 * @version [1.0 , 2017/11/21]
 */
public class JavaMialUtil {
    private String username = null;
    private String password = null;
    private Authenticator auth = null;
    private MimeMessage mimeMessage = null;
    private Properties pros = null;
    private Multipart multipart = null;
    private BodyPart bodypart = null;

    public JavaMialUtil(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setPros(Map<String, String> map) {
        pros = new Properties();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            pros.setProperty(entry.getKey(), entry.getValue());
        }
    }

    public void initMessage() {
        this.auth = new Email_Autherticator();
        Session session = Session.getDefaultInstance(pros, auth);
        session.setDebug(true); // 设置获取 debug 信息
        mimeMessage = new MimeMessage(session);
    }


    public class Email_Autherticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

    public String setRecipients(List<String> recs) throws AddressException, MessagingException {
        if (recs.isEmpty()) {
            return "接收人地址为空!";
        }
        for (String str : recs) {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(str));
        }
        return "加入接收人地址成功!";
    }

    public void setSubject(String subject) throws MessagingException {
        mimeMessage.setSubject(subject);
    }

    public void setFrom(String from) throws UnsupportedEncodingException, MessagingException {
        mimeMessage.setFrom(new InternetAddress(username, from));
    }

    public void setMultipart(String file, List<ExcludeRepeat> list) throws MessagingException, IOException {
        if (multipart == null) {
            multipart = new MimeMultipart();
        }
        multipart.addBodyPart(writeFiles(file, list));
        mimeMessage.setContent(multipart);
    }

    public BodyPart writeFiles(String filePath, List<ExcludeRepeat> list) throws IOException, MessagingException {
        bodypart = new MimeBodyPart();
        InputStream is = InputStreamUtil.getInputStream(list);
        DataSource dataSource = new ByteArrayDataSource(is, "application/octet-stream");
        bodypart.setDataHandler(new DataHandler(dataSource));

        File f = new File("data\\apps\\排重日报.xls");
        bodypart.setFileName(MimeUtility.encodeText(f.getName()));
        return bodypart;
    }

    public void setContent(String s, String type) throws MessagingException {
        if (multipart == null) {
            multipart = new MimeMultipart();
        }
        bodypart = new MimeBodyPart();
        bodypart.setContent(s, type);
        multipart.addBodyPart(bodypart);
        mimeMessage.setContent(multipart);
        mimeMessage.saveChanges();
    }

    public void setMultiparts(List<String> fileList, List<ExcludeRepeat> list) throws MessagingException, IOException {
        if (multipart == null) {
            multipart = new MimeMultipart();
        }
        multipart.addBodyPart(writeFiles("", list));
        mimeMessage.setContent(multipart);
    }

    public String sendMessage() throws MessagingException {
        Transport.send(mimeMessage);
        return "success";
    }
}
