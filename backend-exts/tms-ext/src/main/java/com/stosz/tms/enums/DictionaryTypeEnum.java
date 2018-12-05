package com.stosz.tms.enums;

public enum  DictionaryTypeEnum {
    CLASSIFY_STATUS("classify_status"),
    MAIL_NOTIFY_NAME("mail_notify_name")
    ;



    private String type;

    DictionaryTypeEnum(String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
