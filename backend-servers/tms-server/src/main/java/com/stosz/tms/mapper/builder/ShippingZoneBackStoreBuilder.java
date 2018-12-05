package com.stosz.tms.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.ShippingZoneBackStore;
import org.apache.ibatis.jdbc.SQL;

public class ShippingZoneBackStoreBuilder extends AbstractBuilder<ShippingZoneBackStore> {

    @Override
    public void buildSelectOther(SQL sql) {
        sql.SELECT(" s.shipping_way_name shippingWayName ");
        sql.SELECT(" s.shipping_way_code shippingWayCode ");
    }

    @Override
    public void buildJoin(SQL sql) {
        sql.LEFT_OUTER_JOIN(" shipping_way s on _this.shipping_way_id=s.id");
    }

    @Override
    public void buildWhere(SQL sql, ShippingZoneBackStore param) {
        buildWhereBase(sql,param);
        eq(sql,"shipping_way_id","shippingWayId",param.getShippingWayId());
        eq(sql,"wms_id","wmsId",param.getWmsId());
        eq(sql,"zone_id","zoneId",param.getZoneId());
        eq(sql,"back_wms_id","backWmsId",param.getBackWmsId());
        in(sql,"wms_id","wmsId",param.getWmsIdList());
        in(sql,"zone_id","zoneId",param.getZoneIdList());
        in(sql,"back_wms_id","backWmsId",param.getBackWmsIdList());
        eq(sql,"enable","enable",param.getEnable());
    }
}
