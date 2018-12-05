package com.stosz.tms.chain.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.chain.ITransportHandlerChain;
import com.stosz.tms.chain.ITransportHandlerFilter;
import com.stosz.tms.enums.AllowedProductTypeEnum;
import com.stosz.tms.enums.NeedTrackNumEnum;
import com.stosz.tms.model.AssignTransportRequest;
import com.stosz.tms.model.AssignTransportResponse;
import com.stosz.tms.model.ShippingSchedule;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.service.ShippingScheduleService;
import com.stosz.tms.service.ShippingTrackingNoListService;

/**
 * 检查 单次已分配数量如果到达阈值，全部清0
 * @author feiheping
 * @version [1.0,2017年12月21日]
 */
@Component
@Scope("prototype")
public class CheckTransportHandler implements ITransportHandlerFilter {

	private List<ShippingStoreRel> shippingStoreRels;

	@Autowired
	private ShippingTrackingNoListService trackingNoListService;

	@Autowired
	protected ShippingScheduleService scheduleService;

	public CheckTransportHandler() {

	}

	@Override
	public AssignTransportResponse doAssignChain(AssignTransportRequest request, ITransportHandlerChain handlerChain) {
		if (ArrayUtils.isNotEmpty(shippingStoreRels)) {
			List<ShippingStoreRel> usableShippingWays = new ArrayList<>();
			// 缺少运单号的物流方式
			List<ShippingStoreRel> lackTrackShippingWays = new ArrayList<>();
			for (ShippingStoreRel shippingStoreRel : shippingStoreRels) {

				AllowedProductTypeEnum productTypeEnum = shippingStoreRel.getProductType();
				ShippingSchedule schedule = getShippingSchedule(shippingStoreRel);
				ShippingWay shippingWay = shippingStoreRel.getShippingWay();
				// 获取物流商的接口是否是 预先导入运单号
				Integer needTrackNum = shippingWay.getNeedTrackNum();
				if (needTrackNum == NeedTrackNumEnum.NEED.ordinal()) {
					int usableTrackNoCount = trackingNoListService.queryUsableTrackNoCount(shippingStoreRel.getShippingWayId(),
							request.getTransportRequest().getWarehouseId(), productTypeEnum);
					// 没有可用的运单号时，则物流方式不可用
					if (usableTrackNoCount <= 0) {
						shippingStoreRel.setLackTrackNo(true);
						lackTrackShippingWays.add(shippingStoreRel);
						continue;
					}
				}
				if (productTypeEnum == AllowedProductTypeEnum.sensitive) {// 特货
					if (schedule.getTodaySpecialNum() >= schedule.getSpecialCargoNum()) {
						continue;
					}
				} else if (productTypeEnum == AllowedProductTypeEnum.general) {// 普货发货
					if (schedule.getTodayGeneralNum() >= schedule.getGeneralCargoNum()) {
						continue;
					}
				} else if (productTypeEnum == AllowedProductTypeEnum.all) {// 不区分
					if (schedule.getTodayCargoNum() >= schedule.getCargoNum()) {
						continue;
					}
				}
				usableShippingWays.add(shippingStoreRel);
			}
			// 检查可用物流方式，每次分配数量是否已经全部满了
			if (ArrayUtils.isNotEmpty(usableShippingWays)) {
				// 检查单次分配数量是否都已经到达阈值
				boolean fullStatus = usableShippingWays.stream().allMatch(item -> {
					ShippingSchedule schedule = this.getShippingSchedule(item);
					return whetherOnceNumExceed(item, schedule);
				});
				if (fullStatus) {
					for (ShippingStoreRel shippingStoreRel : usableShippingWays) {
						scheduleService.updateOnceScheduleNum(shippingStoreRel);
					}
				}
			} else if (ArrayUtils.isNotEmpty(lackTrackShippingWays)) {// 如果没有可用的物流方式，但是存在因为没有运单号而排除的方式,需要检查单次排程是否已经到达阈值
				for (ShippingStoreRel shippingStoreRel : lackTrackShippingWays) {
					ShippingSchedule schedule = this.getShippingSchedule(shippingStoreRel);
					if (whetherOnceNumExceed(shippingStoreRel, schedule)) {
						scheduleService.updateOnceScheduleNum(shippingStoreRel);
					}
				}
			}
		}
		return handlerChain.doAssignChain(request);
	}

	public List<ShippingStoreRel> getShippingStoreRels() {
		return shippingStoreRels;
	}

	public void setShippingStoreRels(List<ShippingStoreRel> shippingStoreRels) {
		this.shippingStoreRels = shippingStoreRels;
	}
}
