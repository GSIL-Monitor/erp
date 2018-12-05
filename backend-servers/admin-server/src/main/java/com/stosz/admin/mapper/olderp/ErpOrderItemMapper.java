package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.ErpOrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * mapper代码
 */

@Repository
public interface ErpOrderItemMapper {


    @Select("SELECT * FROM erp_order_item WHERE id_order=#{id}")
    List<ErpOrderItem> findByParam(@Param("id") Integer orderId);

    @Select("<script>" +
            "SELECT * FROM erp_order_item WHERE id_order in " +
            "<foreach item=\"item\" index=\"index\" collection=\"list\"\n" +
            "     open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "  </foreach>" +
            "</script>")
    List<ErpOrderItem> findByIds(List<Integer> ids);

}