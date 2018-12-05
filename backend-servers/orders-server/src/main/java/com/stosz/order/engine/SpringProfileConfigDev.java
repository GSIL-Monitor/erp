package com.stosz.order.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;



@Configuration
@PropertySource(value = "classpath:properties/dev/project.change.properties")
@PropertySource(value = "file:/data/erp/src/erp-v2/backend-servers/global-config/dev/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${user.home}/erp/config/orders.properties", ignoreResourceNotFound = true)
@Profile("dev")
public class SpringProfileConfigDev {}
