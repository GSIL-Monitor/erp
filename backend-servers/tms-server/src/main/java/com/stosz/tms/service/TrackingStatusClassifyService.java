package com.stosz.tms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.stosz.tms.mapper.ShippingTrackApiMapper;
import com.stosz.tms.model.ShippingTrackApi;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.BeanMapper;
import com.stosz.tms.dto.TrackingStatusClassifyAddDto;
import com.stosz.tms.enums.ClassifyEnum;
import com.stosz.tms.mapper.BaseDictionaryMapper;
import com.stosz.tms.mapper.ShippingMapper;
import com.stosz.tms.mapper.TrackingStatusClassifyMapper;
import com.stosz.tms.model.Shipping;
import com.stosz.tms.model.TrackStatusClassify;
import com.stosz.tms.vo.TrackStatusClassifyListVo;
import com.stosz.tms.vo.TrackStatusClassifyVo;

@Service
public class TrackingStatusClassifyService {

	@Resource
	private TrackingStatusClassifyMapper mapper;

	@Resource
	private ShippingTrackApiMapper trackApiMapper;

	@Resource
	private BaseDictionaryMapper dictionaryMapper;


	public List<TrackStatusClassifyListVo> queryList(TrackStatusClassify trackStatusClassify) {

		final List<TrackStatusClassify> statusClassifies = mapper.select(trackStatusClassify);

		Set<Integer> trackApiIds = statusClassifies.stream().map(TrackStatusClassify::getTrackApiId).collect(Collectors.toSet());

		final List<ShippingTrackApi> trackApis = trackApiMapper.queryByIds(trackApiIds);

		final Map<Integer, List<ShippingTrackApi>>trackApiMapById = trackApis.stream().collect(Collectors.groupingBy(ShippingTrackApi::getId));

		final List<TrackStatusClassifyListVo> trackStatusClassifyListVos = BeanMapper.mapList(statusClassifies, TrackStatusClassifyListVo.class);

		trackStatusClassifyListVos.forEach(e -> {
			final Integer trackApiId = e.getTrackApiId();

			final List<ShippingTrackApi> trackApiList = trackApiMapById.get(trackApiId);

			// 没有找到数据
			if (trackApiList == null || trackApiList.isEmpty())
				return;

			final ShippingTrackApi trackApi = trackApiList.get(0);

			e.setTrackApiName(trackApi.getApiName());
		});

		return trackStatusClassifyListVos;
	}

	public int count(TrackStatusClassify trackStatusClassify) {
		return mapper.count(trackStatusClassify);
	}

	public void add(TrackingStatusClassifyAddDto statusClassifyAddDto) {
		final String statusClassifys = statusClassifyAddDto.getStatusClassifys();

		final String[] statusClassifyArr = statusClassifys.split("\n");

		List<TrackingStatusClassifyAddDto.StatusClassify> statusClassifyList = Lists.newArrayList();

		for (int i = 0; i < statusClassifyArr.length; i++) {
			String[] trackStatusStr = statusClassifyArr[i].split("\t");
			Assert.isTrue(trackStatusStr != null && (trackStatusStr.length == 2 || trackStatusStr.length == 3), "第" + (i + 1) + "行的格式有误,请检查");
			String shippingStatusCode = null;
			String shippingStatus = null;
			String classifyStatus = null;
			if (trackStatusStr.length == 2) {
				shippingStatus = trackStatusStr[0];
				classifyStatus = trackStatusStr[1];
			} else if (trackStatusStr.length >= 3) {
				shippingStatus = trackStatusStr[0];
				shippingStatusCode = trackStatusStr[1];
				classifyStatus = trackStatusStr[2];
			}
			final ClassifyEnum classifyEnum = ClassifyEnum.getEnumByName(classifyStatus);

			Assert.notNull(classifyEnum, "第" + (i + 1) + "行的erp状态标签为无效数据");

			TrackingStatusClassifyAddDto.StatusClassify classify = new TrackingStatusClassifyAddDto.StatusClassify();
			classify.setShippingStatus(shippingStatus);
			classify.setClassifyStatus(classifyEnum.getName());
			classify.setClassifyCode(classifyEnum.getCode());
			classify.setShippingStatusCode(shippingStatusCode);
			statusClassifyList.add(classify);
		}
		statusClassifyAddDto.setStatusClassifyList(statusClassifyList);
		Assert.isTrue(mapper.addStatus(statusClassifyAddDto) > 0, "保存物流状态失败");
	}

	public void updateEnable(TrackStatusClassify statusClassify) {
		Assert.isTrue(mapper.update(statusClassify) > 0, "更新物流状态失败");
	}

