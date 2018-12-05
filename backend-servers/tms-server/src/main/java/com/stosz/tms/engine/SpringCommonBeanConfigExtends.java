package com.stosz.tms.engine;

import com.stosz.plat.engine.SpringCommonBeanConfig;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SpringCommonBeanConfigExtends extends SpringCommonBeanConfig {

    @Override
    protected ConcurrentHashMap<String, List<AbstractHandler>> rabbitMessageHandlerMap() {
        return new ConcurrentHashMap<String, List<AbstractHandler>>();
    }
}
