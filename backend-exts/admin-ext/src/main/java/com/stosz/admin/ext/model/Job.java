package com.stosz.admin.ext.model;

import com.stosz.admin.ext.common.MenuNode;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Job extends AbstractParamEntity implements Serializable, ITable {

    @DBColumn
    private Integer id;
    @DBColumn
    private String name;
    @DBColumn
    private String ecologyPinyinSearch;
    @DBColumn
    private String status = "1";
    @DBColumn
    private String remark;
    @DBColumn
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    /**
     * 存一个角色有哪些权限
     */
    private List<Menu> menuList;
    /**
     * 将菜单转为菜单树
     */
    private MenuNode menuNode;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEclolgyPinyinSearch() {
        return ecologyPinyinSearch;
    }

    public void setEclolgyPinyinSearch(String ecologyPinyinSearch) {
        this.ecologyPinyinSearch = ecologyPinyinSearch == null ? null : ecologyPinyinSearch.trim();
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public MenuNode getMenuNode() {
        return menuNode;
    }

    public void setMenuNode(MenuNode menuNode) {
        this.menuNode = menuNode;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getEcologyPinyinSearch() {
        return ecologyPinyinSearch;
    }

    public void setEcologyPinyinSearch(String ecologyPinyinSearch) {
        this.ecologyPinyinSearch = ecologyPinyinSearch;
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

    @Override
    public String getTable() {
        return "job";
    }
}