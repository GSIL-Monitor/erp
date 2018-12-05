package com.stosz.tms.mapper.builder;

import org.apache.ibatis.jdbc.SQL;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.TrackingTask;

public class TrackingTaskBuilder extends AbstractBuilder<TrackingTask> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {

	}

	@Override
	public void buildWhere(SQL sql, TrackingTask param) {
		buildWhereBase(sql,param);
		eq(sql, "complete", "complete", param.getComplete());
		eq(sql, "order_id", "orderId", param.getOrderId());
		eq(sql, "shipping_parcel_id", "shippingParcelId", param.getShippingParcelId());
		eq(sql,"track_no","trackNo",param.getTrackNo());
		eq(sql,"shipping_way_id","shippingWayId",param.getShippingWayId());
		eq(sql, "api_code", "apiCode", param.getApiCode());
		in(sql,"shipping_parcel_id","shippingParcelId",param.getParcelIdList());
		eq(sql, "classify_code", "classifyCode", param.getClassifyCode());

		if (param.getTimeType() != null){
			String filedName = null;
			String cloumName = null;
			switch (param.getTimeType()){
				case 1:
					cloumName = "online_time";
					break;
				case 2:
					cloumName= "pending_time";
					break;
				case 3:
					cloumName  = "reject_time";
					break;
				case 4:
					cloumName = "returned_time";
					break;
				case 5:
					cloumName = "last_capture_time";
					break;
				case 6:
					cloumName = "receive_time";
					break;
			}
			ge(sql,cloumName,"startTime",param.getStartTime());
			le(sql,cloumName,"endTime",param.getEndTime());
		}
	}

}
