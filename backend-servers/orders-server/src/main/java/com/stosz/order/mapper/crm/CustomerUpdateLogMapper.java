package com.stosz.order.mapper.crm;


import com.stosz.crm.ext.model.CustomerUpdateLog;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerUpdateLogMapper {

    @InsertProvider(type = CustomerUpdateLogBuilder.class,method = "insert")
    int insert(CustomerUpdateLog param);

    @Select("select * from customer_update_log where customer_id = #{customerId}")
    List<CustomerUpdateLog> findByCustomerId(@Param("customerId") Integer cutomerId);
}
