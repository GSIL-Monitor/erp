package com.stosz.tms.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.DistrictCode;
import com.stosz.tms.model.ShippingWay;
import org.apache.ibatis.jdbc.SQL;

public class DistrictCodeBuilder extends AbstractBuilder<DistrictCode> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, DistrictCode param) {
        like_l(sql,"province","province",param.getProvince());
        eq(sql,"enable","enable",param.getEnable());
        eq(sql,"zone_id","zoneId",param.getZoneId());
    }
}
