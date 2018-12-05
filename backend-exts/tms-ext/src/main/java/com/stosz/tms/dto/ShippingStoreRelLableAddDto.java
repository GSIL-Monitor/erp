package com.stosz.tms.dto;

import java.util.List;

public class ShippingStoreRelLableAddDto {

    private Integer type;

    private List<Integer> lableList;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getLableList() {
        return lableList;
    }

    public void setLableList(List<Integer> lableList) {
        this.lableList = lableList;
    }
}
