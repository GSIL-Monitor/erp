package com.stosz.crm.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author
 * Api调用日志
 */
public class CustomerApiLog extends AbstractParamEntity implements ITable,Serializable {

    @DBColumn
    private Integer id;

    /**
     * 订单编号（可为空）
     */
    @DBColumn
    private Long orderId;

    /**
     * 客户编号
     */
    @DBColumn
    private Integer customerId;

    /**
     * 客户等级
     */
    @DBColumn
    private Integer level;

    @DBColumn
    private String reqParam;


    @DBColumn
    private String respBody;

    /**
     * 备注
     */
    @DBColumn
    private String memo;

    /**
     * 创建时间
     */
    @DBColumn
    private LocalDateTime createAt;

    private static final long serialVersionUID = 1L;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getReqParam() {
        return reqParam;
    }

    public void setReqParam(String reqParam) {
        this.reqParam = reqParam;
    }

    public String getRespBody() {
        return respBody;
    }

    public void setRespBody(String respBody) {
        this.respBody = respBody;
    }

    @Override
    public String toString() {
        return "CustomerApiLog{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", customerId=" + customerId +
                ", level=" + level +
                ", reqParam='" + reqParam + '\'' +
                ", respBody='" + respBody + '\'' +
                ", memo='" + memo + '\'' +
                ", createAt=" + createAt +
                '}';
    }

    @Override
    public String getTable() {
        return "customer_api_log";
    }
}