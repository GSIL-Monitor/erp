package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 用户管理模块的dao
 *
 * @author xiongchenyang
 * @version 1.0 , 2017/8/24
 */

@Repository
public interface UserMapper {
    /**
     * 新增用户，目前只用在OA用户同步
     */
    @Insert("insert user set id=#{id}, loginid = #{loginid}, lastname = #{lastname}, department_id = #{departmentId}, manager_id = #{managerId},ecology_pinyin_search = #{ecologyPinyinSearch}, state = #{state} ,email = #{email}, old_id = #{oldId},create_at = current_timestamp()")
    Integer insert(User user);

    @InsertProvider(type = UserBuilder.class, method = "insertBatch")
    Integer insertBatch(@Param("userList") List<User> userList, @Param("id") Integer id);


    /**
     * 根据id删除用户
     */
    @DeleteProvider(type = UserBuilder.class, method = "delete")
    Integer delete(Integer id);

    /**
     * 截断表，仅限全量导入数据时使用，其余地方慎用！！！
     */
    @Delete("delete from user")
    void truncate();


    /**
     * 同步OA信息时，更新OA的用户信息
     */
    @Update("update user set  loginid = #{loginid}, lastname = #{lastname}, department_id = #{departmentId}, manager_id = #{managerId},ecology_pinyin_search = #{ecologyPinyinSearch}, state = #{state} ,email = #{email}, create_at = current_timestamp() where id = #{id}")
    Integer update(User user);

    /**
     * 修改用户对应的老erp用户id
     */
    @Update({"update user set old_id = #{oldId} where id = #{id}"})
    Integer updateOldId(User user);


    @SelectProvider(type = UserBuilder.class, method = "getById")
    User getById(Integer id);

    @Select("select * from user where lastname = #{lastname} and loginid is not null and loginid != '' and department_id is not null and department_id != 0 limit 1")
    User getByLastname(String lastname);

    /**
     * 根据用户id获取到老erp的id
     */
    @Select("SELECT  old_id FROM  user WHERE  id = #{id}")
    Integer getOldIdById(@Param("id") Integer id);

    /**
     * 根据老erp的用户id获取到用户
     */
    @Select("select * from user where old_id = #{oldId}")
    User getByOldId(@Param("oldId") Integer oldId);


    /**
     * 根据账号获取到用户信息
     */
    @Select("select *,d.department_name,d.department_no from user u left join department d on u.department_id = d.id where u.loginid = #{loginid}")
    User getByLoginid(@Param("loginid") String loginid);


    /**
     * 根据用户idset结合获取用户列表
     */
    @SelectProvider(type = UserBuilder.class, method = "findByIds")
    List<User> findByIds(@Param("idsets") Set<Integer> idsets);

    /**
     * 查询所有没有老erp用户id的用户信息
     */
    @Select("select * from user where old_id is null and (loginid != '' or loginid != null)")
    List<User> findByNoOldId();

    /**
     * 根据用户中文名查询对应用户
     */
    @Select("SELECT id,lastname,loginid FROM user WHERE lastname = #{lastname}")
    List<User> findByLastname(@Param("lastname") String lastname);

    /**
     * 根据用户登录名和用户名称进行模糊搜索查询
     */
    @Select("select * from user where loginid like concat('%',#{condition},'%') or lastname like concat('%',#{condition},'%') or ecology_pinyin_search like concat(#{condition},'%')")
    List<User> findByCondition(@Param("condition") String condition);

    @Select("select * from user  ")
    List<User> findAll();


    /**
     * 根据用户id查询信息(用户表+部门表)
     */
    @Select("SELECT u.*,d.department_no,d.department_name FROM user u LEFT OUTER JOIN department d ON u.department_id = d.id WHERE u.id = #{id}")
	User getByUserId(@Param("id") Integer id);

    @SelectProvider(type = UserBuilder.class, method = "findByDepartmentNos")
    List<User> findByDepartmentNos(@Param("departmentNos") List<String> departmentNos ,@Param("q") String q);
}
