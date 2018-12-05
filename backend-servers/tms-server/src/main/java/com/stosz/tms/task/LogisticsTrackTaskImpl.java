package com.stosz.tms.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.EncryptUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTask;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.TrackingTaskShippingDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.TrackingTaskDetailService;
import com.stosz.tms.service.TrackingTaskService;
import com.stosz.tms.service.TrackingTaskShippingDetailService;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.service.track.AbstractTrackHandler;
import com.stosz.tms.service.track.TrackHandlerFactory;
import com.stosz.tms.vo.TrackStatusClassifyVo;

/**
 * 物流轨迹数据抓取(排除黑猫)
 * @author feiheping
 * @version [1.0,2018年1月15日]
 */
@Component
@Scope("prototype")
public class LogisticsTrackTaskImpl extends Thread {

	private Logger logger = LoggerFactory.getLogger(LogisticsTrackTaskImpl.class);

	private int beginIndex;

	private int endIndex;

	private Long maxRecordId;

	private CountDownLatch countDownLatch;

	private Map<String, ShippingExtend> shippingExtendMap;

	private static final int fetchCount = 5000;

	@Resource(name = "trackingTaskService")
	private TrackingTaskService trackingTaskService;

	@Autowired
	private TrackHandlerFactory trackHandlerFactory;

	@Autowired
	private TrackingTaskShippingDetailService taskShippingDetailService;

	@Autowired
	private TrackingTaskDetailService taskDetailService;

	private Map<String, TrackStatusClassifyVo> trackStatusClassifyMap;

	@Override
	public void run() {
		try {
			int batchSize = endIndex - beginIndex;
			int targeteFetchCount = fetchCount;

			int count = (int) Math.ceil((batchSize / (double) fetchCount));
			for (int i = 0; i < count; i++) {
				int startIndex = beginIndex + (i * fetchCount);

				if (startIndex + fetchCount > endIndex) {
					targeteFetchCount = endIndex - startIndex;
				}

				logger.info("LogisticsTrackTaskImpl run() 开始抓取物流数据并入库,startIndex={},fetchCount={}", startIndex, targeteFetchCount);
				List<TrackingTask> trackingTaskList = this.pigeonhole(startIndex, targeteFetchCount);
				logger.info("LogisticsTrackTaskImpl run() 抓取物流数据入库完成,startIndex={},fetchCount={}", startIndex, targeteFetchCount);

				logger.info("LogisticsTrackTaskImpl run() 开始物流数据归类,startIndex={},fetchCount={}", startIndex, targeteFetchCount);
				this.classifyPigeonhole(trackingTaskList);
				logger.info("LogisticsTrackTaskImpl run() 物流数据归类完成,startIndex={},fetchCount={}", startIndex, targeteFetchCount);
			}
		} catch (Exception e) {
			logger.info("LogisticsTrackTaskImpl run() Exception={}", e);
		} finally {
			countDownLatch.countDown();
		}
	}

