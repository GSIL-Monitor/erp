package com.stosz.tms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.stosz.tms.dto.TrackingStatusClassifyAddDto;
import com.stosz.tms.mapper.builder.TrackingStatusClassifyBuilder;
import com.stosz.tms.model.TrackStatusClassify;

@Repository
public interface TrackingStatusClassifyMapper {

	@SelectProvider(type = TrackingStatusClassifyBuilder.class, method = "find")
	public List<TrackStatusClassify> select(TrackStatusClassify trackStatusClassify);

	@SelectProvider(type = TrackingStatusClassifyBuilder.class, method = "count")
	public int count(TrackStatusClassify trackStatusClassify);

	@InsertProvider(type = TrackingStatusClassifyBuilder.class, method = "addStatus")
	@Options(useGeneratedKeys = false)
	public int addStatus(@Param("classifyAddDto") TrackingStatusClassifyAddDto classifyAddDto);

	@UpdateProvider(type = TrackingStatusClassifyBuilder.class, method = "updateSelective")
	public int update(TrackStatusClassify statusClassify);

	@Select(" SELECT a.*,b.api_code apiCode FROM tracking_status_classify  a LEFT JOIN shipping_track_api b ON a.`track_api_id`=b.id WHERE ENABLE=1")
	public List<TrackStatusClassify> selectAll();

	
	@SelectProvider(type = TrackingStatusClassifyBuilder.class, method = "findAll")
	public List<TrackStatusClassify> selectAllByParam(TrackStatusClassify trackStatusClassify);
}
