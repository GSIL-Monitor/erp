package com.stosz.tms.mapper.builder;

import org.apache.ibatis.jdbc.SQL;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.ShippingWay;

public class ShippingWayBuilder extends AbstractBuilder<ShippingWay> {

	@Override
	public void buildSelectOther(SQL sql) {
		sql.SELECT(" s.shipping_name shippingName ");
		sql.SELECT(" a.api_name apiName ");

	}

	@Override
	public void buildJoin(SQL sql) {
		sql.LEFT_OUTER_JOIN(" shipping s on _this.shipping_id=s.id"," shipping_track_api a on _this.api_id=a.id");
	}

	@Override
	public void buildWhere(SQL sql, ShippingWay param) {
		like_r(sql, "shipping_way_name", "shippingWayName", param.getShippingWayName());
		eq(sql, "state", "state", param.getState());
		eq(sql, "shipping_id", "shippingId", param.getShippingId());
		in(sql,_this("id"),"id",param.getIdList());
	}

}
