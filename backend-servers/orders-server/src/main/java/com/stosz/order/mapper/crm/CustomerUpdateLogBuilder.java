package com.stosz.order.mapper.crm;

import com.stosz.crm.ext.model.CustomerUpdateLog;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.jdbc.SQL;

public class CustomerUpdateLogBuilder extends AbstractBuilder<CustomerUpdateLog> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, CustomerUpdateLog param) {
        eq(sql, _this("customer_id"), "customerId", param.getCustomerId());
    }
}
