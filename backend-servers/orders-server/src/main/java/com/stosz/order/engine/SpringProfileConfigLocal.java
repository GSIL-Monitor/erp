package com.stosz.order.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


/**
 * 1,先加载全局的配置文件;
 * 2,加载classpath下确定的配置文件，如果跟全局的不一样，在这里可以再次定义，会覆盖全局定义的配置；
 * 3,用户目录下的配置文件，优先级别最高，可以覆盖
 */

@Configuration
@PropertySource(value = "classpath:properties/local/project.change.properties")
@PropertySource(value = "file:/d:/workspace/erp-v2/backend-servers/global-config/local/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${user.home}/erp/config/orders.properties", ignoreResourceNotFound = true)
@Profile("local")
public class SpringProfileConfigLocal {}
