package com.stosz.admin.ext.model;

import com.stosz.admin.ext.enums.JobAuthorityRelEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/13]
 */
public class JobAuthorityRel extends AbstractParamEntity implements ITable,Serializable {

    @DBColumn
    private Integer id;
    @DBColumn
    private Integer jobId;
    @DBColumn
    private String authority;
    @DBColumn
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
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
        this.creator = creator;
    }

    public JobAuthorityRelEnum getJobAuthorityRelEnum(){
        return this.authority == null ? null : JobAuthorityRelEnum.fromName(this.authority);
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getTable() {
        return "job_authority_rel";
    }
}
