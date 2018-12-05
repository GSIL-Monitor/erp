package com.stosz;

import com.stosz.admin.ext.service.*;
import com.stosz.olderp.ext.service.*;
import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.hessian.AbstractHessianProviderUrlConfig;
import com.stosz.plat.hessian.HessianUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.util.Assert;

/**
 * @auther carter
 * create time    2017-11-28
 * hessian的消费者配置
 *
 * 每一个接口对应一个类
 *
 */
public abstract class AdminHessianConsumer extends AbstractHessianProviderUrlConfig {


    @Override
    public SystemEnum getProviderSystemEnum() {
        return SystemEnum.admin;
    }

    @Bean
    public HessianProxyFactoryBean iErpDomainService(){
        return getHessianProxyFactoryBean(IErpDomainService.url , IErpDomainService.class);
//        return getHessianProxyFactoryBean(IErpDomainService.url,IErpDomainService.class);
    }

    @Bean
    public HessianProxyFactoryBean iErpOrderinfoService(){
        return getHessianProxyFactoryBean(IErpOrderInfoService.url,IErpOrderInfoService.class);
    }

    @Bean
    public HessianProxyFactoryBean iErpOrderitemService(){
        return getHessianProxyFactoryBean(IErpOrderItemService.url,IErpOrderItemService.class);
    }

    @Bean
    public HessianProxyFactoryBean iErpOrderShippingService(){
        return getHessianProxyFactoryBean(IErpOrderShippingService.url,IErpOrderShippingService.class);
    }

    @Bean
    public HessianProxyFactoryBean iErpWarehouseService(){
        return getHessianProxyFactoryBean(IErpWarehouseService.url,IErpWarehouseService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpAttributeService(){
        return getHessianProxyFactoryBean(IOldErpAttributeService.url,IOldErpAttributeService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpAttributeValueService(){
        return getHessianProxyFactoryBean(IOldErpAttributeValueService.url,IOldErpAttributeValueService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpBlackListService(){
        return getHessianProxyFactoryBean(IOldErpBlackListService.url,IOldErpBlackListService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpCategoryService(){
        return getHessianProxyFactoryBean(IOldErpCategoryService.url,IOldErpCategoryService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpCheckProductService(){
        return getHessianProxyFactoryBean(IOldErpCheckProductService.url,IOldErpCheckProductService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpCountryService(){
        return getHessianProxyFactoryBean(IOldErpCountryService.url,IOldErpCountryService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpCurrencyService(){
        return getHessianProxyFactoryBean(IOldErpCurrencyService.url,IOldErpCurrencyService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpDepartmentService(){
        return getHessianProxyFactoryBean(IOldErpDepartmentService.url,IOldErpDepartmentService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpOrderService(){
        return getHessianProxyFactoryBean(IOldErpOrderService.url,IOldErpOrderService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpProductService(){
        return getHessianProxyFactoryBean(IOldErpProductService.url,IOldErpProductService.class);
    }


    @Bean
    public HessianProxyFactoryBean iOldErpProductSkuService(){
        return getHessianProxyFactoryBean(IOldErpProductSkuService.url,IOldErpProductSkuService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpProductZoneDomainService(){
        return getHessianProxyFactoryBean(IOldErpProductZoneDomainService.url,IOldErpProductZoneDomainService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpProductZoneService(){
        return getHessianProxyFactoryBean(IOldErpProductZoneService.url,IOldErpProductZoneService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpQuantityService(){
        return getHessianProxyFactoryBean(IOldErpQuantityService.url,IOldErpQuantityService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpStockService(){
        return getHessianProxyFactoryBean(IOldErpStockService.url,IOldErpStockService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpSupplierService(){
        return getHessianProxyFactoryBean(IOldErpSupplierService.url,IOldErpSupplierService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpUserService(){
        return getHessianProxyFactoryBean(IOldErpUserService.url,IOldErpUserService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpZoneService(){
        return getHessianProxyFactoryBean(IOldErpZoneService.url,IOldErpZoneService.class);
    }

    @Bean
    public HessianProxyFactoryBean iDepartmentService(){
        return getHessianProxyFactoryBean(IDepartmentService.url,IDepartmentService.class);
    }

    @Bean
    public HessianProxyFactoryBean iDepartmentSyncService(){
        return getHessianProxyFactoryBean(IDepartmentSyncService.url,IDepartmentSyncService.class);
    }

    @Bean
    public HessianProxyFactoryBean iGreetingService(){
        return getHessianProxyFactoryBean(IGreetingService.url,IGreetingService.class);
    }

    @Bean
    public HessianProxyFactoryBean iJobAuthorityRelService(){
        return getHessianProxyFactoryBean(IJobAuthorityRelService.url,IJobAuthorityRelService.class);
    }

    @Bean
    public HessianProxyFactoryBean iJobSyncService(){
        return getHessianProxyFactoryBean(IJobSyncService.url,IJobSyncService.class);
    }

    @Bean
    public HessianProxyFactoryBean iSystemService(){
        return getHessianProxyFactoryBean(ISystemService.url,ISystemService.class);
    }

    @Bean
    public HessianProxyFactoryBean iUserDepartmentRelService(){
        return getHessianProxyFactoryBean(IUserDepartmentRelService.url,IUserDepartmentRelService.class);
    }

    @Bean
    public HessianProxyFactoryBean iUserService(){
        return getHessianProxyFactoryBean(IUserService.url,IUserService.class);
    }

    @Bean
    public HessianProxyFactoryBean iUserSyncService(){
        return getHessianProxyFactoryBean(IUserSyncService.url,IUserSyncService.class);
    }


    @Bean
    public HessianProxyFactoryBean iErpZoneWarehouseService(){
        return getHessianProxyFactoryBean(IErpZoneWarehouseService.url,IErpZoneWarehouseService.class);
    }

    @Bean
    public HessianProxyFactoryBean iOldErpCategoryNewService(){
        return getHessianProxyFactoryBean(IOldErpCategoryNewService.url,IOldErpCategoryNewService.class);
    }
}
