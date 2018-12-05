package com.stosz.plat.wms.model;

import java.io.Serializable;

public class WmsSender implements Serializable {

    private String url = "";
    private String jsonContent="";
    private WmsConfig wmsConfig;

    public WmsSender(String url, String jsonContent, WmsConfig wmsConfig) {
        this.url = url;
        this.jsonContent = jsonContent;
        this.wmsConfig = wmsConfig;
    }

    public String getUrl() {
            return url;
        }

    public void setUrl(String url) {
            this.url = url;
        }

    public String getJsonContent() {
            return jsonContent;
        }

    public void setJsonContent(String jsonContent) {
            this.jsonContent = jsonContent;
        }

    public WmsConfig getWmsConfig() {
        return wmsConfig;
    }

    public void setWmsConfig(WmsConfig wmsConfig) {
        this.wmsConfig = wmsConfig;
    }
}