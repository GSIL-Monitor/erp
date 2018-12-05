package com.stosz.store.mapper;


import com.stosz.store.ext.model.Wms;
import com.stosz.store.mapper.builder.WmsBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/9]
 */
@Repository
public interface WmsMapper {

    //-----------------------------------基础数据增删改查------------------------------------//

    @InsertProvider(type = WmsBuilder.class, method = "insert")
    int insert(Wms wms);

    @DeleteProvider(type = WmsBuilder.class, method = "delete")
    int delete(Integer id);

    @Update("UPDATE wms SET deleted=#{deleted},update_at = now() WHERE id=#{id}")
    int changeDel(Wms wms);

    @UpdateProvider(type = WmsBuilder.class, method = "update")
    int update(Wms wms);

    @SelectProvider(type = WmsBuilder.class, method = "count")
    int count(Wms wms);

    @Update("UPDATE wms SET state=#{state},update_at = now() WHERE id=#{id}")
    int changeState(Wms wms);

    @SelectProvider(type = WmsBuilder.class, method = "getById")
    Wms getById(Integer id);

    @SelectProvider(type = WmsBuilder.class, method = "query")
    List<Wms> query(Wms wms);

    //----------------------------------模块创建前业务逻辑（后期可能删除）------------------------------------//
    @Select("select * from wms")
    List<Wms> findAll();


    //    'delete'=0 AND state=1 AND
    @Select("<script>"
            + "SELECT * FROM wms WHERE id in "
            + " <foreach collection =\"ids\" item=\"item\" index= \"index\" open=\"(\" close=\")\" separator =\",\"> "
            + " #{item}"
            + "</foreach> "
            + "</script>")
    List<Wms> findByIds(@Param("ids") List<Integer> ids);

    @Insert("<script>" +
            "INSERT INTO wms(`id`, `create_at`, `update_at`, `creator`, `creator_id`, `name`, `zone_id`," +
            " `zone_name`, `type`, `state`, `use_wms`, `linkman`, `mobile`, `address`," +
            " `wms_sys_code`, `deleted`, `priority`, `sender`, `sender_email`, `sender_province`, " +
            " `sender_city`, `sender_town`, `sender_zipcode`, `sender_country`)" +
            " VALUES " +
            "<foreach item=\"item\" collection=\"list\" separator=\",\" >" +
            "(#{item.id},#{item.createAt},#{item.updateAt},#{item.creator},#{item.creatorId},#{item.name}," +
            "#{item.zoneId},#{item.zoneName},#{item.type},#{item.state},#{item.useWms},#{item.linkman}," +
            "#{item.mobile},#{item.address},#{item.wmsSysCode},#{item.deleted},#{item.priority},#{item.sender}," +
            "#{item.senderEmail},#{item.senderProvince},#{item.senderCity},#{item.senderTown},#{item.senderZipcode}," +
            "#{item.senderCountry})" +
            "</foreach>" +
            "</script>")
    Integer insertBatch(List<Wms> newLists);
}
