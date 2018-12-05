package com.stosz.store.engine;

import com.stosz.plat.engine.SpringCommonBeanConfig;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将 各个系统都会用到的 公用组件在这里生成
 */
@Configuration
public class StoreSpringCommonBeanConfig extends SpringCommonBeanConfig {


    /**
     * 在子系统中配置消息处理器
     **********************************************************************************/
    @Override
    protected ConcurrentHashMap<String, List<AbstractHandler>> rabbitMessageHandlerMap() {
        return new ConcurrentHashMap<>();
    }
}
