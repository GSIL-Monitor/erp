package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpProduct;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/28]
 */
public class OldErpProductBuilder extends AbstractBuilder<OldErpProduct> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, OldErpProduct param) {

    }

    public String findByLimit(@Param("limit") Integer limit, @Param("start") Integer start) {
        SQL sql = new SQL();
        sql.SELECT("_this.id_product as id, _this.id_department as depratment_id, _this.id_users as user_id, _this.title, _this.id_category as category_id, _this.id_classify as classify, _this.inner_name,_this.model, _this.thumbs, _this.purchase_price, _this.length, width, _this.height, _this.weight, _this.status, _this.created_at ,_this.updated_at,u.user_nicename as user_name,_this.foreign_title ");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("erp_users", "u", "id", "id_users"));
        return sql.toString() + " limit" + " #{limit} " + " offset " + " #{start}";

    }
}
