package com.stosz.order.engine;

import com.stosz.crm.ext.service.ICustomerRateService;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.hessian.HessianUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

/**
 * @auther carter
 * create time    2017-12-01
 */
@Configuration
public class OrderProvider {

    @Bean(IOrderService.url)
    public RemoteExporter remoteIOrderService(@Autowired IOrderService iOrderService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOrderService.class);
        hessianServiceExporter.setService(iOrderService);
        return hessianServiceExporter;
    }


    @Bean(ICustomerRateService.url)
    public RemoteExporter remoteICustomerRateService(@Autowired ICustomerRateService customerRateService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(ICustomerRateService.class);
        hessianServiceExporter.setService(customerRateService);
        return hessianServiceExporter;
    }




}
