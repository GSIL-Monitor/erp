package com.stosz.tms.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.BeanMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.enums.ClassifyEnum;
import com.stosz.tms.mapper.ShippingParcelMapper;
import com.stosz.tms.mapper.ShippingWayMapper;
import com.stosz.tms.mapper.TrackingStatusClassifyMapper;
import com.stosz.tms.mapper.TrackingTaskDetailMapper;
import com.stosz.tms.mapper.TrackingTaskMapper;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.model.TrackStatusClassify;
import com.stosz.tms.model.TrackingTask;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.vo.ShippingParcelTaskListVo;
import com.stosz.tms.vo.TrackingTaskExportVo;
import com.stosz.tms.vo.TrackingTaskListVo;

@Service
public class TrackingTaskService {

	private Logger logger = LoggerFactory.getLogger(TrackingTaskService.class);

	@Autowired
	private TrackingTaskMapper mapper;

	@Resource
	private ShippingWayMapper shippingWayMapper;

	@Resource
	private TrackingTaskDetailMapper trackingTaskDetailMapper;

	@Resource
	private ShippingParcelMapper shippingParcelMapperl;

	@Resource
	private TrackingStatusClassifyMapper statusClassifyMapper;

	@Resource
	private TrackingTaskImportDataService importDataService;

	@Autowired
	private ShippingNotifyService shippingNotifyService;

	public int updateSelective(TrackingTask trackingTask) {
		return mapper.updateSelective(trackingTask);
	}

	public Parameter<String, Object> queryTrackTaskCount(TrackingTask trackingTask) {
		return mapper.queryTrackTaskCount(trackingTask);
	}

	public List<TrackingTask> getTrackTaskListByTask(TrackingTask trackingTask) {
		return mapper.getTrackTaskListByTask(trackingTask);
	}

	public int count(TrackingTask trackingTask) {
		return mapper.count(trackingTask);
	}

