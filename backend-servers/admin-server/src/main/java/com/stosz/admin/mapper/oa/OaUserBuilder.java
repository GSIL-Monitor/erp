package com.stosz.admin.mapper.oa;

import com.stosz.admin.ext.model.User;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/28]
 */
public class OaUserBuilder extends AbstractBuilder<User> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, User param) {

    }

    public String findByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime) {
        SQL sql = new SQL();
        sql.SELECT(" id, loginid, password, lastname , jobtitle as job_id, departmentid, managerid, ecology_pinyin_search,status as state");
        sql.FROM("HrmResource");
        ge(sql, "lastmoddate", "startTime", startTime);
        le(sql, "lastmoddate", "endTime", endTime);
        return sql.toString();
    }
}
