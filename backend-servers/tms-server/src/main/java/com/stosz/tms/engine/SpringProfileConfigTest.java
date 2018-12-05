package com.stosz.tms.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:properties/test/project.change.properties")
@PropertySource(value = "file:/data/erp/src/erp-v2/backend-servers/global-config/test/project.global.properties", ignoreResourceNotFound = true)
@Profile("test")
public class SpringProfileConfigTest {

}
