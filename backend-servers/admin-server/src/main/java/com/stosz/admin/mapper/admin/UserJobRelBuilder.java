package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.model.UserJobRel;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;

/**
 * 用户-职位mapper
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/28]
 */

public class UserJobRelBuilder extends AbstractBuilder<UserJobRel> {

    public static final Logger logger = LoggerFactory.getLogger(UserJobRelBuilder.class);

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, UserJobRel param) {

    }


    public String insertBatch(@Param("userList") List<User> userList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT IGNORE INTO user_job ");
        sb.append("(user_id, job_id, create_at)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'userList[{0}].id}, #'{'userList[{0}].jobId},current_timestamp())");
        for (int i = 0; i < userList.size(); i++) {
            String j = i + "";
            sb.append(mf.format(new Object[]{j}));
            if (i < userList.size() - 1) {
                sb.append(",");
            }
        }
        logger.info("=== insertBatch sql:" + sb.toString());
        return sb.toString();
    }
}
