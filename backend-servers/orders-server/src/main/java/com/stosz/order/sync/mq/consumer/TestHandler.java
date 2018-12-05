package com.stosz.order.sync.mq.consumer;

import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @auther carter
 * create time    2017-12-08
 */
@Component
public class TestHandler extends AbstractHandler<Map> {

    private static final Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @Override
    public boolean handleMessage(String method, Map dataItem) {
        logger.info("处理测试消息..." +  dataItem);
        return true;
    }

    @Override
    public Class<Map> getTClass() {
        return Map.class;
    }
}
