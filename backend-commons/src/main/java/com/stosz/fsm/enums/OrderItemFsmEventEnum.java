package com.stosz.fsm.enums;

/**
 * 订单项状态机Event枚举
 *
 * @ClassName OrderItemFsmEventEnum
 * @author shrek
 * @version 1.0
 */
public enum OrderItemFsmEventEnum implements FsmEventEnum {

	create,
	purchase,
	repurchase,
	match,
	send_to_wh,
	ship,
	received,
	
	out_of_stock,
	un_hold,
	return_received,
	returned,
	cancel,
	
	unknown,
	;


	public static OrderItemFsmEventEnum fromName(String name){
		for(OrderItemFsmEventEnum enumm:values()){
			if(enumm.name().equals(name)){
				return enumm;
			}
		}
		return unknown;
	}
	
}
