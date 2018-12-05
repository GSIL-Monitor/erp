package com.stosz.olderp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stosz.olderp.mapper.OrderShippingMapper;
import com.stosz.olderp.model.ErpOrderShipping;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.mapper.TrackingTaskMapper;
import com.stosz.tms.model.TrackingTask;

@Service
public class OrderShippingService {

	private Logger logger = LoggerFactory.getLogger(OrderShippingService.class);
	private static int batchSize = 4000;

	@Autowired
	private OrderShippingMapper orderShippingMapper;

	@Autowired
	private TrackingTaskMapper trackingTaskMapper;

	public void syncOrderShipping(HashMap<HashSet<Integer>, TrackApiTypeEnum> shippingRelMap) {
		try {
			// 同步的最大记录ID
			Integer shippingMaxId = trackingTaskMapper.getMaxOrderShippingId();
			shippingMaxId = shippingMaxId == null ? 0 : shippingMaxId;
			int rowCount = orderShippingMapper.getCount(shippingMaxId);
			int pageCount = (int) Math.ceil(rowCount / (double) batchSize);
			logger.info("syncOrderShipping() maxOrderShippingId={},rowCount={}", shippingMaxId, rowCount);
			for (int i = 0; i < pageCount; i++) {
				List<TrackingTask> trackingTasks = new ArrayList<>();
				List<ErpOrderShipping> orderShippings = orderShippingMapper.getList(shippingMaxId, i * batchSize, batchSize);
				for (ErpOrderShipping erpOrderShipping : orderShippings) {
					TrackingTask trackingTask = new TrackingTask();
					trackingTask.setApiCode(getHandlerType(shippingRelMap, erpOrderShipping.getIdShipping()));
					trackingTask.setTrackNo(erpOrderShipping.getTrackNumber());
					trackingTask.setFetchCount(0);
					trackingTask.setComplete(0);
					trackingTask.setCreateAt(erpOrderShipping.getCreatedAt());
					trackingTask.setIdOrderShipping(erpOrderShipping.getIdOrderShipping());
					trackingTask.setOldIdShipping(erpOrderShipping.getIdShipping());
					trackingTasks.add(trackingTask);
				}
				if (ArrayUtils.isNotEmpty(trackingTasks)) {
					trackingTaskMapper.addList(trackingTasks);
				}
			}
			logger.info("syncOrderShipping maxOrderShippingId={},rowCount={} 物流订单数据同步完成", shippingMaxId, rowCount);
		} catch (Exception e) {
			logger.error("syncOrderShipping() Exception={}", e);
		}
	}

	public String getHandlerType(HashMap<HashSet<Integer>, TrackApiTypeEnum> shippingRelMap, Integer idShipping) {
		String handlerCode = null;
		if (idShipping != null) {
			for (HashSet<Integer> key : shippingRelMap.keySet()) {
				if (key.contains(idShipping)) {
					handlerCode = shippingRelMap.get(key).code();
				}
			}
		}
		return handlerCode;
	}
}
