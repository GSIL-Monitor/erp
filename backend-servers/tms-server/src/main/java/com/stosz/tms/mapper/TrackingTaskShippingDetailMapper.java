package com.stosz.tms.mapper;

import java.util.HashSet;
import java.util.List;

import com.stosz.tms.model.api.KerryStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.stosz.tms.model.TrackingTaskShippingDetail;

@Repository
public interface TrackingTaskShippingDetailMapper {

	@Select("<script>SELECT hash_value  FROM tracking_task_shipping_detail WHERE hash_value IN "
			+ "<foreach item=\"item\" collection=\"hashValues\" open=\"(\" separator=\",\" close=\")\">" + "#{item}" + "</foreach></script>")
	public HashSet<String> queryDetailHashExists(@Param("hashValues") List<String> hashValues);

	@Insert("<script>INSERT INTO tracking_task_shipping_detail( track_no,track_status,track_time,location,track_record,create_at,api_code,hash_value) values"
			+ "<foreach item=\"item\" collection=\"list\"  separator=\",\" >"
			+ "(#{item.trackNo},#{item.trackStatus},#{item.trackTime},#{item.location},#{item.trackRecord},#{item.createAt},#{item.apiCode},#{item.hashValue})"
			+ "</foreach></script>")
	public int addTrackDeatils(List<TrackingTaskShippingDetail> trackDetails);

	@Select("<script> SELECT *  FROM tracking_task_shipping_detail  where track_no in "
			+ "<foreach item=\"item\" collection=\"trackNoList\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>  ORDER BY track_time ASC </script> ")
	public List<TrackingTaskShippingDetail> queryDetails(@Param("trackNoList") List<String> trackNoList);
    @Insert("")
	int insertKerryTrack(KerryStatus status);
}
