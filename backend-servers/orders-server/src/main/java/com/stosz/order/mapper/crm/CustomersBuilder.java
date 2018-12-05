package com.stosz.order.mapper.crm;

import com.stosz.crm.ext.model.Customers;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author wangqian
 * 2017年11月6日
 * 用户表
 */
public class CustomersBuilder extends AbstractBuilder<Customers> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Customers param) {


    }


    public String getByTelphone(@Param("tel") String tel){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableNameThis());
        sql.WHERE("telphone=#{tel}");
        return sql.toString();
    }


}
