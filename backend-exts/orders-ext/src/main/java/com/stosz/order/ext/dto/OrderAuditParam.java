package com.stosz.order.ext.dto;

import java.util.List;

/**
 * 审单请求
 *
 * @author wangqian
 * @date 2017-11-30
 */
public class OrderAuditParam {

    /**
     * 操作类型
     *
     * @see com.stosz.order.ext.enums.OperationEnum
     */
    private Integer type;

    private List<AuditParam> auditList;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<AuditParam> getAuditList() {
        return auditList;
    }

    public void setAuditList(List<AuditParam> auditList) {
        this.auditList = auditList;
    }

    @Override
    public String toString() {
        return "OrderAuditParam{" +
                "type=" + type +
                ", auditList=" + auditList +
                '}';
    }

    public static class AuditParam {

        private Integer orderId;

        private String memo;

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
}
