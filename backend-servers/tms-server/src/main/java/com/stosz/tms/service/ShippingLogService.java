package com.stosz.tms.service;

import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.mapper.ShippingLogMapper;
import com.stosz.tms.model.api.ShippingLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShippingLogService {

	private Logger logger = LoggerFactory.getLogger(ShippingLogService.class);

	@Autowired
	private ShippingLogMapper shippingLogMapper;

	@Async
	public void addLog(HandlerTypeEnum handlerType, String orderNo, String intefaceUrl, String requestBody, String responseBody) {
		try {
			ShippingLog log = new ShippingLog();
			log.setOrderNo(orderNo);
			log.setCode(handlerType.code());
			log.setType(1);
			log.setInterfaceUrl(intefaceUrl);
			log.setRequestBody(requestBody);
			log.setResponseBody(responseBody);
			log.setExecuteTime(new Date());
			shippingLogMapper.addLog(log);
		} catch (Exception e) {
			logger.error("addLog() orderNo={},handlerType={},interfaceUrl={},requestBody={},responseBody={} Exception={} ", orderNo, handlerType,
					intefaceUrl, requestBody, responseBody, e);
		}
	}
}
