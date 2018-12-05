package com.stosz.purchase.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum ErrorGoodsItemEvent implements IEnum,FsmEventEnum {
    create("创建"),
    agreeByBusiness("业务同意"),
    disagreeByBusiness("业务不同意"),
    completing("完成"),
    giveUp("放弃");


    private String display;
    public static final Logger logger = LoggerFactory.getLogger(ErrorGoodsItemEvent.class);
    ErrorGoodsItemEvent(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static ErrorGoodsItemEvent fromName(String name) {
        for (ErrorGoodsItemEvent orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Arrays.asList(ErrorGoodsItemEvent.values()).forEach(item-> logger.info(item.ordinal() +" name= " + item.name() + " display= " + item.display() ));
    }


}
