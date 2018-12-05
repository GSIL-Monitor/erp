package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.ErpDomain;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * mapper代码
 */

@Repository
public interface ErpDomainMapper {


	@Select("SELECT * FROM erp_domain WHERE id_domain=#{id_domain}")
    ErpDomain findById(@Param("id_domain") Long id);

    /**
     *
     * @param ids 多个id以逗号隔开
     * @return
     */
    @Select("<script>" +
            "SELECT * FROM erp_domain WHERE id_domain in" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\"\n" +
            "     open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "  </foreach>" +
            "</script>")
    List<ErpDomain> findByIds(List<Integer> ids);





}