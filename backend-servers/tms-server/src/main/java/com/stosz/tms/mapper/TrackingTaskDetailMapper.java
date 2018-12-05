package com.stosz.tms.mapper;

import java.util.HashSet;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.stosz.tms.mapper.builder.TrackingTaskDetailBuilder;
import com.stosz.tms.model.TrackingTaskDetail;

public interface TrackingTaskDetailMapper {

	@Select("<script>SELECT hash_value  FROM tracking_task_detail WHERE hash_value IN "
			+ "<foreach item=\"item\" collection=\"hashValues\" open=\"(\" separator=\",\" close=\")\">" + "#{item}" + "</foreach></script>")
	public HashSet<String> queryDetailHashExists(@Param("hashValues") List<String> hashValues);

	@Insert("<script>INSERT INTO tracking_task_detail(tracking_task_id,track_no,track_record,track_status,classify_code,classify_status,track_time,city_name,track_reason,hash_value) values"
			+ "<foreach item=\"item\" collection=\"list\"  separator=\",\" >"
			+ "(#{item.trackingTaskId},#{item.trackNo},#{item.trackRecord},#{item.trackStatus},#{item.classifyCode},#{item.classifyStatus},#{item.trackTime},#{item.cityName},#{item.trackReason},#{item.hashValue})"
			+ "</foreach></script>")
	public int addTrackDeatils(List<TrackingTaskDetail> trackDetails);

	@SelectProvider(type = TrackingTaskDetailBuilder.class, method = "find")
	public List<TrackingTaskDetail> select(TrackingTaskDetail trackingTaskDetail);


	@InsertProvider(type = TrackingTaskDetailBuilder.class, method = "insertSelective")
	public int add(TrackingTaskDetail trackingTaskDetail);
}
