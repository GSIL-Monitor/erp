package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.UserZone;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author wangqian
 * 客服与地区
 */
public class UserZoneBuilder extends AbstractBuilder<UserZone> {


    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, UserZone param) {
        eq(sql, "user_id", "userId", param.getUserId());
        eq(sql, "zone_id", "zoneId",param.getZoneId());
        eq(sql, "use_status", "useStatus", param.getUseStatus());
    }
}
