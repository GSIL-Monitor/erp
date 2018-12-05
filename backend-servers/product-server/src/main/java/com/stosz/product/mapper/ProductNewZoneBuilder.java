package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.ProductNewZone;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Collection;

public class ProductNewZoneBuilder extends AbstractBuilder<ProductNewZone> {

    @Override
    public void buildSelectOther(SQL sql) {
        sql.SELECT("z.code zoneCode, z.title zoneName");
    }

    @Override
    public void buildJoin(SQL sql) {
        sql.JOIN(joinString("zone", "z", "id", "zone_id"));
    }

    @Override
    public void buildWhere(SQL sql, ProductNewZone param) {
        eq(sql, _this("product_new_id"), "productNewId", param.getProductNewId());
        eq(sql, _this("zone_id"), "zoneId", param.getZoneId());
        eq(sql,"z.code","zoneCode",param.getZoneCode());
        eq(sql, "z.country_id", "countryId", param.getCountryId());
    }

    public String getByUnique(@Param("productNewId") Integer productNewId, @Param("zoneId") Integer zoneId){
        ProductNewZone param = new ProductNewZone();
        param.setProductNewId(productNewId);
        param.setZoneId(zoneId);
        return this.find(param);
    }

    public String findByProductNewIds(@Param("productNewIds") Collection<Integer> productNewIds) {
        ProductNewZone param = new ProductNewZone();
        SQL sql = this.findSQL(param);
        String val = StringUtils.join(productNewIds, ',');
        sql.WHERE(_this("product_new_id in ( " + val + " )"));
        return sql.toString();
    }

    public String findByProductNewId(@Param("productNewId") Integer productNewId) {
        ProductNewZone param = new ProductNewZone();
        param.setProductNewId(productNewId);
        SQL sql = this.findSQL(param);
        return sql.toString();
    }

}
