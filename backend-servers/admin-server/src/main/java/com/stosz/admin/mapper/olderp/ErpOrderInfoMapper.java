package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.ErpOrderInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 订单ip信息mapper代码
 */

@Repository
public interface ErpOrderInfoMapper {


	@Select("SELECT * FROM erp_order_info WHERE id_order=#{id_order}")
    ErpOrderInfo findById(@Param("id_order") Long idOrder);


    @Select("<script>" +
            "SELECT * FROM erp_order_info WHERE id_order in " +
            "<foreach item=\"item\" index=\"index\" collection=\"list\"\n" +
            "     open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "  </foreach>" +
            "</script>")
    List<ErpOrderInfo> findByIds(List<Integer> ids);

    /**
     * todo
     * @param ids
     * @return
     */
    List<String> findForwardStatus(List<Integer> ids);

    /**
     * 已入转寄仓并且有结款记录的数据
     * @return
     */
    @Select("select o.id_order from erp_order o where o.id_order_status=24 " +
            "and EXISTS(select 1 from erp_order_settlement t where o.id_order=t.id_order)")
    List<Integer> findSettlements();

    @Select("SELECT count(1) from erp_order_return d where d.id_order=#{id}")
    int findRecords(Integer idOrder);
}