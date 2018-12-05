package com.stosz.tms.model.philippine;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class PhilippineTrackResponseData {
    public static final Logger logger = LoggerFactory.getLogger(PhilippineTrackResponseData.class);

    private String OperateTime;
    private String OperateAttr;
    private String OperateFlag;
    private String IsPublic;
    private String ShortAttr;

    public void setOperateTime(String operateTime) {
        OperateTime = operateTime;
    }

    public void setOperateAttr(String operateAttr) {
        OperateAttr = operateAttr;
    }

    public void setOperateFlag(String operateFlag) {
        OperateFlag = operateFlag;
    }

    public void setIsPublic(String isPublic) {
        IsPublic = isPublic;
    }

    public void setShortAttr(String shortAttr) {
        ShortAttr = shortAttr;
    }

    @JsonProperty(value="OperateTime")
    public String getOperateTime(){return OperateTime;}
    @JsonProperty(value="OperateAttr")
    public String getOperateAttr(){return OperateAttr;}
    @JsonProperty(value="OperateFlag")
    public String getOperateFlag(){return OperateFlag;}
    @JsonProperty(value="IsPublic")
    public String getIsPublic(){return IsPublic;}
    @JsonProperty(value="ShortAttr")
    public String getShortAttr(){return ShortAttr;}

    public static void main(String[] args) {
        Field[] fields = PhilippineTrackResponseData.class.getDeclaredFields();
        for (Field field : fields) {
            String typeName = field.getType().getSimpleName();
            String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
            String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
            logger.info(getMethod);
        }
    }
}
