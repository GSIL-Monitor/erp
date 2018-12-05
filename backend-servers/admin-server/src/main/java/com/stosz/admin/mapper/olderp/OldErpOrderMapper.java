package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpOrder;
import com.stosz.olderp.ext.model.OldErpOrderLink;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangqian
 */
@Repository
public interface OldErpOrderMapper {


    ///////////////////时间增量查询////////////////////
    /**
     * 查找老ERP订单联系人，通过时间增量查询
     * @param offset offset 分段查询起始位置
     * @param limit 分段查询条数
     * @param beginTime 指定的开始时间
     * @param endTime 指定的结束时间
     * @return
     */
    @Select("select id_order, id_zone, id_order_status, first_name, last_name, country, tel, email, province, city, area, address, zipcode  " +
            "from erp_order " +
            "where created_at > #{beginTime} " +
            "and created_at <= #{endTime} " +
            "order by id_order asc " +
            "limit #{limit} offset #{offset}")
//    @Select("select id_order, id_zone, id_order_status, first_name, last_name, country, tel, email, province, city, area, address, zipcode  " +
//            "from erp_order " +
//            "where tel = '0938563556'")
    List<OldErpOrderLink> findOldErpOrderLinkInc(@Param("offset") Integer offset, @Param("limit") Integer limit,
                                                 @Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);


    /**
     *  从指定的开始时间到指定的结束时间的记录数
     * @param beginTime
     * @param endTime
     * @return
     */
    @Select("select count(1) from erp_order " +
            "where created_at > #{beginTime} " +
            "and created_at <= #{endTime}")
//    @Select("select count(1) from erp_order where tel = '0938563556'")
    Integer countOldErpOrderLinkInc(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);




    ///////////以下部分是全量处理////////////////////
    ////////////////////////////////////////////////
    /**
     * 查找老ERP订单联系人
     * 通过id排序
     * @param offset 分段查询起始位置
     * @param limit 分段查询条数
     * @return
     */
    @Select("select id_order, id_zone, id_order_status, first_name, last_name, country, tel, email, province, city, area,address, zipcode  \n" +
            "from erp_order \n" +
            "order by id_order asc \n" +
            "limit #{limit} offset #{offset}")
    List<OldErpOrderLink> findRiskOldErpOrderLink(@Param("offset") Integer offset, @Param("limit") Integer limit);


    /**
     * 统计老ERP订单联系人数量
     * @return
     */
    @Select("select count(1) from erp_order ")
    Integer countOldErpOrderLink();


    @Select("select id_order, id_zone, id_order_status, first_name, last_name, country, tel, email, province, city, area, address, zipcode  \n" +
            "from erp_order \n" +
            "where tel = #{tel} " +
            "order by id_order asc " +
            "limit #{limit} offset #{offset}")
    List<OldErpOrderLink> findRiskOldErpOrderLinkByTel(@Param("tel") String tel, @Param("limit") Integer limit, @Param("offset") Integer offset);

    /**
     * 找到老epr订单表的最大id;
     * @return
     */
    @Select("SELECT IFNULL(MAX(id_order),0) FROM erp_order ")
    Integer getMaxId();

    @Select("SELECT * FROM erp_order where id_order>#{idMin} AND id_order<#{idMax} ")
    List<OldErpOrder>  fetchByIdRegion(@Param("idMin") int idMin, @Param("idMax") int idMax);


    /**
     * 查找老ERP订单
     * @param offset offset 分段查询起始位置
     * @param limit 分段查询条数
     * @param beginTime 指定的开始时间
     * @param endTime 指定的结束时间
     * @return
     */
    @Select("select *  " +
            " from erp_order " +
            " where created_at > #{beginTime} " +
            " and created_at <= #{endTime} " +
            " order by id_warehouse asc " +
            " limit #{limit} offset #{offset}")
    List<OldErpOrder> findErpOrder(@Param("offset") Integer offset, @Param("limit") Integer limit,
                                    @Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);




    /**
     * 从指定的开始时间到指定的结束时间的记录数
     * @param beginTime
     * @param endTime
     * @return
     */
    @Select(" select count(1) from erp_order " +
            " where created_at > #{beginTime} " +
            " and created_at <= #{endTime}")
    Integer countErpOrder(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);
}
