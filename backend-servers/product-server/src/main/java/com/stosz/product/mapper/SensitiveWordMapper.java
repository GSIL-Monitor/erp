package com.stosz.product.mapper;

import com.stosz.product.ext.model.SensitiveWord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensitiveWordMapper {

    //=================      公共方法 开始       ===================


    @InsertProvider(type = SensitiveWordBuilder.class, method = "insert")
    @Options(useGeneratedKeys = true)
    Integer insert(SensitiveWord param);

    @DeleteProvider(type = SensitiveWordBuilder.class, method = "delete")
    Integer delete(@Param("id") Integer id);


    @UpdateProvider(type = SensitiveWordBuilder.class, method = "update")
    Integer update(SensitiveWord param);

    @SelectProvider(type = SensitiveWordBuilder.class, method = "getById")
    SensitiveWord getById(@Param("id") Integer id);

    @SelectProvider(type = SensitiveWordBuilder.class, method = "find")
    List<SensitiveWord> find(SensitiveWord param);

    @SelectProvider(type = SensitiveWordBuilder.class, method = "count")
    Integer count(SensitiveWord param);
    //=================      公共方法 结束       ===================

    @Select("select name from sensitive_word")
    List<String> findAllWord();
}
