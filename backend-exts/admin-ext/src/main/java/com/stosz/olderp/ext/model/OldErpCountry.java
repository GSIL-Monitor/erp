package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * 老erp产品分类的实体
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
public class OldErpCountry extends AbstractParamEntity implements Serializable, ITable {

    private Integer id;
    private String title="";
    private String code="";
    private String ename="";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    @Override
    public Integer getId() {

        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getTable() {
        return "erp_country";
    }
}
