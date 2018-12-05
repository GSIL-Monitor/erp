package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * @auther carter
 * create time    2017-11-07
 */
public class OldErpBlackList extends AbstractParamEntity implements Serializable, ITable {

    @DBColumn
    private Integer idBlacklist = 0;
    @DBColumn
    private String field = "";
    @DBColumn
    private String title = "";
    @DBColumn
    private String level = "";
    @DBColumn
    private Integer idUser = 0;


    @Override
    public String getTable() {
        return "erp_blacklist";
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public Integer getId() {
        return idBlacklist;
    }

    @Override
    public void setId(Integer id) {
        this.idBlacklist=id;
    }

    public Integer getIdBlacklist() {
        return idBlacklist;
    }

    public void setIdBlacklist(Integer idBlacklist) {
        this.idBlacklist = idBlacklist;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}

