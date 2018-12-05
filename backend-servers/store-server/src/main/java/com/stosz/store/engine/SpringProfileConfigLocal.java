package com.stosz.store.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:/d:/workspace/erp-v2/backend-servers/global-config/local/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${user.home}/workspace/erp-v2/backend-servers/global-config/local/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${user.home}/erp/config/local.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:/data/erp/src/erp-v2/global-config/local/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:properties/local/project.change.properties")
@Profile("local")
public class SpringProfileConfigLocal {

}
