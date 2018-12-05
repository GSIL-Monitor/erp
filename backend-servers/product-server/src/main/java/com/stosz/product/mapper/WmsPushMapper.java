package com.stosz.product.mapper;

import com.stosz.product.ext.model.WmsPush;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @auther carter
 * create time    2017-11-07
 */
@Repository
public interface WmsPushMapper {

    @Insert("INSERT INTO wms_push(object_id,object_type,push_status,push_qty,push_url,push_param,create_at,update_at,response_message) VALUES(#{objectId},#{objectType},0,0,#{pushUrl},#{pushParam},now(),now(),'')")
    void insert(WmsPush wmsPush);

    @Update("UPDATE wms_push SET push_status=#{pushStatus}, response_message=#{responseMessage} , update_at=now(),push_qty=push_qty+1  where id=#{id}")
    Integer update(@Param("id") Long id, @Param("pushStatus") Integer pushStatus, @Param("responseMessage") String responseMessage);

    @Select("SELECT * FROM wms_push WHERE push_status=#{pushStatus} and push_qty<3 limit 200 ")
    List<WmsPush> getByStatus(@Param("pushStatus") Integer pushStatus);


}
