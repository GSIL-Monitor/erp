package com.stosz.plat.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Configurable
public class RabbitMQPublisherConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQPublisherConfig.class);
//    @Autowired
//    private ProjectConfigService projectConfigService;
//    @Autowired
//    private RabbitMQConfig rabbitMQConfig;

//    @Bean
//    //@Lazy
//    public ConnectionFactory connectionFactory(@Value("${rabbitmq.host}") String host,
//                                               @Value("${rabbitmq.port}") int port,
//                                               @Value("${rabbitmq.username}") String username,
//                                               @Value("${rabbitmq.password}") String password,
//                                               @Value("${rabbitmq.virtualHost}") String virtualHost,
//                                               @Value("${rabbitmq.connectionCacheSize}") int connectionCacheSize,
//                                               @Value("${rabbitmq.publisherConfirm}") boolean publisherConfirm
//                                               )
//    {
//
//        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//
//        cachingConnectionFactory.setHost(host);
//        cachingConnectionFactory.setPort(port);
//        cachingConnectionFactory.setUsername(username);
//        cachingConnectionFactory.setPassword(password);
//        cachingConnectionFactory.setVirtualHost(virtualHost);
//        cachingConnectionFactory.setConnectionCacheSize(connectionCacheSize);
//        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
//        cachingConnectionFactory.setPublisherConfirms(publisherConfirm);
//
//        cachingConnectionFactory.setConnectionListeners(Arrays.asList(new ConnectionListener() {
//            @Override
//            public void onCreate(Connection connection) {
//                logger.info("==============rabbitmq生产者的连接[" + connection + "]建立============");
//            }
//
//            @Override
//            public void onClose(Connection connection) {
//                logger.info("==============rabbitmq生产者的连接[" + connection + "]关闭============");
//            }
//        }));
//
//        return cachingConnectionFactory;
//    }
//
//
//    @Bean
//    //@Lazy
//    public RabbitAdmin rabbitAdmin(@Autowired ConnectionFactory connectionFactory)
//    {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        return rabbitAdmin;
//    }
//
//    @Bean
//    //@Lazy
//    public DirectExchange  projectDirectExchange(@Autowired RabbitAdmin rabbitAdmin)
//    {
//        DirectExchange directExchange = new DirectExchange(getProjectName()+".Exchange",true,false);
//        rabbitAdmin.declareExchange(directExchange);
//
//        return directExchange;
//    }
//
//    private String getProjectName() {
//        return "erp";
//    }
//
//
//    @Bean
//    //@Lazy
//    public Binding binding(@Autowired RabbitAdmin rabbitAdmin, @Autowired DirectExchange projectDirectExchange)
//    {
//
//        Set<String> qNameSet = rabbitMQConfig.getQNameList();
//        qNameSet.forEach(qName->{
//
//            Queue zoneQueue = new Queue(qName,true,false,false);
//            rabbitAdmin.declareQueue(zoneQueue);
//
//            Binding binding = BindingBuilder.bind(zoneQueue).to(projectDirectExchange).with(zoneQueue.getName());
//            rabbitAdmin.declareBinding(binding);
//        });
//
//
//
//        return null;
//
//    }




//
//    @Bean
//    public Binding currencyBinding(@Autowired RabbitAdmin rabbitAdmin,  @Autowired TopicExchange pcTopicExchange)
//    {
//        Queue currencyQueue = new Queue("q_pc_currency",true,false,false);
//        rabbitAdmin.declareQueue(currencyQueue);
//
//        Binding binding = BindingBuilder.bind(currencyQueue).to(pcTopicExchange).with(currencyQueue.getName());
//        rabbitAdmin.declareBinding(binding);
//
//        return binding;
//
//    }
//
//    @Bean
//    public Binding languageBinding(@Autowired RabbitAdmin rabbitAdmin,  @Autowired TopicExchange pcTopicExchange)
//    {
//        Queue languageQueue = new Queue("q_pc_language",true,false,false);
//        rabbitAdmin.declareQueue(languageQueue);
//
//        Binding binding = BindingBuilder.bind(languageQueue).to(pcTopicExchange).with(languageQueue.getName());
//        rabbitAdmin.declareBinding(binding);
//        return binding;
//
//    }


//    @Autowired
//    private ObjectMapper objectMapper;

//    @Bean
//    //@Lazy
//    public RabbitTemplate rabbitTemplate(@Autowired ConnectionFactory connectionFactory)
//    {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setEncoding("UTF-8");
//        rabbitTemplate.setConnectionFactory(connectionFactory);
//
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//
//        jackson2JsonMessageConverter.setDefaultCharset("UTF-8");
//        jackson2JsonMessageConverter.setJsonObjectMapper(objectMapper);
//
//        //todo 添加对新的类型的支持；比如LocalDateTime, LocalDate 等java8特有类的支持；
//        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
//
//        return rabbitTemplate;
//    }




}
