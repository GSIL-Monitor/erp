package com.stosz.fsm.enums;


/**
 * 订单请求状态机Event枚举
 *
 * @ClassName RequestFsmEventEnum
 * @author shrek
 * @version 1.0
 */
public enum RequestFsmEventEnum implements FsmEventEnum {

	create,
	submit,
	pass,
	reject,
	execute,
	
	unknown,
	;


	public static RequestFsmEventEnum fromName(String name){
		for(RequestFsmEventEnum enumm:values()){
			if(enumm.name().equals(name)){
				return enumm;
			}
		}
		return unknown;
	}
}
