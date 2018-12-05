package com.stosz.plat.utils;

import java.io.Serializable;

public abstract class DeleteFlag implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	//public static final String DEL_FLAG_AUDIT = "2";
}
