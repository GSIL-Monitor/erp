package com.stosz.tms.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.BaseDictionary;
import com.stosz.tms.model.Shipping;
import org.apache.ibatis.jdbc.SQL;

public class BaseDictionaryBuilder extends AbstractBuilder<BaseDictionary> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, BaseDictionary param) {
        eq(sql,"type","type",param.getType());
    }
}
