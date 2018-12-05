package com.stosz.tms.engine;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.stosz.plat.engine.SpringCommonBeanConfig;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;

@Configuration
@ComponentScan(basePackages = { "com.stosz" }, excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class),
		@Filter(type = FilterType.ANNOTATION, value = Controller.class), @Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class), })
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
// @Import(value = {RabbitMQPublisherConfig.class,RabbitMQConsumerConfig.class})
// "classpath:consumer.xml",
// @ImportResource(value = {"classpath:provider.xml","classpath:consumer.xml"})
public class SpringRootConfig extends SpringCommonBeanConfig {

	@Value("${cache.names}")
	public String cacheNames;

	@Bean("logisticsThreadPool")
	public ThreadPoolTaskExecutor logisticsThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(15);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setQueueCapacity(10000000);
		return executor;
	}

	@Bean("placeOrderThreadPool")
	public ThreadPoolTaskExecutor placeOrderThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(15);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setQueueCapacity(10000000);
		return executor;
	}

	@Bean("blackCatClassifyThreadPool")
	public ThreadPoolTaskExecutor blackCatClassifyThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(6);
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
}