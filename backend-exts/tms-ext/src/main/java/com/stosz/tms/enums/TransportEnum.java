package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 运输id
 * @author xiepengcheng
 * @version [1.0,2017年12月5日]
 */
public enum TransportEnum implements IEnum {
	TRANS_EG("2041","埃及，科威特，巴林，啊曼，卡塔尔"), TRANS_SA("1981","沙特"),TRANS_AE("2101","阿联酋"),TRANS_IR("IR","伊朗");

	private String id;
	private String district;

	TransportEnum(String code, String name) {
		this.id = id;
		this.district = district;
	}

	@Override
	public String display() {
		return district;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
}
