package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 物流接口HandlerType
 * @author feiheping
 * @version [1.0,2017年12月4日]
 */
public enum TrackApiTypeEnum implements IEnum {

	CXC("CXC", "香港CXC"), //
	INDONESIA("INDONESIA", "印尼HY"), //
	INDONESIAZWY("INDONESIAZWY", "印尼zwy"), //
	INDONESIAJT("INDONESIAJT", "印尼JT"), //
	NIM("NIM", "泰国NIM"), //
	PHILIPPINEXH("PHILIPPINEXH", "菲律宾XH"), //
	SHYLON("SHYLON", "祥龙"), //
	TIMES("TIMES", "TIMES"), //
	YUFENG("YUFENG", "裕丰"), //
	KEERY("KERRY", "泰国kerry"), //
	RUSSIA("RUSSIA", "俄罗斯"), //
	BLACKCAT("BLACKCAT", "黑猫"), //
	SHUNFENG("SHUNFENG", "顺丰"), //
	ZWY("ZWY", "中外运"), //
	HKCOE("HKCOE", "香港coe"), //
	ARAMEX("ARAMEX", "aramex"), //
	FEIFAN("FEIFAN", "非凡"), //
	GHT("GHT", "柬埔寨GHT"), //
	SINGAPORENV("SINGAPORENV", "新加坡NV"), //
	HKYSP("HKYSP", "香港YSP"), //
	THAILANDBJT("THAILANDBJT", "泰国博佳图"), //
	GDEX("GDEX", "GDEX"), //
	XINZHU("XINZHU", "新竹森鸿"), //
	DF("DF", "新加坡东丰"), //
	MALAYDF("MALAYDF", "马来东丰"), //
	IMILE("IMILE", "阿联酋imile"), //
	PAKISTAN("PAKISTAN","巴基斯坦皇家"),
	QHY("QHY", "QHY"); //

	private String display = "";

	private String code = "";

	TrackApiTypeEnum(String code, String display) {
		this.code = code;
		this.display = display;
	}

	@Override
	public String display() {
		return display;
	}

	public String code() {
		return code;
	}

	public static TrackApiTypeEnum fromId(Integer id) {
		TrackApiTypeEnum orderTypeEnumFind = null;
		if (null == id || id.intValue() < 0)
			return orderTypeEnumFind;

		for (TrackApiTypeEnum orderTypeEnum : values()) {
			if (orderTypeEnum.ordinal() == id.intValue()) {
				orderTypeEnumFind = orderTypeEnum;
				break;
			}
		}
		return orderTypeEnumFind;

	}

}
