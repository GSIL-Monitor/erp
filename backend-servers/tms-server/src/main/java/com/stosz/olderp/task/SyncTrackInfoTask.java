package com.stosz.olderp.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.stosz.olderp.service.OrderShippingService;
import com.stosz.tms.enums.TrackApiTypeEnum;

@Component
public class SyncTrackInfoTask implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(SyncTrackInfoTask.class);

	Map<TrackApiTypeEnum, HashSet<Integer>> shippingRelMap;

	@Autowired
	private OrderShippingService orderShippingService;

	private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

	@Override
	public void afterPropertiesSet() throws Exception {
		shippingRelMap = new HashMap<TrackApiTypeEnum, HashSet<Integer>>();
		shippingRelMap.put(TrackApiTypeEnum.ARAMEX, Sets.newHashSet(174, 176));
		shippingRelMap.put(TrackApiTypeEnum.BLACKCAT, Sets.newHashSet(17, 21, 22, 24, 25, 26, 27, 28, 43, 74));
		shippingRelMap.put(TrackApiTypeEnum.GHT, Sets.newHashSet(114, 158, 160));
		shippingRelMap.put(TrackApiTypeEnum.CXC, Sets.newHashSet(65));
		shippingRelMap.put(TrackApiTypeEnum.FEIFAN, Sets.newHashSet(104));
		shippingRelMap.put(TrackApiTypeEnum.GDEX, Sets.newHashSet(82, 186, 188, 210, 212));
		shippingRelMap.put(TrackApiTypeEnum.HKCOE, Sets.newHashSet(98));
		shippingRelMap.put(TrackApiTypeEnum.HKYSP, Sets.newHashSet(170));
		shippingRelMap.put(TrackApiTypeEnum.INDONESIAJT, Sets.newHashSet(208, 214, 216));
		shippingRelMap.put(TrackApiTypeEnum.INDONESIA, Sets.newHashSet(108, 150, 152));
		shippingRelMap.put(TrackApiTypeEnum.TIMES, Sets.newHashSet(70, 142, 144));
		shippingRelMap.put(TrackApiTypeEnum.XINZHU, Sets.newHashSet(29, 76));
		shippingRelMap.put(TrackApiTypeEnum.YUFENG, Sets.newHashSet(51));
		shippingRelMap.put(TrackApiTypeEnum.THAILANDBJT, Sets.newHashSet(84, 254));
		shippingRelMap.put(TrackApiTypeEnum.SHUNFENG, Sets.newHashSet(23, 226));
		shippingRelMap.put(TrackApiTypeEnum.RUSSIA, Sets.newHashSet(198, 200));
		shippingRelMap.put(TrackApiTypeEnum.PHILIPPINEXH, Sets.newHashSet(110, 138, 140));
		shippingRelMap.put(TrackApiTypeEnum.NIM, Sets.newHashSet(196));
		shippingRelMap.put(TrackApiTypeEnum.DF, Sets.newHashSet(252));
		shippingRelMap.put(TrackApiTypeEnum.MALAYDF, Sets.newHashSet(244));
	}

	/**
	 * 同步老Erp 物流订单数据
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void syncOrderShipping() {
		if (atomicBoolean.get()) {
			logger.info("同步老ERP物流订单数据正在执行");
			return;
		}
		logger.info("开始同步老ERP物流订单数据");
		long startTime = System.currentTimeMillis();
		try {
			atomicBoolean.set(true);
			HashMap<HashSet<Integer>, TrackApiTypeEnum> handlerTypeMap = new HashMap<>();
			shippingRelMap.entrySet().forEach(item -> {
				handlerTypeMap.put(item.getValue(), item.getKey());
			});
			orderShippingService.syncOrderShipping(handlerTypeMap);
		} finally {
			atomicBoolean.set(false);
		}
		long endTime = System.currentTimeMillis();
		logger.info("syncOrderShipping() executeTime={} second", (endTime - startTime) / 1000);
		logger.info("开始同步老ERP物流订单数据完成");
	}
}
