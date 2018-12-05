package com.stosz.tms.model.api;

public class ShylonDetail {


	private String desname;// String(100) 必选 子单商品名称
	private String childno;// String(30) 必选 子单号码
	public String getDesname() {
		return desname;
	}
	public void setDesname(String desname) {
		this.desname = desname;
	}
	public String getChildno() {
		return childno;
	}
	public void setChildno(String childno) {
		this.childno = childno;
	}
	
	
}
