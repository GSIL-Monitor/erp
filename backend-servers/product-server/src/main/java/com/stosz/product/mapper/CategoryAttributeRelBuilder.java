package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.CategoryAttributeRel;
import org.apache.ibatis.jdbc.SQL;

public class CategoryAttributeRelBuilder extends AbstractBuilder<CategoryAttributeRel> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }
    
    
    @Override
    public void buildWhere(SQL sql, CategoryAttributeRel param) {
        eq(sql, _this("category_id"), "categoryId", param.getCategoryId());
        eq(sql, _this("attribute_id"), "attributeId", param.getAttributeId());

    }
}
