package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.Job;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface JobMapper {

    @Insert("insert job set id=#{id} , name = #{name}, ecology_pinyin_search = #{ecologyPinyinSearch} ,create_at= current_timestamp()")
    Integer insert(Job record);

    @InsertProvider(type = JobBuilder.class, method = "insertBatch")
    Integer insertBatch(@Param("id") Integer id, @Param("jobList") List<Job> jobList);

    /**
     * 插入这个职位的菜单权限
     */
    @InsertProvider(type = JobBuilder.class, method = "insertJobMenu")
    void insertJobMenu(@Param("id") Integer id, @Param("jobId") Integer jobId, @Param("menuIdList") List<Integer> menuIdList);

    /**
     * 插入职位_菜单映射表
     */
    @Insert("INSERT  INTO job_menu(job_id,menu_id) VALUES (#{jobId}, #{menuId})")
    void insertOneJobMenu(@Param("id") Integer id, @Param("jobId") Integer jobId, @Param("menuId") Integer menuId);

    /**
     * 根据菜单id和职位id删除这条记录
     */
    @Insert("DELETE FROM job_menu WHERE job_id = #{jobId} and menu_id = #{menuId}")
    void deleteOneJobMenu(@Param("jobId") Integer jobId, @Param("menuId") Integer menuId);

    @DeleteProvider(type = JobBuilder.class, method = "delete")
    Integer delete(Integer id);

    /**
     * 删除该用户的所有菜单权限
     */
    @Delete("DELETE FROM job_menu WHERE job_id = #{jobId}")
    void deleteJobMenu(Integer jobId);

    @Update("update job set name = #{name}, ecology_pinyin_search = #{ecologyPinyinSearch} ,create_at= current_timestamp() where  id =#{id}")
    Integer update(Job job);

    @SelectProvider(type = JobBuilder.class, method = "getById")
    Job getById(Integer id);

    /**
     * 职位表中目前的最大id
     */
    @Select("SELECT  MAX(id) FROM job")
    Integer getMaxId();

    /**
     * 根据用户id获取到该用户的所有职位
     */
    @SelectProvider(type = JobBuilder.class, method = "findByUserId")
    List<Job> findByUserId(@Param("userId") Integer userId);

    /**
     * 根据分页信息获取到对应的职位
     */
    @SelectProvider(type = JobBuilder.class, method = "findByParam")
    List<Job> findByParam(Map<String, Object> map);

    /**
     * 根据搜索关键字查询匹配的名称
     */
    @Select("SELECT * FROM job WHERE (ecology_pinyin_search LIKE CONCAT('%',#{search},'%')) or (name like CONCAT('%',#{search},'%'))")
    List<Job> findNameBySearch(String search);

    /**
     * 汇总当前所有的记录数
     */
    @SelectProvider(type = JobBuilder.class, method = "getCount")
    Integer count(Job job);


    /**
     * 截断表
     */
    @Delete("delete from job")
    void truncate();

    /**
     * 添加菜单元素权限
     *
     * @param id
     * @param jobId
     * @param menuId
     * @param elementId
     */
    @Insert("insert into job_menu(job_id,menu_id,element_id) values(#{jobId},#{menuId},#{elementId})")
    void insertOneJobMenuElement(@Param("id") Integer id, @Param("jobId") Integer jobId, @Param("menuId") Integer menuId, @Param("elementId") Integer elementId);

    /**
     * 删除菜单元素权限
     *
     * @param jobId
     * @param menuId
     * @param elementId
     */
    @Delete("delete from job_menu WHERE job_id = #{jobId} and menu_id = #{menuId} and element_id=#{elementId}")
    void deleteOneJobMenuElement(@Param("jobId") Integer jobId, @Param("menuId") Integer menuId, @Param("elementId") Integer elementId);


    /**
     * 查询用户对页面元素的权限
     *
     * @param jobId
     * @param menuId
     * @return
     */
    @Select("select element_id from  job_menu WHERE job_id =#{jobId} and menu_id = #{menuId} and element_id is not null")
    List<Integer> findElementIdFromJobMenuElement(@Param("jobId") Integer jobId, @Param("menuId") Integer menuId);
}