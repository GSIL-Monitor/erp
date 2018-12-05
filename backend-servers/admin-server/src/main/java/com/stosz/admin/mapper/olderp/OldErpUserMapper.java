package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OldErpUserMapper {

    /**
     * 新增老erp用户
     *
     * @param oldErpUser 老erp用户
     * @return 新增结果
     */
    @Insert("INSERT  INTO  erp_users ( user_login, user_pass, user_nicename,  sex,birthday, user_type ) VALUES (#{userLogin}, #{userPass}, #{userNicename}, #{sex}, #{birthday}, #{userType})")
    Integer insert(OldErpUser oldErpUser);

    /**
     * 根据id删除老erp用户
     *
     * @param id id
     * @return 删除结果
     */
    @Delete("DELETE FROM  erp_users WHERE  id = #{id}")
    Integer delete(@Param("id") Integer id);

    /**
     * 查询老erp所有有效且用户名称不重复的用户
     */
    @Select("SELECT id,user_login,user_nicename,user_email,user_tel,sex,birthday,create_time,user_type,mobile,superior_user_id,belong_ware_id FROM erp_users WHERE  user_nicename IN (SELECT user_nicename FROM erp_users WHERE user_status !=0 GROUP BY user_nicename HAVING count(*) = 1)")
    List<OldErpUser> findNoRepeat();

    /**
     * 查询老erp所有用户名重复的用户
     */
    @Select("SELECT id,user_login,user_nicename,user_email,user_tel,sex,birthday,create_time,user_type,mobile,superior_user_id,belong_ware_id FROM erp_users WHERE user_status != 0 AND user_nicename IN (SELECT user_nicename FROM erp_users WHERE user_status !=0 GROUP BY user_nicename HAVING count(*) > 1)")
    List<OldErpUser> findRepeat();

    /**
     * 根据用户中文名获取该有效用户的id
     */
    @Select("SELECT id FROM erp_users WHERE user_status != 0 AND  user_nicename = #{niceName} limit 1")
    Integer getIdByNiceName(@Param("niceName") String niceName);

    /**
     * 根据用户账号获取到用户id
     */
    @Select("select id from erp_users where user_login = #{loginId}")
    Integer getIdByLoginId(@Param("loginId") String loginId);

    @Select("select * from erp_users where user_login != ''")
    List<OldErpUser> findAll();

}