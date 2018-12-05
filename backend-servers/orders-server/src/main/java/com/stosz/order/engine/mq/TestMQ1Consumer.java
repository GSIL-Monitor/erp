//package com.stosz.order.engine.mq;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.base.Strings;
//import com.stosz.plat.engine.SpringCommonBeanConfig;
//import com.stosz.plat.rabbitmq.consumer.RabbitMQListener;
//import com.stosz.plat.utils.CommonUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.AcknowledgeMode;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.core.env.Environment;
//
//import java.util.Arrays;
//
///**
// * @auther carter
// * create time    2017-12-08
// */
////@Configuration
//public class TestMQ1Consumer implements EnvironmentAware {
//
//    private static final Logger logger = LoggerFactory.getLogger(TestMQ1Consumer.class);
//    public static final String MQPrefix = "mq2.";
//
//    @Autowired
//    private static  Environment environment;
//
//    /**
//     * Set the {@code Environment} that this component runs in.
//     *
//     * @param environment
//     */
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.environment = environment ;
//    }
//
//    private static class MQConsumerConfig1 {
//
//        @Bean(MQPrefix+"consumerConnectionFactory")
//        @Qualifier(MQPrefix+"consumerConnectionFactory")
//        @Lazy
//        public ConnectionFactory connectionFactory() {
//
//
//            String host = environment.getProperty(MQPrefix+"rabbitmq.consumer.host","");
//            String portString = environment.getProperty(MQPrefix+"rabbitmq.consumer.port", "");
//            int port = CommonUtils.getIntValue(portString);
//            String username = environment.getProperty(MQPrefix+"rabbitmq.consumer.username","");
//            String password = environment.getProperty(MQPrefix+"rabbitmq.consumer.password","");
//            String virtualHost = environment.getProperty(MQPrefix+"rabbitmq.consumer.virtualHost","");
//            String connectionCacheSizeString = environment.getProperty(MQPrefix+"rabbitmq.consumer.connectionCacheSize", "");
//            int connectionCacheSize = CommonUtils.getIntValue(connectionCacheSizeString);
//            String publisherConfirmString = environment.getProperty(MQPrefix+"rabbitmq.consumer.publisherConfirm", "");
//            boolean publisherConfirm = CommonUtils.getBooleanValue(publisherConfirmString);
//
//
//            if(Strings.isNullOrEmpty(host) ||
//                    Strings.isNullOrEmpty(portString)||
//                    Strings.isNullOrEmpty(username)||
//                    Strings.isNullOrEmpty(password)||
//                    Strings.isNullOrEmpty(virtualHost)||
//                    Strings.isNullOrEmpty(connectionCacheSizeString)||
//                    Strings.isNullOrEmpty(publisherConfirmString)
//                    )
//            {
//                logger.info("子系统没有配置rabbitmq的消费者连接信息,表示不消费消息yyyy");
//                return  null;
//            }
//
//            return SpringCommonBeanConfig.getConnectionFactory(host, port, username, password, virtualHost, connectionCacheSize, publisherConfirm, true);
//        }
//
//
//        @Bean(MQPrefix+"SimpleMessageListenerContainer")
//        @Qualifier(MQPrefix+"SimpleMessageListenerContainer")
//        public SimpleMessageListenerContainer simpleMessageListenerContainer(@Autowired @Qualifier(MQPrefix+"consumerConnectionFactory") ConnectionFactory connectionFactory, @Autowired RabbitMQListener rabbitMQListener,
//                                                                             @Autowired @Qualifier("rabbitmqObjectMapper") ObjectMapper objectMapper
//        ) {
//
//            if(null == connectionFactory) {
//                return null;
//            }
//
//            SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//            simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
//            /** 这里配置所有的需要监听的队列名；*/
//            String   consumerListenQnames =  environment.getProperty(MQPrefix+"rabbitmq.consumer.listen.qnames","");
//            String[] consumerListenQnameArray = org.apache.commons.lang3.StringUtils.split(consumerListenQnames, "|");
//
//            if(null == consumerListenQnameArray || consumerListenQnameArray.length<1) {
//                return null;
//            }
//
//
//            //尝试创建队列
//            Arrays.asList(consumerListenQnameArray).forEach(item-> new RabbitAdmin(connectionFactory).declareQueue( new Queue(item, true, false, false)));
//
//
//            simpleMessageListenerContainer.setQueueNames(consumerListenQnameArray);
//
//            simpleMessageListenerContainer.setMessageListener(rabbitMQListener);
//            simpleMessageListenerContainer.setConcurrentConsumers(1);//这里可以设置消费消息的并发程度，默认设置为1，单个消费者
//            Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
//            messageConverter.setDefaultCharset("UTF-8");
//            messageConverter.setJsonObjectMapper(objectMapper);
//
//            simpleMessageListenerContainer.setMessageConverter(messageConverter);
//            simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动设置消息确认
//            return simpleMessageListenerContainer;
//
//        }
//
//
//    }
//
//
//}
