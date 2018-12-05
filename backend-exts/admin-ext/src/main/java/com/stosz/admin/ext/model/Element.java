package com.stosz.admin.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;

/**
 * @author liufeng
 * @version [1.0 , 2017/9/11]
 */
public class Element extends AbstractParamEntity implements ITable {

    /**
     * 自增长id
     */
    @DBColumn
    private Integer id;

    /**
     * 页面元素名
     */
    @DBColumn
    private String name;

    /**
     * 关键词
     */
    @DBColumn
    private String keyword;

    /**
     * 备注
     */
    @DBColumn
    private String remark;

    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    private LocalDateTime updatedAt;

    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getTable() {
        return "element";
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
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", keyword='" + keyword + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", createAt=" + createAt +
                ", updatedAt=" + updatedAt +
                ", checked=" + checked +
                '}';
    }
}
