package com.stosz.tms.engine;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.stosz.tms.service.transport.AbstractTransportHandler;

@Component
public class SpringBeanDefineConfigue implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		ConfigurableApplicationContext context = (ConfigurableApplicationContext) event.getApplicationContext();
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getBeanFactory();

		// 物流API Handler
		String[] handlers = factory.getBeanNamesForType(AbstractTransportHandler.class);
		if (handlers != null && handlers.length > 0) {
			for (String handlerBeanName : handlers) {
				BeanDefinition beanDefinition = factory.getBeanDefinition(handlerBeanName);
				String scope = beanDefinition.getScope();
				if (!ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(scope)) {
					beanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
					factory.registerBeanDefinition(handlerBeanName, beanDefinition);
				}
			}
		}
	}

}
