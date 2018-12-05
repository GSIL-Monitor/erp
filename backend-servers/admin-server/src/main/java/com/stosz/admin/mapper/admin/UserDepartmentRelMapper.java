package com.stosz.admin.mapper.admin;

import com.stosz.product.ext.model.UserDepartmentRel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDepartmentRelMapper {
	
	@InsertProvider(type = UserDepartmentRelBuilder.class, method = "insert")
	Integer insert(UserDepartmentRel param);

	@DeleteProvider(type = UserDepartmentRelBuilder.class, method = "delete")
	Integer delete(@Param("id") Integer id);

	@UpdateProvider(type = UserDepartmentRelBuilder.class, method = "update")
	Integer update(UserDepartmentRel param);

	@SelectProvider(type = UserDepartmentRelBuilder.class, method = "getById")
	UserDepartmentRel getById(@Param("id") Integer id);

	@SelectProvider(type = UserDepartmentRelBuilder.class, method = "find")
	List<UserDepartmentRel> find(UserDepartmentRel param);
	
	@Select("SELECT * FROM user_department_rel WHERE user_id = #{userId}")
	List<UserDepartmentRel> findByUserID(@Param("userId") Integer userId);
	
	@Select("SELECT * FROM user_department_rel WHERE usable = #{usable} AND user_id = #{userId}")
	List<UserDepartmentRel> findByUserId(@Param("userId") Integer userId, @Param("usable") Boolean usable);
	
	@SelectProvider(type = UserDepartmentRelBuilder.class, method = "count")
	Integer count(UserDepartmentRel param);

	@Select("select department_id from user_department_rel where user_id = #{userId}")
	List<Integer> findDeptIdByUserId(@Param("userId") Integer userId);
	
}
