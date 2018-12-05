package com.stosz.order.engine;

import com.stosz.order.ext.dto.TransportDTO;
import com.stosz.order.ext.mq.MatchLogisticsModel;
import com.stosz.order.ext.mq.OccupyStockModel;
import com.stosz.order.service.asyn.OrderAsynService;
import com.stosz.order.sync.mq.consumer.*;
import com.stosz.plat.engine.SpringCommonBeanConfig;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
public class SpringRootConfig  extends SpringCommonBeanConfig {


    @Bean
    public OrderAsynService orderAsynService()
    {
        return OrderAsynService.getInstance();
    }


    @Value("${cache.names}")
    public String cacheNames;


    @Bean(name = "springCM")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("getDepartmentNode");
    }

    /**
     * 在子系统中配置消息处理器
     **********************************************************************************/
    @Override
    protected ConcurrentHashMap<String, List<AbstractHandler>> rabbitMessageHandlerMap() {
        ConcurrentHashMap<String,  List<AbstractHandler>> concurrentHashMap = new ConcurrentHashMap<>();

        concurrentHashMap.putIfAbsent("src/main/webapp/test",Arrays.asList(springContextHolder.getBean(TestHandler.class)));
        concurrentHashMap.putIfAbsent("frontOrder",Arrays.asList(springContextHolder.getBean(FrontOrderHandler.class)));
        concurrentHashMap.putIfAbsent(OccupyStockModel.MESSAGE_TYPE,Arrays.asList(springContextHolder.getBean(OccupyStockHandler.class)));

//        concurrentHashMap.putIfAbsent(OrderToLogisticsHandler.class.getSimpleName(), Arrays.asList(springContextHolder.getBean(MatchLogisticsHandler.class)));
        concurrentHashMap.putIfAbsent(MatchLogisticsModel.MESSAGE_TYPE, Arrays.asList(springContextHolder.getBean(MatchLogisticsHandler.class)));
        concurrentHashMap.putIfAbsent(TransportDTO.MESSAGE_TYPE,Arrays.asList(springContextHolder.getBean(LogisticsTrajectoryHandler.class)));
        return  concurrentHashMap;
    }
}