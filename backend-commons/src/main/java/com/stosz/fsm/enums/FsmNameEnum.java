package com.stosz.fsm.enums;

/**
 * 订单状态机类型枚举
 *
 * @ClassName FsmNameEnum
 * @author shrek
 * @version 1.0
 */
public enum FsmNameEnum implements FsmStateEnum {

	OrdersEntity, OrderItemEntity,

	unknown,;

	public static FsmNameEnum fromName(String name) {
		for (FsmNameEnum enumm : values()) {
			if (enumm.name().equals(name)) {
				return enumm;
			}
		}
		return unknown;
	}
}