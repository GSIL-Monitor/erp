package com.stosz.store.mapper;

import com.stosz.store.ext.model.InvalidItem;
import com.stosz.store.mapper.builder.InvalidItemBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Author:ChenShifeng
 * @Description:Mapper
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Repository
public interface InvalidItemMapper {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = InvalidItemBuilder.class, method = "insert")
    int insert(InvalidItem item);

    @DeleteProvider(type = InvalidItemBuilder.class, method = "delete")
    int delete(Integer id);

    @Delete("delete FROM invalid_item WHERE invalid_id=#{invalidId}")
    void deleteByInvalidId(Integer invalidId);

    @Delete("delete FROM invalid_item WHERE transit_id=#{transitId}")
    void deleteByTransitId(Integer transitId);

    @UpdateProvider(type = InvalidItemBuilder.class, method = "update")
    int update(InvalidItem item);

    @SelectProvider(type = InvalidItemBuilder.class, method = "getById")
    InvalidItem getById(Integer id);

    @Select("SELECT COUNT(1) FROM  invalid_item WHERE tracking_no_old=#{trackNo}")
    int count(String trackNo);

    @Select("SELECT COUNT(1) FROM  invalid_item WHERE invalid_id=#{InvalidId}")
    int countByInvalidId(Integer InvalidId);


}
