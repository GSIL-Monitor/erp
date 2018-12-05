package com.stosz.admin.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户-职位
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/28]
 */
public class UserJobRel extends AbstractParamEntity implements Serializable, ITable {
    /**
     * 主键
     */
    @DBColumn
    private Integer id;
    /**
     * 用户id
     */
    @DBColumn
    private Integer userId;
    /**
     * 职位Id
     */
    @DBColumn
    private Integer jobId;
    /**
     * 创建时间
     */
    @DBColumn
    private LocalDateTime createAt;
    /**
     * 修改时间
     */
    private LocalDateTime updateAt;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
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
        return "user_job";
    }
}
