package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 上架目标枚举类型
 *
 * @author liufeng
 * @version [1.0 , 2017/9/20]
 */
public enum GoalEnum implements IEnum {
    normal("正品"),
    clearing("清库存"),
    accessory("配件"),
    gift("赠品")
    ;
    String display;

    GoalEnum(String dis) {
        this.display = dis;
    }


    @Override
    public String display() {
        return this.display;
    }

    public static GoalEnum fromName(String name) {
        for (GoalEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

}
