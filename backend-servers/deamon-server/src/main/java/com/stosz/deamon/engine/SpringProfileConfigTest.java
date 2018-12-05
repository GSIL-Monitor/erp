package com.stosz.deamon.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:/data/erp/src/erp-v2/global-config/test/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:/d:/workspace/erp-v2/backend-servers/global-config/local/project.change.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:/data/erp/src/erp-v2/backend-servers/global-config/dev/project.global.properties", ignoreResourceNotFound = true)
@Profile("test")
public class SpringProfileConfigTest {

}
