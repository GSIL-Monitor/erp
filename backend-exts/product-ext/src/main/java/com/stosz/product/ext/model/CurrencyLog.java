package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyLog extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private String name;
	@DBColumn
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	@DBColumn
	private String currencyCode;
	@DBColumn
	private BigDecimal rateCny;
	@DBColumn
	private Integer creatorId;
	@DBColumn
	private String creator;

	private LocalDateTime minUpdateAt;
	private LocalDateTime maxUpdateAt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	@Override
	public String getTable() {
		return "currency_log";
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public LocalDateTime getMinUpdateAt() {
		return minUpdateAt;
	}

	public void setMinUpdateAt(LocalDateTime minUpdateAt) {
		this.minUpdateAt = minUpdateAt;
	}

	public LocalDateTime getMaxUpdateAt() {
		return maxUpdateAt;
	}

	public void setMaxUpdateAt(LocalDateTime maxUpdateAt) {
		this.maxUpdateAt = maxUpdateAt;
	}

	public BigDecimal getRateCny() {
		return rateCny;
	}

	public void setRateCny(BigDecimal rateCny) {
		this.rateCny = rateCny;
	}

}