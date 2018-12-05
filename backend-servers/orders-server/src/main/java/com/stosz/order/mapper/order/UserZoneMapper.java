package com.stosz.order.mapper.order;


import com.stosz.order.ext.model.UserZone;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author  wangqian
 * 客服与地区
 */
@Repository
public interface UserZoneMapper {


    @InsertProvider(type = UserZoneBuilder.class,method = "insert")
    int insert(UserZone param);

    @DeleteProvider(type = UserZoneBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = UserZoneBuilder.class, method = "update")
    int update(UserZone param);

    @SelectProvider(type = UserZoneBuilder.class, method = "find")
    List<UserZone> find(UserZone param);

    @SelectProvider(type = UserZoneBuilder.class, method = "count")
    int count(UserZone param);

    @SelectProvider(type = UserZoneBuilder.class, method = "getById")
    UserZone getById(@Param("id") Integer id);

    @Select("select * from user_zone u where u.user_id = #{userId} and use_status = #{useStatus}")
    List<UserZone> findByUserId(@Param("userId") Integer userId, @Param("useStatus") Integer useStatus);


}
