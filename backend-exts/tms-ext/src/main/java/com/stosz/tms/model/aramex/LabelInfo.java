package com.stosz.tms.model.aramex;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;

public class LabelInfo implements Serializable{

    public static final Logger logger = LoggerFactory.getLogger(LabelInfo.class);


    private Integer ReportID;//据文档固定9201

    private String ReportType;// “URL” or “RPT”

    public void setReportID(Integer reportID) {
        ReportID = reportID;
    }

    public void setReportType(String reportType) {
        ReportType = reportType;
    }

    @JsonProperty(value="ReportID")
    public Integer getReportID(){return ReportID;}
    @JsonProperty(value="ReportType")
    public String getReportType(){return ReportType;}

    public static void main(String[] args) {
        Field[] fields = LabelInfo.class.getDeclaredFields();
        for (Field field : fields) {
            String typeName = field.getType().getSimpleName();
            String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
            String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
            logger.info(getMethod);
        }
    }
}
