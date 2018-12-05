package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by XCY on 2017/8/22.
 * desc: 老erp部门实体
 */
public class OldErpDepartment extends AbstractParamEntity implements Serializable, ITable {

    private Integer idDepartment;

    private Integer parentId;

    private Integer idUsers;

    private Integer type;

    private String title;

    private Date updateAt;

    private Integer departmentNum;

    private String departmentCode;

    public Integer getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getDepartmentNum() {
        return departmentNum;
    }

    public void setDepartmentNum(Integer departmentNum) {
        this.departmentNum = departmentNum;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Override
    public Integer getId() {
        return this.idDepartment;
    }

    @Override
    public void setId(Integer id) {
        this.idDepartment = id;
    }


    @Override
    public String getTable() {
        return "erp_department";
    }
}
