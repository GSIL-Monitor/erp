package com.stosz.tms.web;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.stosz.olderp.task.SyncTrackInfoTask;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.BeanMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.ExcelUtil;
import com.stosz.plat.utils.ExeclRenderUtils;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.model.TrackingTask;
import com.stosz.tms.service.TrackingTaskService;
import com.stosz.tms.vo.ShippingParcelTaskListVo;
import com.stosz.tms.vo.TrackingTaskExportVo;
import com.stosz.tms.vo.TrackingTaskListVo;

@RestController
@RequestMapping("/tms/trackingTask")
public class TrackingTaskController extends AbstractController {

	@Resource
	private TrackingTaskService service;

	@Resource
	private StringRedisTemplate redisTemplate;
	
	@Resource
	private SyncTrackInfoTask syncTrackInfoTask;

	@RequestMapping(value = "query", method = RequestMethod.POST)
	public RestResult queryList(TrackingTask trackingTask) {
		RestResult restResult = new RestResult();
		int count = service.count(trackingTask);
		restResult.setTotal(count);
		if (count <= 0) {
			restResult.setCode(RestResult.OK);
			return restResult;
		}
//
//		final Integer start = trackingTask.getStart();
//		final Integer limit = trackingTask.getLimit();
//
//		// 开始位置
//		trackingTask.setStart((start == null || start <= 0) ? 0 : (start - 1) * limit);
//		// 需要显示的条数
//		trackingTask.setLimit((limit == null) ? 10 : limit);

		final List<TrackingTaskListVo> taskListVos = service.queryList(trackingTask);
		restResult.setItem(taskListVos);
		restResult.setCode(RestResult.OK);
		return restResult;
	}

	@RequestMapping(value = "/taskList", method = RequestMethod.GET)
	public RestResult selectTaskList(@RequestParam(name = "id", required = true) Integer id) {
		final List<ShippingParcelTaskListVo> taskListVos = service.selectTaskList(id);
		RestResult restResult = new RestResult();
		restResult.setItem(taskListVos);
		restResult.setCode(RestResult.OK);
		return restResult;
	}

	@RequestMapping(value = "/exportTemplate", method = RequestMethod.GET)
	public void exportTemplate(HttpServletResponse response) throws Exception {
		List<TrackingTaskExportVo> exportVos = Lists.newArrayList();

		TrackingTaskExportVo trackingTaskExportVo = new TrackingTaskExportVo();
		trackingTaskExportVo.setOrderNo("test");
		trackingTaskExportVo.setTrackNo("test");
		trackingTaskExportVo.setTrackStatus("test");
		trackingTaskExportVo.setTrackStatusTime("2018-01-01 01:01:01");

		exportVos.add(trackingTaskExportVo);

		String[] headers = { "订单号", "运单号", "物流轨迹状态", "状态变化时间" };
		String[] fileds = { "orderNo", "trackNo", "trackStatus", "trackStatusTime" };

		ExcelUtil.exportExcelAndDownload(response, "物流轨迹导入模板", "物流轨迹信息", headers, fileds, exportVos);

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public RestResult upload(
			@RequestParam(name = "file", required = true) MultipartFile file,
			@RequestParam(name = "shippingWayId", required = true) Integer shippingWayId,
			HttpServletResponse response) throws Exception {

		final List<List<String>> uploadData = ExeclRenderUtils.readExecl(file);

		final List<TrackingTaskExportVo> exportVos = service.importExeclData(uploadData,shippingWayId);

		RestResult restResult = new RestResult();
		restResult.setCode(RestResult.NOTICE);

		final String key = "task_import_fail_" + new Date().getTime();

		if (CollectionUtils.isNotNullAndEmpty(exportVos)) {
			redisTemplate.opsForValue().set(key, JsonUtils.toJson(exportVos), 2, TimeUnit.MINUTES);
			restResult.setItem(key);
		}

		return restResult;
	}

	@RequestMapping(value = "/exportFailExecl", method = RequestMethod.POST)
	public void exportFailExecl(String key, HttpServletResponse response) throws Exception {
		Assert.notNull(key, "key不能为空");

		if (redisTemplate.hasKey(key)) {
			final String failDataJson = redisTemplate.opsForValue().get(key);
			final List failDataList = JsonUtils.jsonToObject(failDataJson, List.class);

			final List<TrackingTaskExportVo> exportVos = BeanMapper.mapList(failDataList, TrackingTaskExportVo.class);

			String[] headers = { "订单号", "运单号", "物流轨迹状态", "状态变化时间", "错误原因" };
			String[] fileds = { "orderNo", "trackNo", "trackStatus", "trackStatusTime", "failMessage" };

			ExcelUtil.exportExcelAndDownload(response, "物流轨迹导入错误数据", "错误数据", headers, fileds, exportVos);
		}
	}
	
	@RequestMapping("syncOrderShipping")
	public void syncOrderShipping() {
		syncTrackInfoTask.syncOrderShipping();
	}
	

}
