package com.stosz.store.ext.dto.request;

import java.io.Serializable;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/1 12:01
 */
public class WmsReq implements Serializable {

    private String key;
    private String result_data;

    public String getKey() {

        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResult_data() {
        return result_data;
    }

    public void setResult_data(String result_data) {
        this.result_data = result_data;
    }
}
