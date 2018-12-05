package com.stosz.product.mapper;

import com.stosz.product.ext.model.Language;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LanguageMapper_bak {

    @Select("select * from language")
    List<Language> find();

    @Select("select * from language where lang_code=#{langCode}")
    Language getByCode(@Param("langCode") String langCoce);

    @Select("select * from language where id=#{id}")
    Language getById(@Param("id") Integer id);

    @Insert("insert into language set name=#{name} , lang_code=#{langCode} ,create_at=current_timestamp")
    void insert(Language language);


}
