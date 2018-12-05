package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.model.UserJobRel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/28]
 */
@Repository
public interface UserJobRelMapper {

    /**
     * 添加用户和职位的对应关系
     */
    @InsertProvider(type = UserJobRelBuilder.class, method = "insert")
    Integer insert(UserJobRel userJobRel);

    @InsertProvider(type = UserJobRelBuilder.class, method = "insertBatch")
    Integer insertBatch(@Param("id") Integer id, @Param("userList") List<User> userList);

    @Delete("delete from user_job where user_id=#{userId} and job_id=#{jobId}")
    Integer delete(UserJobRel userJobRel);

    /**
     * 截断表，导入用户职位信息时使用，其余地方慎用！！！
     */
    @Delete("delete from user_job")
    void truncate();

    @Select("select * from user_job where id = #{id}")
    UserJobRel getById(@Param("id") Integer id);

    /**
     * 通过联合主键查询唯一数据
     */
    @Select("select * from user_job where user_id =#{userId} and job_id=#{jobId}")
    UserJobRel getByUnique(@Param("userId") Integer userId, @Param("jobId") Integer jobId);


}
