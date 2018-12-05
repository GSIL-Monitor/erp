package com.stosz.plat.common;

import com.stosz.plat.model.AbstractParamEntity;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> extends RestResult{

	/**
	 * 总数
	 */
	private Integer count = 0;
	/**
	 * 一页大小
	 */
	private Integer limit = 10;
	/**
	 * 当前行号
	 */
    private int start = 0;

	/**
	 * 排序
	 */
	private String orderBy;
	
	private List<T> results = new ArrayList<T>();
	/**
	 * 是否升序
	 */
	private boolean isASC;
	
	
	
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	
	public PageResult<T> addItem(T item){
		this.results.add(item);
		return this;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public boolean isASC() {
		return isASC;
	}
	public void setASC(boolean isASC) {
		this.isASC = isASC;
	}

    public PageResult() {
        super();
    }

    public PageResult(List<T> results, AbstractParamEntity paramEntity, Integer count) {
        this.results = results;
        this.isASC = paramEntity.getOrder();
        this.orderBy = paramEntity.getOrderBy();
        this.count = count;
        this.start = paramEntity.getStart();
        this.limit = paramEntity.getLimit();
    }
	
}
