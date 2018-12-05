package com.stosz.tms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.stosz.tms.dto.Parameter;
import com.stosz.tms.mapper.builder.TrackingTaskBuilder;
import com.stosz.tms.model.TrackingTask;

public interface TrackingTaskMapper {

	@UpdateProvider(type = TrackingTaskBuilder.class, method = "updateSelective")
	public int updateSelective(TrackingTask trackingTask);

	@InsertProvider(type = TrackingTaskBuilder.class, method = "insertSelective")
	public int add(TrackingTask trackingTask);

	@SelectProvider(type = TrackingTaskBuilder.class, method = "count")
	public int count(TrackingTask shippingParcel);

	@SelectProvider(type = TrackingTaskBuilder.class, method = "find")
	public List<TrackingTask> find(TrackingTask trackingTask);

	@Select("select count(*) rowCount,IFNULL(max(id),0) maxRecordId from tracking_task where complete=#{complete} and api_code!=#{apiCode}")
	public Parameter<String, Object> queryTrackTaskCount(TrackingTask trackingTask);

	@Select("select * from tracking_task where id<=#{maxRecordId}  and complete=#{complete}  and api_code!=#{apiCode} order by id desc limit ${start},${limit}")
	public List<TrackingTask> getTrackTaskListByTask(TrackingTask trackingTask);

	@Insert("<script>INSERT INTO tracking_task(api_code,track_no,fetch_count,complete,create_at,shipping_way_id,id_order_shipping,old_id_shipping) VALUES "
			+ "<foreach item=\"item\" collection=\"list\"  separator=\",\" >"
			+ "(#{item.apiCode},#{item.trackNo},#{item.fetchCount},#{item.complete},#{item.createAt},#{item.shippingWayId},#{item.idOrderShipping},#{item.oldIdShipping})"
			+ "</foreach></script>")
	public int addList(List<TrackingTask> trackingTasks);

	@Select("SELECT MAX(id_order_shipping) FROM tracking_task")
	public Integer getMaxOrderShippingId();

	@Select("select count(*) from tracking_task where complete=0 and api_code=#{apiCode}")
	public int getTrackTaskCount(String apiCode);

	@Select("select * from tracking_task where complete=0 and api_code=#{apiCode} order by id asc limit ${start},${limit}")
	public List<TrackingTask> getTrackTaskList(@Param("apiCode") String apiCode, @Param("start") int start, @Param("limit") int limit);

	@Update("<script>INSERT INTO tracking_task(id,online_time,returned_time,pending_time,reject_time,receive_time,track_status,track_status_time,routine_code\r\n"
			+ ",routine_status,routine_status_time,classify_status,classify_code,classify_status_time,fetch_count,last_capture_time,delivery_failure_time,complete) values "
			+ "<foreach item=\"item\" collection=\"list\"  separator=\",\" >"
			+ "(#{item.id},#{item.onlineTime},#{item.returnedTime},#{item.pendingTime},#{item.rejectTime},#{item.receiveTime},#{item.trackStatus},#{item.trackStatusTime},#{item.routineCode},#{item.routineStatus},#{item.routineStatusTime},#{item.classifyStatus},#{item.classifyCode},#{item.classifyStatusTime},#{item.fetchCount},#{item.lastCaptureTime},#{item.deliveryFailureTime},#{item.complete})"
			+ "</foreach>  ON DUPLICATE KEY UPDATE online_time=VALUES(online_time),returned_time=VALUES(returned_time)"
			+ ",pending_time=VALUES(pending_time),reject_time=VALUES(reject_time),receive_time=VALUES(receive_time),track_status=VALUES(track_status),track_status_time=VALUES(track_status_time)"
			+ ",routine_code=VALUES(routine_code),routine_status=VALUES(routine_status),routine_status_time=VALUES(routine_status_time),classify_status=VALUES(classify_status)"
			+ ",classify_code=VALUES(classify_code),classify_status_time=VALUES(classify_status_time),fetch_count=VALUES(fetch_count),last_capture_time=VALUES(last_capture_time),delivery_failure_time=VALUES(delivery_failure_time),complete=VALUES(complete) </script>")
	public int batchUpdateTrackTask(List<TrackingTask> trackingTasks);

}
