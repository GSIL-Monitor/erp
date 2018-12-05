package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 标签类型
 * @author feiheping
 * @version [1.0,2017年12月21日]
 */
public enum LabelTypeEnum implements IEnum {

	Product("product-label"),
	ProductSku("productSku-label"),
	Material("material-label"), //材质标签
	Logistics("logistics-label"), //物流标签
	ShippingStoreRel("shippingstorerel-label");

	LabelTypeEnum(String keyword) {
		this.keyword = keyword;
	}

	private String keyword;// 标签的Keyword

	@Override
	public String display() {
		return this.name();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