	/**
	 * 调用物流API抓取物流轨迹
	 * @param startIndex
	 * @param targeteFetchCount
	 */
	public List<TrackingTask> pigeonhole(int startIndex, int targeteFetchCount) {
		// 获取归类的任务
		TrackingTask searchTrackingTask = new TrackingTask();
		searchTrackingTask.setComplete(0);
		searchTrackingTask.setApiCode(TrackApiTypeEnum.BLACKCAT.code());
		searchTrackingTask.setStart(startIndex);
		searchTrackingTask.setLimit(targeteFetchCount);
		searchTrackingTask.setMaxRecordId(maxRecordId);
		List<TrackingTask> trackingTasks = trackingTaskService.getTrackTaskListByTask(searchTrackingTask);
		if (ArrayUtils.isEmpty(trackingTasks)) {
			return trackingTasks;
		}
		int countTask = 0;
		List<TrackingTaskDetail> batchTaskDetails = new ArrayList<>();
		for (TrackingTask trackingTask : trackingTasks) {
			countTask++;
			ShippingExtend shippingExtend = shippingExtendMap.get(trackingTask.getApiCode());
			if (shippingExtend != null) {
				AbstractTrackHandler abstractTrackHandler = trackHandlerFactory.getHandler(trackingTask.getApiCode());
				if (abstractTrackHandler instanceof AbstractSingleTrackHandler) {
					AbstractSingleTrackHandler singleTrackHandler = (AbstractSingleTrackHandler) abstractTrackHandler;

					TrackRequest trackRequest = new TrackRequest();
					trackRequest.setTrackingNo(trackingTask.getTrackNo());
					trackRequest.setTrackingTask(trackingTask);
					TransportTrackResponse transportTrackResponse = singleTrackHandler.captureTransTrack(trackRequest, shippingExtend);
					if (TransportTrackResponse.SUCCESS.equals(transportTrackResponse.getCode())) {
						List<TrackingTaskDetail> onceTaskDetais = transportTrackResponse.getTrackDetails();
						if (ArrayUtils.isNotEmpty(onceTaskDetais)) {
							onceTaskDetais.forEach(item -> {
								item.setTrackingTaskId(trackingTask.getLongId());
								item.setTrackNo(trackingTask.getTrackNo());
								item.setApiCode(trackingTask.getApiCode());
							});
							batchTaskDetails.addAll(onceTaskDetais);
						}
					}
				}
			} else {
				logger.info("LogisticsTrackTaskImpl pigeonhole() apiCode={},轨迹API接口地址未配置", trackingTask.getApiCode());
			}
			if ((batchTaskDetails.size() >= fetchCount || countTask == trackingTasks.size()) && ArrayUtils.isNotEmpty(batchTaskDetails)) {
				List<TrackingTaskShippingDetail> taskShippingDetails = this.transferShippingDetails(batchTaskDetails);
				List<String> hashValues = taskShippingDetails.stream().map(TrackingTaskShippingDetail::getHashValue).collect(Collectors.toList());
				HashSet<String> containsSet = taskShippingDetailService.queryDetailHashExists(hashValues);
				List<TrackingTaskShippingDetail> addShippingDetails = taskShippingDetails.stream()
						.filter(item -> !containsSet.contains(item.getHashValue())).collect(Collectors.toList());
				if (ArrayUtils.isNotEmpty(addShippingDetails)) {
					taskShippingDetailService.addTrackDeatils(addShippingDetails);
				}
				batchTaskDetails.clear();
			}
		}
		return trackingTasks;
	}

	public List<TrackingTaskShippingDetail> transferShippingDetails(List<TrackingTaskDetail> batchTaskDetails) {
		List<TrackingTaskShippingDetail> taskShippingDetails = new ArrayList<>();
		for (TrackingTaskDetail taskDetail : batchTaskDetails) {
			TrackingTaskShippingDetail shippingDetail = new TrackingTaskShippingDetail();
			shippingDetail.setTrackNo(taskDetail.getTrackNo());
			shippingDetail.setTrackStatus(taskDetail.getTrackStatus());
			shippingDetail.setTrackTime(taskDetail.getTrackTime());
			shippingDetail.setLocation(taskDetail.getCityName());
			shippingDetail.setTrackRecord(taskDetail.getTrackRecord());
			shippingDetail.setApiCode(taskDetail.getApiCode());
			shippingDetail.setCreateAt(new Date());
			String trackTimeStr = taskDetail.getTrackTime() == null ? "" : DateUtils.format(taskDetail.getTrackTime(), "yyyy-MM-dd HH:mm:ss");

			String orignal = StringUtil.concatBySplitExcludeNull("_", taskDetail.getTrackingTaskId(), taskDetail.getTrackNo(),
					taskDetail.getTrackStatus(), trackTimeStr, taskDetail.getTrackRecord(), shippingDetail.getApiCode());
			String hashValue = EncryptUtils.md5(orignal);
			shippingDetail.setHashValue(hashValue);
			taskShippingDetails.add(shippingDetail);
		}
		return taskShippingDetails;
	}

