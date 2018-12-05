package com.stosz.admin.mapper.oa;

import com.stosz.admin.ext.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by XCY on 2017/8/18.
 * desc:
 */
@Repository
public interface OaUserMapper {

    @Select("select id, loginid, password, lastname , jobtitle , departmentid, managerid, ecology_pinyin_search,status as state from HrmResource where loginid = #{loginId}")
    User getByLoginid(@Param("loginid") String loginid);

    /**
     * 查询oa所有的用户
     *
     * @return 所有用户信息
     */
    @Select("select id, loginid, password, lastname , jobtitle as job_id , departmentid, managerid, ecology_pinyin_search,status as state, mobile, subcompanyid1 as companyId, email from HrmResource ")
    List<User> findAll();

    /**
     * 根据修改时间查询用户 ,查询范围 开始和结束都包含，如果要查询一天的，需要开始时间等于结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 匹配的用户信息
     */
    @SelectProvider(type = OaUserBuilder.class, method = "findByDate")
    List<User> findByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);


}
