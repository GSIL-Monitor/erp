package com.stosz.tms.service;

import com.stosz.plat.common.ResultBean;
import com.stosz.plat.enums.LabelTypeEnum;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.product.ext.model.LabelObject;
import com.stosz.product.ext.service.ILabelObjectServcie;
import com.stosz.tms.dto.TransportRequest;
import com.stosz.tms.enums.AllowedProductTypeEnum;
import com.stosz.tms.mapper.ShippingMapper;
import com.stosz.tms.mapper.ShippingStoreRelationMapper;
import com.stosz.tms.mapper.ShippingWayMapper;
import com.stosz.tms.mapper.ShippingZoneStoreRelMapper;
import com.stosz.tms.model.*;
import com.stosz.tms.utils.ShippingStoreLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShippingWayService {

	private static final Logger logger = LoggerFactory.getLogger(ShippingWayService.class);

	@Autowired
	private ShippingWayMapper mapper;

	@Resource
	private ShippingMapper shippingMapper;

	@Resource
	private ShippingStoreRelationMapper shippingStoreRelationMapper;

	@Resource
	private ShippingZoneStoreRelMapper zoneStoreRelMapper;

	@Autowired
	private ShippingScheduleService shippingScheduleService;

	@Autowired
	private ShippingAllocationTemplateService allocationTemplateService;

	@Autowired
	private ShippingZoneStoreRelService shippingZoneStoreRelService;

	@Autowired
	private ILabelObjectServcie labelObjectServcie;

	@Autowired
	private ShippingTrackApiService shippingTrackApiService;

	/**
	 * 新增物流线路
	 * @param shippingWay
	 * @return
	 */
	public int addShippingWay(ShippingWay shippingWay) {
		ShippingWay shippingWayByCode = mapper.getByCode(shippingWay.getShippingWayCode());
		Assert.isTrue(shippingWayByCode == null, "该物流代码已存在");

		Shipping shippingById = shippingMapper.getShippingById(shippingWay.getShippingId());
		Assert.notNull(shippingById, "查找物流商失败!");

		// 物流轨迹API
		Integer apiId = shippingWay.getApiId();
		ShippingTrackApi shippingTrackApi = shippingTrackApiService.queryTrackApiById(apiId);
		Assert.notNull(shippingTrackApi, "查询物流轨迹API失败！");

		shippingWay.setShippingCode(shippingById.getShippingCode());
		shippingWay.setState(0);
		shippingWay.setApiCode(shippingTrackApi.getApiCode());
		return mapper.addShippingWay(shippingWay);
	}

	public int count(ShippingWay shippingWay) {
		return mapper.count(shippingWay);
	}

	public List<ShippingWay> queryList(ShippingWay shippingWay) {
		if (!StringUtils.hasText(shippingWay.getOrderBy())) {
			shippingWay.setOrderBy(" update_at desc,create_at");
		}
		return mapper.queryList(shippingWay);
	}

	/**
	 * 更新状态
	 */
	public void updateState(ShippingWay shippingWay) {
		if (shippingWay.getState() == 2) {
			List<ShippingStoreRel> shippingStoreRels = shippingStoreRelationMapper.selectByShippingWayId(shippingWay.getId());
			Assert.isTrue(CollectionUtils.isEmpty(shippingStoreRels), "该物流线路有正在被仓库使用，不能失效!");
		}
		Assert.isTrue(mapper.updateState(shippingWay) > 0, "更新物流线路状态失败!");
	}

	public void update(ShippingWay shippingWay) {
		// 物流轨迹API
		Integer apiId = shippingWay.getApiId();
		ShippingTrackApi shippingTrackApi = shippingTrackApiService.queryTrackApiById(apiId);
		Assert.notNull(shippingTrackApi, "查询物流轨迹API失败！");

		shippingWay.setApiCode(shippingTrackApi.getApiCode());
		Assert.isTrue(mapper.update(shippingWay) > 0, "更新物流线路失败!");
	}

	public List<ShippingWay> queryByIds(Set<Integer> ids) {
		return mapper.queryByIds(ids);
	}

	public void delete(Integer id) {
		Assert.isTrue(mapper.delete(id) > 0, "删除物流线路失败!");
	}

	/**
	 * 获取物流线路，根据仓库ID去掉该仓库已对应的物流线路
	 * @param storeId
	 * @param type 根据类型区分拿仓库ID去哪里查找数据筛选
	 * @return
	 */
	public List<ShippingWay> queryExceptStoreExist(Integer storeId, Integer type) {
		Set<Integer> shippingWayIds = null;

		if (type == 1) {
			List<ShippingStoreRel> storeRelList = shippingStoreRelationMapper.selectStoreRelByStoreId(storeId);
			shippingWayIds = storeRelList.stream().map(ShippingStoreRel::getShippingWayId).collect(Collectors.toSet());
		} else if (type == 2) {
			final List<ShippingZoneStoreRel> shippingZoneStoreRelList = zoneStoreRelMapper.queryByStoreId(storeId);
			shippingWayIds = shippingZoneStoreRelList.stream().map(ShippingZoneStoreRel::getShippingWayId).collect(Collectors.toSet());
		}

		if (shippingWayIds != null && !shippingWayIds.isEmpty())
			return mapper.queryByExceptIds(shippingWayIds);

		ShippingWay shippingWay = new ShippingWay();
		shippingWay.setLimit(100000);
		shippingWay.setStart(0);
		return mapper.queryList(shippingWay);
	}

	/**
	 * 根据排程获取到排序后的物流线路
	 * @param ids 物流线路ID
	 * @param wmsId 仓库ID
	 * @return
	 */
	public List<ShippingStoreRel> queryAssignShipping(List<ShippingStoreRel> shippingStoreRels, Integer wmsId) {
		List<Integer> shippingIdList = shippingStoreRels.stream().map(ShippingStoreRel::getShippingWayId).collect(Collectors.toList());
		String scheduleDate = DateUtils.format(new Date(), "yyyy-MM-dd");
		// 获取到今日的排程
		List<ShippingSchedule> shippingSchedules = shippingScheduleService.queryAssignSchedule(shippingIdList, wmsId, scheduleDate);
		// 根据物流线路获取模板排程
		List<ShippingAllocationTemplate> templates = allocationTemplateService.queryAssignTemplate(shippingIdList, wmsId);

		Map<String, ShippingSchedule> scheduleMap = shippingSchedules.stream()
				.collect(Collectors.toMap(item -> StringUtil.concat(item.getWmsId(), "_", item.getShippingWayId()), Function.identity()));

		Map<String, ShippingAllocationTemplate> templateMap = templates.stream()
				.collect(Collectors.toMap(item -> StringUtil.concat(item.getWmsId(), "_", item.getShippingWayId()), Function.identity()));

		Iterator<ShippingStoreRel> iterator = shippingStoreRels.iterator();
		while (iterator.hasNext()) {
			ShippingStoreRel shippingStoreRel = iterator.next();
			Integer shippingWayId = shippingStoreRel.getShippingWayId();
			String wmsWayKey = StringUtil.concat(wmsId, "_", shippingWayId);
			String anyWmsWayKey = StringUtil.concat("-1", "_", shippingWayId);

			ShippingSchedule shippingSchedule = scheduleMap.get(wmsWayKey);
			ShippingAllocationTemplate template = templateMap.get(wmsWayKey);

			ShippingSchedule anyWmsShippingSchedule = scheduleMap.get(anyWmsWayKey);
			ShippingAllocationTemplate anyWmsTemplate = templateMap.get(anyWmsWayKey);

			if (shippingSchedule != null) {// 仓库ID+物流线路 匹配 排程
				shippingStoreRel.setMatchWay(0);
				shippingStoreRel.setSchedule(shippingSchedule);
				shippingStoreRel.setSorted(shippingSchedule.getSorted());
				shippingStoreRel.setEachCount(shippingSchedule.getEachCount());
			} else if (template != null) {// 仓库ID+物流线路 匹配到模板排程
				shippingStoreRel.setMatchWay(1);
				shippingStoreRel.setTemplate(template);
				shippingStoreRel.setSorted(template.getSorted());
				shippingStoreRel.setEachCount(0);
			} else if (anyWmsShippingSchedule != null) {// 任意仓+物流线路 匹配的排程
				shippingStoreRel.setMatchWay(2);
				shippingStoreRel.setSchedule(anyWmsShippingSchedule);
				shippingStoreRel.setSorted(anyWmsShippingSchedule.getSorted());
				shippingStoreRel.setEachCount(anyWmsShippingSchedule.getEachCount());
			} else if (anyWmsTemplate != null) {// 任意仓+物流线路 匹配到模板排程
				shippingStoreRel.setMatchWay(3);
				shippingStoreRel.setTemplate(anyWmsTemplate);
				shippingStoreRel.setSorted(anyWmsTemplate.getSorted());
				shippingStoreRel.setEachCount(0);
			} else {
				// 如果没有排程也没有排程模板，则物流线路不可用
				iterator.remove();
				logger.info("queryAssignShipping() shippingWayId={},未匹配到排程", shippingWayId);
			}
		}
		if (ArrayUtils.isNotEmpty(shippingStoreRels)) {
			// 优先根据eachCount 排序 eachCount值越小越先匹配 (eachCount 代表已经轮询过的次数)
			// sorted 值越到大 越先匹配
			shippingStoreRels.sort((src, target) -> {
				if (src.getEachCount() > target.getEachCount()) {
					return 1;
				} else if (src.getEachCount() < target.getEachCount()) {
					return -1;
				} else {
					if (src.getSorted() > target.getSorted()) {
						return -1;
					} else if (src.getSorted() < target.getSorted()) {
						return 1;
					}
					return 0;
				}
			});
		}
		return shippingStoreRels;
	}

	/**
	 * 根据仓库ID 区域ID  与 产品标签  匹配物流线路 
	 * @param wmsId
	 * @param zoneId
	 * @param productLabelMap
	 * @return
	 */
	public ResultBean<List<ShippingStoreRel>> queryUsableShippingWay(TransportRequest request, Map<String, List<LabelObject>> productLabelMap) {
		ResultBean<List<ShippingStoreRel>> resultBean = new ResultBean<>();
		Integer warehouseId = request.getWarehouseId();
		Integer zoneId = request.getZoneId();
		Integer orderId = request.getOrderId();
		List<ShippingZoneStoreRel> zoneStoreRels = shippingZoneStoreRelService.queryShippingZoneStore(warehouseId, zoneId);
		if (ArrayUtils.isEmpty(zoneStoreRels)) {
			logger.info("queryUsableShippingWay() 指派物流失败,订单号={},仓库ID={},区域Id={},未找到对应的物流线路", orderId, warehouseId, zoneId);
			return resultBean.fail(String.format("未匹配仓库Id=%s,区域ID=%s,对应的物流线路", warehouseId, zoneId));
		}
		Set<Integer> shippingWayIdSet = zoneStoreRels.stream().map(ShippingZoneStoreRel::getShippingWayId).collect(Collectors.toSet());
		// 查询物流线路
		List<ShippingWay> shippingWays = mapper.queryByIds(shippingWayIdSet);
		Map<Integer, ShippingWay> shippingWayMap = shippingWays.stream().collect(Collectors.toMap(ShippingWay::getId, Function.identity()));
		shippingWays.clear();

		// 根据物流线路ID 仓库ID 查询仓库物流关系
		List<ShippingStoreRel> shippingStoreRels = shippingStoreRelationMapper.selectStoreRelByWayIdList(shippingWayIdSet, warehouseId);
		Map<Integer, ShippingStoreRel> shippingStoreMap = shippingStoreRels.stream()
				.collect(Collectors.toMap(ShippingStoreRel::getId, Function.identity()));

		// 获取到仓库物流线路的所有标签
		List<Integer> shippingStoreIds = shippingStoreRels.stream().map(ShippingStoreRel::getId).collect(Collectors.toList());
		Map<Integer, List<LabelObject>> labelObjectMap = labelObjectServcie.queryListLabelObject(shippingStoreIds, LabelTypeEnum.Product);
		// 避免，仓库物流线路没有打标签的情况(物流线路不区分货物类型，不设置禁运类型，则没有标签，但是物流线路是可用)
		shippingStoreIds.forEach(item -> {
			if (!labelObjectMap.containsKey(item)) {
				labelObjectMap.put(item, new ArrayList<>());
			}
		});

		List<ShippingStoreLabel> shippingStoreLabels = labelObjectMap.entrySet().stream().map(item -> {
			ShippingStoreRel shippingStoreRel = shippingStoreMap.get(item.getKey());
			ShippingWay shippingWay = shippingWayMap.get(shippingStoreRel.getShippingWayId());
			shippingStoreRel.setShippingWay(shippingWay);
			return ShippingStoreLabel.groupLabel(shippingStoreRel, item.getValue());
		}).collect(Collectors.toList());

		shippingStoreRels.clear();
		labelObjectMap.clear();
		shippingStoreIds.clear();

		// 获取到订单上的标签
		Set<Integer> orderLabelSet = new HashSet<>();
		for (Entry<String, List<LabelObject>> entry : productLabelMap.entrySet()) {
			List<LabelObject> labels = entry.getValue();
			if (ArrayUtils.isEmpty(labels)) {
				logger.info("queryUsableShippingWay() 指派物流失败,订单id={},SKU={},没有标签", orderId, entry.getKey());
				return resultBean.fail(String.format("订单id={},SKU={},没有标签", orderId, entry.getKey()));
			}
			for (LabelObject label : labels) {
				orderLabelSet.add(label.getLabelId());
			}
		}
		// 根据产品上的标签 匹配 仓库物流上的标签
		List<ShippingStoreRel> usableShippingStoreList = getProductLabelMathStore(request, shippingStoreLabels, orderLabelSet);
		// 检测区域是否可配送货物类型
		usableShippingStoreList = getStoreZoneConnectivity(zoneStoreRels, usableShippingStoreList);

		resultBean.setCode(ResultBean.OK);
		resultBean.setItem(usableShippingStoreList);
		return resultBean;
	}

	/**
	 * 检测区域是否可配送货物类型
	 * @param zoneStoreRels
	 * @param usableShippingStoreList
	 * @return
	 */
	private List<ShippingStoreRel> getStoreZoneConnectivity(List<ShippingZoneStoreRel> zoneStoreRels,
			List<ShippingStoreRel> usableShippingStoreList) {
		// 检测区域连通性
		Map<String, ShippingZoneStoreRel> shippingZoneStoreMap = zoneStoreRels.stream()
				.collect(Collectors.toMap(item -> StringUtil.concat(item.getShippingWayId(), "_" + item.getWmsId()), Function.identity()));
		Iterator<ShippingStoreRel> iterator = usableShippingStoreList.iterator();
		while (iterator.hasNext()) {
			ShippingStoreRel shippingStoreRel = iterator.next();
			String key = StringUtil.concat(shippingStoreRel.getShippingWayId(), "_", shippingStoreRel.getWmsId());
			ShippingZoneStoreRel zoneStoreRel = shippingZoneStoreMap.get(key);
			// 配送的产品类型
			AllowedProductTypeEnum productType = shippingStoreRel.getProductType();
			// 区域 仓库可配送的类型
			AllowedProductTypeEnum zoneStoreType = AllowedProductTypeEnum.fromId(zoneStoreRel.getAllowedProductType());
			if (zoneStoreType == null) {
				logger.info("getStoreZoneConnectivity() wmsId={},zoneId={},shippingWayId={},货物类型未设置", zoneStoreRel.getWmsId(),
						zoneStoreRel.getZoneId(), zoneStoreRel.getShippingWayId());
				iterator.remove();
				continue;
			}
			if (zoneStoreType == AllowedProductTypeEnum.all) {// 支持所有类型
				continue;
			}
			if (zoneStoreType != productType) {
				iterator.remove();
				logger.info("getStoreZoneConnectivity() wmsId={},zoneId={},shippingWayId={},可配送的类型={},产品的类型={},无法配送", zoneStoreRel.getWmsId(),
						zoneStoreRel.getZoneId(), zoneStoreRel.getShippingWayId(), zoneStoreType, productType);
				continue;
			}
		}
		return usableShippingStoreList;
	}

	/**
	 * 根据产品上的标签 匹配仓库物流上的标签
	 * @param request
	 * @param shippingStoreLabels
	 * @param orderLabelSet
	 * @return
	 */
	private List<ShippingStoreRel> getProductLabelMathStore(TransportRequest request, List<ShippingStoreLabel> shippingStoreLabels,
			Set<Integer> orderLabelSet) {
		List<ShippingStoreRel> usableShippingStoreList = new ArrayList<>();
		for (ShippingStoreLabel storeLabel : shippingStoreLabels) {
			// 物流线路
			ShippingStoreRel shippingStoreRel = storeLabel.getShippingStoreRel();
			// 产品类型
			int allowedProductType = shippingStoreRel.getAllowedProductType();
			// 面单类型
			Integer expressType = shippingStoreRel.getExpressSheetType();
			// 获取到禁运的标签
			Set<Integer> forbiddenLables = storeLabel.getForbiddenLables();
			if (ArrayUtils.isNotEmpty(forbiddenLables)) {
				// 如果产品标签里面出现了任意一个禁运的标签,则物流线路不可用
				boolean forbiddenStatus = orderLabelSet.stream().anyMatch(item -> forbiddenLables.contains(item));
				if (forbiddenStatus) {
					continue;
				}
			}
			AllowedProductTypeEnum productType = null;
			if (expressType == 0) {// 不区分货物类型
				productType = AllowedProductTypeEnum.all;
			} else {
				productType = AllowedProductTypeEnum.fromId(allowedProductType);
				if (productType == null) {
					logger.info("queryUsableShippingWay() orderId={},shippingWayId={},shippingStoreRelId={},allowedProductType={} undefined",
							request.getOrderId(), shippingStoreRel.getShippingWayId(), shippingStoreRel.getId(), allowedProductType);
					continue;
				}
				if (AllowedProductTypeEnum.general == productType) {// 普货
					// 获取到普货可配的标签
					Set<Integer> generalLabels = storeLabel.getGeneralLabels();
					boolean generalStatus = orderLabelSet.stream().anyMatch(item -> !generalLabels.contains(item));
					if (generalStatus) {
						continue;
					}
				} else if (AllowedProductTypeEnum.sensitive == productType) {// 特货
					// 获取到特货可配的标签
					Set<Integer> specialLabels = storeLabel.getSpecialLabels();
					boolean specialStatus = orderLabelSet.stream().anyMatch(item -> !specialLabels.contains(item));
					if (specialStatus) {
						continue;
					}
				} else if (AllowedProductTypeEnum.all == productType) {// 所有
					// 获取到普货可配的标签
					Set<Integer> generalLabels = storeLabel.getGeneralLabels();
					boolean generalStatus = orderLabelSet.stream().anyMatch(item -> !generalLabels.contains(item));
					if (generalStatus) {
						// 获取到特货可配的标签
						Set<Integer> specialLabels = storeLabel.getSpecialLabels();
						boolean specialStatus = orderLabelSet.stream().anyMatch(item -> !specialLabels.contains(item));
						if (specialStatus) {
							continue;
						} else {
							// 走特货发货
							productType = AllowedProductTypeEnum.sensitive;
						}
					} else {
						// 走普货发货
						productType = AllowedProductTypeEnum.general;
					}
				}
			}
			shippingStoreRel.setProductType(productType);
			usableShippingStoreList.add(shippingStoreRel);
		}
		return usableShippingStoreList;
	}

	/**
	 * 根据物流线路Code查询
	 * @param shippingWayCode
	 * @return
	 */
	public ShippingWay queryShippingWayByCode(String shippingWayCode) {
		return mapper.getByCode(shippingWayCode);
	}

	/**
	 * 根据物流线路name查询
	 * @param shippingWayName
	 * @return
	 */
	public ShippingWay queryShippingWayByName(String shippingWayName) {
		return mapper.getByName(shippingWayName);
	}

	public static void main(String[] args) {

		List<Integer> a = Arrays.asList(1, 2, 4, 5);
		List<Integer> b = Arrays.asList(1, 2, 4, 3);

		a.sort((src, target) -> {
			if (src > target) {
				return -1;
			} else if (src < target) {
				return 1;
			} else {
				return 0;
			}
		});

		List<ShippingStoreRel> shippingStoreRels = new ArrayList<>();

		ShippingStoreRel shippingStoreRel = new ShippingStoreRel();
		shippingStoreRel.setEachCount(1);
		shippingStoreRel.setSorted(10);
		shippingStoreRels.add(shippingStoreRel);

		ShippingStoreRel shippingStoreRel1 = new ShippingStoreRel();
		shippingStoreRel1.setEachCount(0);
		shippingStoreRel1.setSorted(5);
		shippingStoreRels.add(shippingStoreRel1);

		ShippingStoreRel shippingStoreRel2 = new ShippingStoreRel();
		shippingStoreRel2.setEachCount(2);
		shippingStoreRel2.setSorted(5);
		shippingStoreRels.add(shippingStoreRel2);

		ShippingStoreRel shippingStoreRel3 = new ShippingStoreRel();
		shippingStoreRel3.setEachCount(0);
		shippingStoreRel3.setSorted(10);
		shippingStoreRels.add(shippingStoreRel3);

		shippingStoreRels.sort((src, target) -> {
			if (src.getEachCount() > target.getEachCount()) {
				return 1;
			} else if (src.getEachCount() < target.getEachCount()) {
				return -1;
			} else {
				if (src.getSorted() > target.getSorted()) {
					return -1;
				} else if (src.getSorted() < target.getSorted()) {
					return 1;
				}
				return 0;
			}
		});

		logger.info(JsonUtils.toJson(shippingStoreRels));
	}

}
