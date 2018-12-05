package com.stosz.tms.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingWay;
import org.apache.ibatis.jdbc.SQL;

public class ShippingStoreRelationBuilder extends AbstractBuilder<ShippingStoreRel> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, ShippingStoreRel param) {
        buildWhereBase(sql,param);
        eq(sql,_this("shipping_way_id"),"shippingWayId",param.getShippingWayId());
        in(sql, "shipping_way_id", "shippingWayId", param.getShippingWayIdList());
        eq(sql,_this("wms_id"),"wmsId",param.getWmsId());
        eq(sql,_this("shipping_general_name"),"shippingGeneralName",param.getShippingGeneralName());
        eq(sql,_this("shipping_specia_name"),"shippingSpeciaName",param.getShippingSpeciaName());
        eq(sql,_this("shipping_general_code"),"shippingGeneralCode",param.getShippingGeneralCode());
        eq(sql,_this("shipping_specia_code"),"shippingSpeciaCode",param.getShippingSpeciaCode());
        eq(sql,_this("is_back_choice"),"isBackChoice",param.getIsBackChoice());
    }
}
