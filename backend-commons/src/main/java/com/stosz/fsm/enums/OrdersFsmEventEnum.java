package com.stosz.fsm.enums;


/**
 * 订单状态机Event枚举
 *
 * @ClassName OrdersFsmEventEnum
 * @author shrek
 * @version 1.0
 */
public enum OrdersFsmEventEnum implements FsmEventEnum {

	create,
	pending,
	received_payment,
	review_payment,
	confirm_payment,
	matched,
	send_to_wh,
	ship,
	received,
	
	suspend,
	recover,
	cancel,
	returned,
	
	unknown,
	;


	public static OrdersFsmEventEnum fromName(String name){
		for(OrdersFsmEventEnum enumm:values()){
			if(enumm.name().equals(name)){
				return enumm;
			}
		}
		return unknown;
	}
}
