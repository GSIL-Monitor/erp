package com.stosz.admin.mapper.oa;

import com.stosz.admin.ext.model.Job;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by XCY on 2017/8/18.
 * desc: 获取oa职位信息的mapper
 */
@Repository
public interface OaJobMapper {

    /**
     * 获取到oa的所有职位信息
     *
     * @return 所有职位信息
     */
    @Select("SELECT id, jobtitlename as name , ecology_pinyin_search, jobtitleremark as remark FROM HrmJobTitles")
    List<Job> findAll();

}