	public List<TrackingTaskListVo> queryList(TrackingTask trackingTask) {
		// if (!StringUtils.hasText(shippingParcel.getOrderBy())) {
		// shippingParcel.setOrderBy(" update_at desc,create_at");
		// }
		final List<TrackingTask> shippingParcels = mapper.find(trackingTask);

		final List<TrackingTaskListVo> trackingTaskListVos = BeanMapper.mapList(shippingParcels, TrackingTaskListVo.class);

		Set<Integer> shippingWayIds = shippingParcels.stream().filter(item -> item.getShippingWayId() != null).map(TrackingTask::getShippingWayId)
				.collect(Collectors.toSet());

		if (ArrayUtils.isEmpty(shippingWayIds))
			return trackingTaskListVos;

		List<ShippingWay> shippingWays = shippingWayMapper.queryByIds(shippingWayIds);

		final Map<Integer, List<ShippingWay>> shippingWayMapById = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getId));

		trackingTaskListVos.forEach(e -> {
			final Integer shippingWayId = e.getShippingWayId();

			final List<ShippingWay> wayList = shippingWayMapById.get(shippingWayId);

			// 没有找到数据
			if (wayList == null || wayList.isEmpty())
				return;

			final ShippingWay shippingWay = wayList.get(0);

			e.setShippingWayName(shippingWay.getShippingWayName());
		});

		return trackingTaskListVos;
	}

	/**
	 * 获取物流轨迹列表
	 * @param id
	 * @return
	 */
	public List<ShippingParcelTaskListVo> selectTaskList(Integer id) {
		TrackingTaskDetail detailParamBean = new TrackingTaskDetail();
		detailParamBean.setTrackingTaskId(Long.valueOf(id));
		detailParamBean.setLimit(Integer.MAX_VALUE);

		if (!StringUtils.hasText(detailParamBean.getOrderBy())) {
			detailParamBean.setOrderBy(" track_time ");
			detailParamBean.setOrder(false);
		}

		final List<TrackingTaskDetail> taskDetails = trackingTaskDetailMapper.select(detailParamBean);

		if (CollectionUtils.isNullOrEmpty(taskDetails))
			return Collections.emptyList();

		return BeanMapper.mapList(taskDetails, ShippingParcelTaskListVo.class);
	}

	public int inserSelective(TrackingTask trackingTask) {
		return mapper.add(trackingTask);
	}

	public TrackingTask selectByOrderId(int orderId) {
		TrackingTask paramBean = new TrackingTask();
		paramBean.setOrderId(orderId);

		final List<TrackingTask> trackingTasks = mapper.find(paramBean);
		if (ArrayUtils.isNotEmpty(trackingTasks)) {
			return trackingTasks.get(0);
		}
		return null;

	}

	/**
	 * 导入excel数据
	 * @param uploadData
	 * @return
	 */
	public List<TrackingTaskExportVo> importExeclData(List<List<String>> uploadData,Integer wayId) {
		Assert.notEmpty(uploadData, "上传的是空文件");

		List<TrackingTaskExportVo> trackingTaskExportVos = Lists.newArrayList();

		final List<ShippingParcel> shippingParcels = getShippingParcels();

		final List<ShippingWay> shippingWays = getShippingWays();

		final ArrayList<Integer> apiIdList = Lists
				.newArrayList(shippingWays.stream().filter(e -> e.getApiId() != null).map(ShippingWay::getApiId).collect(Collectors.toSet()));

		final List<TrackStatusClassify> trackStatusClassifies = getTrackStatusClassifies(apiIdList);

		final List<Integer> shippingParcelIdList = shippingParcels.stream().map(ShippingParcel::getId).collect(Collectors.toList());

		final List<TrackingTask> trackingTasks = getTrackingTasks(shippingParcelIdList);

		final Map<Integer, List<TrackingTask>> taskMapByParcelId = trackingTasks.stream()
				.collect(Collectors.groupingBy(TrackingTask::getShippingParcelId));

		final Map<Integer, List<ShippingWay>> shippingWayMapById = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getId));

		final Map<Integer, List<TrackStatusClassify>> statusClassifyMapByTrackApiId = trackStatusClassifies.stream()
				.collect(Collectors.groupingBy(TrackStatusClassify::getTrackApiId));

		final Map<String, List<ShippingParcel>> parcelMapByOrderNo = shippingParcels.stream()
				.collect(Collectors.groupingBy(ShippingParcel::getOrderNo));
		final Map<String, List<ShippingParcel>> parcelMapByTarckNo = shippingParcels.stream().filter(e -> e.getTrackNo() != null)
				.collect(Collectors.groupingBy(ShippingParcel::getTrackNo));

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		uploadData.forEach(e -> {
			TrackingTaskExportVo taskExportVo = new TrackingTaskExportVo();

			final String orderNo = e.get(0);
			final String tarckNo = e.get(1);
			final String trackStatus = e.get(2);
			final String trackStatusDate = e.get(3);

			taskExportVo.setOrderNo(orderNo);
			taskExportVo.setTrackNo(tarckNo);
			taskExportVo.setTrackStatus(trackStatus);
			taskExportVo.setTrackStatusTime(trackStatusDate);

			if (checkData(trackingTaskExportVos, taskExportVo, orderNo, tarckNo, trackStatus, trackStatusDate))
				return;

			ShippingParcel shippingParcel = null;
			if (!StringUtils.isEmpty(orderNo)) {
				final List<ShippingParcel> shippingParcelList = parcelMapByOrderNo.get(orderNo);
				if (CollectionUtils.isNotNullAndEmpty(shippingParcelList)) {
					shippingParcel = shippingParcelList.get(0);
				} else if (CollectionUtils.isNullOrEmpty(shippingParcelList) && StringUtils.isEmpty(tarckNo)) {
					taskExportVo.setFailMessage("通过运单号和订单号查找包裹失败");
					trackingTaskExportVos.add(taskExportVo);
					return;
				}
			}

			if (shippingParcel == null && !StringUtils.isEmpty(tarckNo)) {
				final List<ShippingParcel> shippingParcelList = parcelMapByTarckNo.get(tarckNo);
				if (CollectionUtils.isNullOrEmpty(shippingParcelList)) {
					taskExportVo.setFailMessage("通过运单号和订单号查找包裹失败");
					trackingTaskExportVos.add(taskExportVo);
					return;
				} else {
					shippingParcel = shippingParcelList.get(0);
				}
			}

			if ((!StringUtils.isEmpty(orderNo) && !shippingParcel.getOrderNo().equals(orderNo)) || (!StringUtils.isEmpty(tarckNo)
					&& (StringUtils.isEmpty(shippingParcel.getTrackNo()) || !shippingParcel.getTrackNo().equals(tarckNo)))) {
				taskExportVo.setFailMessage("订单号与订单号不匹配");
				trackingTaskExportVos.add(taskExportVo);
				return;
			}

			Date trackStatusTime = null;
			trackStatusTime = gettrackingTaskTime(trackingTaskExportVos, df, taskExportVo, trackStatusDate, shippingParcel);

			if (trackStatusTime == null)
				return;

			final Integer shippingWayId = shippingParcel.getShippingWayId();

			if (!shippingWayId.equals(wayId)){
				taskExportVo.setFailMessage("所属包裹物流线路与选择的物流线路不一致");
				trackingTaskExportVos.add(taskExportVo);
				return;
			}


			final List<ShippingWay> shippingWayList = shippingWayMapById.get(shippingWayId);

			if (CollectionUtils.isNullOrEmpty(shippingWayList)) {
				taskExportVo.setFailMessage("所属包裹物流线路数据错误");
				trackingTaskExportVos.add(taskExportVo);
				return;
			}

			final ShippingWay shippingWay = shippingWayList.get(0);

			final Integer apiId = shippingWay.getApiId();

			final List<TrackStatusClassify> statusClassifyList = statusClassifyMapByTrackApiId.get(apiId);

			if (CollectionUtils.isNullOrEmpty(statusClassifyList)) {
				taskExportVo.setFailMessage("所属包裹物流接口下无物流状态分类数据");
				trackingTaskExportVos.add(taskExportVo);
				return;
			}

			String classifyStatus = null;
			String classifyCode = null;

			final Map<String, List<TrackStatusClassify>> statusClassifyMapByShippingStatus = statusClassifyList.stream()
					.collect(Collectors.groupingBy(TrackStatusClassify::getShippingStatus));

			final List<TrackStatusClassify> statusClassifies = statusClassifyMapByShippingStatus.get(trackStatus);

			if (CollectionUtils.isNullOrEmpty(statusClassifies)) {
				final List<TrackStatusClassify> classifyList = statusClassifyList.stream().filter(s -> s.getStatusType() == 2)
						.collect(Collectors.toList());

				if (CollectionUtils.isNotNullAndEmpty(classifyList)) {
					for (TrackStatusClassify statusClassify : classifyList) {
						Pattern pattern = Pattern.compile(statusClassify.getShippingStatus());

						if (pattern.matcher(trackStatus).find()) {
							classifyStatus = statusClassify.getClassifyStatus();
							classifyCode = statusClassify.getClassifyCode();
						}
					}
				}
			} else {
				classifyStatus = statusClassifies.get(0).getClassifyStatus();
				classifyCode = statusClassifies.get(0).getClassifyCode();
			}

			if (StringUtils.isEmpty(classifyStatus)) {
				taskExportVo.setFailMessage("获取物流状态对应归类状态失败");
				trackingTaskExportVos.add(taskExportVo);
				return;
			}

			ShippingParcel updateParcelBean = new ShippingParcel();
			updateParcelBean.setId(shippingParcel.getId());
			updateParcelBean.setLastTrackTime(trackStatusTime);
			updateParcelBean.setClassifyStatus(classifyStatus);
			updateParcelBean.setTrackStatus(trackStatus);

			final List<TrackingTask> trackingTaskList = taskMapByParcelId.get(shippingParcel.getId());

			final TrackingTask updateTaskBean = new TrackingTask();

			TrackingTask trackingTask = packageTrackingTaskData(trackStatus, shippingParcel, trackStatusTime, shippingWayId, shippingWay,
					classifyStatus, classifyCode, trackingTaskList, updateTaskBean);

			TrackingTaskDetail insertTaskDetailBean = packageTrackingTaskDetailData(tarckNo, trackStatus, trackStatusTime, classifyStatus,
					classifyCode);

			try {
				importDataService.importData(updateParcelBean, updateTaskBean, insertTaskDetailBean);
				if ((trackingTask == null || !trackingTask.getClassifyCode().equals(updateTaskBean.getClassifyCode()))
						&& (ClassifyEnum.RECEIVE.getCode().equals(updateTaskBean.getClassifyCode())
								|| ClassifyEnum.REJECTION.getCode().equals(updateTaskBean.getClassifyCode())))
					// 通知其他业务系统，物流状态节点变更
					shippingNotifyService.notifyTrackChange(updateTaskBean);

			} catch (Exception e1) {
				logger.error("保存物流轨迹信息失败", e1);
				taskExportVo.setFailMessage(String.format("出现异常，异常信息:%s", e1.getMessage()));
				trackingTaskExportVos.add(taskExportVo);
				return;
			}

		});

		return trackingTaskExportVos;
	}

	private TrackingTaskDetail packageTrackingTaskDetailData(String tarckNo, String trackStatus, Date trackStatusTime, String classifyStatus,
			String classifyCode) {
		TrackingTaskDetail insertTaskDetailBean = new TrackingTaskDetail();
		insertTaskDetailBean.setTrackStatus(trackStatus);
		insertTaskDetailBean.setTrackNo(tarckNo);
		insertTaskDetailBean.setTrackStatus(trackStatus);
		insertTaskDetailBean.setClassifyCode(classifyCode);
		insertTaskDetailBean.setClassifyStatus(classifyStatus);
		insertTaskDetailBean.setTrackTime(trackStatusTime);
		return insertTaskDetailBean;
	}

	/**
	 * 封装物流任务数据
	 * @param trackStatus
	 * @param shippingParcel
	 * @param trackStatusTime
	 * @param shippingWayId
	 * @param shippingWay
	 * @param classifyStatus
	 * @param classifyCode
	 * @param trackingTaskList
	 * @param updateTaskBean
	 * @return
	 */
	private TrackingTask packageTrackingTaskData(String trackStatus, ShippingParcel shippingParcel, Date trackStatusTime, Integer shippingWayId,
			ShippingWay shippingWay, String classifyStatus, String classifyCode, List<TrackingTask> trackingTaskList, TrackingTask updateTaskBean) {
		TrackingTask trackingTask = null;
		if (CollectionUtils.isNotNullAndEmpty(trackingTaskList) && !StringUtils.isEmpty(trackingTaskList.get(0).getClassifyCode())) {
			trackingTask = trackingTaskList.get(0);

			updateTaskBean.setId(trackingTask.getId());
			updateTaskBean.setTrackStatus(trackStatus);
			updateTaskBean.setOrderId(shippingParcel.getOrderId());
			updateTaskBean.setTrackNo(shippingParcel.getTrackNo());
			updateTaskBean.setTrackStatusTime(trackStatusTime);
			updateTaskBean.setRoutineCode(classifyCode);
			updateTaskBean.setRoutineStatus(classifyStatus);

			final Map<String, HashSet<String>> classifyReverseMap = ClassifyEnum.classifyReverseMap;

			final HashSet<String> allowCodeSet = classifyReverseMap.get(trackingTask.getClassifyCode());

			if (!StringUtil.isAnyEmpty(trackingTask.getClassifyCode())) {
				if (allowCodeSet != null && allowCodeSet.contains(classifyCode)) {
					updateTaskBean.setClassifyStatus(classifyStatus);
					updateTaskBean.setClassifyCode(classifyCode);
					updateTaskBean.setClassifyStatusTime(trackStatusTime);

					ClassifyEnum classifyEnum = ClassifyEnum.getEnum(classifyCode);

					if (classifyEnum == ClassifyEnum.SALESRETURN) {// 退货
						updateTaskBean.setReturnedTime(trackStatusTime);
					} else if (classifyEnum == ClassifyEnum.PENDING) {// 配送中
						updateTaskBean.setPendingTime(trackStatusTime);
					} else if (classifyEnum == ClassifyEnum.REJECTION) {// 问题件
						updateTaskBean.setRejectTime(trackStatusTime);
					} else if (classifyEnum == ClassifyEnum.RECEIVE) {// 已经签收
						updateTaskBean.setReceiveTime(trackStatusTime);
					}
				}
			} else {
				updateTaskBean.setClassifyStatus(classifyStatus);
				updateTaskBean.setClassifyCode(classifyCode);
				updateTaskBean.setClassifyStatusTime(trackStatusTime);

				ClassifyEnum classifyEnum = ClassifyEnum.getEnum(classifyCode);
				if (classifyEnum == ClassifyEnum.SALESRETURN) {// 退货
					updateTaskBean.setReturnedTime(trackStatusTime);
				} else if (classifyEnum == ClassifyEnum.PENDING) {// 配送中
					updateTaskBean.setPendingTime(trackStatusTime);
				} else if (classifyEnum == ClassifyEnum.REJECTION) {// 问题件
					updateTaskBean.setRejectTime(trackStatusTime);
				} else if (classifyEnum == ClassifyEnum.RECEIVE) {// 已经签收
					updateTaskBean.setReceiveTime(trackStatusTime);
				}
			}
		} else {
			updateTaskBean.setShippingParcelId(shippingParcel.getId());
			updateTaskBean.setShippingWayId(shippingWayId);
			updateTaskBean.setApiCode(shippingWay.getShippingCode());
			updateTaskBean.setOrderId(shippingParcel.getOrderId());
			updateTaskBean.setTrackNo(shippingParcel.getTrackNo());
			updateTaskBean.setTrackStatus(trackStatus);
			updateTaskBean.setClassifyStatus(classifyStatus);
			updateTaskBean.setClassifyCode(classifyCode);
			updateTaskBean.setTrackStatusTime(trackStatusTime);
			updateTaskBean.setClassifyStatusTime(trackStatusTime);
			updateTaskBean.setOnlineTime(trackStatusTime);
			updateTaskBean.setFetchCount(0);
			updateTaskBean.setComplete(0);
			updateTaskBean.setRoutineCode(classifyCode);
			updateTaskBean.setRoutineStatus(classifyStatus);

			ClassifyEnum classifyEnum = ClassifyEnum.getEnum(classifyCode);
			if (classifyEnum == ClassifyEnum.SALESRETURN) {// 退货
				updateTaskBean.setReturnedTime(trackStatusTime);
			} else if (classifyEnum == ClassifyEnum.PENDING) {// 配送中
				updateTaskBean.setPendingTime(trackStatusTime);
			} else if (classifyEnum == ClassifyEnum.REJECTION) {// 问题件
				updateTaskBean.setRejectTime(trackStatusTime);
			} else if (classifyEnum == ClassifyEnum.RECEIVE) {// 已经签收
				updateTaskBean.setReceiveTime(trackStatusTime);
			}
		}
		return trackingTask;
	}

	/**
	 * 获取运单状态时间
	 * @param trackingTaskExportVos
	 * @param df
	 * @param taskExportVo
	 * @param trackStatusDate
	 * @param shippingParcel
	 * @return
	 */
	private Date gettrackingTaskTime(List<TrackingTaskExportVo> trackingTaskExportVos, DateFormat df, TrackingTaskExportVo taskExportVo,
			String trackStatusDate, ShippingParcel shippingParcel) {
		Date trackStatusTime;
		try {
			trackStatusTime = df.parse(trackStatusDate);
			final Calendar trackStatusCalendar = Calendar.getInstance();
			trackStatusCalendar.setTime(trackStatusTime);

			final Date lastTrackTime = shippingParcel.getLastTrackTime();

			if (lastTrackTime != null) {
				final Calendar lastTrackCalendar = Calendar.getInstance();
				lastTrackCalendar.setTime(lastTrackTime);

				if ((trackStatusCalendar.compareTo(lastTrackCalendar) <= 0)
				// || (trackStatusCalendar.get(Calendar.YEAR) ==
				// lastTrackCalendar.get(Calendar.YEAR)
				// && trackStatusCalendar.get(Calendar.DAY_OF_YEAR) <=
				// lastTrackCalendar.get(Calendar.DAY_OF_YEAR))
				) {
					taskExportVo.setFailMessage("物流状态时间必须大于当前订单物流状态最后变更时间");
					trackingTaskExportVos.add(taskExportVo);
					return null;
				}
			}

		} catch (ParseException e1) {
			taskExportVo.setFailMessage("运单状态时间格式错误");
			trackingTaskExportVos.add(taskExportVo);
			return null;
		}
		return trackStatusTime;
	}

	/**
	 * 检查导入数据
	 * @param trackingTaskExportVos
	 * @param taskExportVo
	 * @param orderNo
	 * @param tarckNo
	 * @param trackStatus
	 * @param trackStatusDate
	 * @return
	 */
	private boolean checkData(List<TrackingTaskExportVo> trackingTaskExportVos, TrackingTaskExportVo taskExportVo, String orderNo, String tarckNo,
			String trackStatus, String trackStatusDate) {
		if (StringUtils.isEmpty(orderNo) && StringUtils.isEmpty(tarckNo)) {
			taskExportVo.setFailMessage("订单号与运单号不能都为空");
			trackingTaskExportVos.add(taskExportVo);
			return true;
		}

		if (StringUtils.isEmpty(trackStatus)) {
			taskExportVo.setFailMessage("物流轨迹状态不能为空");
			trackingTaskExportVos.add(taskExportVo);
			return true;
		}

		if (StringUtils.isEmpty(trackStatusDate)) {
			taskExportVo.setFailMessage("物流轨迹状态时间不能为空");
			trackingTaskExportVos.add(taskExportVo);
			return true;
		}
		return false;
	}

	private List<TrackingTask> getTrackingTasks(List<Integer> shippingParcelIdList) {
		final TrackingTask taskParamBean = new TrackingTask();
		taskParamBean.setStart(0);
		taskParamBean.setLimit(Integer.MAX_VALUE);
		taskParamBean.setParcelIdList(shippingParcelIdList);
		taskParamBean.setForceUseIndex(true);
		taskParamBean.setForceIndexName("idx_shipping_parcel_id");

		return mapper.find(taskParamBean);
	}

	private List<TrackStatusClassify> getTrackStatusClassifies(ArrayList<Integer> apiIdList) {
		final TrackStatusClassify classifyParamBean = new TrackStatusClassify();
		classifyParamBean.setStart(0);
		classifyParamBean.setLimit(Integer.MAX_VALUE);
		classifyParamBean.setTrackApiIdList(apiIdList);

		return statusClassifyMapper.select(classifyParamBean);
	}

	private List<ShippingWay> getShippingWays() {
		final ShippingWay shippingWayParamBean = new ShippingWay();
		shippingWayParamBean.setStart(0);
		shippingWayParamBean.setLimit(Integer.MAX_VALUE);
		return shippingWayMapper.queryList(shippingWayParamBean);
	}

	private List<ShippingParcel> getShippingParcels() {
		final ShippingParcel parcelParamBean = new ShippingParcel();
		parcelParamBean.setLimit(Integer.MAX_VALUE);
		parcelParamBean.setStart(0);
		return shippingParcelMapperl.queryList(parcelParamBean);
	}

	public int getTrackTaskCount(String apiCode) {
		return mapper.getTrackTaskCount(apiCode);
	}

	public List<TrackingTask> getTrackTaskList(String apiCode, int start, int limit) {
		return mapper.getTrackTaskList(apiCode, start, limit);
	}

	public int batchUpdateTrackTask(List<TrackingTask> trackingTasks) {
		for (TrackingTask trackingTask : trackingTasks) {
			if (ClassifyEnum.completeClassifySet.contains(trackingTask.getClassifyCode())) {
				trackingTask.setComplete(1);
			}
		}
		// 更新包裹归类状态TODO

		return mapper.batchUpdateTrackTask(trackingTasks);
	}

}
