package com.stosz.tms.service;

import com.stosz.order.ext.dto.TransportDTO;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.tms.enums.ClassifyEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.stosz.tms.model.TrackingTask;

import javax.annotation.Resource;

@Service
public class ShippingNotifyService {

	private Logger logger = LoggerFactory.getLogger(ShippingNotifyService.class);

	@Resource
	private RabbitMQPublisher mqPublisher;

	@Async
	public void notifyTrackChange(TrackingTask trackingTask) {
		try {
			TransportDTO transportDTO = new TransportDTO();
			transportDTO.setOrderId(trackingTask.getOrderId());
			transportDTO.setTrackingNo(trackingTask.getTrackNo());
			transportDTO.setShippingWayId(trackingTask.getShippingWayId());

			final String classifyCode = trackingTask.getClassifyCode();

			final ClassifyEnum classifyEnum = ClassifyEnum.getEnum(classifyCode);

			transportDTO.setClassifyEnum(classifyEnum);
			transportDTO.setClassifyStatusTime(trackingTask.getClassifyStatusTime());

			if (classifyEnum == ClassifyEnum.RECEIVE) {
				transportDTO.setOrderEventEnum(OrderEventEnum.matchSign);
			} else if (classifyEnum == ClassifyEnum.REJECTION) {
				transportDTO.setOrderEventEnum(OrderEventEnum.matchNotSign);
			}
			RabbitMQMessage rabbitMQMessage = new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setMessageType(TransportDTO.MESSAGE_TYPE)
					.setData(transportDTO);

			mqPublisher.saveMessage(rabbitMQMessage);
		} catch (Exception e) {
			logger.error("notifyTrackChange Exception={}", e);
		}

	}
}
