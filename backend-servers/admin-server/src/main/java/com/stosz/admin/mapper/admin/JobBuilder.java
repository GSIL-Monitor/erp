package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.Job;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/28]
 */
public class JobBuilder extends AbstractBuilder<Job> {

    public static final Logger logger = LoggerFactory.getLogger(JobBuilder.class);

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Job param) {
        eq(sql, "id", "id", param.getId());
        if (notEmpty(param.getName())) {
            like_i(sql, _this("name"), "name", param.getName());
            sql.OR();
            like_i(sql, _this("ecology_pinyin_search"), "name", param.getName());
        }

    }

    public void buildWhereByParam(SQL sql, Map<String, Object> param) {
        Iterator<Map.Entry<String, Object>> entries = param.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            if (entry.getKey().equals("name")) {
                like_i(sql, _this("name"), "name", entry.getValue());
                sql.OR();
                like_i(sql, _this("ecology_pinyin_search"), "name", entry.getValue());
            }
        }
    }

    public String findByUserId(@Param("userId") Integer userId) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("user_job", "uj", "job_id", "id"));
        sql.WHERE("uj.user_id = #{userId}");
        return sql.toString();
    }

    /**
     * 根据传入的map返回对应的SQL语句
     */
    public String findByParam(Map<String, Object> map) {
        SQL sql = new SQL();
        sql.SELECT(_this("*"));
        sql.FROM(getTableNameThis());
        buildWhereByParam(sql, map);
        StringBuilder sb = new StringBuilder();
        if (notEmpty(map.get("start"))) {
            sb.append(" order by id desc limit ").append(map.get("start")).append(",").append(map.get("limit"));
        } else {
            sb.append(" order by id desc limit ").append(map.get("limit"));
        }
        return sql.toString() + sb.toString();
    }

    public String insertJobMenu(@Param("id") Integer id, @Param("jobId") Integer jobId, @Param("menuIdList") List<Integer> menuIdList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO job_menu ");
        sb.append("(job_id,menu_id)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'jobId}, #'{'menuIdList[{0}]})");
        for (int i = 0; i < menuIdList.size(); i++) {
            String j = i + "";
            sb.append(mf.format(new Object[]{j}));
            if (i < menuIdList.size() - 1) {
                sb.append(",");
            }
        }
        logger.info("=== isnertJobMenu sql:" + sb.toString());
        return sb.toString();
    }

    public String insertBatch(@Param("id") Integer id, @Param("jobList") List<Job> jobList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO job ");
        sb.append("(id,name,ecology_pinyin_search,create_at)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'jobList[{0}].id}, #'{'jobList[{0}].name},#'{'jobList[{0}].ecologyPinyinSearch},current_timestamp())");
        for (int i = 0; i < jobList.size(); i++) {
            String j = i + "";
            sb.append(mf.format(new Object[]{j}));
            if (i < jobList.size() - 1) {
                sb.append(",");
            }
        }
        logger.info("=== isnertBatch sql:" + sb.toString());
        return sb.toString();
    }

    public String getCount(Job job) {
        SQL sql = new SQL();
        sql.SELECT(" count(1) ");
        sql.FROM(getTableNameThis());
        buildWhere(sql, job);
        return sql.toString();
    }
}
