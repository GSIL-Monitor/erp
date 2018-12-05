package com.stosz.tms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.xmlbeans.impl.regex.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.stosz.plat.common.ResultBean;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.BeanMapper;
import com.stosz.tms.dto.ShippingTrackingNoSectionAddDto;
import com.stosz.tms.dto.ShippingTrackingNumberListAddDto;
import com.stosz.tms.enums.AllowedProductTypeEnum;
import com.stosz.tms.mapper.ShippingTrackingNoListMapper;
import com.stosz.tms.mapper.ShippingWayMapper;
import com.stosz.tms.model.ShippingTrackingNoList;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.vo.ShippingTrackingGroupCountListVo;
import com.stosz.tms.vo.ShippingTrackingNoCountVo;
import com.stosz.tms.vo.ShippingTrackingNoListListVo;

@Service
public class ShippingTrackingNoListService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ShippingTrackingNoListMapper mapper;

	@Resource
	private ShippingWayMapper shippingWayMapper;

	/**
	 * 占用一个运单号
	 * @param shippingWayId 物流线路ID
	 * @param wmsId  仓库ID
	 * @param productType 
	 */
	public ResultBean<ShippingTrackingNoList> occupyUsableTrackNo(Integer shippingWayId, Integer wmsId, AllowedProductTypeEnum productType) {
		ResultBean<ShippingTrackingNoList> resultBean = new ResultBean<>();
		int retryCount = 10;
		do {
			List<ShippingTrackingNoList> trackNoList = mapper.queryUsableTrackNo(shippingWayId, wmsId, productType.ordinal());
			if (ArrayUtils.isEmpty(trackNoList)) {// 没有可用的运单号
				break;
			}
			ShippingTrackingNoList updateTrackingNoList = trackNoList.get(0);
			updateTrackingNoList.setTrackStatus(1);
			int occupyCount = mapper.updateTrackingUse(updateTrackingNoList);
			if (occupyCount > 0) {
				resultBean.setItem(updateTrackingNoList);
				resultBean.setCode(ResultBean.OK);
				return resultBean;
			}
			retryCount--;
		} while (retryCount > 0);
		// 未匹配到运单号
		resultBean.setCode(ResultBean.FAIL);
		return resultBean;

	}

	public List<ShippingTrackingNoListListVo> queryList(ShippingTrackingNoList shippingTrackingNoList) {
		final List<ShippingTrackingNoList> shippingZoneStoreRels = mapper.queryList(shippingTrackingNoList);

		Set<Integer> shippingWayIds = shippingZoneStoreRels.stream().map(ShippingTrackingNoList::getShippingWayId).collect(Collectors.toSet());

		List<ShippingWay> shippingWays = shippingWayMapper.queryByIds(shippingWayIds);

		final Map<Integer, List<ShippingWay>> shippingWayMapById = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getId));

		final List<ShippingTrackingNoListListVo> zoneStoreRelationListVos = BeanMapper.mapList(shippingZoneStoreRels,
				ShippingTrackingNoListListVo.class);

		zoneStoreRelationListVos.forEach(e -> {
			final Integer shippingWayId = e.getShippingWayId();

			final List<ShippingWay> wayList = shippingWayMapById.get(shippingWayId);

			// 没有找到数据
			if (wayList == null || wayList.isEmpty())
				return;

			final ShippingWay shippingWay = wayList.get(0);

			e.setShippingWayCode(shippingWay.getShippingWayCode());
			e.setShippingWayName(shippingWay.getShippingWayName());
		});

		return zoneStoreRelationListVos;
	}

	public int count(ShippingTrackingNoList shippingTrackingNoList) {
		return mapper.count(shippingTrackingNoList);
	}

	public void sectionAdd(ShippingTrackingNoSectionAddDto sectionAddDto) {

		ShippingTrackingNoList paramBean = new ShippingTrackingNoList();
		paramBean.setStart(0);
		paramBean.setLimit(Integer.MAX_VALUE);
		paramBean.setShippingWayId(sectionAddDto.getShippingWayId());

		final List<ShippingTrackingNoList> shippingZoneStoreRels = mapper.queryList(paramBean);


		final List<String> trackNumbers = shippingZoneStoreRels.stream().map(ShippingTrackingNoList::getTrackNumber).collect(Collectors.toList());

		logger.info(JSONUtils.toJSONString(trackNumbers));
		//TODO 检验运单号唯一
		 int i = 0;
		try {
			i = mapper.sectionAdd(sectionAddDto, trackNumbers);
		} catch (Exception e) {
			if (e.getCause().getCause() instanceof InvocationTargetException){
				throw new RuntimeException("进行添加的运单号都已存在该物流线路下");
			}else{
				throw new RuntimeException(e);
			}
		}
		Assert.isTrue(i > 0, "保存运单号失败!");
	}

	public void numberListAdd(ShippingTrackingNumberListAddDto numberListAddDto) {
		ShippingTrackingNoList paramBean = new ShippingTrackingNoList();
		paramBean.setStart(0);
		paramBean.setLimit(Integer.MAX_VALUE);
		paramBean.setShippingWayId(numberListAddDto.getShippingWayId());

		final List<ShippingTrackingNoList> shippingZoneStoreRels = mapper.queryList(paramBean);

		final List<String> trackNumbers = shippingZoneStoreRels.stream().map(ShippingTrackingNoList::getTrackNumber).collect(Collectors.toList());

		final List<String> notExistNumberList = numberListAddDto.getTrackNumberList().stream().filter(e -> !trackNumbers.contains(e)).collect(Collectors.toList());

		Assert.notEmpty(notExistNumberList,"导入的运单号都已存在该物流线路下");

		Assert.isTrue(mapper.numberListAdd(numberListAddDto) > 0, "保存运单号失败!");
	}

	public void disable(int id, String modifier, Integer modifierId) {
		Assert.isTrue(mapper.disable(id, modifier, modifierId) > 0, "失效运单号失败");
	}

	public ShippingTrackingNoCountVo getNoCount() {
		ShippingTrackingNoList paramBean = new ShippingTrackingNoList();
		final int allCount = mapper.count(paramBean);

		paramBean.setTrackStatus(0);

		final int unsedCount = mapper.count(paramBean);
		ShippingTrackingNoCountVo countVo = new ShippingTrackingNoCountVo();
		countVo.setAllCount(allCount);
		countVo.setUnusedCount(unsedCount);

		return countVo;
	}

	public List<ShippingTrackingGroupCountListVo> getGroupCountList(Integer shippingWayId) {
		ShippingTrackingNoList paramBean = new ShippingTrackingNoList();

		paramBean.setStart(0);
		paramBean.setLimit(Integer.MAX_VALUE);


		if (shippingWayId != null)
			paramBean.setShippingWayId(shippingWayId);

		final List<ShippingTrackingNoList> trackingNoLists = mapper.queryList(paramBean);

		if (trackingNoLists == null || trackingNoLists.isEmpty())
			return Collections.emptyList();

		Set<Integer> shippingWayIds = trackingNoLists.stream().map(ShippingTrackingNoList::getShippingWayId).collect(Collectors.toSet());

		List<ShippingWay> shippingWays = shippingWayMapper.queryByIds(shippingWayIds);

		final Map<Integer, ShippingWay> shippingWayMapById = shippingWays.stream().collect(Collectors.toMap(ShippingWay::getId, Function.identity()));

		final Map<Integer, List<ShippingTrackingNoList>> trackingMapByShippingWayId = trackingNoLists.stream()
				.collect(Collectors.groupingBy(ShippingTrackingNoList::getShippingWayId));

		List<ShippingTrackingGroupCountListVo> countListVos = Lists.newArrayList();

		trackingMapByShippingWayId.forEach((k, v) -> {
			final ShippingWay shippingWay = shippingWayMapById.get(k);

			// 没有找到数据
			if (shippingWay == null)
				return;

			final ShippingTrackingGroupCountListVo countListVo = new ShippingTrackingGroupCountListVo();

			countListVo.setShippingWayId(k);
			countListVo.setShippingWayName(shippingWay.getShippingWayName());
			countListVo.setAllCount(v.size());
			countListVo.setProductType(v.get(0).getProductType());

			final Map<Integer, List<ShippingTrackingNoList>> trackingMapByStatus = v.stream()
					.collect(Collectors.groupingBy(ShippingTrackingNoList::getTrackStatus));

			trackingMapByStatus.forEach((s, sv) -> {
				switch (s) {
				case 0:
					countListVo.setUnusedCount(sv.size());
					break;
				case 1:
					countListVo.setUsedCount(sv.size());
					break;
				case 2:
					countListVo.setDisableCount(sv.size());
					break;
				}
			});
			countListVos.add(countListVo);
		});
		return countListVos;
	}

	/**
	 * 查询可用的运单号数量
	 * @param shippingWayId 物流线路ID
	 * @param wmsId  仓库ID
	 * @param productType  产品类型  0 所有 1 普货 2 特货
	 * @return
	 */
	public int queryUsableTrackNoCount(Integer shippingWayId, Integer wmsId, AllowedProductTypeEnum productType) {
		return mapper.queryUsableTrackCount(shippingWayId, wmsId, productType.ordinal());
	}


}
