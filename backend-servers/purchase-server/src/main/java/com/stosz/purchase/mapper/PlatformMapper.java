package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.Platform;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/28]
 */
@Repository
public interface PlatformMapper {

    @InsertProvider(type = PlatformBuilder.class,method = "insert")
    int insert(Platform platform);

    @DeleteProvider(type = PlatformBuilder.class,method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PlatformBuilder.class,method = "update")
    int update(Platform platform);

    @SelectProvider(type = PlatformBuilder.class,method = "getById")
    Platform getById(Integer id);

    /**
     * 查询所有的采购渠道
     * @return 所有采购渠道
     */
    @Select("select * from platform")
    List<Platform> findList();
}  
