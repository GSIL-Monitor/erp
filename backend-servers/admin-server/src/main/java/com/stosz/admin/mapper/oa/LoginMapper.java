package com.stosz.admin.mapper.oa;

import com.stosz.admin.ext.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by XCY on 2017/8/16.
 * desc:
 */
@Repository
public interface LoginMapper {

    @Select("select id, loginid, password, lastname as name, jobtitle as job_id, departmentid, managerid, ecology_pinyin_search from HrmResource where loginid = #{loginId}")
    User findUserByLoginid(@Param("loginId") String loginid);

}  
