package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.ErpOrderShipping;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * mapper代码
 */

@Repository
public interface ErpOrderShippingMapper {


    @Select("SELECT * FROM erp_order_shipping WHERE id_order=#{id_order}")
    List<ErpOrderShipping> findByOrderId(@Param("id_order") Integer id_order);

    @Select("<script>" +
            "SELECT * FROM erp_order_shipping WHERE id_order in " +
            "<foreach item=\"item\" index=\"index\" collection=\"list\"\n" +
            "     open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "  </foreach>" +
            "</script>")
    List<ErpOrderShipping> findByOrderIds(List<Integer> ids);

}