package com.stosz.purchase.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:properties/local/project.change.properties")
@PropertySource(value = "file:/d:/workspace/erp-v2/backend-servers/global-config/local/project.global.properties", ignoreResourceNotFound = true)

@Profile("local")
public class SpringProfileConfigLocal {

}
