package com.stosz.admin.mapper.admin;

import com.stosz.olderp.ext.model.OldErpUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * 老erp用户的临时表
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
@Repository
public interface OldErpUserTempMapper {

    /**
     * 将比对失败的老erp用户存入临时表
     */
    @Insert("INSERT INTO user_temp(id, user_login, user_nicename, user_email, user_tel,sex,birthday, create_time, user_type, mobile, superior_user_id, belong_ware_id) VALUES (#{id},#{userLogin},#{userNicename},#{userEmail},#{userTel},#{sex},#{birthday},#{createTime},#{userType},#{mobile},#{superiorUserId},#{belongWareId})")
    Integer insert(OldErpUser oldErpUser);

    @Delete("delete from user_temp")
    void truncate();

}  
