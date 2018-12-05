//package com.stosz.plat.rabbitmq;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import com.stosz.plat.rabbitmq.consumer.RabbitMQListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.AcknowledgeMode;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.Connection;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionListener;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Scope;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//
//@Configurable
//public class RabbitMQConsumerConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumerConfig.class);
//
//    @Autowired
//    private RabbitMQConfig rabbitMQConfig;
//
////    @Bean
////    @Qualifier("consumerConnectionFactory")
////    @Lazy
////    public ConnectionFactory connectionFactory(@Value("${rabbitmq.host}") String host,
////                                               @Value("${rabbitmq.port}") int port,
////                                               @Value("${rabbitmq.username}") String username,
////                                               @Value("${rabbitmq.password}") String password,
////                                               @Value("${rabbitmq.virtualHost}") String virtualHost,
////                                               @Value("${rabbitmq.connectionCacheSize}") int connectionCacheSize,
////                                               @Value("${rabbitmq.publisherConfirm}") boolean publisherConfirm
////                                               )
////    {
////
////        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
////
////        cachingConnectionFactory.setHost(host);
////        cachingConnectionFactory.setPort(port);
////        cachingConnectionFactory.setUsername(username);
////        cachingConnectionFactory.setPassword(password);
////        cachingConnectionFactory.setVirtualHost(virtualHost);
////        cachingConnectionFactory.setConnectionCacheSize(connectionCacheSize);
////        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
////        cachingConnectionFactory.setPublisherConfirms(publisherConfirm);
////
////        cachingConnectionFactory.setConnectionListeners(Arrays.asList(new ConnectionListener() {
////            @Override
////            public void onCreate(Connection connection) {
////                logger.info("==============rabbitmq消费者的连接[" + connection + "]建立============");
////            }
////
////            @Override
////            public void onClose(Connection connection) {
////                logger.info("==============rabbitmq消费者的连接[" + connection + "]关闭============");
////            }
////        }));
////
////        return cachingConnectionFactory;
////    }
////
////
////    @Bean
////    @Qualifier("consumerRabbitAdmin")
////    @Lazy
////    public RabbitAdmin rabbitAdmin(@Autowired @Qualifier("consumerConnectionFactory") ConnectionFactory connectionFactory)
////    {
////        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
////        return rabbitAdmin;
////    }
////
////
////    @Bean
////    @Qualifier("SimpleMessageListenerContainer")
////    public SimpleMessageListenerContainer simpleMessageListenerContainer(@Autowired @Qualifier("consumerConnectionFactory") ConnectionFactory connectionFactory,
////                                                                         @Autowired RabbitMQListener rabbitMQListener
////                                                                         )
////    {
////
////        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
////
////        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
////        /**
////         * 这里配置所有的需要监听的队列名；
////         */
////        simpleMessageListenerContainer.setQueueNames(rabbitMQConfig.getCurrentSystemQNameArray());
////
////        simpleMessageListenerContainer.setMessageListener(rabbitMQListener);
////
////        simpleMessageListenerContainer.setConcurrentConsumers(1);
////
////        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
////        messageConverter.setJsonObjectMapper(objectMapper());
////        messageConverter.setDefaultCharset("UTF-8");
////
////        simpleMessageListenerContainer.setMessageConverter(messageConverter);
////        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动设置消息确认
////        return simpleMessageListenerContainer;
////
////    }
//
//
////    @Bean
////    public ConcurrentHashMap<String,List<AbstractHandler>> rabbitMessageHandlerMap(
////            @Autowired ZoneHandler zoneHandler,
////            @Autowired CountryHandler countryHandler,
////            @Autowired LanguageHandler languageHandler,
////            @Autowired CurrencyHandler currencyHandler,
////            @Autowired CategoryHandler categoryHandler,
////            @Autowired OrderRequiredHandler orderRequiredHandler,
////            @Autowired FrontOrderHandler frontOrderHandler,
////            @Autowired OrderToLogisticsHandler orderToLogisticsHandler,
////            @Autowired OrderLogisticsHandler orderLogisticsHandler
////            )
////    {
////        ConcurrentHashMap<String,List<AbstractHandler>> rabbitMessageHandlerMap = new ConcurrentHashMap();
////
////        rabbitMessageHandlerMap.putIfAbsent("zone",Arrays.asList(zoneHandler));
////        rabbitMessageHandlerMap.putIfAbsent("country",Arrays.asList(countryHandler));
////        rabbitMessageHandlerMap.putIfAbsent("language",Arrays.asList(languageHandler));
////        rabbitMessageHandlerMap.putIfAbsent("currency",Arrays.asList(currencyHandler));
////        rabbitMessageHandlerMap.putIfAbsent("category",Arrays.asList(categoryHandler));
////        rabbitMessageHandlerMap.putIfAbsent("orderPurchase", Arrays.asList(orderRequiredHandler));
////        rabbitMessageHandlerMap.putIfAbsent("frontOrder", Arrays.asList(frontOrderHandler));
////        rabbitMessageHandlerMap.putIfAbsent("matchLogistics", Arrays.asList(orderToLogisticsHandler));
////        rabbitMessageHandlerMap.putIfAbsent("orderLogisticsHandler", Arrays.asList(orderLogisticsHandler));
////        return rabbitMessageHandlerMap;
////    }
//
//
//
//
//
//
//
//}
