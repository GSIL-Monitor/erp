package com.stosz.plat.common;

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
public class RestResult{

    /**
     * 成功但不提示，也不刷新数据，一般用于查询接口
     */
	public static final String OK="OK";
    /**
     * 需要跳转到登陆
     */
    public static final String LOGIN = "LOGIN";
    /**
     * 成功提示，并刷新数据，一般涉及到数据修改，成功后返回的提示
     */
    public static final String NOTICE="NOTICE";
    /**
     * 出错，任何错误都提示
     */
	public static final String FAIL = "FAIL";

    /**
     * 默认提示操作成功
     */
    public static final String DESC = "操作成功";


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6888891304358182599L;


    protected String code = OK;
    protected String desc;
    protected Object item;
    protected Integer total;
    
    public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public RestResult() {
        this("OK", DESC);
    }

    public RestResult(Object item) {
        this.item = item;
    }

    public RestResult(String code, String desc) {
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

    public RestResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public RestResult setDesc(String desc) {
        this.desc = desc;
        return this;
    }


    public Object getItem() {
        return item;
    }

    public RestResult setItem(Object item) {
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
