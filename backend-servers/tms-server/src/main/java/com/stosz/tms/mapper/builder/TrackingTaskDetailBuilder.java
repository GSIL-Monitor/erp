package com.stosz.tms.mapper.builder;

import org.apache.ibatis.jdbc.SQL;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.TrackingTaskDetail;

public class TrackingTaskDetailBuilder extends AbstractBuilder<TrackingTaskDetail> {

	@Override
	public void buildSelectOther(SQL sql) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildJoin(SQL sql) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildWhere(SQL sql, TrackingTaskDetail param) {
		eq(sql,"tracking_task_id","trackingTaskId",param.getTrackingTaskId());
	}

}
