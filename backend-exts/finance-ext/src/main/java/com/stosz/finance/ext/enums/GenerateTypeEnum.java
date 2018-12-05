package com.stosz.finance.ext.enums;

import com.stosz.plat.utils.IEnum;


/**
 * @author ChenShifeng
 * 2018-1-7
 *  是否生成状态
 */
public enum GenerateTypeEnum implements IEnum {

    isNull("否"),
    notNull("是")
    ;

    private String display;

    GenerateTypeEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }

    public static GenerateTypeEnum fromName(String name) {
        for (GenerateTypeEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static GenerateTypeEnum fromId(Integer id) {
        for (GenerateTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
