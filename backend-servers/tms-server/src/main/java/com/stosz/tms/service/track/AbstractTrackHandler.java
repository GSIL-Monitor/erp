package com.stosz.tms.service.track;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.EncryptUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.enums.ClassifyEnum;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackStatusClassify;
import com.stosz.tms.model.TrackingTask;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.ShippingNotifyService;
import com.stosz.tms.service.TrackingStatusClassifyService;
import com.stosz.tms.vo.TrackStatusClassifyVo;

/**
 * 物流轨迹抓取
 * @author feiheping
 * @version [1.0,2017年12月8日]
 */
public abstract class AbstractTrackHandler {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 解析物流轨迹的策略
	 */
	protected AbstractParseStartegy parseStartegy;

	@Autowired
	protected TrackingStatusClassifyService statusClassifyService;

	@Autowired
	private ShippingNotifyService shippingNotifyService;

	public AbstractTrackHandler(AbstractParseStartegy parseStartegy) {
		this.parseStartegy = parseStartegy;
	}

	public abstract TrackApiTypeEnum getCode();

	protected String getRequestAction(String requestAction) {
		if (requestAction.endsWith("?")) {
			return requestAction.substring(0, requestAction.length() - 1);
		}
		if (requestAction.endsWith("/")) {
			return requestAction.substring(0, requestAction.length() - 1);
		}
		if (requestAction.endsWith("//")) {
			return requestAction.substring(0, requestAction.length() - 2);
		}
		return requestAction;
	}

	/**
	 * 物流归类并保存明细
	 * @param trackingTaskId
	 * @param taskDetailList
	 */
	public List<TrackingTaskDetail> trackTaskDetailClassify(List<TrackingTaskDetail> taskDetailList,
			Map<String, TrackStatusClassifyVo> trackStatusClassifyMap) {
		if (ArrayUtils.isEmpty(taskDetailList)) {
			return taskDetailList;
		}
		TrackApiTypeEnum handlerType = this.getCode();
		TrackStatusClassifyVo statusClassifyVo = trackStatusClassifyMap.get(handlerType.code());
		if (statusClassifyVo == null)
			statusClassifyVo = new TrackStatusClassifyVo();

		// 纯文本匹配归类数据
		Map<String, TrackStatusClassify> trackStatusMap = StringUtil.nvl(statusClassifyVo.getTrackStatusMap(), new HashMap<>());
		// 物流状态码匹配归类数据
		Map<String, TrackStatusClassify> trackStatusCodeMap = StringUtil.nvl(statusClassifyVo.getTrackStatusCodeMap(), new HashMap<>());
		// 正则表达式匹配归类数据
		List<TrackStatusClassify> patternTrackStatusList = StringUtil.nvl(statusClassifyVo.getPatternTrackStatusList(), new ArrayList<>());

		taskDetailList.forEach(item -> {
			String trackStatus = item.getTrackStatus();
			TrackStatusClassify statusClassify = trackStatusMap.get(trackStatus);
			if (statusClassify != null) {// 纯文本匹配
				item.setClassifyStatus(statusClassify.getClassifyStatus());
				item.setClassifyCode(statusClassify.getClassifyCode());
			} else if ((statusClassify = trackStatusCodeMap.get(trackStatus)) != null) {// 物流状态码匹配
				item.setClassifyStatus(statusClassify.getClassifyStatus());
				item.setClassifyCode(statusClassify.getClassifyCode());
			} else if ((statusClassify = matchStatusClassify(patternTrackStatusList, trackStatus)) != null) {// 正则表达式匹配
				item.setClassifyStatus(statusClassify.getClassifyStatus());
				item.setClassifyCode(statusClassify.getClassifyCode());
			} else {
				item.setClassifyStatus(ClassifyEnum.UNKNOWN.getName());
				item.setClassifyCode(ClassifyEnum.UNKNOWN.getCode());
			}
			String trackTimeStr = item.getTrackTime() == null ? "" : DateUtils.format(item.getTrackTime(), "yyyy-MM-dd HH:mm:ss");
			String orignal = StringUtil.concat(item.getTrackingTaskId(), item.getTrackNo(), item.getTrackStatus(), trackTimeStr,
					item.getTrackRecord(), item.getFileName());
			String hashValue = EncryptUtils.md5(orignal);
			item.setHashValue(hashValue);
			item.setCreateAt(new Date());
		});
		return taskDetailList;
	}

	/**
	 * 正则表达式匹配归类
	 * @param patternTrackStatusList
	 * @param trackStatus
	 * @return
	 */
	public TrackStatusClassify matchStatusClassify(List<TrackStatusClassify> patternTrackStatusList, String trackStatus) {
		TrackStatusClassify statusClassify = null;
		if (ArrayUtils.isNotEmpty(patternTrackStatusList)) {
			for (TrackStatusClassify classify : patternTrackStatusList) {
				Pattern pattern = Pattern.compile(classify.getShippingStatus());
				if (pattern.matcher(trackStatus).find()) {
					statusClassify = classify;
					break;
				}
			}
		}
		return statusClassify;
	}

