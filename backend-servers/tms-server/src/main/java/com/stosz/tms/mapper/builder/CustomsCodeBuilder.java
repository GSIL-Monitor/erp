package com.stosz.tms.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.CustomsCode;
import com.stosz.tms.model.DistrictCode;
import org.apache.ibatis.jdbc.SQL;

public class CustomsCodeBuilder extends AbstractBuilder<CustomsCode> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, CustomsCode param) {
        eq(sql,"zone_id","zoneId",param.getZoneId());
        like_r(sql,"sku","sku",param.getSku());
        like_r(sql,"code","code",param.getCode());
        eq(sql,"enable","enable",param.getEnable());
    }
}
