package com.stosz.tms;

import com.caucho.hessian.client.HessianProxyFactory;
import com.stosz.plat.common.ResultBean;
import com.stosz.plat.hessian.HessianUtil;
import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.enums.ParcelOrderStateEnum;
import com.stosz.tms.service.ITransportFacadeService;
import com.stosz.tms.utils.JsonUtils;
import org.junit.Test;

public class TestTrackService extends JUnitBaseTest {

	@Test
	public void test() {
		HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
		try {
			hessianProxyFactory.setSerializerFactory(HessianUtil.getSerializerFactory());
			ITransportFacadeService transportFacadeService = (ITransportFacadeService) hessianProxyFactory.create(ITransportFacadeService.class, "http://localhost:8086/tms/remote/ITransportFacadeService");

//			OrderStateInfo stateInfo = new OrderStateInfo();
//			stateInfo.setOrderStateEnum(OrderStateEnum.deliver);
//			stateInfo.setShippingWayCode("12211121");
//			stateInfo.setWeight(new BigDecimal(10));
//			stateInfo.setOrderId(1);

//			transportFacadeService.notifyLogisticsIsFetch(stateInfo);

//			final TrackingTaskAndDetailInfoVo taskAndDetailInfoVo = transportFacadeService.queryTaskAndDetailInvo(1);
//
//			logger.info(JsonUtils.toJson(taskAndDetailInfoVo));

			final ResultBean resultBean = transportFacadeService.updateParcelStatusByOrderId(33093, ParcelOrderStateEnum.REJECT);

			logger.info(JsonUtils.toJson(resultBean));

//			TransportTrackResponse trackResponse = transportFacadeService.captureTransTrack("indonesia", "JH0000001734");

//			logger.info(trackResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

}
