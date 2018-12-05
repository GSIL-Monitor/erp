package com.stosz.tms.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.stosz.tms.enums.OrderStateEnum;

public class OrderStateInfo implements Serializable {



	private Integer orderId;

	private OrderStateEnum orderStateEnum;

	// 订单状态为deliver(已发货时) 仓库有可能线下换物流方式 需要修改运单号 物流方式Code
	// 已发货时，仓库会回填包裹重量
	private String trackNo;// 运单号

	private String shippingWayCode;

	private String shippingWayName;

	private BigDecimal weight;// 总量

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public OrderStateEnum getOrderStateEnum() {
		return orderStateEnum;
	}

	public void setOrderStateEnum(OrderStateEnum orderStateEnum) {
		this.orderStateEnum = orderStateEnum;
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}


	public String getShippingWayCode() {
        return shippingWayCode;
    }

	public void setShippingWayCode(String shippingWayCode) {
        this.shippingWayCode = shippingWayCode;
    }

    public String getShippingWayName() {
        return shippingWayName;
    }
	public void setShippingWayName(String shippingWayName) {
		this.shippingWayName = shippingWayName;
	}
}
