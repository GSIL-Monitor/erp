package com.stosz.admin.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.util.List;

public class Menu extends AbstractParamEntity implements Serializable, ITable {

    @DBColumn
    private Integer id;
    @DBColumn
    private String name;
    @DBColumn
    private String keyword;
    @DBColumn
    private Integer parentId;
    @DBColumn
    private String url;
    @DBColumn
    private String remark;
    @DBColumn
    private String isShow = "1";//是否展现，默认为1
    /**
     * 拥有这个菜单的角色
     */
    private List<Job> jobs;

    @DBColumn
    private String icon;
    /**
     * 展现的顺序
     */
    @DBColumn
    private Long sort;

    @DBColumn
    private String status = "1";//默认为1,是否有效，1：有效，0：无效
    /**
     * 编号
     */
    @DBColumn
    private String no;
    /**
     * 是否拥有该权限
     */
    private Boolean isChecked = false;
    /**
     * http访问前缀
     */
    private String httpPrefix;

    private boolean leaf = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getHttpPrefix() {
        return httpPrefix;
    }

    public void setHttpPrefix(String httpPrefix) {
        this.httpPrefix = httpPrefix;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

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
        return "menu";
    }
}