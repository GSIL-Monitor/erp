package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

/**
 * 部门区域关系表
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
public class DepartmentZoneRel extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private Integer departmentId;
	@DBColumn
	private String departmentNo;
	@DBColumn
	private String departmentName;
	@DBColumn
	private Integer zoneId;
	@DBColumn
	private Boolean usable;
	
	private String zoneName;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	@Override
	public String getTable() {
		return "department_zone_rel";
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public Boolean getUsable() {
		return usable;
	}

	public void setUsable(Boolean usable) {
		this.usable = usable;
	}

}
