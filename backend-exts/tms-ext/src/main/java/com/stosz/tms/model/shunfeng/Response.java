package com.stosz.tms.model.shunfeng;

import java.io.Serializable;

/**
 * 响应实体
 */
public class Response implements Serializable{
    private String service;
    private String Head;
    private String Body;
    private ERROR error;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public ERROR getError() {
        return error;
    }

    public void setError(ERROR error) {
        this.error = error;
    }
}
