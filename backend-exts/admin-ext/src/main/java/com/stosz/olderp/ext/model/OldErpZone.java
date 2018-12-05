package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/12]
 */
public class OldErpZone extends AbstractParamEntity implements Serializable, ITable {

    private Integer id;//主键id
    private Integer parentId=new Integer(0); //父级id
    private Integer countryId=new Integer(0); //国家id
    private String title=""; //title
    private String code="";//地区编码
    private String currency=""; //币种

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String getTable() {
        return "erp_zone";
    }
}
