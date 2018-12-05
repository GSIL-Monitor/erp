package com.stosz.product.email;

import com.stosz.product.ext.model.ExcludeRepeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author he_guitang
 * @version [1.0 , 2017/11/21]
 */
public class JavaMail {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 邮件发送
     *
     * @param listData             数据
     * @param date                 文本内容
     * @param excludeRepeatMailbox 收件人邮箱
     */
    public void mailSendOut(List<ExcludeRepeat> listData, String date, String excludeRepeatMailbox) {
        try {
            Map<String, String> map = new HashMap<>();
            JavaMialUtil mail = new JavaMialUtil("erp@stosz.com", "bugu123");
            map.put("mail.smtp.host", "smtp.qiye.163.com");
            map.put("mail.smtp.auth", "true");
            mail.setPros(map);
            mail.initMessage();
            List<String> list = new ArrayList<String>();
            list.add(excludeRepeatMailbox);
            mail.setRecipients(list);
            mail.setSubject("排重日报");
            mail.setFrom("ERP产品中心");
            mail.setContent("<span style='font-size:18px'>" + date + "</span></br>", "text/html; charset=UTF-8");
            List<String> fileList = new ArrayList<String>();
            mail.setMultiparts(fileList, listData);
            mail.sendMessage();
            logger.info("邮件发送成功!发送给" + list + "的邮件成功,共" + listData.size() + "条数据");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}
