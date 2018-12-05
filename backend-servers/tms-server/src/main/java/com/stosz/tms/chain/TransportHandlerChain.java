package com.stosz.tms.chain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.dto.TransportRequest;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.AssignTransportRequest;
import com.stosz.tms.model.AssignTransportResponse;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.service.transport.AbstractTransportHandler;
import com.stosz.tms.service.transport.TransportHandlerFactory;

public class TransportHandlerChain implements ITransportHandlerChain {

	private Logger logger = LoggerFactory.getLogger(TransportHandlerChain.class);

	private List<ITransportHandlerFilter> handlerFilters;

	public TransportHandlerChain(List<ShippingStoreRel> shippingStoreRels, TransportHandlerFactory handlerFactory) {
		if (ArrayUtils.isNotEmpty(shippingStoreRels)) {
			for (ShippingStoreRel shippingStoreRel : shippingStoreRels) {
				ShippingWay shippingWay = shippingStoreRel.getShippingWay();
				String shippingCode = shippingWay.getShippingCode();
				AbstractTransportHandler transportHandler = null;
				if (HandlerTypeEnum.assignHandlerCodeSet.contains(shippingCode)) {
					transportHandler = handlerFactory.getNewHandler(HandlerTypeEnum.ASSIGN.code());
				} else {
					transportHandler = handlerFactory.getNewHandler(shippingCode);
				}
				if (transportHandler != null) {
					transportHandler.setShippingWay(shippingWay);
					transportHandler.setShippingStoreRel(shippingStoreRel);
					this.addFilter(transportHandler);
				} else {
					logger.info("TransportHandlerChain shippingCode={},handlerFactory 处理handler 不存在");
				}
			}
		}
	}

	private int index;

	@Override
	public AssignTransportResponse doAssignChain(AssignTransportRequest request) {
		if (index >= handlerFilters.size()) {
			AssignTransportResponse failResponse = new AssignTransportResponse();
			failResponse.setCode(AssignTransportResponse.FAIL);
			TransportRequest transportRequest = request.getTransportRequest();
			failResponse.setErrorMsg(String.format("orderId=%s,所有物流配额已满,请检查", transportRequest.getOrderId()));
			return failResponse;
		}
		ITransportHandlerFilter chain = handlerFilters.get(index);
		index++;
		return chain.doAssignChain(request, this);
	}

	public TransportHandlerChain addFilter(ITransportHandlerFilter handlerFilter) {
		if (handlerFilters == null) {
			handlerFilters = new ArrayList<>();
		}
		handlerFilters.add(handlerFilter);
		return this;
	}

	public TransportHandlerChain addFirstFilter(ITransportHandlerFilter handlerFilter) {
		if (handlerFilters == null) {
			handlerFilters = new ArrayList<>();
		}
		handlerFilters.add(0, handlerFilter);
		return this;
	}

	@Override
	public boolean isChainComplete() {
		return index >= handlerFilters.size();
	}
}
