package com.stosz.product.engine;

import com.stosz.plat.engine.SpringCommonBeanConfig;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import com.stosz.plat.utils.JacksonMappingJackson2HttpMessageConverter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@ComponentScan(basePackages = { "com.stosz" }, excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class),
        @Filter(type = FilterType.ANNOTATION, value = Controller.class),
        @Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class),
        })
@EnableAsync
@EnableScheduling
@EnableCaching
@EnableAspectJAutoProxy( proxyTargetClass = true)
//@Import(value = {RabbitMQPublisherConfig.class,RabbitMQConsumerConfig.class})
public class SpringRootConfig  extends SpringCommonBeanConfig{

//    @Bean
//    public RedisTemplate<String,String> redisTemplate()
//    {
//        return new RedisTemplate<>();
//    }
//

    @Value("${cache.names}")
    public String cacheNames;

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

    /**
     * 在子系统中配置消息处理器,因为每个子系统只是处理一次，所以这里直接定义成一个
     **********************************************************************************/
    @Override
    protected ConcurrentHashMap<String, List<AbstractHandler>> rabbitMessageHandlerMap() {
        return new ConcurrentHashMap<>();
    }

    //
    @Bean(name = "springCM")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("getDepartmentNode", "getDepartmentByOldId", "getOldSkuByAttValueId", "getAttributeByTitle", "getZoneById", "getByTitleAndAttribute", "getDepartmentByUserOldId", "getProductAttributeRel", "getUserByOldId", "getProductById", "getNewCategoryById");
    }
}