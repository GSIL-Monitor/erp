package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpProductSku;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class OldErpProductSkuBuilder extends AbstractBuilder<OldErpProductSku> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, OldErpProductSku param) {
		
	}
	
	
	public String findList(@Param("limit") Integer limit, @Param("start") Integer start){
		return  " SELECT * FROM erp_product_sku limit " + "#{limit} " + "offset " + "#{start}";
	}

	//	@Select("<script>SELECT id_product_option AS attribute_id,id_product_option_value AS attribute_value_id "
//			+ "FROM erp_product_option_value"
//			+ "WHERE id_product_option_value in  "
//			+ " <foreach item=\"item\" index=\"index\" collection=\"optionValues\" open=\"(\" separator=\",\" close=\")\">"
//			+ " #{item} "
//			+ " </foreach></script>")
	public String getByAttributeValueId(@Param("optionValues") List<String> optionValues) {
		SQL sql = new SQL();
		sql.SELECT("id_product_option AS attribute_id,id_product_option_value AS attribute_value_id");
		sql.FROM("erp_product_option_value");
		StringBuilder whereSql = new StringBuilder("id_product_option_value in ( ");
		for (String id : optionValues) {
			whereSql.append(id).append(",");
		}
		whereSql.deleteCharAt(whereSql.length() - 1);
		whereSql.append(")");
		sql.WHERE(whereSql.toString());
		return sql.toString();
	}
	
}
