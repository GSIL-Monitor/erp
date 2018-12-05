package com.stosz.purchase.ext;

import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.hessian.AbstractHessianProviderUrlConfig;
import com.stosz.plat.hessian.HessianUtil;
import com.stosz.purchase.ext.service.IOrderRequiredService;
import com.stosz.purchase.ext.service.IPurchaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * @auther carter
 * create time    2017-12-01
 */
public abstract class PurchaseHessianConsumer  extends AbstractHessianProviderUrlConfig {


    @Override
    public SystemEnum getProviderSystemEnum() {
        return SystemEnum.purchase;
    }
    @Bean
    public HessianProxyFactoryBean iOrderRequiredService(){
        return getHessianProxyFactoryBean(IOrderRequiredService.url,IOrderRequiredService.class);
    }

    @Bean
    public HessianProxyFactoryBean iPurchaseService(){
        return getHessianProxyFactoryBean(IPurchaseService.url,IPurchaseService.class);
    }
}
