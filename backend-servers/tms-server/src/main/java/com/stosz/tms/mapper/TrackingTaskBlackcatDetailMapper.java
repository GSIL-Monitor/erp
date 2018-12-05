package com.stosz.tms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.stosz.tms.model.TrackingTaskBlackcatDetail;

@Repository
public interface TrackingTaskBlackcatDetailMapper {

	@Insert("<script>INSERT INTO tracking_task_blackcat_detail(track_no,  track_status,  track_time,  location , track_record ,create_at,file_name ) VALUES "
			+ "<foreach item=\"item\" collection=\"list\"  separator=\",\" >"
			+ "(#{item.trackNo},#{item.trackStatus},#{item.trackTime},#{item.location},#{item.trackRecord},#{item.createAt},#{item.fileName})"
			+ "</foreach></script>")
	public int addDeatils(List<TrackingTaskBlackcatDetail> details);

	@Select("<script> SELECT *  FROM tracking_task_blackcat_detail  where track_no in "
			+ "<foreach item=\"item\" collection=\"trackNoList\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>  ORDER BY track_time ASC </script> ")
	public List<TrackingTaskBlackcatDetail> queryDetails(@Param("trackNoList") List<String> trackNoList);

}
