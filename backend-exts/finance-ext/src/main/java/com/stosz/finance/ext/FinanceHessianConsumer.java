package com.stosz.finance.ext;

import com.stosz.finance.ext.service.IFinanceService;
import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.hessian.AbstractHessianProviderUrlConfig;
import com.stosz.plat.hessian.HessianUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * @auther carter
 * create time    2017-12-01
 */
public abstract class FinanceHessianConsumer extends AbstractHessianProviderUrlConfig {

    @Override
    public SystemEnum getProviderSystemEnum() {
        return SystemEnum.finance;
    }

    @Bean
    public HessianProxyFactoryBean iFinanceService() {
        return getHessianProxyFactoryBean(IFinanceService.url, IFinanceService.class);
    }
}
