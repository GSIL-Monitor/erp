package com.stosz.tms.service.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.stosz.plat.common.ResultBean;
import com.stosz.tms.chain.ITransportHandlerChain;
import com.stosz.tms.chain.ITransportHandlerFilter;
import com.stosz.tms.dto.TransportRequest;
import com.stosz.tms.enums.AllowedProductTypeEnum;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.enums.NeedTrackNumEnum;
import com.stosz.tms.model.AssignTransportRequest;
import com.stosz.tms.model.AssignTransportResponse;
import com.stosz.tms.model.ShippingAllocationTemplate;
import com.stosz.tms.model.ShippingSchedule;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingTrackingNoList;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.service.ShippingLogService;
import com.stosz.tms.service.ShippingScheduleService;
import com.stosz.tms.service.ShippingTrackingNoListService;

@Service
public abstract class AbstractTransportHandler implements ITransportHandlerFilter {

	protected Logger logger = LoggerFactory.getLogger(AbstractTransportHandler.class);

	@Autowired
	protected ShippingLogService logService;

	@Autowired
	protected ShippingScheduleService scheduleService;

	@Autowired
	protected ShippingTrackingNoListService trackingNoListService;

	protected ShippingStoreRel shippingStoreRel;// 物流仓库方式

	protected ShippingWay shippingWay;

	public ShippingStoreRel getShippingStoreRel() {
		return shippingStoreRel;
	}

	public void setShippingStoreRel(ShippingStoreRel shippingStoreRel) {
		this.shippingStoreRel = shippingStoreRel;
	}

	public ShippingWay getShippingWay() {
		return shippingWay;
	}

	public void setShippingWay(ShippingWay shippingWay) {
		this.shippingWay = shippingWay;
	}

	/**
	 * 获取到Handler的类型
	 * @return
	 */
	public abstract HandlerTypeEnum getCode();

	/**
	 * 指派物流公司
	 * @param request 请求对象
	 * @param handlerChain 处理的链式对象
	 * @return
	 */
	@Override
	public AssignTransportResponse doAssignChain(AssignTransportRequest request, ITransportHandlerChain handlerChain) {
		int matchWay = shippingStoreRel.getMatchWay();
		ShippingSchedule schedule = shippingStoreRel.getSchedule();
		TransportRequest transportRequest = request.getTransportRequest();
		AllowedProductTypeEnum productTypeEnum = shippingStoreRel.getProductType();
		if (matchWay == 1 || matchWay == 3) {// 根据排程模板
			ShippingAllocationTemplate template = shippingStoreRel.getTemplate();
			// 创建今日排程
			schedule = scheduleService.createNowSchedule(template);
		}
		int assignCount = 0;// 分配成功的标志
		if (!shippingStoreRel.isLackTrackNo() || shippingWay.getNeedTrackNum().equals(NeedTrackNumEnum.NOTNEED.ordinal())) {// 不缺少运单号||不需要预先导入运单号
			if (productTypeEnum == AllowedProductTypeEnum.sensitive) {// 特货
				if (schedule.getTodaySpecialNum() < schedule.getSpecialCargoNum() && schedule.getOnceSpecialNum() < schedule.getEachSpecialNum()) {
					assignCount = scheduleService.updateScheduleNum(schedule, productTypeEnum, 1);
				}
			} else if (productTypeEnum == AllowedProductTypeEnum.general) {// 普货发货
				if (schedule.getTodayGeneralNum() < schedule.getGeneralCargoNum() && schedule.getOnceGeneralNum() < schedule.getEachGeneralNum()) {
					assignCount = scheduleService.updateScheduleNum(schedule, productTypeEnum, 1);
				}
			} else if (productTypeEnum == AllowedProductTypeEnum.all) {// 不区分
				if (schedule.getTodayCargoNum() < schedule.getCargoNum() && schedule.getOnceCargoNum() < schedule.getEachCargoNum()) {
					assignCount = scheduleService.updateScheduleNum(schedule, productTypeEnum, 1);
				}
			}
		}
		if (assignCount > 0) {// 指派物流成功
			ResultBean<AssignTransportResponse> resultBean = assignAfterProcess(request, schedule, transportRequest);
			if (ResultBean.OK.equals(resultBean.getCode())) {// 分配运单号成功
				return resultBean.getItem();
			}
			scheduleService.updateScheduleNum(schedule, productTypeEnum, -1);
			if (handlerChain.isChainComplete()) {
				AssignTransportResponse transportResponse = new AssignTransportResponse();
				transportResponse.setCode(AssignTransportResponse.FAIL);
				transportResponse.setErrorMsg(resultBean.getDesc());
				return transportResponse;
			}
		} else {
			if (shippingStoreRel.isLackTrackNo() && handlerChain.isChainComplete()) {
				AssignTransportResponse transportResponse = new AssignTransportResponse();
				transportResponse.setCode(AssignTransportResponse.FAIL);
				transportResponse
						.setErrorMsg(String.format("物流方式:%s,仓库ID:%s,,缺少物流运单号", shippingWay.getShippingWayName(), transportRequest.getWarehouseId()));
				return transportResponse;
			}
		}
		return handlerChain.doAssignChain(request);

	}

