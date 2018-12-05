package com.stosz.tms.enums;

import java.util.Set;

import com.google.common.collect.Sets;
import com.stosz.plat.utils.IEnum;

/**
 * 物流接口HandlerType
 * @author feiheping
 * @version [1.0,2017年12月4日]
 */
public enum HandlerTypeEnum implements IEnum {

	CXC("CXC", "香港CXC"), //
	INDONESIA("INDONESIA", "印尼HY"), //
	INDONESIAZWY("INDONESIAZWY", "印尼zwy"), //
	INDONESIAJT("INDONESIAJT", "印尼JT"), //
	NIM("NIM", "泰国NIM"), //
	PHILIPPINEXH("PHILIPPINEXH", "菲律宾XH"), //
	SHYLON("SHYLON", "祥龙"), //
	TIMES("TIMES", "TIMES"), //
	YUFENG("YUFENG", "裕丰"), //
	KERRY("KERRY", "泰国kerry"), //
	RUSSIA("RUSSIA", "俄罗斯"), //
	SHUNFENG("SHUNFENG", "顺丰"), //
	ZWY("ZWY", "中外运"), //
	HKCOE("HKCOE", "香港coe"), //
	ARAMEX("ARAMEX", "aramex"), //
	FEIFAN("FEIFAN", "非凡"), //
	GHT("GHT", "柬埔寨GHT"), //
	SINGAPORENV("SINGAPORENV", "新加坡NV"), //
	HKYSP("HKYSP", "香港YSP"), //
	GDEX("GDEX", "GDEX"), //
	DF("DF", "新加坡东丰"), //
	IMILE("IMILE", "阿联酋imile"), //
	QHY("QHY", "QHY"), //
	PAKISTAN("PAKISTAN","皇家巴基斯坦PAKISTAN"),

	/********页面不需要显示********/
	ASSIGN("ASSIGN", "指派物流", false), //
	SPECIAL("SPECIAL", "特殊派单", false), // 不受排程数量限制
	/** 不需要抛送运单的物流商**/
	THAILANDBJT("THAILANDBJT", "博佳图"), //
	SHENGHONG("SHENGHONG", "森鸿"), //
	YIJIAYI("YIJIAYI", "壹加壹"), //
	KERRY_DY("KERRY_DY","嘉里大荣"),
	KERRY_DT("KERRY_DT","嘉里大通"),
	TMWL("TMWL","天马物流"),
	XTY("XTY","鑫腾跃"),
	YTAI("YTAI","亿泰"),
	DEX("DEX","DEX"),
	DBY("DBY","DBY"),
	RINCOS("RINCOS","RINCOS"),
	SKYNET("SKYNET","SKYNET"),
	RCT("RCT","RCT"),
	GEX("GEX","GEX"),
	YN_YD("YN_YD","YN_YD"),
	RBOX("RBOX","RBOX"),
	CDEX("CDEX","CDEX"),
	MALAYDF("MALAYDF", "马来东丰"), //
	XZHU("XZHU","新竹");
	

	private String display = "";

	private String code = "";

	private boolean visible = true;// 页面是否可见

	HandlerTypeEnum(String code, String display) {
		this.code = code;
		this.display = display;
	}

	HandlerTypeEnum(String code, String display, boolean visiable) {
		this.code = code;
		this.display = display;
		this.visible = visiable;
	}

	@Override
	public String display() {
		return display;
	}

	public String code() {
		return code;
	}

	/**
	 * 定义不需要抛送运单的Handler
	 */
	public static final Set<String> assignHandlerCodeSet = Sets.newHashSet(THAILANDBJT.code, SHENGHONG.code, YIJIAYI.code);

	public static HandlerTypeEnum fromId(Integer id) {
		HandlerTypeEnum orderTypeEnumFind = null;
		if (null == id || id.intValue() < 0)
			return orderTypeEnumFind;

		for (HandlerTypeEnum orderTypeEnum : values()) {
			if (orderTypeEnum.ordinal() == id.intValue()) {
				orderTypeEnumFind = orderTypeEnum;
				break;
			}
		}
		return orderTypeEnumFind;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
