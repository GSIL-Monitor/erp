package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.ProductSkuAttributeValueRel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;

public class ProductSkuAttributeValueRelBuilder extends AbstractBuilder<ProductSkuAttributeValueRel> {

	@Override
	public void buildSelectOther(SQL sql) {
		
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
		
	}

	@Override
	public void buildWhere(SQL sql, ProductSkuAttributeValueRel param) {
		
		
	}


    public String findBySku(@Param("sku") String sku) {
        SQL sql = new SQL();
		sql.SELECT("a.title as attribute_title, av.title as attribute_value_title");
		sql.FROM(getTableNameThis());
		sql.LEFT_OUTER_JOIN(joinString("attribute", "a", "id", "attribute_id"));
		sql.LEFT_OUTER_JOIN(joinString("attribute_value", "av", "id", "attribute_value_id"));
		eq(sql, "_this.sku", "sku", sku);
		return sql.toString();
	}
    
    public void skuByAttrValueWhere(SQL sql, Integer productId,List<Integer> attributeIds){
    	eq(sql, "product_id", "productId", productId);
    	if(attributeIds != null){
    		if(attributeIds.isEmpty()){
    			sql.WHERE("1 != 1");
    			return;
    		}
    		StringBuilder sb = new StringBuilder();
    		for(Integer val : attributeIds){
    			sb.append("attribute_value_id = "+val+" OR ");
    		}
    		String s = sb.toString();
    		if(s.length() > 0){
    			s = sb.substring(0, sb.length() - " OR ".length());
    		}
    		sql.WHERE(s);
    	}
    	sql.GROUP_BY("sku");
    }
    
    public String countSku(@Param("productId") Integer productId,@Param("attributeIds") List<Integer> attributeIds){
    	SQL sql = new SQL();
    	sql.SELECT("COUNT(1)");
    	sql.FROM(getTableName());
    	skuByAttrValueWhere(sql,productId,attributeIds);
    	return "SELECT COUNT(1) FROM ( " + sql.toString() + " ) a ";
    }
    
    
    public String getSkuByAttrValueIds(@Param("productId") Integer productId,@Param("attributeIds") List<Integer> attributeIds){
    	SQL sql = new SQL();
    	sql.SELECT("*");
    	sql.FROM(getTableName());
    	skuByAttrValueWhere(sql,productId,attributeIds);
    	return sql.toString();
    }
    

    public String insertList(@Param("id") Integer id, @Param("productSkuAttributeValueRelList") List<ProductSkuAttributeValueRel> productSkuAttributeValueRelList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO product_sku_attribute_value_rel");
        sb.append("(sku,product_id,attribute_id,attribute_value_id)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'productSkuAttributeValueRelList[{0}].sku}, #'{'productSkuAttributeValueRelList[{0}].productId},#'{'productSkuAttributeValueRelList[{0}].attributeId},#'{'productSkuAttributeValueRelList[{0}].attributeValueId})");
        for (int i = 0; i < productSkuAttributeValueRelList.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < productSkuAttributeValueRelList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
//SELECT * FROM product_sku_attribute_value_rel WHERE product_id = 60308 AND attribute_value_id IN (222479,222508,222520) GROUP BY sku HAVING COUNT(1) = 3    
    public String findByProductIdAttrValueIds(ProductSkuAttributeValueRel param){
    	SQL sql = new SQL();
    	sql.SELECT("*");
    	sql.FROM(getTableName());
    	findByProductIdAttrValueIdsWhere(sql,param);
    	return sql.toString();
    }

	private void findByProductIdAttrValueIdsWhere(SQL sql, ProductSkuAttributeValueRel param) {
		eq(sql, "product_id", "productId", param.getProductId());
		if(param.getAttributeValueIds() != null){
			if(!param.getAttributeValueIds().isEmpty()){
				String val = StringUtils.join(param.getAttributeValueIds(), ',');
			    sql.WHERE("attribute_value_id IN ( " + val + " )");
			}
		}
		sql.GROUP_BY("sku");
		sql.HAVING("COUNT(1) = "+param.getCountAttribute()+"");
    }
	/**
	 * 根据产品id/属性id/属性值id统计sku
	 */
	public String countById(ProductSkuAttributeValueRel param){
		SQL sql = new SQL();
		sql.SELECT("COUNT(1)");
		sql.FROM(getTableName());
		countByIdWhere(sql,param);
		return sql.toString();
	}
	public void countByIdWhere(SQL sql,ProductSkuAttributeValueRel param){
		eq(sql, "product_id", "productId", param.getProductId());
		eq(sql, "attribute_id", "attributeId", param.getAttributeId());
		eq(sql, "attribute_value_id", "attributeValueId", param.getAttributeValueId());
	}
    
}



