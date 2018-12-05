package com.stosz.product.ext.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * sku 返回给前端的数据
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
public class SkuModel implements Serializable {
	private static final long serialVersionUID = -2898866672279471294L;
	private Integer productId; // 产品id
	private String sku;
	private List<AttributeValue> attributeValues = new ArrayList<>();

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public List<AttributeValue> getAttributeValues() {
		return attributeValues;
	}

	public void setAttributeValues(List<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}

	@Override
	public String toString() {
		return "SkuModel [productId=" + productId + ", sku=" + sku + ", attributeValues=" + attributeValues + "]";
	}

}
