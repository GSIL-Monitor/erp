package com.stosz.plat.wms.model;

import java.io.Serializable;


/**
 * wms接口返回的消息对象
 */
public class WMSResponse implements Serializable {

    private boolean isOK = false;
    private String isSuccess = TRUE;
    private String body = "Success";
    static final String TRUE="true";

    public boolean isOK() {
        return TRUE.equals(isSuccess);
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
}
