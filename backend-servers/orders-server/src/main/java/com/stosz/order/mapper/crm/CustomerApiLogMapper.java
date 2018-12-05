package com.stosz.order.mapper.crm;

import com.stosz.crm.ext.model.CustomerApiLog;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerApiLogMapper {



    @InsertProvider(type = CustomerApiLogBuilder.class,method = "insert")
    int insert(CustomerApiLog param);




    static class CustomerApiLogBuilder extends AbstractBuilder<CustomerApiLog> {


        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, CustomerApiLog param) {

        }

    }


}
