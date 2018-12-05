package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 老erp产品分类的实体
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
public class OldErpCategory extends AbstractParamEntity implements Serializable, ITable {
    @DBColumn
    private Integer idCategory;

    @DBColumn
    private Integer parentId=new Integer(0);

    @DBColumn
    private String title="";

    @DBColumn
    private Boolean status = false;

    private LocalDateTime createAt = LocalDateTime.now();
    private LocalDateTime updateAt = LocalDateTime.now();
    private Integer  sort = new Integer(0);

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public Integer getId() {
        return idCategory;
    }

    @Override
    public void setId(Integer id) {
        this.idCategory = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String getTable() {
        return "erp_check_category";
    }
}  
