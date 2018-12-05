package com.stosz.tms.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.dto.ShippingZoneStoreRelQueryListDto;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.model.ShippingZoneStoreRel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;

public class ShippingZoneStoreRelBuilder extends AbstractBuilder<ShippingZoneStoreRel> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, ShippingZoneStoreRel param) {
        eq(sql, "shipping_way_id", "shippingWayId", param.getShippingWayId());
        eq(sql, "zone_id", "zoneId", param.getZoneId());
        eq(sql, "wms_id", "wmsId", param.getWmsId());
        in(sql, "zone_id", "zoneId", param.getZoneIdList());
        eq(sql, "allowed_product_type", "allowedProductType", param.getAllowedProductType());
        in(sql, "wms_id", "wmsId", param.getWmsIdList());
        eq(sql, "enable", "enable", param.getEnable());
    }

    public String batchInsert(@Param("zoneStoreRels")List<ShippingZoneStoreRel> zoneStoreRels ){
        SQL sql = new SQL();
        sql.INSERT_INTO(getTableName());
        sql.INTO_COLUMNS("shipping_way_id","zone_name","zone_id","allowed_product_type","wms_id","wms_name","enable","creator","creator_id");

        StringBuilder sb = new StringBuilder(" values ");

        MessageFormat mf = new MessageFormat("({0},\"{1}\",{2},{3},{4},\"{5}\",{6},\"{7}\",{8})");


        zoneStoreRels.forEach(e -> {
            sb.append(mf.format(new Object[]{e.getShippingWayId().toString(),e.getZoneName(),e.getZoneId().toString(),
                    e.getAllowedProductType(),e.getWmsId().toString(),e.getWmsName(),e.getEnable(),e.getCreator(),e.getCreatorId().toString()}));
            sb.append(",");
        });

        return sql.toString()+sb.substring(0,sb.length()-1);
    }


    public String countByQueryDto(@Param("queryListDto")ShippingZoneStoreRelQueryListDto queryListDto){
        SQL sql = new SQL();
        sql.SELECT(_this("*"));

        return null;
    }

}
