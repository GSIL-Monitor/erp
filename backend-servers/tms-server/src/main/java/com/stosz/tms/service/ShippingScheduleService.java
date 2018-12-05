package com.stosz.tms.service;

import com.stosz.plat.utils.BeanMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.tms.enums.AllowedProductTypeEnum;
import com.stosz.tms.mapper.ShippingScheduleMapper;
import com.stosz.tms.mapper.ShippingWayMapper;
import com.stosz.tms.model.ShippingAllocationTemplate;
import com.stosz.tms.model.ShippingSchedule;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.vo.ShippingScheduleListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShippingScheduleService {

	@Autowired
	private ShippingScheduleMapper mapper;

	@Resource
	private ShippingWayMapper shippingWayMapper;

	@Resource
	private IStockService stockService;

	@Resource
	private IStorehouseService storehouseService;

	private byte[] lockObject = new byte[0];

	private byte[] clearOnceNumObject = new byte[0];

	public List<ShippingSchedule> queryAssignSchedule(List<Integer> shippingWayIds, Integer wmsId, String scheduleDate) {
		List<ShippingSchedule> schedules=mapper.queryAssignSchedule(shippingWayIds, wmsId, scheduleDate);
		return schedules;
	}

	/**
	 * 查询排程
	 * @param shippingWayId 物流方式Id
	 * @param wmsId 仓库ID
	 * @param scheduleDate 排程时间
	 * @return
	 */
	public ShippingSchedule querySchedule(Integer shippingWayId, Integer wmsId, String scheduleDate) {
		return mapper.querySchedule(shippingWayId, wmsId, scheduleDate);
	}

	/**
	 * 创建今日的排程
	 * @param template
	 * @return
	 */
	public ShippingSchedule createNowSchedule(ShippingAllocationTemplate template) {
		Date nowDate = new Date();
		ShippingSchedule shippingSchedule = new ShippingSchedule();
		shippingSchedule.setScheduleDate(nowDate);
		shippingSchedule.setShippingWayId(template.getShippingWayId());
		shippingSchedule.setTemplateId(template.getId());
		shippingSchedule.setWmsId(template.getWmsId());
		shippingSchedule.setGeneralCargoNum(template.getGeneralCargoNum());
		shippingSchedule.setSpecialCargoNum(template.getSpecialCargoNum());
		shippingSchedule.setCargoNum(template.getCargoNum());
		shippingSchedule.setTodayGeneralNum(0);
		shippingSchedule.setTodaySpecialNum(0);
		shippingSchedule.setTodayCargoNum(0);
		shippingSchedule.setEachGeneralNum(template.getEachGeneralNum());
		shippingSchedule.setEachSpecialNum(template.getEachSpecialNum());
		shippingSchedule.setEachCargoNum(template.getEachCargoNum());
		shippingSchedule.setOnceSpecialNum(0);
		shippingSchedule.setOnceGeneralNum(0);
		shippingSchedule.setOnceCargoNum(0);
		shippingSchedule.setSorted(template.getSorted());
		shippingSchedule.setVersion(0);
		shippingSchedule.setEachCount(0);
		shippingSchedule.setEnable(1);
		synchronized (lockObject) {
			ShippingSchedule schedule = mapper.querySchedule(template.getShippingWayId(), template.getWmsId(),
					DateUtils.format(nowDate, "yyyy-MM-dd"));
			if (schedule != null) {
				return schedule;
			}
			mapper.insert(shippingSchedule);
		}
		return shippingSchedule;
	}

	/**
	 * 修改排程今日已配特货数量
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "tmsTransactionManager", rollbackFor = Exception.class)
	public int updateScheduleNum(ShippingSchedule schedule, AllowedProductTypeEnum productType, int addNum) {
		schedule.setAddNum(addNum);
		if (productType == AllowedProductTypeEnum.sensitive) {
			return mapper.updateSpecialNum(schedule);
		} else if (productType == AllowedProductTypeEnum.general) {
			return mapper.updateGeneralNum(schedule);
		} else if (productType == AllowedProductTypeEnum.all) {
			return mapper.updateCargoNum(schedule);
		}
		return 0;
	}

	/**
	 * 清空单次排程数量为0
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "tmsTransactionManager", rollbackFor = Exception.class)
	public void updateOnceScheduleNum(ShippingStoreRel shippingStoreRel) {
		if (shippingStoreRel.getMatchWay() == 1 || shippingStoreRel.getMatchWay() == 3) {// 如果匹配上的是模板
			return;
		}
		ShippingSchedule shippingSchedule = shippingStoreRel.getSchedule();
		if (shippingSchedule == null) {
			return;
		}
		AllowedProductTypeEnum productTypeEnum = shippingStoreRel.getProductType();
		synchronized (clearOnceNumObject) {
			ShippingSchedule schedule = mapper.getById(shippingSchedule.getId());
			ShippingSchedule updateSchedule = null;
			if (productTypeEnum == AllowedProductTypeEnum.sensitive) {// 特货
				if (schedule.getOnceSpecialNum() >= schedule.getEachSpecialNum() && schedule.getEachSpecialNum() > 0) {// 如果单次分配数量<=0 则代表物流方式不可能
					updateSchedule = new ShippingSchedule();
					updateSchedule.setOnceSpecialNum(0);
					shippingSchedule.setOnceSpecialNum(0);
				}
			} else if (productTypeEnum == AllowedProductTypeEnum.general) {// 普货
				if (schedule.getOnceGeneralNum() >= schedule.getEachGeneralNum() && schedule.getEachGeneralNum() > 0) {
					updateSchedule = new ShippingSchedule();
					updateSchedule.setOnceGeneralNum(0);
					shippingSchedule.setOnceGeneralNum(0);
				}
			} else if (productTypeEnum == AllowedProductTypeEnum.all) {// 不区分货物类型
				if (schedule.getOnceCargoNum() >= schedule.getEachCargoNum() && schedule.getEachCargoNum() > 0) {
					updateSchedule = new ShippingSchedule();
					updateSchedule.setOnceCargoNum(0);
					shippingSchedule.setOnceCargoNum(0);
				}
			}
			if (updateSchedule != null) {
				updateSchedule.setEachCount(schedule.getEachCount() + 1);
				updateSchedule.setId(schedule.getId());
				mapper.update(updateSchedule);
			}
		}
	}

	public List<ShippingScheduleListVo> queryList(ShippingSchedule shippingSchedule) {
		final List<ShippingSchedule> shippingSchedules = mapper.queryList(shippingSchedule);

		// 物流方式数据
		final Set<Integer> shippingWayIds = shippingSchedules.stream().map(ShippingSchedule::getShippingWayId).collect(Collectors.toSet());
		final List<ShippingWay> shippingWays = shippingWayMapper.queryByIds(shippingWayIds);
		final Map<Integer, List<ShippingWay>> shippingWayMapById = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getId));

		final List<Integer> wmsIdList = new ArrayList<>(shippingSchedules.stream().map(ShippingSchedule::getWmsId).collect(Collectors.toSet()));

		final List<Wms> wmss = storehouseService.findWmsByIds(wmsIdList);
		final Map<Integer, List<Wms>> wmsMapById = wmss.stream().collect(Collectors.groupingBy(Wms::getId));

		final List<ShippingScheduleListVo> shippingScheduleListVos = BeanMapper.mapList(shippingSchedules, ShippingScheduleListVo.class);

		shippingScheduleListVos.forEach(e -> {
			final Integer shippingWayId = e.getShippingWayId();
			final List<ShippingWay> shippingWayList = shippingWayMapById.get(shippingWayId);
			if (CollectionUtils.isNotNullAndEmpty(shippingWayList)) {
				e.setShippingWayName(shippingWayList.get(0).getShippingWayName());
			}

			final Integer wmsId = e.getWmsId();
			final List<Wms> wmsList = wmsMapById.get(wmsId);
			if (CollectionUtils.isNotNullAndEmpty(wmsList)) {
				e.setWmsName(wmsList.get(0).getName());
			}
		});

		return shippingScheduleListVos;
	}

	public int count(ShippingSchedule shippingSchedule) {
		return mapper.count(shippingSchedule);
	}

	/**
	 * 修改排程今日已配数量 不受数量限制
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "tmsTransactionManager", rollbackFor = Exception.class)
	public int updateScheduleNumNotLimit(ShippingSchedule schedule, AllowedProductTypeEnum productType, int addNum) {
		schedule.setAddNum(addNum);
		if (productType == AllowedProductTypeEnum.sensitive) {
			return mapper.updateSpecialNumNotLimit(schedule);
		} else if (productType == AllowedProductTypeEnum.general) {
			return mapper.updateGeneralNumNotLimit(schedule);
		} else if (productType == AllowedProductTypeEnum.all) {
			return mapper.updateCargoNumNotLimit(schedule);
		}
		return 0;
	}

	public void add(ShippingSchedule shippingSchedule) {
		ShippingSchedule paramBean = new ShippingSchedule();
		paramBean.setTemplateId(shippingSchedule.getTemplateId());
		paramBean.setScheduleDate(shippingSchedule.getScheduleDate());

		if (CollectionUtils.isNotNullAndEmpty(mapper.queryList(paramBean))) {
			throw new RuntimeException("今天该物流方式与仓库对应的日程已存在!");
		}
		shippingSchedule.setTodayCargoNum(0);
		shippingSchedule.setTodayGeneralNum(0);
		shippingSchedule.setTodaySpecialNum(0);
		shippingSchedule.setOnceCargoNum(0);
		shippingSchedule.setOnceGeneralNum(0);
		shippingSchedule.setOnceSpecialNum(0);
		shippingSchedule.setEachCount(0);

		Assert.isTrue(mapper.add(shippingSchedule) > 0, "物流日程保存失败!");
	}

	/**
	 * 更新状态
	 */
	public void updateEnable(ShippingSchedule shippingSchedule) {
		Assert.isTrue(mapper.update(shippingSchedule) > 0, "更新物流日程状态失败!");
	}

	public void update(ShippingSchedule shippingSchedule) {
		ShippingSchedule paramBean = new ShippingSchedule();
		paramBean.setTemplateId(shippingSchedule.getTemplateId());
		paramBean.setScheduleDate(shippingSchedule.getScheduleDate());

		final List<ShippingSchedule> schedules = mapper.queryList(paramBean);
		if (CollectionUtils.isNotNullAndEmpty(schedules) && !schedules.get(0).getId().equals(shippingSchedule.getId())) {
			throw new RuntimeException("该天该物流方式与仓库对应的日程已存在!");
		}
		Assert.isTrue(mapper.update(shippingSchedule) > 0, "更新物流日程失败!");
	}
}