	/**
	 * 物流轨迹归类
	 * @param startIndex
	 * @param targeteFetchCount
	 */
	public void classifyPigeonhole(List<TrackingTask> trackingTasks) {
		if (ArrayUtils.isEmpty(trackingTasks)) {
			return;
		}
		// 获取到所有运单号
		List<String> trackNoList = trackingTasks.stream().map(TrackingTask::getTrackNo).collect(Collectors.toList());
		// 根据运单号 查询轨迹数据
		Map<String, List<TrackingTaskShippingDetail>> collectTrackMap = taskShippingDetailService.queryShippingDetailsByTrackNo(trackNoList);

		if (ArrayUtils.isEmpty(collectTrackMap)) {
			return;
		}
		List<TrackingTaskDetail> batchTaskDetails = new ArrayList<>();
		List<TrackingTask> batchTracks = new ArrayList<>();
		int countTask = 0;
		for (TrackingTask trackingTask : trackingTasks) {
			countTask++;
			String trackNo = trackingTask.getTrackNo();
			List<TrackingTaskShippingDetail> taskBlackcatDetails = collectTrackMap.get(trackNo);
			List<TrackingTaskDetail> trackingTaskDetails = this.transferTaskDetail(trackingTask, taskBlackcatDetails);

			AbstractSingleTrackHandler trackHandler = trackHandlerFactory.getHandler(trackingTask.getApiCode());

			List<TrackingTaskDetail> taskDetails = trackHandler.trackTaskDetailClassify(trackingTaskDetails, trackStatusClassifyMap);
			TrackingTask updateTrackTask = trackHandler.trackTaskClassify(trackingTask, taskDetails);

			if (ArrayUtils.isNotEmpty(taskDetails)) {
				batchTaskDetails.addAll(taskDetails);
			}
			batchTracks.add(updateTrackTask);
			if (batchTaskDetails.size() >= fetchCount || countTask == trackingTasks.size()) {
				List<String> hashValues = batchTaskDetails.stream().map(TrackingTaskDetail::getHashValue).collect(Collectors.toList());
				if (ArrayUtils.isNotEmpty(hashValues)) {
					// 已经存在的明细
					HashSet<String> existsDetails = taskDetailService.queryDetailHashExists(hashValues);
					List<TrackingTaskDetail> addTaskDetails = batchTaskDetails.stream().filter(item -> !existsDetails.contains(item.getHashValue()))
							.collect(Collectors.toList());
					if (ArrayUtils.isNotEmpty(addTaskDetails)) {
						// 物流轨迹明细入库
						taskDetailService.addTrackDeatils(addTaskDetails);
					}
				}
				// 没有抓取到轨迹明细记录，更新抓取次数，最后抓取时间
				trackingTaskService.batchUpdateTrackTask(batchTracks);
				batchTaskDetails.clear();
				batchTracks.clear();
			}
		}
	}

	public List<TrackingTaskDetail> transferTaskDetail(TrackingTask trackingTask, List<TrackingTaskShippingDetail> taskShippingDetails) {
		List<TrackingTaskDetail> taskDetails = new ArrayList<>();
		if (ArrayUtils.isNotEmpty(taskShippingDetails)) {
			for (TrackingTaskShippingDetail taskBlackcatDetail : taskShippingDetails) {
				TrackingTaskDetail taskDetail = new TrackingTaskDetail();
				taskDetail.setTrackingTaskId(trackingTask.getId().longValue());
				taskDetail.setTrackNo(taskBlackcatDetail.getTrackNo());
				taskDetail.setTrackStatus(taskBlackcatDetail.getTrackStatus());
				taskDetail.setTrackRecord(taskBlackcatDetail.getTrackRecord());
				taskDetail.setTrackTime(taskBlackcatDetail.getTrackTime());
				taskDetail.setCityName(taskBlackcatDetail.getLocation());
				taskDetail.setCreateAt(new Date());
				taskDetail.setFileName("");
				taskDetails.add(taskDetail);
			}
		}
		return taskDetails;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	public Map<String, ShippingExtend> getShippingExtendMap() {
		return shippingExtendMap;
	}

	public void setShippingExtendMap(Map<String, ShippingExtend> shippingExtendMap) {
		this.shippingExtendMap = shippingExtendMap;
	}

	public Long getMaxRecordId() {
		return maxRecordId;
	}

	public void setMaxRecordId(Long maxRecordId) {
		this.maxRecordId = maxRecordId;
	}

	public Map<String, TrackStatusClassifyVo> getTrackStatusClassifyMap() {
		return trackStatusClassifyMap;
	}

	public void setTrackStatusClassifyMap(Map<String, TrackStatusClassifyVo> trackStatusClassifyMap) {
		this.trackStatusClassifyMap = trackStatusClassifyMap;
	}

}
