package com.stosz.purchase.ext.common;

/**
 * wmsh回写时返回的参数
 * @author xiongchenyang
 * @version [1.0 , 2017/11/25]
 */
public class WmsReturnDto {

    private String isSuccess;

    private String body;

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

    @Override
    public String toString() {
        return "WmsReturnDto{" +
                "isSuccess=" + isSuccess +
                ", body='" + body + '\'' +
                '}';
    }
}
