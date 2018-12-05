package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @auther carter
 * create time    2017-11-07
 */
public class WmsPush extends AbstractParamEntity implements ITable,Serializable {

    @DBColumn
    private Long id = 0L;
    @DBColumn
    private Long objectId=0L;
    @DBColumn
    private String objectType="";
    @DBColumn
    private Integer pushStaus=0;
    @DBColumn
    private Integer pushQty = 0;
    @DBColumn
    private String  pushUrl="";
    @DBColumn
    private String  pushParam="";
    @DBColumn
    private LocalDateTime createAt;
    @DBColumn
    private LocalDateTime updateAt;
    @DBColumn
    private String responseMessage="";




    @Override
    public Integer getId() {
        return id.intValue();
    }

    @Override
    public void setId(Integer id) {
        this.id = new Long(id) ;
    }

    @Override
    public String getTable() {
        return "wms_push";
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Integer getPushStaus() {
        return pushStaus;
    }

    public void setPushStaus(Integer pushStaus) {
        this.pushStaus = pushStaus;
    }

    public Integer getPushQty() {
        return pushQty;
    }

    public void setPushQty(Integer pushQty) {
        this.pushQty = pushQty;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getPushParam() {
        return pushParam;
    }

    public void setPushParam(String pushParam) {
        this.pushParam = pushParam;
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

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
