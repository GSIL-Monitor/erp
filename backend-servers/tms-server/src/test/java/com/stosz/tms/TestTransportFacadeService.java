package com.stosz.tms;

import com.stosz.plat.utils.JsonUtils;
import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportRequest;
import com.stosz.tms.dto.TransportResponse;
import com.stosz.tms.enums.OrderTypeEnum;
import com.stosz.tms.service.TransportFacadeService;
import com.stosz.tms.task.LogisticsTrackTaskImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

public class TestTransportFacadeService extends JUnitBaseTest {

	@Autowired
	 private TransportFacadeService transportFacadeService;
	@Autowired
	private LogisticsTrackTaskImpl logisticsTrackTask;

	@Test
	public void test1()throws Exception{
		logisticsTrackTask.pigeonhole(0,100);
	}


	@Test
	public void testTimesHandler() throws Exception {
		/*HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();*/
		/*try {*/
			/*hessianProxyFactory.setSerializerFactory(HessianUtil.getSerializerFactory());
			ITransportFacadeService transportFacadeService = (ITransportFacadeService) hessianProxyFactory.create(ITransportFacadeService.class,
					"http://localhost:8086/tms/remote/ITransportFacadeService");
			*/
			TransportRequest request = new TransportRequest();

			request.setOrderAmount(new BigDecimal(1380));
			request.setOrderNo(String.valueOf(new Random(System.currentTimeMillis()).nextInt(10000000) + 5000));
			request.setGoodsQty(20);
			request.setPayState(0);
			request.setOrderTypeEnum(OrderTypeEnum.normal);
			request.setOrderDate(new Date());
			request.setRemark("测试-remark");
			request.setOrderId(new Random(System.currentTimeMillis()).nextInt(1000000) + 10);
			request.setOrderNo(UUID.randomUUID().toString().replace("-", "").substring(0, 20));
			request.setServiceRemark("1221");
			request.setCurrencyCode("CNY");
			request.setWarehouseId(18);
			request.setZoneId(3);

			OrderLinkDto orderLinkDto = new OrderLinkDto();
			orderLinkDto.setAddress("Bangkok cone.");
			orderLinkDto.setArea("121212");
			orderLinkDto.setCity("Bangkok");
			orderLinkDto.setCountry("Thailand");
			orderLinkDto.setCustomerId(1232434011L);
			orderLinkDto.setEmail("feerer@sina.cn");
			orderLinkDto.setFirstName("zhang");
			orderLinkDto.setLastName("san");
			orderLinkDto.setProvince("Bangkok");
			orderLinkDto.setTelphone("13682548442");
			orderLinkDto.setZipcode("644007");
			orderLinkDto.setCompanyName("laoganmcommpany");
			orderLinkDto.setCountryCode("IN");

			request.setOrderLinkDto(orderLinkDto);

			List<TransportOrderDetail> list = new ArrayList<>();
			TransportOrderDetail orderDetail = new TransportOrderDetail();
			orderDetail.setSku("1090202");
			orderDetail.setProductTitle("yifu");
			orderDetail.setProductNameCN("测试明细中文");
			orderDetail.setProductNameEN("test english detail");
			orderDetail.setForeignHsCode("1212");
			orderDetail.setInlandHsCode("123");
			orderDetail.setOrderDetailQty(10);
			orderDetail.setPrice(new BigDecimal(12));
			orderDetail.setTotalAmount(new BigDecimal(12 * 10));
			list.add(orderDetail);

			TransportOrderDetail orderDetail1 = new TransportOrderDetail();
			orderDetail1.setSku("1090204");
			orderDetail1.setProductTitle("kuzi");
			orderDetail1.setProductNameCN("测试明细中文11");
			orderDetail1.setProductNameEN("test english detail 11");
			orderDetail1.setForeignHsCode("56988");
			orderDetail1.setInlandHsCode("47562");
			orderDetail1.setOrderDetailQty(10);
			orderDetail1.setPrice(new BigDecimal(5));
			orderDetail1.setTotalAmount(new BigDecimal(50));
			list.add(orderDetail1);

			request.setOrderDetails(list);

			TransportResponse response = transportFacadeService.addTransportOrder(request);
			logger.info(JsonUtils.toJson(response));

	}
}
