package com.stosz.purchase.ext.service;

import com.stosz.purchase.ext.model.OrderNotifyRequired;

/**
 * 
 * @author feiheping
 * @version [1.0,2017年11月10日]
 */
public interface IOrderRequiredService {

	String url="/purchase/remote/IOrderRequiredService";

    /**
	 * 订单通知采购 库存不足
	 * 查询订单状态为 审核通过 和待采购状态
	 */
	 void notifyOrderRequired(OrderNotifyRequired orderRequired);
}
