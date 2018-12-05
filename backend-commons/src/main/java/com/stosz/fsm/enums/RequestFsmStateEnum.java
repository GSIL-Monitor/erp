package com.stosz.fsm.enums;

/**
 * 请求状态机State枚举
 *
 * @ClassName RequestFsmStateEnum
 * @author shrek
 * @version 1.0
 */
public enum RequestFsmStateEnum implements FsmStateEnum {

	start("开始"),
	pending("待处理"),
	draft("草稿"),
	pass("通过"),
	reject("拒绝"),
	end("已处理"),
	
	unknown("未知"),
	;
	
	private String displayName;
	
	RequestFsmStateEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String displayName(){
		return this.displayName;
	}

	public static RequestFsmStateEnum fromName(String name){
		for(RequestFsmStateEnum enumm:values()){
			if(enumm.name().equals(name)){
				return enumm;
			}
		}
		return unknown;
	}
}
