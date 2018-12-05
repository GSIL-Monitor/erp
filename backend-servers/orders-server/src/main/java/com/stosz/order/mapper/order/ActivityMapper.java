package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.Activity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by carter on 2017-10-27. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 实体类
 */
@Repository
public interface ActivityMapper {

    @Insert("INSERT INTO activity (merchant_id , name , create_at , update_at , creator_id , creator) values(#{merchant_id},#{name},#{create_at},#{update_at},#{creator_id},#{creator}) ")
    Integer insert(Activity activity);

    @Insert("<script>INSERT IGNORE INTO activity(id, title,version, create_at) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"attributeList\" separator=\",\" >" +
            " (#{item.id},#{item.title},#{item.version},current_timestamp())" +
            " </foreach>" +
            "</script>")
    Integer insertList(@Param("id") Integer id, @Param("activity") List<Activity> activityList);


    @Delete("delete from user_job where user_id=#{userId} and job_id=#{jobId}")
    Integer delete(Integer id);

    @Select("truncate table activity")
    void truncate();


    @Update("update activity set old_id = #{oldId} where id = #{id}")
    Integer update(Activity record);

    @SelectProvider(type = ActivityBuilder.class, method = "getById")
    Activity getById(Integer id);

    @Select("<script>SELECT * FROM activity WHERE id in " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<Activity> getByIds(@Param("ids") List<Integer> ids);

//    /**
//     * 按照条件得到总记录
//     * @param param
//     * @return
//     */
//    @SelectProvider(type = ActivityBuilder.class, method = "countByCondition")
//    List<Activity> selectByCondition(Activity param);
//
//    /**
//     * 按照条件得到总记录数量
//     * @param param
//     * @return
//     */
//    @SelectProvider(type = ActivityBuilder.class, method = "countByCondition")
//    Integer        countByCondition(Activity param);

    
    
}
