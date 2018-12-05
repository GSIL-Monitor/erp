package com.stosz.admin.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Department extends AbstractParamEntity implements ITable, Serializable {

    /**
     * 自增长id
     */
	@DBColumn
    private Integer id;
    
    /**
     * 创建时间
     */
	@DBColumn
    private LocalDateTime createAt;
    
    /**
    * 修改时间
    */
    private LocalDateTime updatedAt;

    @DBColumn
    private String departmentName;

	@DBColumn
    private Integer parentId;

	@DBColumn
    private Integer tlevel;

	@DBColumn
    private String masterId;

	@DBColumn
    private String departmentNo;

	@DBColumn
    private String ecologyPinyinSearch;

	@DBColumn
    private String status = "1"; //默认状态为1

    private String masterName;

    private Integer oldId;//老erp的部门id

    public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

	public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getTlevel() {
        return tlevel;
    }

    public void setTlevel(Integer tlevel) {
        this.tlevel = tlevel;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getEcologyPinyinSearch() {
        return ecologyPinyinSearch;
    }

    public void setEcologyPinyinSearch(String ecologyPinyinSearch) {
        this.ecologyPinyinSearch = ecologyPinyinSearch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getTable() {
		return "department";
	}

}