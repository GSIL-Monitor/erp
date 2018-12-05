package com.stosz.tms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.stosz.order.ext.dto.TransportRequest;
import com.stosz.order.ext.dto.TransportResponse;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.common.ResultBean;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.tms.cache.PlaceOrderCache;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.OrderTypeEnum;
import com.stosz.tms.enums.ParcelStateEnum;
import com.stosz.tms.enums.SyncStatusEnum;
import com.stosz.tms.model.Shipping;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.ShippingParcelDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.service.transport.AbstractTransportHandler;
import com.stosz.tms.service.transport.TransportHandlerFactory;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PlaceOrderService implements IRecursiveProcessor<ShippingParcel, ShippingParcel> {

	@Autowired
	private TransportHandlerFactory transportHandlerFactory;

	@Autowired
	private ShippingParcelService shippingParcelService;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private PlaceOrderCache placeOrderCache;

	@Autowired
	private IStorehouseService storehouseService;

	@Autowired
	private ShippingService shippingService;

	@Autowired
	private ShippingParcelDetailService parcelDetailService;

	@Autowired
	private IZoneService zoneService;

	@Autowired
	private ExpressSheetService expressSheetService;

	private Logger logger = LoggerFactory.getLogger(PlaceOrderService.class);

	@Override
	public ShippingParcel recursiveHandle(ShippingParcel instance, Parameter<String, Object> params) {
		ResultBean<TransportOrderResponse> resultBean = this.placeOrderProcessorTask(instance, params);
		return updateParcelAndNotifyOrder(instance, resultBean);
	}

	/**
	 * 异步推送订单到物流商
	 */
	@Async
	public void asyncPlaceOrderHandle(ShippingParcel instance) {
		try {
			List<ShippingParcelDetail> parcelDetails = parcelDetailService.queryParcelDetail(instance.getId());
			instance.setParcelDetails(parcelDetails);
			placeOrderCache.addAsyncOrder(instance.getId());
			Wms wms = storehouseService.wmsTransferRequest(instance.getWarehouseId());
			Shipping shipping = shippingService.queryShippingByWayId(instance.getShippingWayId());
			ResultBean<TransportOrderResponse> resultBean = this.placeOrderProcessor(instance, shipping, wms);
			updateParcelAndNotifyOrder(instance, resultBean);
		} catch (Exception e) {
			logger.error("asyncPlaceOrderHandle() Exception={}", e);
		} finally {
			placeOrderCache.remove(instance.getId());
		}
	}

	/**
	 * 定时任务抛送订单
	 * @param shippingParcel
	 * @param params
	 * @return
	 */
	private ResultBean<TransportOrderResponse> placeOrderProcessorTask(ShippingParcel shippingParcel, Parameter<String, Object> params) {
		ResultBean<TransportOrderResponse> resultBean = new ResultBean<>();
		Integer shippingWayId = shippingParcel.getShippingWayId();
		Map<Integer, Shipping> shippingMap = params.getObject("shippingWayMap");
		Map<Integer, Wms> warehouseMap = params.getObject("warehouseMap");
		Shipping shipping = shippingMap.get(shippingWayId);
		if (shipping == null) {
			logger.info("recursiveHandle() 物流方式Id={},对应的物流商不存在", shippingWayId);
			return resultBean.fail(String.format("物流方式ID=%s,对应的物流商不存在", shippingWayId));
		}
		// 获取到仓库信息
		Wms wms = warehouseMap.get(shippingParcel.getWarehouseId());
		return this.placeOrderProcessor(shippingParcel, shipping, wms);
	}

	/**
	 * 保存同步信息，如果推送订单成功，通知OMS
	 * @param instance
	 * @param resultBean
	 * @return
	 */
	private ShippingParcel updateParcelAndNotifyOrder(ShippingParcel instance, ResultBean<TransportOrderResponse> resultBean) {
		ShippingParcel updateParcel = new ShippingParcel();
		if (ResultBean.OK.equals(resultBean.getCode())) {
			TransportOrderResponse orderResponse = resultBean.getItem();
			updateParcel.setSyncStatus(SyncStatusEnum.SYNC_SUCCESS.ordinal());
			updateParcel.setSyncInfo("");
			updateParcel.setSyncTime(new Date());
			updateParcel.setLastSyncTime(new Date());
			updateParcel.setTrackNo(orderResponse.getTrackingNo());
			updateParcel.setResponseData(orderResponse.getResponseData());

			instance.setTrackNo(orderResponse.getTrackingNo());
			instance.setResponseData(orderResponse.getResponseData());

			ResultBean<HashMap<String, Object>> expressResultBean = invokeExpressAndNotifyOrder(instance);
			if (ResultBean.OK.equals(expressResultBean.getCode())) {
				updateParcel.setParcelState(ParcelStateEnum.PUSHORDER_SUCCSS.getType());
				updateParcel.setAssignErrorMsg("");
			} else {
				updateParcel.setParcelState(ParcelStateEnum.PUSHORDER_FAIL.getType());
				updateParcel.setAssignErrorMsg(expressResultBean.getDesc());
			}
		} else {
			// 抛送单据失败
			updateParcel.setSyncStatus(SyncStatusEnum.SYNC_FAIL.ordinal());
			updateParcel.setSyncInfo(resultBean.getDesc());
		}
		updateParcel.setId(instance.getId());
		shippingParcelService.updateSelective(updateParcel);
		return instance;
	}

	/**
	 * 获取物流面单信息，并通知OMS
	 * @param instance
	 * @param transportOrderResponse
	 * @param updateParcel
	 */
	public ResultBean<HashMap<String, Object>> invokeExpressAndNotifyOrder(ShippingParcel instance) {
		/**异步抛单，整理打印面单需要的参数**/
		ResultBean<HashMap<String, Object>> expressResultBean = expressSheetService.assignAsyncExpressSheet(instance);
		if (ResultBean.OK.equals(expressResultBean.getCode())) {// 获取面单信息成功
			// 通知订单，回写物流运单号
			HashMap<String, Object> expressDataMap = expressResultBean.getItem();
			TransportRequest callRequest = new TransportRequest();
			callRequest.setCode(TransportRequest.SUCCESS);
			callRequest.setOrderId(instance.getOrderId());
			callRequest.setShippingCode(instance.getShippingAliasCode());
			callRequest.setShippingName(instance.getShippingAliasName());
			callRequest.setTrackingNo(instance.getTrackNo());
			callRequest.setSheetDataMap(expressDataMap);

			TransportResponse orderResponse = null;
			try {
				orderResponse = orderService.updateOrderLogisticsInfo(callRequest);
			} catch (Exception e) {
				logger.info("updateParcelAdnNotifyOrder 通知订单回写物流信息失败,parcelId,orderId={},Exception={}", instance.getId(), instance.getOrderId(), e);
				orderResponse = new TransportResponse().fail("通知订单回写物流信息失败");
			}
			if (TransportResponse.SUCCESS.equals(orderResponse.getCode())) {
				// 通知订单成功
				expressResultBean.setCode(ResultBean.OK);
				expressResultBean.setDesc("通知订单成功");
			} else {
				// 通知订单失败
				expressResultBean.setCode(ResultBean.FAIL);
				expressResultBean.setDesc(orderResponse.getErrorMsg());
				logger.info("invokeExpressAndNotifyOrder() 通知订单，回写物流信息失败,ErrorMsg={}", orderResponse.getErrorMsg());
			}
		} else {
			expressResultBean.setCode(ResultBean.FAIL);
			expressResultBean.setDesc(String.format("面单信息缺失:%s", expressResultBean.getDesc()));
		}
		return expressResultBean;
	}

	/**
	 * 抛送运单到物流商
	 * @param shippingParcel 包裹
	 * @param
	 * @return
	 */
	private ResultBean<TransportOrderResponse> placeOrderProcessor(ShippingParcel shippingParcel, Shipping shipping, Wms wms) {
		ResultBean<TransportOrderResponse> resultBean = new ResultBean<>();
		resultBean.setCode(ResultBean.FAIL);
		try {
			Integer shippingWayId = shippingParcel.getShippingWayId();
			ShippingExtend shippingExtend = getShippingExtendInfo(shippingParcel, wms, shipping);

			AbstractTransportHandler abstractTransportHandler = transportHandlerFactory.getHandler(shipping.getShippingCode());
			if (abstractTransportHandler == null || !(abstractTransportHandler instanceof AbstractPlaceOrderTransportHandler)) {
				logger.info("recursiveHandle() 物流方式Id={},物流商Code={},未找到对应的handler", shippingWayId, shipping.getShippingCode());
				return resultBean.fail(String.format("物流方式Id=%s,物流商Code=%s,不支持线上下单", shippingWayId, shipping.getShippingCode()));
			}

			AbstractPlaceOrderTransportHandler transportHandler = (AbstractPlaceOrderTransportHandler) abstractTransportHandler;
			TransportOrderRequest orderRequest = transferOrderRequest(shippingParcel);
			// 校验参数是否符合接口要求
			ResultBean<Boolean> validateResultBean = transportHandler.validateParam(orderRequest, shippingExtend);
			if (!ResultBean.OK.equals(validateResultBean.getCode())) {
				logger.info("recursiveHandle() 物流推单失败,参数校验失败,orderNo={},parcelId={},errorMsg={}", orderRequest.getOrderNo(), shippingParcel.getId(),
						validateResultBean.getDesc());
				return resultBean.fail(validateResultBean.getDesc());
			}
			// 推送订单信息
			TransportOrderResponse orderResponse = transportHandler.transportPlaceOrder(orderRequest, shippingExtend);
			if (TransportOrderResponse.SUCCESS.equals(orderResponse.getCode())) {

				orderResponse.setShippingCode(shipping.getShippingCode());
				String trackNo = orderResponse.getTrackingNo();
				shippingParcel.setTrackNo(trackNo);
				resultBean.setCode(ResultBean.OK);
				resultBean.setItem(orderResponse);
			} else {
				return resultBean.fail(orderResponse.getErrorMsg());
			}
		} catch (Exception e) {
			logger.error("placeOrderProcessor() orderId={},parcelId={},Exception={}", shippingParcel.getOrderId(), shippingParcel.getId(), e);
		}
		return resultBean;
	}

	/**
	 * 获取物流商接口地址
	 * @param shippingParcel
	 * @param
	 * @param shipping
	 * @return
	 */
	private ShippingExtend getShippingExtendInfo(ShippingParcel shippingParcel, Wms wms, Shipping shipping) {
		ShippingExtend shippingExtend = new ShippingExtend();
		if (wms != null) {
			// TODO 根据仓库获取区域id进而获取发件人国家二字码，set到shippingExtend中
			String countryCode = zoneService.getCountryCodeById(wms.getZoneId());
			shippingExtend.setSenderCountryCode(countryCode == null ? "CN" : countryCode);

			shippingExtend.setSender(wms.getSender());
			shippingExtend.setSenderContactName(wms.getLinkman());
			shippingExtend.setSenderEmail(wms.getSenderEmail());
			shippingExtend.setSenderTelphone(wms.getMobile());
			shippingExtend.setSenderProvince(wms.getSenderProvince());
			shippingExtend.setSenderCity(wms.getSenderCity());
			shippingExtend.setSenderTown(wms.getSenderTown());
			shippingExtend.setSenderAddress(wms.getAddress());
			shippingExtend.setSenderZipcode(wms.getSenderZipcode());
			shippingExtend.setSenderCountry(wms.getSenderCountry());
		}
		shippingExtend.setCode(shipping.getShippingCode());
		shippingExtend.setInterfaceUrl(shipping.getInterfaceUrl());
		shippingExtend.setWaybilltrackUrl(shipping.getWaybilltrackUrl());
		shippingExtend.setAccount(shipping.getAccount());
		shippingExtend.setMd5Key(shipping.getMd5Key());
		shippingExtend.setToken(shipping.getToken());
		return shippingExtend;
	}

	/**
	 * 构建请求物流接口参数
	 * @param shippingParcel
	 * @return
	 */
	private TransportOrderRequest transferOrderRequest(ShippingParcel shippingParcel) {
		TransportOrderRequest request = new TransportOrderRequest();
		request.setGoodsQty(shippingParcel.getGoodsQty());
		request.setCurrencyCode(shippingParcel.getCurrencyCode());
		request.setOrderAmount(shippingParcel.getOrderAmount());
		request.setOrderDate(shippingParcel.getOrderDate());
		request.setOrderNo(shippingParcel.getOrderNo());
		request.setPayState(shippingParcel.getPayState());
		request.setRemark(shippingParcel.getOrderRemark());
		request.setOrderTypeEnum(OrderTypeEnum.fromId(shippingParcel.getOrderType()));
		request.setPayState(shippingParcel.getPayState());
		request.setWeight(shippingParcel.getWeight().doubleValue());
		request.setTrackNo(shippingParcel.getTrackNo());
		request.setAreaId(shippingParcel.getZoneId());

		OrderLinkDto orderLinkDto = new OrderLinkDto();
		orderLinkDto.setAddress(shippingParcel.getAddress());
		orderLinkDto.setArea(shippingParcel.getArea());
		orderLinkDto.setCity(shippingParcel.getCity());
		orderLinkDto.setCountry(shippingParcel.getCountry());
		orderLinkDto.setCountryCode(shippingParcel.getCountryCode());
		orderLinkDto.setCustomerId(shippingParcel.getCustomerId());
		orderLinkDto.setEmail(shippingParcel.getEmail());
		orderLinkDto.setFirstName(shippingParcel.getFirstName());
		orderLinkDto.setLastName(shippingParcel.getLastName());
		orderLinkDto.setProvince(shippingParcel.getProvince());
		orderLinkDto.setTelphone(shippingParcel.getTelphone());
		orderLinkDto.setZipcode(shippingParcel.getZipcode());
		orderLinkDto.setCompanyName(shippingParcel.getFirstName() + shippingParcel.getLastName());
		request.setOrderLinkDto(orderLinkDto);

		List<TransportOrderDetail> orderDetails = shippingParcel.getParcelDetails().stream().map(item -> {
			TransportOrderDetail orderDetail = new TransportOrderDetail();
			orderDetail.setForeignHsCode(item.getForeignhscode());
			orderDetail.setInlandHsCode(item.getInlandhscode());
			orderDetail.setOrderDetailQty(item.getOrderDetailQty());
			orderDetail.setOrderItemId(item.getOrderItemId());
			orderDetail.setPrice(item.getPrice());
			orderDetail.setProductNameEN(item.getProductTitleEn());
			orderDetail.setProductNameCN(item.getProductTitle());
			orderDetail.setProductTitle(item.getProductTitle());
			orderDetail.setSku(item.getSku());
			orderDetail.setTotalAmount(item.getTotalAmount());
			orderDetail.setTotalWeight(item.getTotalAmount());
			orderDetail.setUnit(item.getUnit());
			orderDetail.setWeight(item.getWeight());
			return orderDetail;
		}).collect(Collectors.toList());
		request.setOrderDetails(orderDetails);
		return request;
	}

}
