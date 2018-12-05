package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.Menu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface MenuMapper {

    /**
     * 全量插入菜单
     */
    @InsertProvider(type = MenuBuilder.class, method = "insert")
    Integer insert(Menu record);

    /**
     * 根据id删除对应的菜单和它的子菜单
     */
    @DeleteProvider(type = MenuBuilder.class, method = "delete")
    Integer delete(Integer id);

    /**
     * 选择更新菜单内容
     */
    @UpdateProvider(type = MenuBuilder.class, method = "update")
    Integer update(Menu record);

    /**
     * 根据id修改no
     */
    @Update("UPDATE menu SET no = #{no} where id = #{id}")
    void moveMenu(@Param("no") String no, @Param("id") Integer id);

    /**
     * 根据id查询到对应的菜单
     */
    @SelectProvider(type = MenuBuilder.class, method = "getById")
    Menu getById(Integer id);

    @Select("select * from menu where keyword=#{keyword}")
    Menu getByKeyword(@Param("keyword") String keyword);

    /**
     * 根据职位id获取到对应的菜单
     */
    @SelectProvider(type = MenuBuilder.class, method = "findByJobId")
    List<Menu> findByJobId(@Param("jobId") Integer jobId);


    @Select(" SELECT  menu_id FROM  job_menu WHERE  job_id = #{jobId}")
    HashSet<Integer> findIdByJobId(@Param("jobId") Integer jobId);


    /**
     * 获取到所有的菜单
     */
    @Select("SELECT *  FROM menu WHERE is_show = '1' AND status = '1' order by sort asc , id asc ")
    List<Menu> query();


    @Select("select DISTINCT m1.* from menu m1 left join menu m2 on m1.id = m2.parent_id where m1.is_show = '1' and m1.status = '1' and m2.keyword is not null;")
    List<Menu> queryUnLeaf();

    /**
     * 根据用户id获取到该用户拥有的所有菜单
     */
    @Select("select * from menu where status = '1' AND id in (select menu_id from job_menu where job_id in (select job_id from user_job where user_id= #{userId}));")
    List<Menu> getMenuByUserId(@Param("userId") Integer userId);

    @Select("select * from menu where status = '1' and parent_id = 0 AND id in (select menu_id from job_menu where job_id in (select job_id from user_job where user_id= #{userId})) order by sort asc ,id asc  ")
    List<Menu> findTopByUserId(@Param("userId") Integer userId);

    @Select("select * from menu where status = '1' and parent_id != 0 and keyword like concat(lower(#{keyword} ),'%')  AND id in (select menu_id from job_menu where job_id in (select job_id from user_job where user_id= #{userId}))")
    List<Menu> findChildrenByUserId(@Param("userId") Integer userId, @Param("keyword") String keyword);

    /**
     * 根据父级id,获取到与其同级的所有菜单
     */
    @Select("SELECT no FROM menu WHERE parent_id = #{parentId} AND status ='1'")
    List<String> findNoByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据父级id,获取到与其同级的所有菜单的名称
     */
    @Select("SELECT name FROM menu WHERE parent_id = #{parentId} AND status ='1'")
    List<String> findNameByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据父级id,获取到与其同级的所有菜单的名称
     */
    @Select("SELECT id FROM menu WHERE keyword=#{keyword}")
    Integer findMenuIdByKeyword(@Param("keyword") String menuKeyword);
}