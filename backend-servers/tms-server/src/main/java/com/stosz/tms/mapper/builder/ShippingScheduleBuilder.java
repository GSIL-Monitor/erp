package com.stosz.tms.mapper.builder;

import org.apache.ibatis.jdbc.SQL;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.ShippingSchedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ShippingScheduleBuilder extends AbstractBuilder<ShippingSchedule> {

	@Override
	public void buildSelectOther(SQL sql) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildJoin(SQL sql) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildWhere(SQL sql, ShippingSchedule param) {
		buildWhereBase(sql,param);
		eq(sql,"shipping_way_id","shippingWayId",param.getShippingWayId());
		eq(sql,"wms_id","wmsId",param.getWmsId());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (param.getScheduleDate() != null){
			ge(sql,"schedule_date","scheduleDate",df.format(param.getScheduleDate())+" 00:00:00");
			le(sql,"schedule_date","scheduleDate",df.format(param.getScheduleDate())+" 23:59:59");
		}
		eq(sql,"template_id","templateId",param.getTemplateId());
	}

}
