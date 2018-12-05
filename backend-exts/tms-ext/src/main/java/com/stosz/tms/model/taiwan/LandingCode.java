package com.stosz.tms.model.taiwan;

public class LandingCode {

	private String address;// 到站地址

	private String tetradCode;// 四字码

	private String brevityCode;// 简码(两位)

	private String brevityAddress;// (到站区域)

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTetradCode() {
		return tetradCode;
	}

	public void setTetradCode(String tetradCode) {
		this.tetradCode = tetradCode;
	}

	public String getBrevityCode() {
		return brevityCode;
	}

	public void setBrevityCode(String brevityCode) {
		this.brevityCode = brevityCode;
	}

	public String getBrevityAddress() {
		return brevityAddress;
	}

	public void setBrevityAddress(String brevityAddress) {
		this.brevityAddress = brevityAddress;
	}

}
