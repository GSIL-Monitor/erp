package com.stosz.order.ext.dto;

/**
 * 批量查询结果
 */
public class BatchQueryResultDTO {

    private Object succ;

    private Object fail;

    public Object getSucc() {
        return succ;
    }

    public void setSucc(Object succ) {
        this.succ = succ;
    }

    public Object getFail() {
        return fail;
    }

    public void setFail(Object fail) {
        this.fail = fail;
    }
}
