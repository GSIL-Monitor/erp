package com.stosz.purchase.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum PurchaseItemEvent implements IEnum,FsmEventEnum {
    create("创建"),
    submit("提交"),
    agreeByBusiness("业务同意"),
    disagreeByBusiness("业务不同意"),
    agreeByFinance("财务同意"),
    disagreeByFinance("财务不同意"),
    pay("付款"),
    refusePay("拒绝付款"),
    inStock("入库"),
    giveUp("放弃");


    private String display;

    public static final Logger logger = LoggerFactory.getLogger(PurchaseItemEvent.class);

    PurchaseItemEvent(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static PurchaseItemEvent fromName(String name) {
        for (PurchaseItemEvent orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Arrays.asList(PurchaseItemEvent.values()).forEach(item-> logger.info(item.ordinal() +" name= " + item.name() + " display= " + item.display() ));
    }


}
