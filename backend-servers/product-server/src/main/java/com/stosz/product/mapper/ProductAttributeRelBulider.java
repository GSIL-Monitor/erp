package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.ProductAttributeRel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ProductAttributeRelBulider extends AbstractBuilder<ProductAttributeRel> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, ProductAttributeRel param) {
		
	}

    public String findByDate(@Param("param") ProductAttributeRel param) {
        SQL sql = new SQL();
        sql.SELECT("_this.attribute_id, _this.product_id, a.title as attribute_title");
        sql.FROM("product_attribute_rel _this");
        sql.LEFT_OUTER_JOIN(joinString("attribute", "a", "id", "attribute_id"));
        ge(sql, "_this.update_at", "param.minCreateAt", param.getMinCreateAt());
        le(sql, "_this.update_at", "param.maxCreateAt", param.getMaxCreateAt());
        String page = buildSearchPageSql(param);
        return sql.toString() + page;
    }
}
