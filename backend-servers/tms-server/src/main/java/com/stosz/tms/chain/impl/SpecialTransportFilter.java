package com.stosz.tms.chain.impl;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.ResourcesUtils;
import com.stosz.product.ext.model.LabelObject;
import com.stosz.tms.chain.ITransportHandlerChain;
import com.stosz.tms.chain.ITransportHandlerFilter;
import com.stosz.tms.dto.TransportRequest;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.AllocationRule;
import com.stosz.tms.model.AllocationRuleList;
import com.stosz.tms.model.AssignTransportRequest;
import com.stosz.tms.model.AssignTransportResponse;
import com.stosz.tms.model.ShippingSchedule;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.service.ShippingScheduleService;
import com.stosz.tms.service.transport.AbstractTransportHandler;
import com.stosz.tms.service.transport.TransportHandlerFactory;

/**
 * 根据special-assign-rule.xml 匹配,指派物流公司
 * 物流指派->优先handler
 * @author feiheping
 * @version [1.0,2017年12月18日]
 */
@Component
@Scope("prototype")
public class SpecialTransportFilter implements ITransportHandlerFilter {

	private Logger logger = LoggerFactory.getLogger(SpecialTransportFilter.class);

	// 产品标签
	private Map<String, List<LabelObject>> productLables;

	// 所有的物流方式
	private List<ShippingStoreRel> shippingStoreRels;

	@Autowired
	private TransportHandlerFactory transportHandlerFactory;

	@Autowired
	protected ShippingScheduleService scheduleService;

	@Value("${tms.assign.rule.file}")
	private String assignRuleFileLocation;

	@Override
	public AssignTransportResponse doAssignChain(AssignTransportRequest assignRequest, ITransportHandlerChain handlerChain) {
		AssignTransportResponse transportResponse = new AssignTransportResponse();

		AllocationRuleList allocationRuleList = this.loadingAssignRuleFile();
		if (ArrayUtils.isEmpty(allocationRuleList.getRules())) { // 没有特殊指派规则
			return handlerChain.doAssignChain(assignRequest);
		}
		// 所有标签的keyword
		Set<String> keywords = new HashSet<>();
		if (ArrayUtils.isNotEmpty(productLables)) {
			productLables.forEach((key, valueList) -> {
				for (LabelObject LabelObject : valueList) {
					keywords.add(LabelObject.getLabelKeyword());
				}
			});
		}
		TransportRequest request = assignRequest.getTransportRequest();
		// 构建ognl上下文
		OgnlContext ognlContext = new OgnlContext();
		ognlContext.put("zoneId", request.getZoneId());
		ognlContext.put("wmsZoneId", request.getWarehouseId());
		ognlContext.put("keywords", keywords);
		ognlContext.put("orderAmount", request.getOrderAmount().doubleValue());

		AllocationRule matchAllocationRule = null;
		for (AllocationRule allocationRule : allocationRuleList.getRules()) {
			Integer zoneId = allocationRule.getZoneId();
			Integer wmsZoneId = allocationRule.getWmsZoneId();
			if (zoneId != null && zoneId != request.getZoneId()) {
				continue;
			}
			if (wmsZoneId != null && wmsZoneId != request.getWarehouseZoneId()) {
				continue;
			}
			if (zoneId == null && wmsZoneId == null && !StringUtils.hasText(allocationRule.getExpress())) {// 表达式没有匹配的条件
				continue;
			}
			try {
				// 表达式
				String express = allocationRule.getExpress();
				if (StringUtils.hasText(express)) {// 如果设置了表达式，则需要test表达式
					Object value = Ognl.getValue(express, ognlContext, ognlContext);
					if (Boolean.TRUE.equals(value)) {// 如果表达式测试成功，则规则成功匹配上
						matchAllocationRule = allocationRule;
						break;
					}
				} else {
					matchAllocationRule = allocationRule;
					break;
				}
			} catch (Exception e) {
				logger.error("SpecialTransportFilter Exception={}", e);
			}
		}

		if (matchAllocationRule != null) {// 已匹配上了特殊的指派规则
			ShippingStoreRel shippingStoreRel = null;
			for (ShippingStoreRel storeRel : shippingStoreRels) {
				if (matchAllocationRule.getCode().equals(storeRel.getShippingWay().getShippingWayCode())) {
					shippingStoreRel = storeRel;
					break;
				}
			}
			if (shippingStoreRel != null) {
				// 获取到排程
				ShippingSchedule schedule = this.getShippingSchedule(shippingStoreRel);
				// 检测每次分配数量是否达到阈值
				if (whetherOnceNumExceed(shippingStoreRel, schedule)) {
					scheduleService.updateOnceScheduleNum(shippingStoreRel);
				}
				AbstractTransportHandler transportHandler = transportHandlerFactory.getNewHandler(HandlerTypeEnum.SPECIAL.code());
				transportHandler.setShippingStoreRel(shippingStoreRel);
				transportHandler.setShippingWay(shippingStoreRel.getShippingWay());
				return transportHandler.doAssignChain(assignRequest, handlerChain);
			} else {// 未配置物流方式
				if (!matchAllocationRule.isChain()) {// 不交给后面的handler处理时，直接指派失败
					transportResponse.setCode(AssignTransportResponse.FAIL);
					transportResponse.setErrorMsg(String.format("特殊规则:物流方式:%s,(仓库|区域|日程)未配置", matchAllocationRule.getCode()));
					return transportResponse;
				}
			}
		}
		return handlerChain.doAssignChain(assignRequest);
	}

	/**
	 * 特殊物流指派规则
	 * @return
	 */
	private AllocationRuleList loadingAssignRuleFile() {
		AllocationRuleList allocationRuleList = new AllocationRuleList();
		try {
			URL url = ResourcesUtils.getResource(assignRuleFileLocation);
			if (url != null && url.getFile() != null) {
				JAXBContext context = JAXBContext.newInstance(AllocationRuleList.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				allocationRuleList = (AllocationRuleList) unmarshaller.unmarshal(new File(url.getFile()));
				// 根据sort排序
				if (ArrayUtils.isNotEmpty(allocationRuleList.getRules())) {
					allocationRuleList.getRules().sort((src, target) -> {
						if (src.getSort() == null && target.getSort() == null) {
							return 0;
						}
						if (src.getSort() == null) {
							return 1;
						}
						if (target.getSort() == null) {
							return -1;
						}
						return src.getSort().compareTo(target.getSort());
					});
				}
			} else {
				logger.info("-----------------------tms 特殊指派规则文件不存在---------------------");
			}
		} catch (Exception e) {
			logger.error("loadingAssignRuleFile 装载特殊指派规则失败 Exception={}", e);
		}
		return allocationRuleList;
	}

	public Map<String, List<LabelObject>> getProductLables() {
		return productLables;
	}

	public void setProductLables(Map<String, List<LabelObject>> productLables) {
		this.productLables = productLables;
	}

	public List<ShippingStoreRel> getShippingStoreRels() {
		return shippingStoreRels;
	}

	public void setShippingStoreRels(List<ShippingStoreRel> shippingStoreRels) {
		this.shippingStoreRels = shippingStoreRels;
	}
}
