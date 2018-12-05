package com.stosz.tms.mapper;

import java.util.List;

import com.stosz.tms.mapper.builder.ShippingAllocationTemplateBuilder;
import com.stosz.tms.model.ShippingAllocationTemplate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.stosz.tms.mapper.builder.ShippingScheduleBuilder;
import com.stosz.tms.model.ShippingSchedule;

@Repository
public interface ShippingScheduleMapper {

	@Select("<script>SELECT * FROM shipping_schedule s WHERE s.shipping_way_id IN"
			+ "<foreach item=\"item\" collection=\"shippingWayIds\" open=\"(\" separator=\",\" close=\")\">" + "${item}"
			+ "</foreach>  AND (s.wms_id=#{wmsId} or s.wms_id=-1)  and DATE_FORMAT(schedule_date,'%Y-%m-%d')=#{scheduleDate} and enable=1 </script>")
	public List<ShippingSchedule> queryAssignSchedule(@Param("shippingWayIds") List<Integer> shippingWayIds, @Param("wmsId") Integer wmsId,
			@Param("scheduleDate") String scheduleDate);

	@Select("SELECT * FROM shipping_schedule s  where s.shipping_way_id=#{shippingWayId} and s.wms_id=#{wmsId} and DATE_FORMAT(schedule_date,'%Y-%m-%d')=#{scheduleDate} and enable=1")
	public ShippingSchedule querySchedule(@Param("shippingWayId") Integer shippingWayId, @Param("wmsId") Integer wmsId,
			@Param("scheduleDate") String scheduleDate);

	@InsertProvider(type = ShippingScheduleBuilder.class, method = "insert")
	public int insert(ShippingSchedule shippingSchedule);

	@Select("select * from shipping_schedule where id=#{id} ")
	public ShippingSchedule getById(Integer id);

	@Update("update shipping_schedule set once_special_num=once_special_num+#{addNum},today_special_num=today_special_num+#{addNum},version=version+1 where id=#{id} and once_special_num<each_special_num and today_special_num<special_cargo_num")
	public int updateSpecialNum(ShippingSchedule schedule);

	@Update("update shipping_schedule set once_general_num=once_general_num+#{addNum},today_general_num=today_general_num+#{addNum},version=version+1 where id=#{id} and once_general_num<each_general_num and today_general_num<general_cargo_num")
	public int updateGeneralNum(ShippingSchedule schedule);

	@Update("UPDATE shipping_schedule SET once_cargo_num=once_cargo_num+#{addNum},today_cargo_num=today_cargo_num+#{addNum},version=version+1 where id=#{id} and once_cargo_num<each_cargo_num and today_cargo_num<cargo_num")
	public int updateCargoNum(ShippingSchedule schedule);
	
	@Update("update shipping_schedule set once_special_num=once_special_num+#{addNum},today_special_num=today_special_num+#{addNum},version=version+1 where id=#{id} ")
	public int updateSpecialNumNotLimit(ShippingSchedule schedule);

	@Update("update shipping_schedule set once_general_num=once_general_num+#{addNum},today_general_num=today_general_num+#{addNum},version=version+1 where id=#{id}")
	public int updateGeneralNumNotLimit(ShippingSchedule schedule);

	@Update("UPDATE shipping_schedule SET once_cargo_num=once_cargo_num+#{addNum},today_cargo_num=today_cargo_num+#{addNum},version=version+1 where id=#{id}")
	public int updateCargoNumNotLimit(ShippingSchedule schedule);
	
	@UpdateProvider(type = ShippingScheduleBuilder.class, method = "updateSelective")
	public int update(ShippingSchedule schedule);

	@SelectProvider(type = ShippingScheduleBuilder.class, method = "count")
	public int count(ShippingSchedule shippingSchedule);

	@SelectProvider(type = ShippingScheduleBuilder.class, method = "find")
	public List<ShippingSchedule> queryList(ShippingSchedule shippingSchedule);

	@InsertProvider(type = ShippingScheduleBuilder.class, method = "insert")
	public int add(ShippingSchedule shippingSchedule);

}
