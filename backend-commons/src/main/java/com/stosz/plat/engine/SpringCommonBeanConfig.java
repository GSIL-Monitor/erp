package com.stosz.plat.engine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.stosz.plat.common.SpringContextHolder;
import com.stosz.plat.interceptor.ClientLoginInterceptor;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import com.stosz.plat.rabbitmq.consumer.RabbitMQListener;
import com.stosz.plat.service.ProjectConfigService;
import com.stosz.plat.service.SsoUserService;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.CommonUtils;
import com.stosz.plat.utils.JacksonMappingJackson2HttpMessageConverter;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.wms.model.WmsConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将 各个系统都会用到的 公用组件在这里生成
 */
public abstract class SpringCommonBeanConfig implements EnvironmentAware {


    private static final Logger logger = LoggerFactory.getLogger(SpringCommonBeanConfig.class);

    @Autowired
    protected static Environment environment;

    public void setEnvironment(Environment environment){
        this.environment = environment;
    }


    @Autowired
    protected SpringContextHolder springContextHolder;

    @Bean
    public ClientLoginInterceptor ClientLoginInterceptor() {
        ClientLoginInterceptor clientLoginInterceptor = new ClientLoginInterceptor();
        return clientLoginInterceptor;
    }

    @Bean
    public ProjectConfigService projectConfigService() {
        ProjectConfigService projectConfigService = new ProjectConfigService();
        return projectConfigService;
    }

    @Bean
    public WmsConfig wmsConfig(){
        WmsConfig wmsConfig = new WmsConfig();
        return wmsConfig;
    }



    @Bean
    public JavaMailSenderImpl mailSenderConfig(@Autowired ProjectConfigService configService){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        if (StringUtils.isNumeric(configService.mailPort)){
            sender.setHost(configService.mailHost);
            sender.setPort(Integer.valueOf(configService.mailPort));
            sender.setUsername(configService.mailUsername);
            sender.setPassword(configService.mailPassword);
            sender.setDefaultEncoding("Utf-8");
            Properties p = new Properties();
            p.setProperty("mail.smtp.timeout","50000");
            p.setProperty("mail.smtp.auth","true");
            sender.setJavaMailProperties(p);
        }

        return sender;
    }



    /*************    redis config start    *************/

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(@Autowired ProjectConfigService configService) {
//        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
//        redisSentinelConfiguration.setDatabase( configService.projectRedisDatabase );
//        redisSentinelConfiguration.setPassword( RedisPassword.of(configService.projectRedisPassword) );
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(Integer.parseInt(configService.projectRedisPoolMaxIdle));
        poolConfig.setMinIdle(Integer.parseInt(configService.projectRedisPoolMinIdle));
        poolConfig.setTestOnBorrow(Boolean.valueOf(configService.projectRedisPoolTestOnBorrow));
        poolConfig.setMaxWaitMillis(Integer.parseInt(configService.projectRedisPoolMaxWait));


//        JedisClientConfiguration JedisClientConfiguration = new JedisClientConfiguration();

//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//
//        try {
//            redisStandaloneConfiguration.setDatabase(Integer.parseInt(configService.projectRedisDatabase));
//            redisStandaloneConfiguration.setHostName(configService.projectRedisHost);
//            redisStandaloneConfiguration.setPort(Integer.parseInt(configService.projectRedisPort));
//        } catch (Exception e) {
//            logger.error("初始化redis客户端配置时参数错误！",e);
//        }

//        redisStandaloneConfiguration.setPassword(RedisPassword.of(configService.projectRedisPassword));

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setPoolConfig(poolConfig);

        factory.setHostName(configService.projectRedisHost);
        factory.setPort(Integer.parseInt(configService.projectRedisPort));
        factory.setDatabase(Integer.parseInt(configService.projectRedisDatabase));
        factory.setPassword(configService.projectRedisPassword);
        factory.setUsePool(true);
        factory.setTimeout(Integer.parseInt(configService.projectRedisTimeout));


        return factory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(@Autowired JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory);

        template.setKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(new JdkSerializationRedisSerializer());


        return template;

    }

    /*************    redis config end    *************/


    @Bean
    public SsoUserService ssoUserService() {
        SsoUserService ssoUserService = new SsoUserService();
        return ssoUserService;
    }

    @Bean
    public RestTemplate restTemplate() {

        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(5);
        pool.setDefaultMaxPerRoute(10);

        DefaultHttpRequestRetryHandler defaultHttpRequestRetryHandler = new DefaultHttpRequestRetryHandler(2, true);

        List<BasicHeader> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));

        //headers.add(new BasicHeader("X_REQUEST_APP_KEY", "app-key"));


        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setRetryHandler(defaultHttpRequestRetryHandler);
        builder.setDefaultHeaders(headers);


        CloseableHttpClient httpClient = builder.build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);

        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new FormHttpMessageConverter());
        converters.add(new MappingJackson2XmlHttpMessageConverter());
        JacksonMappingJackson2HttpMessageConverter jacsonConverter = new JacksonMappingJackson2HttpMessageConverter();
        jacsonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
