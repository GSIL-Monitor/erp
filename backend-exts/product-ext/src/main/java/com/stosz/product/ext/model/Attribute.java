package com.stosz.product.ext.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * 属性实体类
 * 
 * @author he_guitang
 *
 */
public class Attribute extends AbstractParamEntity implements ITable, Comparator<Attribute> {
	@DBColumn
	private Integer id;
	@DBColumn
	private String title;
	@DBColumn
	private LocalDateTime createAt;
	@DBColumn
	private LocalDateTime updateAt;
	@DBColumn
	private Integer version;

	// 属性值
	private List<AttributeValue> attributeValueList;
	// 属性语言包
	private List<AttributeLang> attributeLangs;
	// 是否绑定的判断
	private Boolean bindIs;
	// 当前品类id
	private Integer categoryId;
    //产品id
    private Integer productId;
    //属性绑定数
	private Integer bindingNumber;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

	//产品属性关联表id  对应老erp的时候用到；
	@JsonIgnore
	private Integer  productAttributeRelId;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getTable() {
		return "attribute";
	}

	public Integer getBindingNumber() {
		return bindingNumber == null ? 0 : bindingNumber;
	}

	public void setBindingNumber(Integer bindingNumber) {
		this.bindingNumber = bindingNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
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

	public List<AttributeLang> getAttributeLangs() {
		return attributeLangs;
	}

	public void setAttributeLangs(List<AttributeLang> attributeLangs) {
		this.attributeLangs = attributeLangs;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Boolean getBindIs() {
		return bindIs == null ? false : bindIs;
	}

	public void setBindIs(Boolean bindIs) {
		this.bindIs = bindIs;
	}

	public List<AttributeValue> getAttributeValueList() {
		return attributeValueList;
	}

	public void setAttributeValueList(List<AttributeValue> attributeValueList) {
		this.attributeValueList = attributeValueList;
	}

	public Integer getVersion() {
		return version == null ? 1 : version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getProductAttributeRelId() {
		return productAttributeRelId;
	}

	public void setProductAttributeRelId(Integer productAttributeRelId) {
		this.productAttributeRelId = productAttributeRelId;
	}

	@Override
	public int compare(Attribute o1, Attribute o2) {
		if (!(o1 instanceof Attribute) && !(o2 instanceof  Attribute))
			throw new RuntimeException("类型异常,不是Attribute类型!");
		int num = o2.getBindIs().compareTo(o1.getBindIs());
		if (num == 0){
			return o2.getBindingNumber().compareTo(o1.getBindingNumber());
		}
		return num;
	}
}