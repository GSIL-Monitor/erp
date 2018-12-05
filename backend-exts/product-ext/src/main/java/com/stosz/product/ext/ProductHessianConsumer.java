package com.stosz.product.ext;

import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.hessian.AbstractHessianProviderUrlConfig;
import com.stosz.plat.hessian.HessianUtil;
import com.stosz.product.ext.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * @auther carter
 * create time    2017-11-28
 * hessian的消费者配置
 *
 * 每一个接口对应一个类
 *
 */
public abstract class ProductHessianConsumer extends AbstractHessianProviderUrlConfig {

	@Override
	public SystemEnum getProviderSystemEnum() {
		return SystemEnum.product;
	}
	@Bean
	public HessianProxyFactoryBean iAttributeValueService() {
		return getHessianProxyFactoryBean(IAttributeValueService.url, IAttributeValueService.class);
	}

	@Bean
	public HessianProxyFactoryBean iCurrencyService() {
		return getHessianProxyFactoryBean(ICurrencyService.url, ICurrencyService.class);
	}

	@Bean
	public HessianProxyFactoryBean iProductNewService() {
		return getHessianProxyFactoryBean(IProductNewService.url, IProductNewService.class);
	}

	@Bean
	public HessianProxyFactoryBean iProductService() {
		return getHessianProxyFactoryBean(IProductService.url, IProductService.class);
	}

	@Bean
	public HessianProxyFactoryBean iProductSkuService() {
		return getHessianProxyFactoryBean(IProductSkuService.url, IProductSkuService.class);
	}

	@Bean
	public HessianProxyFactoryBean iWmsService() {
		return getHessianProxyFactoryBean(IWmsService.url, IWmsService.class);
	}

	@Bean
	public HessianProxyFactoryBean iZoneService() {
		return getHessianProxyFactoryBean(IZoneService.url, IZoneService.class);
	}

	@Bean
	public HessianProxyFactoryBean iObjectLabelService() {
		return getHessianProxyFactoryBean(ILabelObjectServcie.url, ILabelObjectServcie.class);
	}

	@Bean
	public HessianProxyFactoryBean iPartnerService() {
		return getHessianProxyFactoryBean(IPartnerService.url, IPartnerService.class);
	}

	@Bean
	public HessianProxyFactoryBean iProductDeamonService() {
		return getHessianProxyFactoryBean(IProductDeamonService.url, IProductDeamonService.class);
	}
}