//        jacsonConverter.getSupportedMediaTypes().add(MediaType.ALL);

        converters.add(jacsonConverter);
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();

        stringConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_OCTET_STREAM));
        converters.add(stringConverter);

        RestTemplate rest = new RestTemplate(factory);

        rest.setErrorHandler(new DefaultResponseErrorHandler());
        rest.setMessageConverters(converters);

        return rest;
    }

    @Bean
    public ThreadPoolTaskExecutor threadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(15);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }


    private static class MQConsumerConfig {

        @Bean
        @Qualifier("consumerConnectionFactory")
        @Lazy
        public ConnectionFactory connectionFactory() {




           String host = environment.getProperty("rabbitmq.consumer.host","");
            String portString = environment.getProperty("rabbitmq.consumer.port", "");
            int port = CommonUtils.getIntValue(portString);
           String username = environment.getProperty("rabbitmq.consumer.username","");
            String password = environment.getProperty("rabbitmq.consumer.password","");
            String virtualHost = environment.getProperty("rabbitmq.consumer.virtualHost","");
            String connectionCacheSizeString = environment.getProperty("rabbitmq.consumer.connectionCacheSize", "");
            int connectionCacheSize = CommonUtils.getIntValue(connectionCacheSizeString);
            String publisherConfirmString = environment.getProperty("rabbitmq.consumer.publisherConfirm", "");
            boolean publisherConfirm = CommonUtils.getBooleanValue(publisherConfirmString);


           if(Strings.isNullOrEmpty(host) ||
              Strings.isNullOrEmpty(portString)||
              Strings.isNullOrEmpty(username)||
              Strings.isNullOrEmpty(password)||
              Strings.isNullOrEmpty(virtualHost)||
              Strings.isNullOrEmpty(connectionCacheSizeString)||
              Strings.isNullOrEmpty(publisherConfirmString)
              )
           {
               logger.info("子系统没有配置rabbitmq的消费者连接信息,表示不消费消息");
               return  null;
           }

            return getConnectionFactory(host, port, username, password, virtualHost, connectionCacheSize, publisherConfirm, true);
        }


        @Bean
        @Qualifier("SimpleMessageListenerContainer")
        public SimpleMessageListenerContainer simpleMessageListenerContainer(@Autowired @Qualifier("consumerConnectionFactory") ConnectionFactory connectionFactory, @Autowired RabbitMQListener rabbitMQListener,
                                                                             @Autowired @Qualifier("rabbitmqObjectMapper") ObjectMapper objectMapper
        ) {

            if(null == connectionFactory) {
                return null;
            }

            SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
            simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
            /** 这里配置所有的需要监听的队列名；*/
            String   consumerListenQnames =  environment.getProperty("rabbitmq.consumer.listen.qnames","");
            String[] consumerListenQnameArray = org.apache.commons.lang3.StringUtils.split(consumerListenQnames, "|");

            if(null == consumerListenQnameArray || consumerListenQnameArray.length<1) {
                return null;
            }

            //尝试创建队列,防止出现监听的队列不存在的情况；
            Arrays.asList(consumerListenQnameArray).forEach(item-> new RabbitAdmin(connectionFactory).declareQueue( new Queue(item, true, false, false)));

            simpleMessageListenerContainer.setQueueNames(consumerListenQnameArray);

            simpleMessageListenerContainer.setMessageListener(rabbitMQListener);
            simpleMessageListenerContainer.setConcurrentConsumers(1);//这里可以设置消费消息的并发程度，默认设置为1，单个消费者
            Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
            messageConverter.setDefaultCharset("UTF-8");
            messageConverter.setJsonObjectMapper(objectMapper);

            simpleMessageListenerContainer.setMessageConverter(messageConverter);
            simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动设置消息确认
            return simpleMessageListenerContainer;

        }


    }


    /**
     * 在子系统中配置消息处理器,因为每个子系统只是处理一次，所以这里直接定义成一个
     **********************************************************************************/
    @Bean
    protected abstract ConcurrentHashMap<String,List<AbstractHandler>> rabbitMessageHandlerMap();
    //rabbitMQ生产者配置/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private static class MQPublisherConfig {

        @Qualifier("publisherConnectionFactory")
        @Bean
        public ConnectionFactory connectionFactoryPublish() {

            String host = environment.getProperty("rabbitmq.publisher.host","");
            String portString = environment.getProperty("rabbitmq.publisher.port", "");
            int port = CommonUtils.getIntValue(portString);
            String username = environment.getProperty("rabbitmq.publisher.username","");
            String password = environment.getProperty("rabbitmq.publisher.password","");
            String virtualHost = environment.getProperty("rabbitmq.publisher.virtualHost","");
            String connectionCacheSizeString = environment.getProperty("rabbitmq.publisher.connectionCacheSize", "");
            int connectionCacheSize = CommonUtils.getIntValue(connectionCacheSizeString);
            String publisherConfirmString = environment.getProperty("rabbitmq.publisher.publisherConfirm", "");
            boolean publisherConfirm = CommonUtils.getBooleanValue(publisherConfirmString);


            if(Strings.isNullOrEmpty(host) ||
                    Strings.isNullOrEmpty(portString)||
                    Strings.isNullOrEmpty(username)||
                    Strings.isNullOrEmpty(password)||
                    Strings.isNullOrEmpty(virtualHost)||
                    Strings.isNullOrEmpty(connectionCacheSizeString)||
                    Strings.isNullOrEmpty(publisherConfirmString)
                    )
            {
                logger.info("子系统没有配置rabbitmq的生产者连接信息,表示不生产消息");
                return  null;
            }


            return getConnectionFactory(host, port, username, password, virtualHost, connectionCacheSize, publisherConfirm, false);
        }


        /**
         * 消费者默认配置的交换机为Erp.Exchange
         * @param connectionFactory
         * @return
         */
        @Bean
        public DirectExchange erpDirectExchange(@Autowired @Qualifier("publisherConnectionFactory") ConnectionFactory connectionFactory) {

            if(null == connectionFactory) {
                return null;
            }
            RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
            DirectExchange directExchange = new DirectExchange(RabbitMQConfig.ERP_EXCHANGE, true, false);
            rabbitAdmin.declareExchange(directExchange);
            return directExchange;
        }


        @Bean
        public Binding binding(@Autowired @Qualifier("publisherConnectionFactory") ConnectionFactory connectionFactory, @Autowired DirectExchange erpDirectExchange) {

            if(null == connectionFactory) {
                return null;
            }

            RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
            /**拿到在json中配置的队列信息,跟交换机进行绑定*******************************************/

            String publisherMessageTypeListString = environment.getProperty("rabbitmq.publisher.message.type.list","");

            Set<String> publisherMessageTypeSet = Sets.newHashSet(Arrays.asList(org.apache.commons.lang3.StringUtils.split(publisherMessageTypeListString,"|")));


            if(CollectionUtils.isNotNullAndEmpty(publisherMessageTypeSet))
            {
                for (String publisherMessageType :  publisherMessageTypeSet)
                {
                    String publisherMessageAcceptQueueListString = environment.getProperty("rabbitmq.publisher.message."+publisherMessageType+".queue.names","");
                    Set<String> publisherMessageAcceptQueueSet = Sets.newHashSet(Arrays.asList(org.apache.commons.lang3.StringUtils.split(publisherMessageAcceptQueueListString,"|")));

                    Optional.ofNullable(
                            publisherMessageAcceptQueueSet)
                            .ifPresent(publisherQueueName->publisherQueueName
                            .stream()
                            .forEachOrdered(
                             qName->
                            {
                                Queue queueItem = new Queue(qName, true, false, false);
                                rabbitAdmin.declareQueue(queueItem);

                                Binding bindingItem = BindingBuilder.bind(queueItem)
                                        .to(erpDirectExchange)
                                        .with(publisherMessageType);

                                rabbitAdmin.declareBinding(bindingItem);
                            }));

                }




            }

            return null;

        }


        @Bean
        public RabbitTemplate rabbitTemplate(@Autowired @Qualifier("publisherConnectionFactory") ConnectionFactory connectionFactory, @Autowired @Qualifier("rabbitmqObjectMapper") ObjectMapper objectMapper) {

            if(null == connectionFactory) {
                return null;
            }

            RabbitTemplate rabbitTemplate = new RabbitTemplate();
            rabbitTemplate.setEncoding("UTF-8");
            rabbitTemplate.setConnectionFactory(connectionFactory);

            Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
            jackson2JsonMessageConverter.setJsonObjectMapper(objectMapper);
            jackson2JsonMessageConverter.setDefaultCharset("UTF-8");
            rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
            rabbitTemplate.setExchange(RabbitMQConfig.ERP_EXCHANGE);

            return rabbitTemplate;
        }

    }


    //MQ基础配置///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * rabbitMQ专用的json读写对象，主要做了一个对java8的LocalDateTime的序列化和反序列化器；
     *
     * @return
     */
    @Bean
    @Qualifier("rabbitmqObjectMapper")
    @Scope("prototype")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    public static ConnectionFactory getConnectionFactory(String host, int port, String username, String password, String virtualHost, int connectionCacheSize, boolean publisherConfirm, boolean isConsumer) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();

        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setConnectionCacheSize(connectionCacheSize);
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        cachingConnectionFactory.setPublisherConfirms(publisherConfirm);

        cachingConnectionFactory.setConnectionListeners(Arrays.asList(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {
                logger.info("==============rabbitmq" + (isConsumer ? "消费" : "生产") + "者的连接[" + connection + "]建立============");
            }

            @Override
            public void onClose(Connection connection) {
                logger.info("==============rabbitmq" + (isConsumer ? "消费" : "生产") + "者的连接[" + connection + "]关闭============");
            }
        }));

        return cachingConnectionFactory;
    }


}
