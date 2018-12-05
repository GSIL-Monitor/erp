package com.stosz.product.mapper;

import com.stosz.product.ext.model.DepartmentZoneRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentZoneRelMapper {
	@InsertProvider(type = DepartmentZoneRelBuilder.class,method = "insert")
	int insert(DepartmentZoneRel param);
	
    @Insert({"<script>INSERT IGNORE INTO department_zone_rel(department_id,department_no,department_name,zone_id,usable) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"param\"  separator=\",\" >" +
            " (#{item.departmentId}, #{item.departmentNo}, #{item.departmentName}, #{item.zoneId}, #{item.usable})" +
            " </foreach>" +
            "</script>"})
    void insertList(@Param("id") Integer id, @Param("param") List<DepartmentZoneRel> param);
	
	@DeleteProvider(type = DepartmentZoneRelBuilder.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = DepartmentZoneRelBuilder.class, method = "update")
	int update(DepartmentZoneRel param);
	
    @SelectProvider(type = DepartmentZoneRelBuilder.class, method = "find")
	List<DepartmentZoneRel> find(DepartmentZoneRel param);

	@SelectProvider(type = DepartmentZoneRelBuilder.class, method = "findAllList")
	List<DepartmentZoneRel> findAll(DepartmentZoneRel param);

    @SelectProvider(type = DepartmentZoneRelBuilder.class, method = "count")
	int count(DepartmentZoneRel param);
	
	@SelectProvider(type = DepartmentZoneRelBuilder.class, method = "getById")
	DepartmentZoneRel getById(@Param("id") Integer id);
	
	@Update("UPDATE department_zone_rel SET usable = #{usable} WHERE id = #{id}")
	int updateUsable(@Param("id") Integer id, @Param("usable") Boolean usable);

	@Select("SELECT COUNT(1) FROM department_zone_rel WHERE zone_id = #{zoneId}")
	int countByZoneId(@Param("zoneId") Integer zoneId);
}
