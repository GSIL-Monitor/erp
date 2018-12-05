package com.stosz.tms.task;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.tms.cache.PlaceOrderCache;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.model.Shipping;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 抛送订单到物流商定时任务
 * @author feiheping
 * @version [1.0,2017年12月28日]
 */
@Component
@EnableScheduling
public class LogisticsPlaceOrderTask {

	private Logger logger = LoggerFactory.getLogger(LogisticsPlaceOrderTask.class);

	@Resource
	private ShippingParcelExtendService parcelExtendService;

	@Resource(name = "placeOrderThreadPool")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Resource(name = "placeOrderService")
	private PlaceOrderService placeOrderService;

	@Autowired
	private ShippingWayService shippingWayService;

	@Autowired
	private ShippingService shippingService;

	@Autowired
	private IStorehouseService storehouseService;

	@Autowired
	private PlaceOrderCache placeOrderCache;

	private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

//	@Scheduled(cron = "0 0/1 * * * ?")
	public void placePushOrderAction() {
		String currentTimeStr = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		if (atomicBoolean.get()) {
			logger.info("placeOrderAction () 定时任务正在执行currentDateTime={}", currentTimeStr);
			return;
		}
		try {
			atomicBoolean.set(true);
			List<ShippingParcel> shippingParcels = parcelExtendService.queryListByParcel();
			this.filterAsyncParcel(shippingParcels);
			if (ArrayUtils.isEmpty(shippingParcels)) {
				logger.info("placeOrderAction() 没有需要同步的单据  currentDateTime={}", currentTimeStr);
				return;
			}
			Set<Integer> shippingWayIdSet = new HashSet<>();
			Set<Integer> wareHouseIdSet = new HashSet<>();
			for (ShippingParcel shippingParcel : shippingParcels) {
				shippingWayIdSet.add(shippingParcel.getShippingWayId());
				wareHouseIdSet.add(shippingParcel.getWarehouseId());
			}
			// 查询仓库信息
			List<Wms> wms = storehouseService.findWmsByIds(new ArrayList<>(wareHouseIdSet));
			Map<Integer, Wms> warehouseMap = wms.stream().collect(Collectors.toMap(Wms::getId, Function.identity()));

			// 查询物流方式
			List<ShippingWay> shippingWays = shippingWayService.queryByIds(shippingWayIdSet);
			Set<Integer> shippingIds = shippingWays.stream().map(ShippingWay::getShippingId).collect(Collectors.toSet());

			// 查询物流商
			List<Shipping> shippings = shippingService.queryByIds(shippingIds);
			Map<Integer, Shipping> shippingMap = shippings.stream().collect(Collectors.toMap(Shipping::getId, Function.identity()));

			HashMap<Integer, Shipping> shippingWayMap = new HashMap<>();
			for (ShippingWay shippingWay : shippingWays) {
				Integer shippingWayId = shippingWay.getId();
				Shipping shipping = shippingMap.get(shippingWay.getShippingId());
				shippingWayMap.put(shippingWayId, shipping);
			}

			Parameter<String, Object> rootParams = new Parameter<>();
			rootParams.put("shippingWayMap", shippingWayMap);
			rootParams.put("warehouseMap", warehouseMap);

			int batchSize = 50;
			int maxThread = 15;
			int threadSize = (int) Math.ceil(shippingParcels.size() / (double) batchSize);
			if (threadSize > maxThread) {
				batchSize = (int) Math.ceil(shippingParcels.size() / (double) maxThread);
				threadSize = maxThread;
			}
			CountDownLatch countDownLatch = new CountDownLatch(threadSize);
			for (int i = 0; i < threadSize; i++) {
				int endIndex = (i + 1) * batchSize;
				endIndex = endIndex > shippingParcels.size() ? shippingParcels.size() : endIndex;
				List<ShippingParcel> subTrackTaskList = shippingParcels.subList(i * batchSize, endIndex);
				threadPoolTaskExecutor.execute(new ForkProcessorTask<>(subTrackTaskList, countDownLatch, placeOrderService, rootParams));
			}
			countDownLatch.await();
		} catch (Exception e) {
			logger.error("placeOrderAction() Exception={}", e);
		} finally {
			atomicBoolean.set(false);
		}
	}

	/**
	 * 排除掉正在异步抛送订单的包裹ID
	 * @param shippingParcels
	 */
	public void filterAsyncParcel(List<ShippingParcel> shippingParcels) {
		try {
			Set<Integer> asyncParcelIdSet = placeOrderCache.queryAysncOrder();
			if (ArrayUtils.isNotEmpty(asyncParcelIdSet)) {
				shippingParcels = shippingParcels.stream().filter(item -> !asyncParcelIdSet.contains(item.getId())).collect(Collectors.toList());
			}
		} catch (Exception e) {
			logger.error("filterAsyncParcel 获取异步执行的推送失败 Exception={}", e);
		}
	}
}
