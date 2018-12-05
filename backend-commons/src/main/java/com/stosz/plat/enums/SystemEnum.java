package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;

public enum SystemEnum implements IEnum {

	front("新ERP"),
	frontOutside("新ERP外网"),
	image("图片服务器"),
	imageOutside("图片服务器"),
	admin("用户中心"),
	adminOutside("用户中心外网"),
	product("产品中心"),
	orders("订单中心"),
	purchase("采购中心"),
	store("仓储中心"),
	tms("物流中心"),
	finance("财务中心"),
	deamon("后台服务"),
	wms("WMS"),
	superMary("建站后台"),
	oa("办公自动化系统")

	;

	String display;

	SystemEnum(String disp) {
		this.display = disp;
	}

	public String display() {
		return this.display;
	}

	public static SystemEnum fromName(String name) {
		for (SystemEnum orderTypeEnum : values()) {
			if (orderTypeEnum.name().equalsIgnoreCase(name)) {
				return orderTypeEnum;
			}
		}
		return null;
	}
	
	 public static String name(int ordinal) {
        for (SystemEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == ordinal) {
                return orderTypeEnum.display;
            }
        }
        return null;
    }
}
