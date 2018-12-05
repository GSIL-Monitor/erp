package com.stosz.product.ext.model;

import java.util.Date;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class LabelObject extends AbstractParamEntity implements ITable {
	
	@DBColumn
	private Integer id;
	@DBColumn
	private Integer objectId;// 对象ID
	@DBColumn
	private String objectType;// 对象类型 例如:Product
	@DBColumn
	private Integer labelId;// 标签ID
	@DBColumn
	private String labelValue;// 标签值
	@DBColumn
	private String extendValue;// 标签的扩展属性
	@DBColumn
	private Date createAt;
	private Date updateAt;
	@DBColumn
	private String creator;
	@DBColumn
	private Integer creatorId;
	@DBColumn
	private String objectMemo;
	
	private String labelName;//标签的中文名
	private String labelKeyword;//标签的keyword

	public String getObjectMemo() {
		return objectMemo;
	}

	public void setObjectMemo(String objectMemo) {
		this.objectMemo = objectMemo;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelKeyword() {
		return labelKeyword;
	}

	public void setLabelKeyword(String labelKeyword) {
		this.labelKeyword = labelKeyword;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType == null ? null : objectType.trim();
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue == null ? null : labelValue.trim();
	}

	public String getExtendValue() {
		return extendValue;
	}

	public void setExtendValue(String extendValue) {
		this.extendValue = extendValue == null ? null : extendValue.trim();
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	@Override
	public String getTable() {
		return "label_object";
	}
}