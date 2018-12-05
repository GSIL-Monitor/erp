package com.stosz.tms.enums;

public enum ShippingWayLableRelTypeEnum {

    CANNOT(0,"无法寄出"),
    GENERAL(1,"普货可配"),
    SPECIA(2,"特货可配")
    ;
    private Integer type;

    private String typeName;

    ShippingWayLableRelTypeEnum(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
