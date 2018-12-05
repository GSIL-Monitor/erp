package com.stosz.product.ext.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class Label extends AbstractParamEntity implements ITable, Comparable<Label> {
	@DBColumn
	private Integer id;
	@DBColumn
	private String keyword;
	@DBColumn
	private String name;
	@DBColumn
	private Integer parentId;
	@DBColumn
	private String remark;
	@DBColumn
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	@DBColumn
	private String creator;
	@DBColumn
	private Integer creatorId;
	private Boolean checked;
	private Integer labelObjectId;

	public Integer getLabelObjectId() {
		return labelObjectId;
	}

	public void setLabelObjectId(Integer labelObjectId) {
		this.labelObjectId = labelObjectId;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
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

	private List<Label> children;

	public List<Label> getChildren() {
		return children;
	}

	public void setChildren(List<Label> children) {
		this.children = children;
	}

	public void addChild(Label label){
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(label);
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword == null ? null : keyword.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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
		return "label";
	}

	@Override
	public int compareTo(Label label) {
		return this.getParentId().compareTo(label.getParentId());
	}
}