package com.stosz.tms.mapper.builder;

import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.dto.TrackingStatusClassifyAddDto;
import com.stosz.tms.model.TrackStatusClassify;

public class TrackingStatusClassifyBuilder extends AbstractBuilder<TrackStatusClassify> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {

	}

	@Override
	public void buildWhere(SQL sql, TrackStatusClassify param) {
		eq(sql, "track_api_id", "trackApiId", param.getTrackApiId());
		in(sql, "track_api_id", "trackApiId", param.getTrackApiIdList());
	}

	public String addStatus(@Param("classifyAddDto") TrackingStatusClassifyAddDto classifyAddDto) {
		SQL sql = new SQL();
		sql.INSERT_INTO(getTableName());
		sql.INTO_COLUMNS("track_api_id", "shipping_status", "shipping_status_code", "classify_status", "classify_code", "enable", "creator",
				"creator_id");

		StringBuilder sb = new StringBuilder(" values ");

		MessageFormat mf = new MessageFormat("(" + classifyAddDto.getTrackApiId() + ",\"{0}\",\"{1}\",\"{2}\",\"{3}\",1," + "\""
				+ classifyAddDto.getCreator() + "\"," + classifyAddDto.getCreatorId() + ")");

		final List<TrackingStatusClassifyAddDto.StatusClassify> statusClassifyList = classifyAddDto.getStatusClassifyList();

		statusClassifyList.forEach(e -> {
			sb.append(mf.format(new Object[] { e.getShippingStatus(), e.getShippingStatusCode(), e.getClassifyStatus(), e.getClassifyCode() }));
			sb.append(",");
		});

		return sql.toString() + sb.substring(0, sb.length() - 1);
	}
}
