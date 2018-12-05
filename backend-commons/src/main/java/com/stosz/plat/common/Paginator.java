/*
 * 文 件 名:  Paginator.java
 * 描    述:  <描述>
 * 修 改 人:  ThinkPad
 * 修改时间:  2017-8-15
 * 修改内容:  
 */
package com.stosz.plat.common;

import org.apache.commons.lang3.Validate;

import java.io.Serializable;

/**
 * @author ThinkPad
 * @version [版本号, 2015-2-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Paginator implements Serializable {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -4808658489700823508L;

	/**
	 * 默认分页大小
	 * */
	public static final int DEFAULT_PAGE_SIZE = 30;

	/**
	 * 起始行
	 * */
	private int startRow;

	/**
	 * 分页大小
	 * */
	protected int pageSize;

	public Paginator() {
		this(0, DEFAULT_PAGE_SIZE);
	}

	public Paginator(int startRow, int pageSize) {
		Validate.isTrue(pageSize > 0 && startRow >= 0);
		this.startRow = startRow;
		this.pageSize = pageSize;
	}

	/**
	 * 直接翻页
	 * 
	 * @param pageNum
	 *            页码，从1开始
	 * */
	public void gotoPage(int pageNum) {
		// 如果输入非法参数，则默认为第一页
		if (pageNum <= 1) {
			pageNum = 1;
		}
		startRow = (pageNum - 1) * pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int calcPageCount(int rows) {
		if (rows <= 0) {
			rows = 1;
		}
		return (int) Math.ceil((double) rows / (double) getPageSize());
	}

	public int calCurrnePage() {
		int pageSize = getPageSize();
		int currPage = 1;

		if (pageSize >= 1) {
			currPage = (getStartRow() + getPageSize()) / getPageSize();
		}
		return currPage;
	}

}
