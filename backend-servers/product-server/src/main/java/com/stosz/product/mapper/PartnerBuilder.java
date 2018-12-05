package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.Partner;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author minxiaolong
 * @create 2017-12-29 15:28
 **/
public class PartnerBuilder extends AbstractBuilder<Partner> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Partner param) {
        eq(sql, _this("type_enum"), "typeEnum", param.getTypeEnum());
        eq(sql, _this("usable"), "usable", param.getUsable());
        eq(sql, _this("id"), "id", param.getId());
        eq(sql, _this("no"), "no", param.getNo());
        like_i(sql,_this("name"),"name",param.getName());
        eq(sql, _this("mobile"), "mobile", param.getMobile());
    }

    public String findByIds(@Param("ids") List<Integer> ids){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableName());
        StringBuilder sb = new StringBuilder();
        for (Integer id : ids
             ) {
            if(sb.length()>0){
                sb.append(",");
            }
            sb.append(id);
        }
        sql.WHERE(String.format("id in (%s)",sb.toString()));
        return sql.toString();
    }
}
