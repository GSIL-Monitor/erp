package com.stosz.order.ext.dto;

import java.io.Serializable;

/**
 * 批量申请实体 刘石磊
 */
public class BatchApplyChangeDTO implements Serializable {
    //退货/换货
    private String changeType;
    //问题来源
    private String sourceType;
    //导入内容
    private String content;

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
