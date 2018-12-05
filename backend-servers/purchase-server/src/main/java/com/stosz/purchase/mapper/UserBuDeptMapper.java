package com.stosz.purchase.mapper;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.purchase.ext.model.UserBuDept;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBuDeptMapper {

    //-----------------------------------------基础增删改查--------------------------------//
    @InsertProvider(type = UserBuDeptBuilder.class, method = "insert")
    int insert(UserBuDept userBuDept);

    @DeleteProvider(type = UserBuDeptBuilder.class, method = "delete")
    int delete(Integer id);

    @Update("UPDATE user_bu_dept SET ENABLE=#{enable},update_at=now() WHERE id=#{id}")
    int update(@Param("id") Integer id, @Param("enable") Integer enable);

    @SelectProvider(type = UserBuDeptBuilder.class, method = "getById")
    UserBuDept getById(Integer id);

    @Select("select * from user_bu_dept where bu_department_id=#{buDeptId} and user_id=#{buyerId}")
    UserBuDept getByUnique(@Param("buDeptId") Integer buDeptId, @Param("buyerId") Integer buyerId);

    @SelectProvider(type = UserBuDeptBuilder.class, method = "count")
    int count(UserBuDept userBuDept);

    @SelectProvider(type = UserBuDeptBuilder.class, method = "find")
    List<UserBuDept> find(UserBuDept userBuDept);

    /**
     *
     * 根据采购员id获取当前用户拥有的部门
     * @param userId 用户id
     * @return 拥有的部门
     */
    @Select("select bu_department_id as id ,bu_department_no as department_no ,bu_department_name as department_name from user_bu_dept where user_id = #{userId} and enable=1 order By id asc")
    List<Department> findByCurrentUser(Integer userId);

    /**
     * 根据搜索获取匹配的采购员
     * @param search 采购员搜索
     * @return 匹配结果
     */
    @Select("select user_id as id ,user_name as lastname from user_bu_dept where lower(user_name) like lower(concat('%',#{search},'%')) and bu_department_id = #{buDeptId} and enable = 1")
    List<User> findUser(@Param("search") String search, @Param("buDeptId") Integer buDeptId);


}
