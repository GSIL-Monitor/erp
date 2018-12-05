package com.stosz.store.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * 生产环境设置为相对路径，方便统一设置配置文件仓库
 */
@Configuration

@PropertySource(value = "classpath:properties/prod/project.change.properties")
@PropertySource(value = "file:${catalina.home}/properties/project.global.properties")
@Profile("prod")
public class SpringProfileConfigProd {

}
