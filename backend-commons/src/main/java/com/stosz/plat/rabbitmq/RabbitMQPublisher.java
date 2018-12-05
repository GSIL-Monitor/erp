package com.stosz.plat.rabbitmq;

import com.google.common.base.Strings;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 通用的rabbitmq消息发送模板
 */
@Component
public class RabbitMQPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQPublisher.class);


    @Autowired
    private List<RabbitTemplate> rabbitTemplateList;

    @Autowired
    private MailSender mailSender;


    public void  saveMessage(String messageType, Object data) {
       saveMessage(messageType,RabbitMQConfig.method_insert,data);
    }

    public void  saveMessage(String messageType, String method,Object data) {
        RabbitMQMessage mq = new RabbitMQMessage();
        mq.setData(data);
        mq.setMethod(method);
        mq.setMessageType(messageType);
        this.saveMessage(mq);
    }

    @Async
    public void  saveMessage(RabbitMQMessage rabbitMQMessage)
    {
        Assert.notNull(rabbitTemplateList,"请配置好本系统的MQ消费者信息,文档地址：https://www.showdoc.cc/buguniao?page_id=15679800");
        Assert.isTrue(rabbitTemplateList.stream().anyMatch(item->null!=item),"请至少配置一个MQ消费者信息");
        Assert.notNull(rabbitMQMessage,"发送的消息不能为空");
        Assert.isTrue(!Strings.isNullOrEmpty(rabbitMQMessage.getMessageType()), "消息所属表名不能为空");
        Assert.isTrue(!Strings.isNullOrEmpty(rabbitMQMessage.getMethod()),"消息的方法名称不能为空");
        Assert.notNull(rabbitMQMessage.getData(),"消息内容不能为空");

       logger.debug("保存的消息是："+ rabbitMQMessage.toString());

        try {
            rabbitTemplateList.stream().filter(item->null!=item).forEachOrdered(rabbitTemplate->rabbitTemplate.convertAndSend(rabbitTemplate.getExchange(), rabbitMQMessage.getMessageType(), rabbitMQMessage));
        }catch (Exception ex)
        {
            logger.error(ex.getMessage(),ex);
            mailSender.sendEmail("RabbitMQ连接异常","请检查配置信息是否正确。<br>异常信息："+ex.getMessage()+"<br>堆栈信息:" + ExceptionUtils.getStackTrace(ex));
        }
        catch (Error error)
        {
            logger.error(error.getMessage(),error);
            mailSender.sendEmail("RabbitMQ连接错误","请检查配置信息是否正确。<br>异常信息："+error.getMessage()+"<br>堆栈信息:" + ExceptionUtils.getStackTrace(error));
        }
    }



}
