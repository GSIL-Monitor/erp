package com.stosz.tms.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.mapper.ShippingMapper;
import com.stosz.tms.model.Shipping;
import org.springframework.util.Assert;

@Service
public class ShippingService {

	@Resource
	private ShippingMapper shippingMapper;

	/**
	 * 查询物流公司
	 * @param name
	 * @return
	 */
	public List<Shipping> getShippingList(String name, int start, int limit) {
		return shippingMapper.getShippingList(name, start, limit);
	}

	public int getShippingCount(Shipping shipping) {
		return shippingMapper.getShippingCount(shipping);
	}

	/**
	 * 新增物流公司
	 * @param shipping
	 */
	public void addShipping(Shipping shipping) {
		if (!"ASSIGN".equals(shipping.getShippingCode())) {
			Shipping shippingByCode = shippingMapper.getShippingByCode(shipping.getShippingCode());
			Assert.isNull(shippingByCode, "该物流商已存在");
		}
		Assert.isTrue(shippingMapper.addShipping(shipping) > 0, "新增物流商失败!");
	}

	/**
	 * 修改物流公司
	 * @param shipping
	 */
	public void editShipping(Shipping shipping) {
		Assert.isTrue(shippingMapper.updateShipping(shipping) > 0, "更新物流商失败!");
	}

	public List<Shipping> queryShipping(Shipping shipping) {
		return shippingMapper.queryShipping(shipping);
	}

	public Shipping selectByName(String shippingName) {
		Shipping paramBean = new Shipping();
		paramBean.setShippingName(shippingName);

		final List<Shipping> shippingList = shippingMapper.queryShipping(paramBean);

		Assert.notEmpty(shippingList, "通过物流名称查找物流公司失败");

		return shippingList.get(0);
	}

	public List<Shipping> queryByIds(Set<Integer> ids) {
		return shippingMapper.queryByIds(ids);
	}

	public Shipping queryShippingByWayId(Integer shippingWayId) {
		return shippingMapper.queryShippingByWayId(shippingWayId);
	}

	public List<Shipping> queryShippingAll(Shipping shipping) {
		return shippingMapper.queryShippingAll(shipping);
	}
}
