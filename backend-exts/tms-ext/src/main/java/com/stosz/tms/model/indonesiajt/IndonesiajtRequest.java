package com.stosz.tms.model.indonesiajt;

import java.io.Serializable;

/**
 * 请求参数
 */
public class IndonesiajtRequest implements Serializable{

    private String logistics_interface;//JSON格式内容

    private String data_digest;//签名结果

    private String msg_type;//消息类型

    private String eccompanyid;//消息提供者ID

    public String getLogistics_interface() {
        return logistics_interface;
    }

    public void setLogistics_interface(String logistics_interface) {
        this.logistics_interface = logistics_interface;
    }

    public String getData_digest() {
        return data_digest;
    }

    public void setData_digest(String data_digest) {
        this.data_digest = data_digest;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getEccompanyid() {
        return eccompanyid;
    }

    public void setEccompanyid(String eccompanyid) {
        this.eccompanyid = eccompanyid;
    }
}
