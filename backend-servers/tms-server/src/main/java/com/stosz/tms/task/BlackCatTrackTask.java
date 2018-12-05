package com.stosz.tms.task;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stosz.tms.service.track.impl.BlackCatTrackHandler;

/**
 * 黑猫 物流轨迹抓取调度任务
 * @author xiepengcheng
 * @version [1.0,2017年12月25日]
 */
@Component
@EnableScheduling
public class BlackCatTrackTask {

	private Logger logger = LoggerFactory.getLogger(BlackCatTrackTask.class);

	@Autowired
	BlackCatTrackHandler blackCatTrackHandler;

	private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

	@Scheduled(cron = "0 */60 * * * ?")
	public void trackCaptureDispatch() {
		if (atomicBoolean.get()) {
			logger.info("trackCaptureDispatch() 黑猫物流轨迹抓取执行中。。");
			return;
		}
		logger.info("trackCaptureDispatch() 黑猫物流轨迹抓取，开始执行");
		long startTime = System.currentTimeMillis();
		try {
			atomicBoolean.set(true);
			blackCatTrackHandler.captureTransTrack();
		} finally {
			atomicBoolean.set(false);
		}
		long endTime = System.currentTimeMillis();
		logger.info("黑猫物流轨迹抓取耗时 trackCaptureDispatch() executeTime={} second", (endTime - startTime) / 1000);

	}

}
