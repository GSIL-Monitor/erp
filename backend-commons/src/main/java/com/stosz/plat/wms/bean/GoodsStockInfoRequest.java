package com.stosz.plat.wms.bean;


/**
 * 商品库存查询请求
 * @author lb
 *
 */
public class GoodsStockInfoRequest {
	
	/**
	 * 仓库编码
	 */
	private String warehouseCode;
	/**
	 * 物料编码
	 */
	private String sku;
	/**
	 * 货主
	 */
	private String goodsOwner;
	
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
	public String getGoodsOwner() {
		return goodsOwner;
	}
	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
	}


}
