package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.model.InvalidItem;
import com.stosz.store.ext.model.TakeStockItem;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:ChenShifeng
 * @Description:TransitStockItemBuilder
 * @Created Time:2017/11/23 14:21
 * @Update Time:
 */
public class InvalidItemBuilder extends AbstractBuilder<InvalidItem> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, InvalidItem param) {

    }


}
