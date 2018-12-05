package com.stosz.tms.model.indonesiajt;

import java.io.Serializable;

public class Item implements Serializable {

    private String itemname;

    private String number; //数量

    private String itemvalue;

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getItemvalue() {
        return itemvalue;
    }

    public void setItemvalue(String itemvalue) {
        this.itemvalue = itemvalue;
    }
}
