package com.stosz.order.ext.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * <p>
 * Rest请求的Response对象
 * </p>
 *
 * @ClassName RestResult
 * @author kelvem
 * @version 1.0
 */
public class WmsResponse implements Serializable{
    /**
     * 成功但不提示，也不刷新数据，一般用于查询接口
     */
	public static final String OK="OK";
    /**
     * 出错，任何错误都提示
     */
	public static final String FAIL = "FAIL";

    protected String code = OK;
    protected String desc;
    protected Object item;

	public WmsResponse() {
        this("OK", "");
    }

    public WmsResponse(Object item) {
        this.item = item;
    }

    public WmsResponse(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public boolean isSuccess() {
        return code.equals("OK");
    }

    public boolean isFailed() {
        return !isSuccess();
    }

    public String getCode() {
        return code;
    }

    public WmsResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public WmsResponse setDesc(String desc) {
        this.desc = desc;
        return this;
    }


    public Object getItem() {
        return item;
    }

    public WmsResponse setItem(Object item) {
        this.item = item;
        return this;
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return this.getClass() + ":JsonProcessingException " + e;
        }
    }

}
