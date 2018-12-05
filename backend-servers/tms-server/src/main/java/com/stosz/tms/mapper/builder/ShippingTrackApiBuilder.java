package com.stosz.tms.mapper.builder;

import org.apache.ibatis.jdbc.SQL;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.ShippingTrackApi;

public class ShippingTrackApiBuilder extends AbstractBuilder<ShippingTrackApi> {

	@Override
	public void buildSelectOther(SQL sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildJoin(SQL sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildWhere(SQL sql, ShippingTrackApi param) {
		// TODO Auto-generated method stub
		buildWhereBase(sql,param);
	}

}
