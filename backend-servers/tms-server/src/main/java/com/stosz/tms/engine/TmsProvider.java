package com.stosz.tms.engine;

import com.stosz.plat.hessian.HessianUtil;
import com.stosz.tms.service.IShippingWayStoreService;
import com.stosz.tms.service.ITransportFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

@Configuration
public class TmsProvider {

	@Bean(ITransportFacadeService.url)
	public RemoteExporter remoteIOrderService(@Autowired ITransportFacadeService facadeService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(ITransportFacadeService.class);
		hessianServiceExporter.setService(facadeService);
		return hessianServiceExporter;
	}

	@Bean(IShippingWayStoreService.url)
	public RemoteExporter remoteIShippingWayStoreService(@Autowired IShippingWayStoreService shippingWayStoreService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(IShippingWayStoreService.class);
		hessianServiceExporter.setService(shippingWayStoreService);
		return hessianServiceExporter;
	}

}
