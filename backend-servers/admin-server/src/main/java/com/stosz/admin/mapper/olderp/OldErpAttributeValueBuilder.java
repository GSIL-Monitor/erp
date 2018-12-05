package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpAttributeValue;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/31]
 */
public class OldErpAttributeValueBuilder extends AbstractBuilder<OldErpAttributeValue> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, OldErpAttributeValue param) {

    }


    @Override
    public String getById() {
        SQL sql = new SQL();
        sql.SELECT(" _this.id_product_option_value as id, _this.id_product_option as option_id, _this.id_product as product_id, _this.title,po.title as option_title");
        sql.FROM("erp_product_option_value _this");
        sql.LEFT_OUTER_JOIN(joinString("erp_product_option", "po", "id_product_option", "id_product_option"));
        sql.WHERE("_this.id_product_option_value = #{id}");
        return sql.toString();
    }

    public String findByLimit(@Param("limit") Integer limit, @Param("start") Integer start) {
        SQL sql = new SQL();
        sql.SELECT("id_product_option_value as id, id_product_option as option_id, id_product as product_id,title");
        sql.FROM("erp_product_option_value");
        return sql.toString() + " limit " + "#{limit} " + "offset " + "#{start}";
    }

    public String findByAttributeValue(@Param("attributeValues") String attributeValues){
        SQL sql = new SQL();
        sql.SELECT("id_product_option_value as id , id_product_option as option_id, id_product as product_id , title , price,code, image,sort");
        sql.FROM(getTableName());
        String arr[] = attributeValues.split(",");
        StringBuilder whereSql = new StringBuilder("id_product_option_value in ( ");
        for (int i =0;i<arr.length;i++) {
            whereSql.append(arr[i]).append(",");
        }
        whereSql.deleteCharAt(whereSql.length() - 1);
        whereSql.append(")");
        sql.WHERE(whereSql.toString());
        return sql.toString();
    }
}
