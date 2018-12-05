package com.stosz.tms.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * file:/data/erp/src/erp-v2/backend-servers/global-config/dev/project.global.properties 里面 oldErp 配置的135环境的老ERP库，但是erp_order_shipping 表没有数据，同步不了记录，别
 * @author feiheping
 * @version [1.0,2018年1月20日]
 */
@Configuration
@PropertySource(value = "file:/d:/workspace/erp-v2/backend-servers/global-config/dev/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${user.home}/workspace/erp-v2/backend-servers/global-config/dev/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${user.home}/erp/config/local.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:/data/erp/src/erp-v2/backend-servers/global-config/dev/project.global.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:properties/dev/project.change.properties")
@Profile("dev")
public class SpringProfileConfigDev {



}
