package com.stosz.plat.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;
import com.stosz.plat.rabbitmq.MailSender;
import com.stosz.plat.utils.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RabbitMQListener implements ChannelAwareMessageListener {

    private  final org.slf4j.Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);


    @Autowired
    private MailSender mailSender;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Map<String, List<AbstractHandler>> abstractHandlerMap;


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {


        Assert.notNull(message,"message 不能为空");

        if(message.getBody().length < 1)
        {
            doNotify("队列中有空消息",message,channel);
            return;
        }

        String rabbitMessage = new String(message.getBody(),"UTF-8");
        logger.info("接受到的消息：" + rabbitMessage);

        String notifyMessage = "";

        try {
            Map<String,Object> messageMap = objectMapper.readValue(rabbitMessage, new HashMap<>().getClass());
            String messageType = String.valueOf(Optional.ofNullable(messageMap.get("messageType")).orElse(""));

            List<AbstractHandler> rabbitMessageHandlerList = abstractHandlerMap.get(messageType);
            if(CollectionUtils.isNullOrEmpty(rabbitMessageHandlerList))
            {
                doNotify("系统中暂时没有定义对应的"+messageType+"的消息处理器，请联系消息消费系统管理员进行添加",message,channel);
                return;
            }

            Map<String,Object>  dataJsonMap = getDataMap(messageMap);
            String methodName=  String.valueOf(Optional.ofNullable(messageMap.get("method")).orElse(""));

           rabbitMessageHandlerList.forEach( rabbitMessageHandler-> {
                                                                     boolean   messageHandleSuccess = rabbitMessageHandler.handleMessage(methodName,dataJsonMap);
                                                                       if(messageHandleSuccess)
                                                                       {
                                                                           doSuccess(message,channel);
                                                                       }else
                                                                       {
                                                                           doRetry(message,channel);
                                                                       }
                                                                    });


        } catch (IOException e) {
           logger.error(e.getMessage(),e);
            notifyMessage = e.getMessage();
            doNotify(notifyMessage,message,channel);
        }

    }

    private Map<String, Object> getDataMap(Map<String, Object> messageMap) {
        try {

            return (Map<String,Object>) Optional.ofNullable(messageMap.get("data")).orElse(new HashMap());
        }catch (Exception ex)
        {
           logger.error(ex.getMessage());
           return Maps.newHashMap();
        }
    }


    /**
     * do not response the  queue ack  , need retry
     */
    private void  doRetry(Message message, Channel channel)
    {
        try {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            doNotify(e.getMessage(),message,channel);
        }

    }

    /**
     * response the queue ack , the message handle ok
     */
    private void doSuccess(Message message, Channel channel)
    {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false );
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * the data is abnormal , should  notify the system admin
     */
    private void doNotify(String notifyMessage,Message message,Channel channel)
    {

        logger.warn(notifyMessage);
        logger.trace("message.getMessageProperties() : "+message.getMessageProperties());
        logger.warn("message : "+ new String(message.getBody()));

        mailSender.sendEmail("RabbitMQ消费报警邮件",
                notifyMessage+"<br>消息内容:"+new String(message.getBody())+"<br>消息结构:" +message.toString()+"<br>发生的系统名称:"+"test");//todo

        /**
         * 防止消息不断重发，到邮件取查看消息即可；
         */
        doSuccess(message,channel);


    }


}
