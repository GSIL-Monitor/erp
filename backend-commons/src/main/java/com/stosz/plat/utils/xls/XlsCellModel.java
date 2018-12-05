package com.stosz.plat.utils.xls;

import java.io.Serializable;

/**
 * @auther carter
 * create time    2017-12-06
 */
public class XlsCellModel implements Serializable {

    /**标题*******/
    private  String   title = "";
    /**序号*******/
    private  Integer  sort = 0;
    /**内容*******/
    private  String   columnValue = "";


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
}
