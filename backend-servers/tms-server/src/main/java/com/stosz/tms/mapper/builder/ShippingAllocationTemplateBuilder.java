package com.stosz.tms.mapper.builder;

import org.apache.ibatis.jdbc.SQL;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.ShippingAllocationTemplate;

public class ShippingAllocationTemplateBuilder extends AbstractBuilder<ShippingAllocationTemplate> {

	@Override
	public void buildSelectOther(SQL sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildJoin(SQL sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildWhere(SQL sql, ShippingAllocationTemplate param) {
		buildWhereBase(sql,param);
		eq(sql,"shipping_way_id","shippingWayId",param.getShippingWayId());
		eq(sql,"wms_id","wmsId",param.getWmsId());
	}
}
