package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.model.TransitItem;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:ChenShifeng
 * @Description:TransitStockBuilder
 * @Created Time:2017/11/23 14:21
 * @Update Time:
 */
public class TransitItemBuilder extends AbstractBuilder<TransitItem> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {
    }

    @Override
    public void buildWhere(SQL sql, TransitItem param) {

    }

}
