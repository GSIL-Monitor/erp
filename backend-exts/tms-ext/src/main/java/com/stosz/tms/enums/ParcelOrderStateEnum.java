package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

import java.io.Serializable;

public enum ParcelOrderStateEnum implements IEnum ,Serializable {

    CREATE(1,"已创建"),
    SEND(2,"已发货"),
    REJECT(3,"拒收"),
    CANCEL(4,"取消"),
    DESIGNATE_FAIL(5,"指派失败"),
    RECEIVE(6,"确认收货")
    ;

    private Integer id;

    private String display;


    ParcelOrderStateEnum(Integer id, String display) {
        this.id = id;
        this.display = display;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String display() {
        return this.display;
    }
}
