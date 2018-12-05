package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CategoryUserTypeEnum implements IEnum {



	 advertis("广告专员"), checker("排重人员");

	 String display;

    public static final Logger logger = LoggerFactory.getLogger(CategoryUserTypeEnum.class);

    CategoryUserTypeEnum(String disp) {
        this.display = disp;
    }


    
    public String display() {
        return this.display;
    }

    public static CategoryUserTypeEnum fromName(String name) {
        for (CategoryUserTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        logger.info(""+CategoryUserTypeEnum.advertis.ordinal());
        logger.info(""+CategoryUserTypeEnum.checker.ordinal());
    }

}
