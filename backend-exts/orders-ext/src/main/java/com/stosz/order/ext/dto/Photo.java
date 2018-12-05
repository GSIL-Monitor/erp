package com.stosz.order.ext.dto;

import java.util.List;
import java.util.Map;

/**
 * 退货凭证图片
 */
public class Photo {
    //图片名
    private String alt;
    //图片id
    private String pid;
    //原图地址
    private String src;
    //缩略图地址
    private String thumb;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = "";
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = "";
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = "";
    }
}
