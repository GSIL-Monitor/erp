package com.stosz;

import com.stosz.crm.ext.service.ICustomerRateService;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.hessian.AbstractHessianProviderUrlConfig;
import com.stosz.plat.hessian.HessianUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * @auther carter
 * create time    2017-11-28
 * hessian的消费者配置
 */
public  abstract class OrderHessianConsumer  extends AbstractHessianProviderUrlConfig {


    @Override
    public SystemEnum getProviderSystemEnum() {
        return SystemEnum.orders;
    }

    @Bean
    public HessianProxyFactoryBean iOrderService(){
        return getHessianProxyFactoryBean(IOrderService.url,IOrderService.class);
    }

    @Bean
    public HessianProxyFactoryBean ICustomerRateService(){
        return getHessianProxyFactoryBean(ICustomerRateService.url,ICustomerRateService.class);
    }

//    @Bean
//    public HessianProxyFactoryBean iOrderService(){
//        return getHessianProxyFactoryBean(IOrderService.url,IOrderService.class);
//    }


}
