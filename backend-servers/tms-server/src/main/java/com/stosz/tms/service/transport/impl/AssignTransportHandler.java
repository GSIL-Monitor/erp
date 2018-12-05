package com.stosz.tms.service.transport.impl;

import org.springframework.stereotype.Component;

import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.service.transport.AbstractTransportHandler;

/**
 * 无须抛送运单的handler,只需要指派物流
 * @author feiheping
 * @version [1.0,2017年12月26日]
 */
@Component
public class AssignTransportHandler extends AbstractTransportHandler {

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.ASSIGN;
	}
}
