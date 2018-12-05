package com.stosz.product.engine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by shiqiangguo on 17/5/12.
 */
@Configuration
public class SpringTaskConfig {




    @Bean("attributePool")
    public ThreadPoolTaskExecutor attributePool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(15);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }

    @Bean("attributeValuePool")
    public ThreadPoolTaskExecutor attributeValuePool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(15);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }

    @Bean("productSkuPool")
    public ThreadPoolTaskExecutor productSkuPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(20);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }

    @Bean("productPool")
    public ThreadPoolTaskExecutor productPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(15);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }

    @Bean("productZonePool")
    public ThreadPoolTaskExecutor productZonePool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(100000000);
        return executor;
    }

    @Bean("productZoneDomainPool")
    public ThreadPoolTaskExecutor productZoneDomainPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(24);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }

    @Bean("quantityPool")
    public ThreadPoolTaskExecutor quantityPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }

    @Bean("hasStockPool")
    public ThreadPoolTaskExecutor hasStockPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }

    @Bean("categoryAttributePool")
    public ThreadPoolTaskExecutor categoryAttributePool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }


    @Bean("wmsThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor wmsThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setQueueCapacity(10000000);
        return executor;
    }
}
