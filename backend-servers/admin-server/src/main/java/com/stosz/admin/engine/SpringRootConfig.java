package com.stosz.admin.engine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = { "com.stosz" }, excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class), 
        @Filter(type = FilterType.ANNOTATION, value = Controller.class), 
        @Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class), 
        })
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching
public class SpringRootConfig {


    @Value("${cache.names}")
    public String cacheNames;

    @Bean(name = "springCM")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("getDepartmentNode", "getDepartmentByOldId", "getOldSkuByAttValueId", "getAttributeByTitle", "getZoneById", "getByTitleAndAttribute", "getDepartmentByUserOldId", "getProductAttributeRel", "getUserByOldId", "getProductById", "getNewCategoryById");
    }
}