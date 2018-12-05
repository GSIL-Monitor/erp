package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.SensitiveWord;
import org.apache.ibatis.jdbc.SQL;

public class SensitiveWordBuilder extends AbstractBuilder<SensitiveWord> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, SensitiveWord param) {
    	like_i(sql, "name", "name", param.getName());
    }
}
