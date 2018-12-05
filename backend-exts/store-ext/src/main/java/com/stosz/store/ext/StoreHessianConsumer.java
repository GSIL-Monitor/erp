package com.stosz.store.ext;

import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.hessian.AbstractHessianProviderUrlConfig;
import com.stosz.plat.hessian.HessianUtil;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.store.ext.service.ITransitStockService;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * @auther carter
 * create time    2017-12-01
 */
public abstract class StoreHessianConsumer   extends AbstractHessianProviderUrlConfig {

    @Override
    public SystemEnum getProviderSystemEnum() {
        return SystemEnum.store;
    }

    @Bean
    public HessianProxyFactoryBean iTransitStockService(){
        return getHessianProxyFactoryBean(ITransitStockService.url,ITransitStockService.class);
    }

    @Bean
    public HessianProxyFactoryBean iStockService(){
        return getHessianProxyFactoryBean(IStockService.url,IStockService.class);
    }

    @Bean
    public HessianProxyFactoryBean iWmsService(){
        return getHessianProxyFactoryBean(IStorehouseService.url,IStorehouseService.class);
    }

}
