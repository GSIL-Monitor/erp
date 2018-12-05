package com.stosz.plat.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>
 * Rest请求的Response对象
 * </p>
 *
 * @ClassName RestResult
 * @author kelvem
 * @version 1.0
 */
public class  RestCallBackResult {
    /**
     * 成功但不提示，也不刷新数据，一般用于查询接口
     */
	public static final String OK= "true";
    /**
     * 出错，任何错误都提示
     */
	public static final String FAIL = "false";

    protected String isSuccess = "true";
    protected String body;
    protected Object item;

    @JsonIgnore
    private Integer recordDetailId = 0;


    public RestCallBackResult() {
    }


    public RestCallBackResult(String isSuccess, String desc) {
        this.isSuccess = isSuccess;
        this.body = body;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return this.getClass() + ":JsonProcessingException " + e;
        }
    }

    public Integer getRecordDetailId() {
        return recordDetailId;
    }

    public void setRecordDetailId(Integer recordDetailId) {
        this.recordDetailId = recordDetailId;
    }
}