	/**
	 * 记录关键节点状态变更到主表 TODO 修改包裹表的状态
	 * @return 
	 */
	public TrackingTask trackTaskClassify(TrackingTask trackingTask, List<TrackingTaskDetail> taskDetails) {
		if (ArrayUtils.isNotEmpty(taskDetails)) {

			TrackingTaskDetail lastTrackDetail = taskDetails.get(taskDetails.size() - 1);
			trackingTask.setTrackStatus(lastTrackDetail.getTrackStatus());// 物流状态
			trackingTask.setTrackStatusTime(lastTrackDetail.getTrackTime());// 物流状态时间

			// 程序归类状态
			trackingTask.setRoutineCode(lastTrackDetail.getClassifyCode());
			trackingTask.setRoutineStatus(lastTrackDetail.getClassifyStatus());
			trackingTask.setRoutineStatusTime(new Date());

			final String nowClassifyCode = trackingTask.getClassifyCode();
			changeTrackClassifyStatus(trackingTask, taskDetails);
			final String changeClassifyCode = trackingTask.getClassifyCode();
			if (StringUtils.hasText(changeClassifyCode)) {
				if (!changeClassifyCode.equals(nowClassifyCode) && (ClassifyEnum.RECEIVE.getCode().equals(changeClassifyCode)
						|| ClassifyEnum.REJECTION.getCode().equals(changeClassifyCode))) {
//					 shippingNotifyService.notifyTrackChange(trackingTask);
				}
			}
		}
		trackingTask.setLastCaptureTime(new Date());
		int fetchCount = StringUtil.nvl(trackingTask.getFetchCount(), 0);
		trackingTask.setFetchCount(fetchCount + 1);
		return trackingTask;
	}

	/**
	 * 物流轨迹主表归类状态
	 * @param trackingTask
	 * @param taskDetails
	 */
	private void changeTrackClassifyStatus(TrackingTask trackingTask, List<TrackingTaskDetail> taskDetails) {
		Map<String, HashSet<String>> classifyReverseMap = ClassifyEnum.classifyReverseMap;

		if (StringUtils.hasText(trackingTask.getClassifyCode())) {// 主表已经存在归类
			HashSet<String> hashSet = classifyReverseMap.get(trackingTask.getClassifyCode());
			if (ArrayUtils.isEmpty(hashSet)) {// 如果当前节点的状态，到达不了任何状态
				return;
			}
		}

		for (TrackingTaskDetail taskDetail : taskDetails) {
			if (ClassifyEnum.UNKNOWN.getCode().equals(taskDetail.getClassifyCode())
					|| ClassifyEnum.NOTCLASSIFY.getCode().equals(taskDetail.getClassifyCode())) {// 未知状态 或者是无需归类状态
				continue;
			}
			if (!StringUtils.hasText(trackingTask.getClassifyCode())) {// 如果主表没有归类状态
				trackingTask.setClassifyStatus(taskDetail.getClassifyStatus());
				trackingTask.setClassifyCode(taskDetail.getClassifyCode());
				trackingTask.setClassifyStatusTime(new Date());
				this.takeTrackingTime(taskDetail, trackingTask);
			} else {
				// 获取当前状态 能到达的状态码列表
				HashSet<String> hashSet = classifyReverseMap.get(trackingTask.getClassifyCode());
				if (ArrayUtils.isNotEmpty(hashSet) && hashSet.contains(taskDetail.getClassifyCode())) {// 如果能到达此状态
					trackingTask.setClassifyStatus(taskDetail.getClassifyStatus());
					trackingTask.setClassifyCode(taskDetail.getClassifyCode());
					trackingTask.setClassifyStatusTime(new Date());
					this.takeTrackingTime(taskDetail, trackingTask);
				}
			}
		}
	}

	private void takeTrackingTime(TrackingTaskDetail taskDetail, TrackingTask trackingTask) {
		String classifyCode = taskDetail.getClassifyCode();
		ClassifyEnum classifyEnum = ClassifyEnum.getEnum(classifyCode);
		if (classifyEnum == ClassifyEnum.SALESRETURN) {// 退货
			trackingTask.setReturnedTime(taskDetail.getTrackTime());
		} else if (classifyEnum == ClassifyEnum.PENDING) {// 配送中
			trackingTask.setPendingTime(taskDetail.getTrackTime());
			trackingTask.setOnlineTime(taskDetail.getTrackTime());
		} else if (classifyEnum == ClassifyEnum.REJECTION) {// 拒收
			trackingTask.setRejectTime(taskDetail.getTrackTime());
		} else if (classifyEnum == ClassifyEnum.RECEIVE) {// 已经签收
			trackingTask.setReceiveTime(taskDetail.getTrackTime());
		} else if (classifyEnum == ClassifyEnum.DELIVERYFAILURE) {// 派送失败
			trackingTask.setDeliveryFailureTime(taskDetail.getTrackTime());
		}
	}
}
