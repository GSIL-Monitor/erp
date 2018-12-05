package com.stosz.fsm.enums;

/**
 * 订单状态机State枚举
 *
 * @ClassName OrdersFsmStateEnum
 * @author shrek
 * @version 1.0
 */
public enum OrdersFsmStateEnum implements FsmStateEnum {

	start("开始"),
	pending("待收款"),
//	payment_received("已收款"),
	payment_confirm("已确认"),
	all_matched("全部匹配"),
	send_to_wh("发送库房"),
	shipped("已发货"),
	received("已收货"),
	
//	suspend_payment("收款暂停"),
	suspend_wh("发货前暂停"),
	cancel("取消"),
	
	unknown("未知"),
	;
	
	private String displayName;
	
	OrdersFsmStateEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String displayName(){
		return this.displayName;
	}

	public static OrdersFsmStateEnum fromName(String name){
		for(OrdersFsmStateEnum enumm:values()){
			if(enumm.name().equals(name)){
				return enumm;
			}
		}
		return unknown;
	}
}
