package com.stosz.store.mapper;

import com.stosz.store.ext.model.TransitDetail;
import com.stosz.store.mapper.builder.TransitDetailBuilder;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

/**
 * @Author:ChenShifeng
 * @Description:TransitDetailMapper
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Repository
public interface TransitDetailMapper {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = TransitDetailBuilder.class, method = "insert")
    int insert(TransitDetail transit);

    @DeleteProvider(type = TransitDetailBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = TransitDetailBuilder.class, method = "update")
    int update(TransitDetail transit);

    @SelectProvider(type = TransitDetailBuilder.class, method = "getById")
    TransitDetail getById(Integer id);

    @SelectProvider(type = TransitDetailBuilder.class, method = "find")
    TransitDetail find(TransitDetail transit);

    //-----------------------------------业务扩展相关操作------------------------------------//
}
