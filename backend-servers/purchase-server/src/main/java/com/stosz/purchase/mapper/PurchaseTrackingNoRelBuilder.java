package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseTrackingNo;
import com.stosz.purchase.ext.model.PurchaseTrackingNoRel;
import com.stosz.purchase.utils.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class PurchaseTrackingNoRelBuilder extends AbstractBuilder<PurchaseTrackingNoRel> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildWhere(SQL sql, PurchaseTrackingNoRel param) {

	}


	public String getTrackingNoByPurchaseNo(@Param("purchaseNo") List<Purchase> purchaseNo){
		SQL sql = new SQL();
		sql.SELECT("purchase_no,group_concat(tracking_no) as tracking_no");
		sql.FROM(this.getTableNameThis());
		if(ArrayUtils.isNotEmpty(purchaseNo)){
			StringBuilder builder = new StringBuilder();
			for (Purchase purchase: purchaseNo){
				if(builder.length()>0){
					builder.append(",");
				}
				builder.append(purchase.getPurchaseNo());
			}

			sql.WHERE(String.format("purchase_no in (%s) ",builder.toString()));
			sql.GROUP_BY("purchase_no");
		}
		return sql.toString();
	}

}
