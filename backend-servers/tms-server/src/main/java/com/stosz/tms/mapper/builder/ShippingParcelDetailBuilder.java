package com.stosz.tms.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.ShippingParcelDetail;
import org.apache.ibatis.jdbc.SQL;

public class ShippingParcelDetailBuilder extends AbstractBuilder<ShippingParcelDetail> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, ShippingParcelDetail param) {
        eq(sql,"parcel_id","parcelId",param.getParcelId());
        in(sql,"parcel_id","parcelId",param.getParcelIdList());
    }

}