	/**
	 * 物流分配成功后,占用运单号
	 * @param request
	 * @param schedule
	 * @param transportRequest
	 * @return
	 */
	public ResultBean<AssignTransportResponse> assignAfterProcess(AssignTransportRequest request, ShippingSchedule schedule,
			TransportRequest transportRequest) {
		ResultBean<AssignTransportResponse> resultBean = new ResultBean<>();
		try {
			AllowedProductTypeEnum productType = shippingStoreRel.getProductType();
			// 预先导入的运单号，需要占用一个物流单号
			String trackingNumber = null;
			if (shippingWay.getNeedTrackNum().equals(NeedTrackNumEnum.NEED.ordinal())) {
				ResultBean<ShippingTrackingNoList> trackResultBean = trackingNoListService.occupyUsableTrackNo(shippingStoreRel.getShippingWayId(),
						transportRequest.getWarehouseId(), productType);
				if (ResultBean.FAIL.equals(trackResultBean.getCode())) {// 物流运单号占用失败
					resultBean.setCode(ResultBean.FAIL);
					resultBean.setDesc("物流运单号占用失败");
					return resultBean;
				} else {
					ShippingTrackingNoList trackingNoList = trackResultBean.getItem();
					trackingNumber = trackingNoList.getTrackNumber();
				}
			}
			AssignTransportResponse response = new AssignTransportResponse();
			response.setScheduleId(schedule.getId());// 排程ID
			response.setShippingWayId(shippingWay.getId());// 物流方式ID
			response.setTrackingNo(trackingNumber);// 运单号
			response.setLogisticsCode(shippingWay.getShippingCode());
			response.setProductType(productType);//// 普货，特货
			Integer needSendOrder = shippingWay.getNeedSendOrder();// 是否需要抛单给物流商 0 不需要 1 需要
			if (productType == AllowedProductTypeEnum.sensitive) {// 如果是特货
				response.setShippingName(shippingStoreRel.getShippingSpeciaName());
				response.setShippingCode(shippingStoreRel.getShippingSpeciaCode());
			} else if (productType == AllowedProductTypeEnum.all || productType == AllowedProductTypeEnum.general) {
				response.setShippingName(shippingStoreRel.getShippingGeneralName());
				response.setShippingCode(shippingStoreRel.getShippingGeneralCode());
			}
			if (needSendOrder.equals(0)) {// 如果不用抛送运单信息给物流商
				response.setCode(AssignTransportResponse.SUCCESS);
			} else {
				response.setCode(AssignTransportResponse.SUCCESS_AFTER_NOTIFY);
			}
			resultBean.setItem(response);
			resultBean.setCode(ResultBean.OK);
		} catch (Exception e) {
			resultBean.fail("占用运单号异常");
			logger.error("assignAfterProcess() orderId={},orderNo={}  Exception={}", transportRequest.getOrderId(), transportRequest.getOrderNo(), e);
		}
		return resultBean;
	}
}
