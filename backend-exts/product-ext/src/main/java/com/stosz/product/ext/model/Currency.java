package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Currency extends AbstractParamEntity implements ITable {
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
	private String symbol;
	@DBColumn
	private String symbolLeft;
	@DBColumn
	private String symbolRight;
	@DBColumn
	private Boolean usable;

	private BigDecimal rateUsd;

	public BigDecimal getRateUsd() {
		return rateUsd;
	}

	public void setRateUsd(BigDecimal rateUsd) {
		this.rateUsd = rateUsd;
	}

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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String getTable() {
		return "currency";
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

	public Boolean getUsable() {
		return usable;
	}

	public void setUsable(Boolean usable) {
		this.usable = usable == null ? false : usable;
	}

	public BigDecimal getRateCny() {
		return rateCny;
	}

	public void setRateCny(BigDecimal rateCny) {
		this.rateCny = rateCny;
	}

	public String getSymbolLeft() {
		return symbolLeft;
	}

	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}

	public String getSymbolRight() {
		return symbolRight;
	}

	public void setSymbolRight(String symbolRight) {
		this.symbolRight = symbolRight;
	}
}