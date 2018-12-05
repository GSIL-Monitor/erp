package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.purchase.ext.model.SkuPurchaseInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Set;

public class SkuPurchaseInfoBuilder extends AbstractBuilder<SkuPurchaseInfo> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildWhere(SQL sql, SkuPurchaseInfo param) {

	}

	public String delSkuPurchaseInfoByPurchaseId(@Param("supplierId") Integer supplierId, @Param("set") Set<String> set){
		SQL sql = new SQL();
		sql.DELETE_FROM(getTableName());
		eq(sql,"supplier_id","supplierId",supplierId);
		if(CollectionUtils.isNullOrEmpty(set)){
			return "";
		}else{
			StringBuilder stringBuilder = new StringBuilder();
			for (String sku: set
				 ) {
				if(stringBuilder.length()>0){
					stringBuilder.append(",'").append(sku).append("'");
				}else{
					stringBuilder.append("'").append(sku).append("'");
				}
			}
			sql.WHERE(String.format("sku in (%s)",stringBuilder.toString()));
			return sql.toString();
		}
	}
}
