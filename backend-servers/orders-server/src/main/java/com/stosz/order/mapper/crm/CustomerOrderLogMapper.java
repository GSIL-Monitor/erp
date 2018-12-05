package com.stosz.order.mapper.crm;

import com.stosz.crm.ext.model.CustomerOrderLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerOrderLogMapper {

    @InsertProvider(type = CustomerOrderLogBuilder.class,method = "insert")
    int insert(CustomerOrderLog param);


    @Select("<script>\n" +
            "   \tSELECT * from customer_order_log \n" +
            "   \tWHERE order_id in \n" +
            "   \t<foreach collection=\"orderIds\" item=\"orderId\" open=\"(\" close=\")\" separator=\",\">\n" +
            "    \t#{orderId}\n" +
            "   \t</foreach>\n" +
            "</script>")
    List<CustomerOrderLog> findByOrderIds(@Param("orderIds") List<Integer> ids);


    @Insert("<script>\n" +
            "INSERT INTO customer_order_log (order_id,tel,state) \n" +
            "VALUES \n" +
            "<foreach collection =\"list\" item=\"customer\" index= \"index\" separator =\",\"> \n" +
            " (#{customer.orderId},#{customer.tel},#{customer.state}) \n" +
            "</foreach>\n" +
            "</script>\t")
    int insertBatch(List<CustomerOrderLog> list);
}
