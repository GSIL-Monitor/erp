package com.stosz.tms;

import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.hessian.AbstractHessianProviderUrlConfig;
import com.stosz.tms.service.IShippingWayStoreService;
import com.stosz.tms.service.ITransportFacadeService;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * @auther carter
 * create time    2017-11-28
 * hessian的消费者配置
 */
public  abstract class TmsHessianConsumer extends AbstractHessianProviderUrlConfig {

    @Override
    public SystemEnum getProviderSystemEnum() {
        return SystemEnum.tms;
    }
    @Bean
    public HessianProxyFactoryBean iTransportFacadeService(){
        return getHessianProxyFactoryBean(ITransportFacadeService.url,ITransportFacadeService.class);
    }
    @Bean
    public HessianProxyFactoryBean iShippingWayStoreService(){
        return getHessianProxyFactoryBean(IShippingWayStoreService.url,IShippingWayStoreService.class);
    }

}
