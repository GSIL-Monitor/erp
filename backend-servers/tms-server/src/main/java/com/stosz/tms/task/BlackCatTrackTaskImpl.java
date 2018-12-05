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
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTask;
import com.stosz.tms.model.TrackingTaskBlackcatDetail;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.service.TrackingTaskBlackcatDetailService;
import com.stosz.tms.service.TrackingTaskDetailService;
import com.stosz.tms.service.TrackingTaskService;
import com.stosz.tms.service.track.impl.BlackCatTrackHandler;
import com.stosz.tms.vo.TrackStatusClassifyVo;

@Component
@Scope("prototype")
public class BlackCatTrackTaskImpl implements Runnable {

	private Logger logger = LoggerFactory.getLogger(BlackCatTrackHandler.class);

	private int beginIndex;

	private int endIndex;

	private Map<String, TrackStatusClassifyVo> statusClassifyList;

	private static final int fetchCount = 20000;

	private static final int batchCount = 5000;

	@Resource
	private TrackingTaskService trackingTaskService;

	@Autowired
	private TrackingTaskBlackcatDetailService blackcatDetailService;

	@Autowired
	private BlackCatTrackHandler blackCatTrackHandler;

	@Autowired
	private TrackingTaskDetailService taskDetailService;

	private CountDownLatch countDownLatch;

	/**
	 * 黑猫数据归类
	 */
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
				logger.info("BlackCatTrackTaskImpl run() 开始黑猫数据归类,startIndex={},fetchCount={}", startIndex, targeteFetchCount);
				this.pigeonhole(startIndex, targeteFetchCount);
				logger.info("BlackCatTrackTaskImpl run() 黑猫数据归类完成,startIndex={},fetchCount={}", startIndex, targeteFetchCount);
			}
		} catch (Exception e) {
			logger.info("BlackCatTrackTaskImpl run() Exception={}", e);
		} finally {
			countDownLatch.countDown();
		}
	}

	public void pigeonhole(int startIndex, int targeteFetchCount) {
		// 获取归类的任务
		List<TrackingTask> trackingTasks = trackingTaskService.getTrackTaskList(TrackApiTypeEnum.BLACKCAT.code(), startIndex, targeteFetchCount);
		if (ArrayUtils.isEmpty(trackingTasks)) {
			return;
		}
		// 获取到所有运单号
		List<String> trackNoList = trackingTasks.stream().map(TrackingTask::getTrackNo).collect(Collectors.toList());
		// 根据运单号 查询黑猫轨迹数据
		Map<String, List<TrackingTaskBlackcatDetail>> collectTrackMap = blackcatDetailService.queryBlackCatByTrackNo(trackNoList);
		if (ArrayUtils.isEmpty(collectTrackMap)) {
			return;
		}
		List<TrackingTaskDetail> batchTaskDetails = new ArrayList<>();
		List<TrackingTask> batchTracks = new ArrayList<>();

		int countTask = 0;
		for (TrackingTask trackingTask : trackingTasks) {
			countTask++;
			String trackNo = trackingTask.getTrackNo();
			List<TrackingTaskBlackcatDetail> taskBlackcatDetails = collectTrackMap.get(trackNo);
			List<TrackingTaskDetail> trackingTaskDetails = this.transferTaskDetail(trackingTask, taskBlackcatDetails);

			List<TrackingTaskDetail> taskDetails = blackCatTrackHandler.trackTaskDetailClassify(trackingTaskDetails, statusClassifyList);
			TrackingTask updateTrackTask = blackCatTrackHandler.trackTaskClassify(trackingTask, taskDetails);

			if (ArrayUtils.isNotEmpty(taskDetails)) {
				batchTaskDetails.addAll(taskDetails);
				batchTracks.add(updateTrackTask);
			}

			if (batchTaskDetails.size() >= batchCount || countTask == trackingTasks.size()) {
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

					// 获取需要更新的主表ID list
					Set<Long> trackingTaskIdSet = batchTaskDetails.stream().map(TrackingTaskDetail::getTrackingTaskId).collect(Collectors.toSet());

					// 更新主表归类
					List<TrackingTask> updateTrackTaskList = batchTracks.stream().filter(item -> trackingTaskIdSet.contains(item.getLongId()))
							.collect(Collectors.toList());
					if (ArrayUtils.isNotEmpty(updateTrackTaskList)) {
						trackingTaskService.batchUpdateTrackTask(updateTrackTaskList);
					}
				}
				batchTaskDetails.clear();
				batchTracks.clear();
			}
		}
	}

	public List<TrackingTaskDetail> transferTaskDetail(TrackingTask trackingTask, List<TrackingTaskBlackcatDetail> taskBlackcatDetails) {
		List<TrackingTaskDetail> taskDetails = new ArrayList<>();
		if (ArrayUtils.isNotEmpty(taskBlackcatDetails)) {
			for (TrackingTaskBlackcatDetail taskBlackcatDetail : taskBlackcatDetails) {
				TrackingTaskDetail taskDetail = new TrackingTaskDetail();
				taskDetail.setTrackingTaskId(trackingTask.getId().longValue());
				taskDetail.setTrackNo(taskBlackcatDetail.getTrackNo());
				taskDetail.setTrackStatus(taskBlackcatDetail.getTrackStatus());
				taskDetail.setTrackRecord(taskBlackcatDetail.getTrackRecord());
				taskDetail.setTrackTime(taskBlackcatDetail.getTrackTime());
				taskDetail.setCityName(taskBlackcatDetail.getLocation());
				taskDetail.setCreateAt(new Date());
				taskDetail.setFileName(taskBlackcatDetail.getFileName());
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

	public Map<String, TrackStatusClassifyVo> getStatusClassifyList() {
		return statusClassifyList;
	}

	public void setStatusClassifyList(Map<String, TrackStatusClassifyVo> statusClassifyList) {
		this.statusClassifyList = statusClassifyList;
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}
}
