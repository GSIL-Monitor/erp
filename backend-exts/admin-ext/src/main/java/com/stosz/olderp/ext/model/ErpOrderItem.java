package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 实体类
 */
public class ErpOrderItem  extends AbstractParamEntity implements ITable, Serializable {

	/***/
	 @DBColumn
	private	Integer	idOrderItem	=	new Integer(0);
	/**订单ID*/
	 @DBColumn
	private	Integer	idOrder	=	new Integer(0);
	/**SKU  ID*/
	 @DBColumn
	private	Integer	idProductSku	=	new Integer(0);
	/**产品ID*/
	 @DBColumn
	private	Integer	idProduct	=	new Integer(0);
	/**SKU名称*/
	 @DBColumn
	private	String	sku	=	"";
	/**sku 标题如：红色-L*/
	 @DBColumn
	private	String	skuTitle	=	"";
	/**前台销售名称*/
	 @DBColumn
	private	String	saleTitle	=	"";
	/**购买的产品标题*/
	 @DBColumn
	private	String	productTitle	=	"";
	/**购买件数*/
	 @DBColumn
	private	Integer	quantity	=	new Integer(0);
	/**最终售价,显示给用户与ERP的价格*/
	 @DBColumn
	private	java.math.BigDecimal	price	=	new java.math.BigDecimal(0);
	/**总价*/
	 @DBColumn
	private	java.math.BigDecimal	total	=	new java.math.BigDecimal(0);
	/**是否免费(0:免费,1:不免费)*/
	 @DBColumn
	private	boolean	isFree	=	false;
	/**缺货 根据地址减库存*/
	 @DBColumn
	private	Integer	sorting	=	new Integer(0);
	/**没有option_value，零时存储方便查询*/
	 @DBColumn
	private	String	attrs	=	"";
	/**属性名称*/
	 @DBColumn
	private	String	attrsTitle	=	"";

	public ErpOrderItem(){}

	public void setIdOrderItem(Integer	idOrderItem){
		this.idOrderItem = idOrderItem;
	}

	public Integer getIdOrderItem(){
		return idOrderItem;
	}

	public void setIdOrder(Integer	idOrder){
		this.idOrder = idOrder;
	}

	public Integer getIdOrder(){
		return idOrder;
	}

	public void setIdProductSku(Integer	idProductSku){
		this.idProductSku = idProductSku;
	}

	public Integer getIdProductSku(){
		return idProductSku;
	}

	public void setIdProduct(Integer	idProduct){
		this.idProduct = idProduct;
	}

	public Integer getIdProduct(){
		return idProduct;
	}

	public void setSku(String	sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
	}

	public void setSkuTitle(String	skuTitle){
		this.skuTitle = skuTitle;
	}

	public String getSkuTitle(){
		return skuTitle;
	}

	public void setSaleTitle(String	saleTitle){
		this.saleTitle = saleTitle;
	}

	public String getSaleTitle(){
		return saleTitle;
	}

	public void setProductTitle(String	productTitle){
		this.productTitle = productTitle;
	}

	public String getProductTitle(){
		return productTitle;
	}

	public void setQuantity(Integer	quantity){
		this.quantity = quantity;
	}

	public Integer getQuantity(){
		return quantity;
	}

	public void setPrice(java.math.BigDecimal	price){
		this.price = price;
	}

	public java.math.BigDecimal getPrice(){
		return price;
	}

	public void setTotal(java.math.BigDecimal	total){
		this.total = total;
	}

	public java.math.BigDecimal getTotal(){
		return total;
	}

	public void setIsFree(boolean	isFree){
		this.isFree = isFree;
	}

	public boolean getIsFree(){
		return isFree;
	}

	public void setSorting(Integer	sorting){
		this.sorting = sorting;
	}

	public Integer getSorting(){
		return sorting;
	}

	public void setAttrs(String	attrs){
		this.attrs = attrs;
	}

	public String getAttrs(){
		return attrs;
	}

	public void setAttrsTitle(String	attrsTitle){
		this.attrsTitle = attrsTitle;
	}

	public String getAttrsTitle(){
		return attrsTitle;
	}

		@Override
		public String getTable() {
		return "erp_order_item";
	}

	@Override
	public Integer getId() {
		return idOrderItem;
	}

	@Override
	public void setId(Integer id) {

		idOrderItem = id;

	}
}