	public void update(TrackStatusClassify statusClassify) {
		Assert.isTrue(mapper.update(statusClassify) > 0, "更新物流状态失败");
	}

	/**
	 * 根据物流公司Code 查询物流状态
	 * @param shippingCode
	 * @return
	 */
	public List<TrackStatusClassify> queryClassifyByCode(String shippingCode) {
		ShippingTrackApi shippingTrackApi = trackApiMapper.getByCode(shippingCode);
		if (shippingTrackApi != null) {
			TrackStatusClassify statusClassify = new TrackStatusClassify();
			statusClassify.setTrackApiId(shippingTrackApi.getId());
			List<TrackStatusClassify> trackStatusClassifies = mapper.selectAllByParam(statusClassify);
			return trackStatusClassifies;
		}
		return null;
	}

	/**
	 * 获取到所有物流公司物流状态归类数据
	 * @return
	 */
	public Map<String, TrackStatusClassifyVo> getAllClassifyMap() {
		Map<String, TrackStatusClassifyVo> trackStatusVoMap = new HashMap<>();
		// 查询所有物流公司的归类数据
		List<TrackStatusClassify> trackStatusClassifies = mapper.selectAll();
		if (ArrayUtils.isEmpty(trackStatusClassifies)) {
			return trackStatusVoMap;
		}
		// 根据物流商的code分组
		Map<String, List<TrackStatusClassify>> trackStatusClassifyMap = trackStatusClassifies.stream()
				.collect(Collectors.groupingBy(TrackStatusClassify::getApiCode));
		for (Entry<String, List<TrackStatusClassify>> entry : trackStatusClassifyMap.entrySet()) {
			List<TrackStatusClassify> shippingClassifyList = entry.getValue();

			Map<String, TrackStatusClassify> trackStatusMap = new HashMap<>();
			List<TrackStatusClassify> patternTrackStatusList = new ArrayList<>();
			Map<String, TrackStatusClassify> trackStatusCodeMap = new HashMap<>();
			for (TrackStatusClassify shippingClassify : shippingClassifyList) {
				trackStatusMap.put(shippingClassify.getShippingStatus(), shippingClassify);
				trackStatusCodeMap.put(shippingClassify.getShippingStatusCode(), shippingClassify);

				if (shippingClassify.getStatusType() != null && shippingClassify.getStatusType().equals(2)) {
					patternTrackStatusList.add(shippingClassify);
				}
			}
			TrackStatusClassifyVo statusClassifyVo = new TrackStatusClassifyVo();
			statusClassifyVo.setTrackStatusMap(trackStatusMap);
			statusClassifyVo.setTrackStatusCodeMap(trackStatusCodeMap);
			statusClassifyVo.setPatternTrackStatusList(patternTrackStatusList);
			trackStatusVoMap.put(entry.getKey(), statusClassifyVo);
		}
		return trackStatusVoMap;
	}

	/**
	 * 查询单个物流公司的归类状态
	 * @param apiCode
	 * @return
	 */
	public Map<String, TrackStatusClassifyVo> getClassifyMapByShippingCode(String apiCode) {
		Map<String, TrackStatusClassifyVo> trackStatusVoMap = new HashMap<>();

		// 根据物流商Code获取所有归类状态
		List<TrackStatusClassify> shippingClassifyList = this.queryClassifyByCode(apiCode);
		if (ArrayUtils.isEmpty(shippingClassifyList)) {
			return trackStatusVoMap;
		}
		// 物流状态归类
		Map<String, TrackStatusClassify> trackStatusMap = new HashMap<>();
		// 正则表达式匹配
		List<TrackStatusClassify> patternTrackStatusList = new ArrayList<>();
		// 物流状态码归类
		Map<String, TrackStatusClassify> trackStatusCodeMap = new HashMap<>();
		for (TrackStatusClassify shippingClassify : shippingClassifyList) {
			trackStatusMap.put(shippingClassify.getShippingStatus(), shippingClassify);
			trackStatusCodeMap.put(shippingClassify.getShippingStatusCode(), shippingClassify);

			if (shippingClassify.getStatusType() != null && shippingClassify.getStatusType().equals(2)) {
				patternTrackStatusList.add(shippingClassify);
			}
		}
		TrackStatusClassifyVo statusClassifyVo = new TrackStatusClassifyVo();
		statusClassifyVo.setTrackStatusMap(trackStatusMap);
		statusClassifyVo.setTrackStatusCodeMap(trackStatusCodeMap);
		statusClassifyVo.setPatternTrackStatusList(patternTrackStatusList);
		trackStatusVoMap.put(apiCode, statusClassifyVo);
		return trackStatusVoMap;
	}

}
