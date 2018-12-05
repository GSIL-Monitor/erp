package com.stosz.tms;

import com.caucho.hessian.client.HessianProxyFactory;
import com.stosz.plat.hessian.HessianUtil;
import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.service.IShippingWayStoreService;
import com.stosz.tms.vo.StoreInfoResponseVo;
import org.junit.Test;

public class TestTShippingWayStoreService extends JUnitBaseTest {

	@Test
	public void test() {
		HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
		try {
			hessianProxyFactory.setSerializerFactory(HessianUtil.getSerializerFactory());
			IShippingWayStoreService shippingWayStoreService = (IShippingWayStoreService) hessianProxyFactory.create(IShippingWayStoreService.class, "http://localhost:8086/tms/remote/IShippingWayStoreService");


//			final StoreInfoResponseVo backStore = shippingWayStoreService.findBackStore(26);

//			logger.info(""+backStore.getId());
//			logger.info(backStore.getName());

//			TransportTrackResponse trackResponse = transportFacadeService.captureTransTrack("indonesia", "JH0000001734");

//			logger.info(trackResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

}
