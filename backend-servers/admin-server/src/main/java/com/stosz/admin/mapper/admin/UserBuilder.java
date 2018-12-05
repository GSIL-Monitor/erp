package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.User;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/24]
 */
public class UserBuilder extends AbstractBuilder<User> {

    public static final Logger logger = LoggerFactory.getLogger(UserBuilder.class);

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {


    }

    @Override
    public void buildWhere(SQL sql, User param) {
        eq(sql, _this("id"), "id", param.getId());
        eq(sql, _this("loginid"), "loginid", param.getLoginid());
        like_i(sql, _this("lastname"), "lastname", param.getLastname());
        like_i(sql, _this("ecology_pinyin_search"), "ecologyPinyinSearch", param.getEcologyPinyinSearch());
        eq(sql, _this("department_id"), "departmentId", param.getDepartmentId());

    }


    public String insertBatch(@Param("userList") List<User> userList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO user ");
        sb.append("(id, loginid, lastname, department_id, manager_id, ecology_pinyin_search, state, email, create_at)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'userList[{0}].id}, #'{'userList[{0}].loginid},#'{'userList[{0}].lastname}, #'{'userList[{0}].departmentId}, #'{'userList[{0}].managerId}, #'{'userList[{0}].ecologyPinyinSearch}, #'{'userList[{0}].email}, #'{'userList[{0}].state},current_timestamp())");
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

    public String findByIds(@Param("idsets") Set<Integer> idsets) {
        SQL sql = new SQL();
        sql.SELECT(_this("*"));
        sql.SELECT("d.department_name");
        sql.SELECT("d.department_no");
        sql.FROM(getTableNameThis());
        sql.JOIN(joinString("department", "d", "id", "department_id"));
        StringBuilder whereSql = new StringBuilder("_this.id in ( ");
        for (Integer id : idsets) {
            whereSql.append(id).append(",");
        }
        whereSql.deleteCharAt(whereSql.length() - 1);
        whereSql.append(")");
        sql.WHERE(whereSql.toString());
        return sql.toString();
    }

    public String findByDepartmentNos(@Param("departmentNos") List<String> departmentNos, @Param("q") String q) {
        SQL sql = new SQL();
        sql.SELECT("_this.*");
        sql.FROM(" user _this ");
        if(q!=null && q.trim().length()>0){
            sql.WHERE(String.format(" (_this.ecology_pinyin_search like '%s%%' or _this.loginid like '%s%%' or lastname like '%s%%') ",q,q,q));
        }
        if (departmentNos.size() == 0) {
            sql.WHERE("1!=1");
        }else{
            SQL sqlsub = new SQL();
            sqlsub.SELECT(" distinct d.id ").FROM("department d");
            for (String no : departmentNos.stream().filter(item->null!=item&&!"".equals(item)).collect(Collectors.toSet())) {
                sqlsub.OR().WHERE(String.format(" d.department_no like '%s%%'", no));
            }
            sql.WHERE(" _this.department_id in (" + sqlsub.toString() + " )");
        }
        sql.WHERE("_this.state<4 ");
        return sql.toString() + " limit 30 ";


    }
}
