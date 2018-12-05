package com.stosz.fsm.enums;

/**
 * 订单项状态机State枚举
 *
 * @ClassName OrderItemFsmStateEnum
 * @author shrek
 * @version 1.0
 */
public enum OrderItemFsmStateEnum implements FsmStateEnum {

	start("开始"),
	pending("待处理"),
	purchasing("采购中"),
	matched("已匹配"),
	send_to_wh("发送库房"),
	shipped("已发货"),
	received("已收货"),
	
	out_of_stock("缺货"),
	return_received("退货已收"),
	cancel("取消"),
	returned("已退货"),
	
	unknown("未知"),
	;

	private String displayName;
	
	OrderItemFsmStateEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String displayName(){
		return this.displayName;
	}

	public static OrderItemFsmStateEnum fromName(String name){
		for(OrderItemFsmStateEnum enumm:values()){
			if(enumm.name().equals(name)){
				return enumm;
			}
		}
		return unknown;
	}
	
}
