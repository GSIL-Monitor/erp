package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.ErpWarehouse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * mapper代码
 */

@Repository
public interface ErpWarehouseMapper {


	@Select("SELECT * FROM erp_warehouse WHERE id_warehouse=#{id}")
    ErpWarehouse findById(@Param("id") Integer id);


    @Select("<script>" +
            "SELECT * FROM erp_warehouse WHERE id_warehouse in " +
            "<foreach item=\"item\" index=\"index\" collection=\"list\"\n" +
            "     open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "  </foreach>" +
            "</script>")
    List<ErpWarehouse> findByIds(List<Integer> ids);


    @Select("SELECT * FROM erp_warehouse")
    List<ErpWarehouse> findAll();


}