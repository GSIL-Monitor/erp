package com.stosz.purchase.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class UserBuDept extends AbstractParamEntity implements ITable,Serializable {

	@DBColumn
	private Integer id;

	@DBColumn
	private Integer userId;
	@DBColumn
	private String userName;
	@DBColumn
	private Integer buDepartmentId;
	@DBColumn
	private String buDepartmentNo;
	@DBColumn
	private String buDepartmentName;
	@DBColumn
	private LocalDateTime createAt;
	@DBColumn
	private LocalDateTime updateAt;
	@DBColumn
	private String creator;
	@DBColumn
	private Integer creatorId;
	@DBColumn
	private Integer enable;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public Integer getBuDepartmentId() {
		return buDepartmentId;
	}

	public void setBuDepartmentId(Integer buDepartmentId) {
		this.buDepartmentId = buDepartmentId;
	}

	public String getBuDepartmentNo() {
		return buDepartmentNo;
	}

	public void setBuDepartmentNo(String buDepartmentNo) {
		this.buDepartmentNo = buDepartmentNo == null ? null : buDepartmentNo.trim();
	}

	public String getBuDepartmentName() {
		return buDepartmentName;
	}

	public void setBuDepartmentName(String buDepartmentName) {
		this.buDepartmentName = buDepartmentName == null ? null : buDepartmentName.trim();
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

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Override
	public String getTable() {
		return "user_bu_dept";
	}
}