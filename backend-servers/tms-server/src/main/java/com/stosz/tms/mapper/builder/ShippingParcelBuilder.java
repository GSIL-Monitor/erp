package com.stosz.tms.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.ShippingParcel;
import org.apache.ibatis.jdbc.SQL;

public class ShippingParcelBuilder extends AbstractBuilder<ShippingParcel> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, ShippingParcel param) {
        buildWhereBase(sql,param);
        eq(sql,"shipping_way_id","shippingWayId",param.getShippingWayId());
        like_i(sql,"order_no","orderNo",param.getOrderNo());
        like_i(sql,"track_no","trackNo",param.getTrackNo());
        eq(sql,"sync_status","syncStatus",param.getSyncStatus());
        eq(sql,"order_id","orderId",param.getOrderId());
        eq(sql,"parcel_state","parcelState",param.getParcelState());
        like_i(sql,"parcel_no","parcelNo",param.getParcelNo());
        in(sql,"shipping_way_id","shippingWayId",param.getShippingWayIdList());
        in(sql,"order_no","orderNo",param.getOrderNoList());
        in(sql,"track_no","trackNo",param.getTrackNoList());
    }
}
