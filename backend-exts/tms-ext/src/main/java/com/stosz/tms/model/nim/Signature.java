package com.stosz.tms.model.nim;

public class Signature {
    private String url;
    private String name;
    private String verification_code;// null
    private String hidden_location;//null

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public String getHidden_location() {
        return hidden_location;
    }

    public void setHidden_location(String hidden_location) {
        this.hidden_location = hidden_location;
    }
}
