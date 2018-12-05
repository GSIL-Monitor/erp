package com.stosz.store.ext.dto.request;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @Description: 发货导入
 * @auther ChenShifeng
 * @Date Create time    2017/11/22
 */
public class ImportDeliverReq implements Serializable {
    /**
     * 物流id
     */
    private String logisticsId;

    /**
     * 物流名称
     */
    private String logisticsName;

    /**
     * 订单信息
     * 格式：订单号,运单号|2222，44444|333,452636
     */
    private String track;

    /**
     * 请求
     */
    private HttpServletRequest request;

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
