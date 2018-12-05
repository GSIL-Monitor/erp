//package com.stosz.order.engine.mq;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.base.Strings;
//import com.google.common.collect.Sets;
//import com.stosz.plat.engine.SpringCommonBeanConfig;
//import com.stosz.plat.utils.CollectionUtils;
//import com.stosz.plat.utils.CommonUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.env.Environment;
//
//import java.util.Arrays;
//import java.util.Optional;
//import java.util.Set;
//
///**
// * @auther carter
// * create time    2017-12-08
// */
////@Configuration
//public class TestMQ1Publisher implements EnvironmentAware {
//
//    private static final Logger logger = LoggerFactory.getLogger(TestMQ1Publisher.class);
//    public static final String MQPrefix = "mq1.";
//    public static final String ExchangeName = "mq1.Exchange";
//
//
//    @Autowired
//    private static Environment environment;
//
//    /**
//     * Set the {@code Environment} that this component runs in.
//     *
//     * @param environment
//     */
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
//
//    private static class MQPublisherConfig1 {
//
//        @Bean(name = MQPrefix + "publisherConnectionFactory")
//        @Qualifier(MQPrefix + "publisherConnectionFactory")
//        public ConnectionFactory connectionFactoryPublishx() {
//
//            String host = environment.getProperty(MQPrefix + "rabbitmq.publisher.host", "");
//            String portString = environment.getProperty(MQPrefix + "rabbitmq.publisher.port", "");
//            int port = CommonUtils.getIntValue(portString);
//            String username = environment.getProperty(MQPrefix + "rabbitmq.publisher.username", "");
//            String password = environment.getProperty(MQPrefix + "rabbitmq.publisher.password", "");
//            String virtualHost = environment.getProperty(MQPrefix + "rabbitmq.publisher.virtualHost", "");
//            String connectionCacheSizeString = environment.getProperty(MQPrefix + "rabbitmq.publisher.connectionCacheSize", "");
//            int connectionCacheSize = CommonUtils.getIntValue(connectionCacheSizeString);
//            String publisherConfirmString = environment.getProperty(MQPrefix + "rabbitmq.publisher.publisherConfirm", "");
//            boolean publisherConfirm = CommonUtils.getBooleanValue(publisherConfirmString);
//
//
//            if (Strings.isNullOrEmpty(host) ||
//                    Strings.isNullOrEmpty(portString) ||
//                    Strings.isNullOrEmpty(username) ||
//                    Strings.isNullOrEmpty(password) ||
//                    Strings.isNullOrEmpty(virtualHost) ||
//                    Strings.isNullOrEmpty(connectionCacheSizeString) ||
//                    Strings.isNullOrEmpty(publisherConfirmString)
//                    ) {
//                logger.info("子系统没有配置rabbitmq的生产者连接信息,表示不生产消息xxx");
//                return null;
//            }
//
//
//            return SpringCommonBeanConfig.getConnectionFactory(host, port, username, password, virtualHost, connectionCacheSize, publisherConfirm, false);
//        }
//
//
//        /**
//         * 消费者默认配置的交换机为Erp.Exchange
//         *
//         * @param connectionFactory
//         * @return
//         */
//        @Bean(name = MQPrefix + "erpDirectExchange")
//        public DirectExchange erpDirectExchangex(@Autowired @Qualifier(MQPrefix + "publisherConnectionFactory") ConnectionFactory connectionFactory) {
//
//            if (null == connectionFactory) {
//                return null;
//            }
//            RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//            DirectExchange directExchange = new DirectExchange(ExchangeName, true, false);
//            rabbitAdmin.declareExchange(directExchange);
//            return directExchange;
//        }
//
//
//        @Bean(name = MQPrefix + "binding")
//        public Binding bindingx(@Autowired @Qualifier(MQPrefix + "publisherConnectionFactory") ConnectionFactory connectionFactory, @Autowired @Qualifier(MQPrefix + "erpDirectExchange") DirectExchange erpDirectExchange) {
//
//            if (null == connectionFactory) {
//                return null;
//            }
//
//            RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//            /**拿到在json中配置的队列信息,跟交换机进行绑定*******************************************/
//
//            String publisherMessageTypeListString = environment.getProperty(MQPrefix + "rabbitmq.publisher.message.type.list", "");
//
//            Set<String> publisherMessageTypeSet = Sets.newHashSet(Arrays.asList(org.apache.commons.lang3.StringUtils.split(publisherMessageTypeListString, "|")));
//            if (CollectionUtils.isNotNullAndEmpty(publisherMessageTypeSet)) {
//                for (String publisherMessageType : publisherMessageTypeSet) {
//                    String publisherMessageAcceptQueueListString = environment.getProperty(MQPrefix + "rabbitmq.publisher.message." + publisherMessageType + ".queue.names", "");
//                    Set<String> publisherMessageAcceptQueueSet = Sets.newHashSet(Arrays.asList(org.apache.commons.lang3.StringUtils.split(publisherMessageAcceptQueueListString, "|")));
//
//                    Optional.ofNullable(
//                            publisherMessageAcceptQueueSet)
//                            .ifPresent(publisherQueueName -> publisherQueueName
//                                    .stream()
//                                    .forEachOrdered(
//                                            qName ->
//                                            {
//                                                Queue queueItem = new Queue(qName, true, false, false);
//                                                rabbitAdmin.declareQueue(queueItem);
//
//                                                Binding bindingItem = BindingBuilder.bind(queueItem)
//                                                        .to(erpDirectExchange)
//                                                        .with(publisherMessageType);
//
//                                                rabbitAdmin.declareBinding(bindingItem);
//                                            }));
//
//                }
//
//
//            }
//
//            return null;
//
//        }
//
//
//        @Bean(name = MQPrefix + "rabbitTemplate")
//        public RabbitTemplate rabbitTemplatex(@Autowired @Qualifier(MQPrefix + "publisherConnectionFactory") ConnectionFactory connectionFactory, @Autowired @Qualifier("rabbitmqObjectMapper") ObjectMapper objectMapper) {
//
//            if (null == connectionFactory) {
//                return null;
//            }
//
//            RabbitTemplate rabbitTemplate = new RabbitTemplate();
//            rabbitTemplate.setEncoding("UTF-8");
//            rabbitTemplate.setConnectionFactory(connectionFactory);
//
//            Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//            jackson2JsonMessageConverter.setJsonObjectMapper(objectMapper);
//            jackson2JsonMessageConverter.setDefaultCharset("UTF-8");
//            rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
//            rabbitTemplate.setExchange(ExchangeName);
//
//            return rabbitTemplate;
//        }
//
//    }
//
//
//}
