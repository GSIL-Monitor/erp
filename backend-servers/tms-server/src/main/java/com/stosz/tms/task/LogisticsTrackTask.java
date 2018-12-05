package com.stosz.tms.task;

import com.stosz.plat.common.SpringContextHolder;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.ShippingTrackApi;
import com.stosz.tms.model.TrackingTask;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.ShippingTrackApiService;
import com.stosz.tms.service.TrackingStatusClassifyService;
import com.stosz.tms.service.TrackingTaskService;
import com.stosz.tms.vo.TrackStatusClassifyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 物流轨迹抓取调度任务
 * @author feiheping
 * @version [1.0,2017年12月24日]
 */
@Component
@EnableScheduling
public class LogisticsTrackTask {

	private Logger logger = LoggerFactory.getLogger(LogisticsTrackTask.class);

	@Resource(name = "trackingTaskService")
	private TrackingTaskService trackingTaskService;

	@Autowired
	private ShippingTrackApiService shippingTrackApiService;

	@Resource(name = "logisticsThreadPool")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private TrackingStatusClassifyService statusClassifyService;

	private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

	@Scheduled(cron = "0 */15 * * * ?")
	public void trackCaptureDispatch() {
		new Thread() {
			public void run() {
				dispatchExecute();
			};
		}.start();
	}

	private void dispatchExecute() {
		if (atomicBoolean.get()) {
			logger.info("trackCaptureDispatch () 定时任务正在执行");
			return;
		}
		int rowCount=0;
		long startTime = System.currentTimeMillis();
		try {
			atomicBoolean.set(true);
			int maxThread = threadPoolTaskExecutor.getMaxPoolSize();
			// 查询物流商接口信息
			ShippingTrackApi shippingTrackApi = new ShippingTrackApi();
			List<ShippingTrackApi> shippingTrackApiList = shippingTrackApiService.queryListAll(shippingTrackApi);
			Map<String, ShippingExtend> shippingExtendMap = this.transferShippingExtend(shippingTrackApiList);

			// 查询物流归类基础数据
			Map<String, TrackStatusClassifyVo> trackStatusClassifyMap = statusClassifyService.getAllClassifyMap();

			// 排除黑猫
			TrackingTask trackingTask = new TrackingTask();
			trackingTask.setComplete(0);
			trackingTask.setApiCode(TrackApiTypeEnum.BLACKCAT.code());
			Parameter<String, Object> trackCountMap = trackingTaskService.queryTrackTaskCount(trackingTask);
			// 总行数
			rowCount = trackCountMap.getInteger("rowCount");
			// 最大记录数
			Long maxRecordId = trackCountMap.getLong("maxRecordId");
			// 每个线程跑的任务数
			int targetCount = (int) Math.ceil(rowCount / (double) maxThread);
			CountDownLatch countDownLatch = new CountDownLatch(maxThread);

			for (int i = 0; i < maxThread; i++) {
				LogisticsTrackTaskImpl logisticsTrackTaskImpl = SpringContextHolder.getBean(LogisticsTrackTaskImpl.class);
				int beginIndex = i * targetCount;
				int endIndex = (i + 1) * targetCount;
				endIndex = endIndex > rowCount ? rowCount : endIndex;
				logisticsTrackTaskImpl.setBeginIndex(beginIndex);
				logisticsTrackTaskImpl.setEndIndex(endIndex);
				logisticsTrackTaskImpl.setCountDownLatch(countDownLatch);
				logisticsTrackTaskImpl.setShippingExtendMap(shippingExtendMap);
				logisticsTrackTaskImpl.setMaxRecordId(maxRecordId);
				logisticsTrackTaskImpl.setTrackStatusClassifyMap(trackStatusClassifyMap);
				logisticsTrackTaskImpl.setPriority(10 - i > 0 ? 10 - i : 1);// 设置线程优先级
				threadPoolTaskExecutor.submit(logisticsTrackTaskImpl);
			}
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
			}
		} catch (Exception e) {
			logger.error("trackCaptureDispatch() Exception={}", e);
		} finally {
			atomicBoolean.set(false);
		}
		long endTime = System.currentTimeMillis();
		logger.info("trackCaptureDispatch() taskSize={},executeTime={} second", rowCount, (endTime - startTime) / 1000);
	}

	public Map<String, ShippingExtend> transferShippingExtend(List<ShippingTrackApi> shippingTrackApiList) {
		Map<String, ShippingExtend> shippingExtendMap = shippingTrackApiList.stream().map(shipping -> {
			ShippingExtend shippingExtend = new ShippingExtend();
			shippingExtend.setWaybilltrackUrl(shipping.getWaybilltrackUrl());
			shippingExtend.setAccount(shipping.getAccount());
			shippingExtend.setMd5Key(shipping.getMd5Key());
			shippingExtend.setCode(shipping.getApiCode());
			shippingExtend.setToken(shipping.getToken());
			return shippingExtend;
		}).collect(Collectors.toMap(ShippingExtend::getCode, Function.identity()));
		return shippingExtendMap;
	}
}
