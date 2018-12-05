package com.stosz.plat.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO基类
 * <功能详细描述>
 */
public class BaseDTO implements Serializable {
    
    /**  */
    private static final long serialVersionUID = -7788954484621993137L;
    
    /**
     * 自增长id
     */
    protected Integer id;
    
    /**
     * 创建时间
     */
    protected LocalDateTime createAt;
    
    /**
     * 创建人姓名
     */
    protected String creator;
    
    /**
     * 创建人ID
     */
    protected Integer creatorId;
    
    /**
    * 修改时间
    */
    protected LocalDateTime updateAt;
    
    /**
     * 排序字符串
     */
    protected String orderBy;
    
    /**
     * 开始位置
     */
    protected Integer start=0;
    
    /**
     * 查询条数
     */
    protected Integer limit=20;
    
    /**
     * sql拼装
     */
    protected String andWhereString;
    
    /**
     * 用于传递字符
     */
    protected String and_where_string;
    
    /**
     * 用户传递字符
     */
    protected String or_where_string;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}


	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getAndWhereString() {
		return andWhereString;
	}

	public void setAndWhereString(String andWhereString) {
		this.andWhereString = andWhereString;
	}

	public String getAnd_where_string() {
		return and_where_string;
	}

	public void setAnd_where_string(String and_where_string) {
		this.and_where_string = and_where_string;
	}

	public String getOr_where_string() {
		return or_where_string;
	}

	public void setOr_where_string(String or_where_string) {
		this.or_where_string = or_where_string;
	}

	/** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
}
