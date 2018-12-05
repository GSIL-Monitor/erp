package com.stosz.tms.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:properties/pre/project.change.properties")
@PropertySource(value = "classpath:properties/local/database.tms.properties")
@PropertySource(value = "classpath:properties/local/database.olderp.properties")
@PropertySource(value = "file:/d:/workspace/erp-v2/backend-servers/global-config/pre/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${user.home}/workspace/erp-v2/backend-servers/global-config/pre/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${user.home}/erp/config/local.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:/data/erp/src/erp-v2/global-config/pre/project.global.properties", ignoreResourceNotFound = true)
@Profile("pre")
public class SpringProfileConfigPre {

}
