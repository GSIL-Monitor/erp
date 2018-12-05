package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.ProductSku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;

public class ProductSkuBulider extends AbstractBuilder<ProductSku> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, ProductSku param) {
		
	}

    public String findByDate(@Param("param") ProductSku param) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableNameThis());
        ge(sql, "_this.update_at", "param.minCreateAt", param.getMinCreateAt());
        le(sql, "_this.update_at", "param.maxCreateAt", param.getMaxCreateAt());
        String page = buildSearchPageSql(param);
        return sql.toString() + page;
    }

    public String findByProductId(@Param("productId") Integer productId) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableNameThis());
        eq(sql, "product_id", "productId", productId);
        return sql.toString();
    }

    public String insertList(@Param("id") Integer id, @Param("productSkuList") List<ProductSku> productSkuList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO product_sku ");
        sb.append("(product_id,spu,sku,barcode,create_at)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'productSkuList[{0}].productId}, #'{'productSkuList[{0}].spu},#'{'productSkuList[{0}].sku},#'{'productSkuList[{0}].barcode},current_timestamp())");
        for (int i = 0; i < productSkuList.size(); i++) {
            String j = i + "";
            sb.append(mf.format(new Object[]{j}));
            if (i < productSkuList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
