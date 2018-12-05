package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.ProductAttributeValueRel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;

public class ProductAttributeValueRelBulider extends AbstractBuilder<ProductAttributeValueRel> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, ProductAttributeValueRel param) {
		
	}

	public String findByDate(@Param("param") ProductAttributeValueRel param) {
		SQL sql = new SQL();
		sql.SELECT("_this.product_id ,a.title as option_title , av.title ");
		sql.FROM("product_attribute_value_rel _this");
		sql.LEFT_OUTER_JOIN(joinString("attribute_value", "av", "id", "attribute_value_id"));
		sql.LEFT_OUTER_JOIN(joinString("product_attribute_rel", "par", "id", "product_attribute_id"));
		sql.LEFT_OUTER_JOIN("attribute a ON a.id = av.attribute_id");
		ge(sql, "_this.update_at", "param.minCreateAt", param.getMinCreateAt());
		le(sql, "_this.update_at", "param.maxCreateAt", param.getMaxCreateAt());
		String page = buildSearchPageSql(param);
		return sql.toString() + page;
	}

    public String findByProductId(@Param("productId") Integer productId) {
        SQL sql = new SQL();
        sql.SELECT("_this.id, _this.product_id ,a.title as option_title , av.title ");
        sql.FROM("product_attribute_value_rel _this");
        sql.LEFT_OUTER_JOIN(joinString("attribute_value", "av", "id", "attribute_value_id"));
        sql.LEFT_OUTER_JOIN(joinString("product_attribute_rel", "par", "id", "product_attribute_id"));
        sql.LEFT_OUTER_JOIN("attribute a ON a.id = av.attribute_id");
        eq(sql, "_this.product_id", "productId", productId);
        return sql.toString();
    }

    public String insertList(@Param("id") Integer id, @Param("productAttributeValueRelList") List<ProductAttributeValueRel> productAttributeValueRelList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO product_attribute_value_rel");
        sb.append("(product_attribute_id,attribute_value_id,product_id,creator,creator_id,create_at)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'productAttributeValueRelList[{0}].productAttributeId}, #'{'productAttributeValueRelList[{0}].attributeValueId},#'{'productAttributeValueRelList[{0}].productId},#'{'productAttributeValueRelList[{0}].creator},#'{'productAttributeValueRelList[{0}].creatorId},current_timestamp())");
        for (int i = 0; i < productAttributeValueRelList.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < productAttributeValueRelList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


}
