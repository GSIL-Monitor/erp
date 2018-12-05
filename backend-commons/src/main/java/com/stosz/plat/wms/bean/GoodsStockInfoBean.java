package com.stosz.plat.wms.bean;


/**
 * 商品库存实体bean
 * @author lb
 *
 */
public class GoodsStockInfoBean {
	/**
	 * 仓库编码	String	50
	 */
	private String warehouseCode;
	/**
	 * 物料编码	String	50
	 */
	private String sku;
	/**
	 * 数量	String	50
	 */
	private String qty;
	/**
	 * 可用数量	String	50
	 */
	private String uqty;
	/**
	 * 货主编码	String	50
	 */
	private String goodsOwner;
	/**
	 * 备用字段1	String	50
	 */
	private String gwf1;
	/**
	 * 备用字段2	String	50
	 */
	private String gwf2;
	/**
	 * 备用字段3	String	50
	 */
	private String gwf3;
	/**
	 * 备用字段4	String	50
	 */
	private String gwf4;
	/**
	 * 备用字段5	String	50
	 */
	private String gwf5;
	
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getUqty() {
		return uqty;
	}
	public void setUqty(String uqty) {
		this.uqty = uqty;
	}
	public String getGoodsOwner() {
		return goodsOwner;
	}
	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
	}
	public String getGwf1() {
		return gwf1;
	}
	public void setGwf1(String gwf1) {
		this.gwf1 = gwf1;
	}
	public String getGwf2() {
		return gwf2;
	}
	public void setGwf2(String gwf2) {
		this.gwf2 = gwf2;
	}
	public String getGwf3() {
		return gwf3;
	}
	public void setGwf3(String gwf3) {
		this.gwf3 = gwf3;
	}
	public String getGwf4() {
		return gwf4;
	}
	public void setGwf4(String gwf4) {
		this.gwf4 = gwf4;
	}
	public String getGwf5() {
		return gwf5;
	}
	public void setGwf5(String gwf5) {
		this.gwf5 = gwf5;
	}
	
}
