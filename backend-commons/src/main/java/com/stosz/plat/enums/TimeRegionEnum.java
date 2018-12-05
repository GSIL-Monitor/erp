package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @auther carter
 * create time    2017-12-12
 * 查询条件枚举,查询最多的期限；
 *
 */
public enum  TimeRegionEnum implements IEnum {

    InThreeMonth("3个月之内"),
    InSixMonth("6个月之内"),
    InOneYear("1年之内"),
    all("所有");

    private String display;

    public static final Logger logger = LoggerFactory.getLogger(TimeRegionEnum.class);

    TimeRegionEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static TimeRegionEnum fromName(String name) {
        for (TimeRegionEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public  Pair<LocalDateTime,LocalDateTime> getTimeRegionPair()
    {
        LocalDateTime now = LocalDateTime.now();
//        String left = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

       LocalDateTime  before = null;
        switch (this) {
            case InOneYear:
                before = now.minusMonths(12);
                break;
            case InSixMonth:
                before = now.minusMonths(6);
                break;
            case all:
                before = LocalDateTime.of(2000,1,1,1,1);
                break;
            default:
                before = now.minusMonths(3);
        }
//        return ImmutablePair.of(before.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),left);
        return ImmutablePair.of(before,now);

    }


    public static void main(String[] args) {

        logger.info("最近3个月："+TimeRegionEnum.InThreeMonth.getTimeRegionPair());

        logger.info("最近6个月："+TimeRegionEnum.InSixMonth.getTimeRegionPair());

        logger.info("最近12个月："+TimeRegionEnum.InOneYear.getTimeRegionPair());
    }



}
