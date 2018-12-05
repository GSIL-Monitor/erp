//package com.stosz.product.engine;
//
//import com.stosz.plat.service.ProjectConfigService;
//import com.stosz.plat.service.SsoUserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * 将 各个系统都会用到的 公用组件在这里生成
// */
//@Configuration
//public class SpringCommonBeanConfig {
//
//
//    private static final Logger logger = LoggerFactory.getLogger(SpringCommonBeanConfig.class);
//
//
//    @Bean
//    public ProjectConfigService projectConfigService() {
//        ProjectConfigService projectConfigService = new ProjectConfigService();
//        return projectConfigService;
//    }
//
//
//    /*************    redis config start    *************/
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory(@Autowired ProjectConfigService configService) {
////        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
////        redisSentinelConfiguration.setDatabase( configService.projectRedisDatabase );
////        redisSentinelConfiguration.setPassword( RedisPassword.of(configService.projectRedisPassword) );
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(Integer.parseInt(configService.projectRedisPoolMaxIdle));
//        poolConfig.setMinIdle(Integer.parseInt(configService.projectRedisPoolMinIdle));
//        poolConfig.setTestOnBorrow(Boolean.valueOf(configService.projectRedisPoolTestOnBorrow));
//        poolConfig.setMaxWaitMillis(Integer.parseInt(configService.projectRedisPoolMaxWait));
//
//
////        JedisClientConfiguration JedisClientConfiguration = new JedisClientConfiguration();
//
////        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
////
////        try {
////            redisStandaloneConfiguration.setDatabase(Integer.parseInt(configService.projectRedisDatabase));
////            redisStandaloneConfiguration.setHostName(configService.projectRedisHost);
////            redisStandaloneConfiguration.setPort(Integer.parseInt(configService.projectRedisPort));
////        } catch (Exception e) {
////            logger.error("初始化redis客户端配置时参数错误！",e);
////        }
//
////        redisStandaloneConfiguration.setPassword(RedisPassword.of(configService.projectRedisPassword));
//
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setPoolConfig(poolConfig);
//
//        factory.setHostName(configService.projectRedisHost);
//        factory.setPort(Integer.parseInt(configService.projectRedisPort));
//        factory.setDatabase(Integer.parseInt(configService.projectRedisDatabase));
//        factory.setPassword(configService.projectRedisPassword);
//        factory.setUsePool(true);
//        factory.setTimeout(Integer.parseInt(configService.projectRedisTimeout));
//
//
//        return factory;
//    }
//
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(@Autowired JedisConnectionFactory jedisConnectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate();
//        template.setConnectionFactory(jedisConnectionFactory);
//
//        template.setKeySerializer(new StringRedisSerializer());
//
//        template.setValueSerializer(new JdkSerializationRedisSerializer());
//
//        return template;
//
//    }
//
//    /*************    redis config end    *************/
//
//
//    @Bean
//    public SsoUserService ssoUserService() {
//        SsoUserService ssoUserService = new SsoUserService();
//        return ssoUserService;
//    }
//}
