package com.stosz.plat.common;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = -8988209834638878924L;
	/**
	* 接口访问成功
	*/
	public static final String OK = "OK";
	/**
	 * 需要跳转到登陆
	 */
	public static final String LOGIN = "LOGIN";
	/**
	 * 出错，任何错误都提示
	 */
	public static final String FAIL = "FAIL";

	protected String code = OK;
	protected String desc;
	protected T item;
	protected Integer total;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public ResultBean<T> fail(String desc) {
		this.code = FAIL;
		this.desc = desc;
		return this;
	}

}
