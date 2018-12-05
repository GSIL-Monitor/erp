package com.stosz.tms.service.transport.impl;

import org.springframework.stereotype.Component;

import com.stosz.plat.common.ResultBean;
import com.stosz.tms.chain.ITransportHandlerChain;
import com.stosz.tms.dto.TransportRequest;
import com.stosz.tms.enums.AllowedProductTypeEnum;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.AssignTransportRequest;
import com.stosz.tms.model.AssignTransportResponse;
import com.stosz.tms.model.ShippingAllocationTemplate;
import com.stosz.tms.model.ShippingSchedule;
import com.stosz.tms.service.transport.AbstractTransportHandler;

/**
 * 特殊处理Handler 不受排程数量限制,按特殊规则配送
 * @author feiheping
 * @version [1.0,2017年12月26日]
 */
@Component
public class SpecialTransportHandler extends AbstractTransportHandler {

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.SPECIAL;
	}

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
		AssignTransportResponse transportResponse = new AssignTransportResponse();
		int assignCount = scheduleService.updateScheduleNumNotLimit(schedule, productTypeEnum, 1);
		if (assignCount > 0) {// 指派物流成功
			ResultBean<AssignTransportResponse> resultBean = assignAfterProcess(request, schedule, transportRequest);
			if (ResultBean.FAIL.equals(resultBean.getCode())) {
				scheduleService.updateScheduleNum(schedule, productTypeEnum, -1);
				transportResponse.fail(resultBean.getDesc());
			} else {
				transportResponse = resultBean.getItem();
			}
		} else {
			transportResponse.fail("修改日程数量失败");
		}
		return transportResponse;
	}
}
