package com.stosz.store.engine;

import com.stosz.OrderHessianConsumer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @auther carter
 * create time    2017-11-30
 */
@Configuration
public class OrdersConsumer extends OrderHessianConsumer implements EnvironmentAware {

}
