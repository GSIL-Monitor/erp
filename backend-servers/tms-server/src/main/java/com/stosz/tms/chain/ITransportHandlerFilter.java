package com.stosz.tms.chain;

import com.stosz.tms.enums.AllowedProductTypeEnum;
import com.stosz.tms.model.AssignTransportRequest;
import com.stosz.tms.model.AssignTransportResponse;
import com.stosz.tms.model.ShippingAllocationTemplate;
import com.stosz.tms.model.ShippingSchedule;
import com.stosz.tms.model.ShippingStoreRel;

public interface ITransportHandlerFilter {

	/**
	 * 指派物流公司
	 * @param request 请求对象
	 * @param handlerChain 处理的链式对象
	 * @return
	 */
	public AssignTransportResponse doAssignChain(AssignTransportRequest request, ITransportHandlerChain handlerChain);

	/**
	 * 检查单次的数量是否已经到达阈值
	 * @param item
	 * @param schedule
	 * @return
	 */
	public default boolean whetherOnceNumExceed(ShippingStoreRel item, ShippingSchedule schedule) {
		if (item.getProductType() == AllowedProductTypeEnum.sensitive) {
			if (schedule.getOnceSpecialNum() >= schedule.getEachSpecialNum()) {
				return true;
			}
		} else if (item.getProductType() == AllowedProductTypeEnum.general) {
			if (schedule.getOnceGeneralNum() >= schedule.getEachGeneralNum()) {
				return true;
			}
		} else if (item.getProductType() == AllowedProductTypeEnum.all) {
			if (schedule.getOnceCargoNum() >= schedule.getEachCargoNum()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取到排程
	 * @param shippingStoreRel
	 * @return
	 */
	public default ShippingSchedule getShippingSchedule(ShippingStoreRel shippingStoreRel) {
		if (shippingStoreRel.getMatchWay() == 0 || shippingStoreRel.getMatchWay() == 2) {// 如果是匹配上的排程模板
			return shippingStoreRel.getSchedule();
		}
		ShippingAllocationTemplate template = shippingStoreRel.getTemplate();
		ShippingSchedule shippingSchedule = new ShippingSchedule();
		shippingSchedule.setShippingWayId(shippingStoreRel.getId());
		shippingSchedule.setWmsId(template.getWmsId());
		shippingSchedule.setGeneralCargoNum(template.getGeneralCargoNum());
		shippingSchedule.setSpecialCargoNum(template.getSpecialCargoNum());
		shippingSchedule.setCargoNum(template.getCargoNum());
		shippingSchedule.setTodayGeneralNum(0);
		shippingSchedule.setTodaySpecialNum(0);
		shippingSchedule.setTodayCargoNum(0);
		shippingSchedule.setEachGeneralNum(template.getEachGeneralNum());
		shippingSchedule.setEachSpecialNum(template.getEachSpecialNum());
		shippingSchedule.setEachCargoNum(template.getEachCargoNum());
		shippingSchedule.setOnceSpecialNum(0);
		shippingSchedule.setOnceGeneralNum(0);
		shippingSchedule.setOnceCargoNum(0);
		shippingSchedule.setSorted(template.getSorted());
		shippingSchedule.setVersion(0);
		return shippingSchedule;
	}
}
