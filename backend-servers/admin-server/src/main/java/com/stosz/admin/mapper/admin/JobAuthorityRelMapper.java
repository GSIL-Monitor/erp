package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.JobAuthorityRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/13]
 */
@Repository
public interface JobAuthorityRelMapper {

    @InsertProvider(type = JobAuthorityRelBuilder.class, method = "insert")
    int insert(JobAuthorityRel jobAuthorityRel);

    @DeleteProvider(type = JobAuthorityRelBuilder.class,method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = JobAuthorityRelBuilder.class,method = "update")
    int update(JobAuthorityRel jobAuthorityRel);

    @SelectProvider(type = JobAuthorityRelBuilder.class, method = "getById")
    JobAuthorityRel getById(Integer id);

    @Select("select * from job_authority_rel where job_id = #{jobId}")
    JobAuthorityRel getByJobId(@Param("jobId") Integer jobId);


    @Select("select * from job_authority_rel where job_id in (select job_id from user_job where user_id = #{userId})")
    List<JobAuthorityRel> findByUser(@Param("userId") Integer userId);
}  
