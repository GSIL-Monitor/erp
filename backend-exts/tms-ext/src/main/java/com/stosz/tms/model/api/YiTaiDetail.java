package com.stosz.tms.model.api;

import java.io.Serializable;

public class YiTaiDetail implements Serializable{

	private String invoice_amount;//申报总价值美金，必填

	private Integer nvoice_pcs;//件数，必填

	private String invoice_title;//英文品名，必填

	private Double invoice_weight;//单件重

	private String item_id;

	private String item_transactionid;

	private String sku;//中文品名

	private String sku_code;//配货信息

	private String hs_code;//海关编码

	public String getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public Integer getNvoice_pcs() {
		return nvoice_pcs;
	}

	public void setNvoice_pcs(Integer nvoice_pcs) {
		this.nvoice_pcs = nvoice_pcs;
	}

	public String getInvoice_title() {
		return invoice_title;
	}

	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}

	public Double getInvoice_weight() {
		return invoice_weight;
	}

	public void setInvoice_weight(Double invoice_weight) {
		this.invoice_weight = invoice_weight;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getItem_transactionid() {
		return item_transactionid;
	}

	public void setItem_transactionid(String item_transactionid) {
		this.item_transactionid = item_transactionid;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSku_code() {
		return sku_code;
	}

	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}

	public String getHs_code() {
		return hs_code;
	}

	public void setHs_code(String hs_code) {
		this.hs_code = hs_code;
	}
}
