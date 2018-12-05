package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpBlackList;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @auther carter
 * create time    2017-11-07
 */
@Repository
public interface OldErpBlackListMapper {

    @Select("SELECT COUNT(1) FROM erp_blacklist")
    Integer getCount();

    @Select("SELECT id_blacklist as id ,field,title,level,id_user FROM erp_blacklist order by id_blacklist asc limit #{limit} offset #{start}")
    List<OldErpBlackList> getByLimit(@Param("start") int start, @Param("limit") int limit);

    @Insert("INSERT INTO erp_blacklist(id_blacklist,field,title,level,id_user) values(#{id_blackList},#{field},#{title},#{level},#{id_user}）")
    Integer insert(OldErpBlackList oldErpBlackList);

    @Update("UPDATE erp_blacklist SET field=#{field},title=#{title},level=#{level},id_user=#{id_user} where id_blacklist=#{id_blackList}")
    Integer update(OldErpBlackList oldErpBlackList);

    @Delete("DELETE FROM erp_blacklist WHERE id_blacklist=#{id_blacklist}")
    Integer delete(@Param("id_blacklist") Integer id_blacklist);

    @Select("SELECT MAX(id_blacklist) FROM erp_blacklist")
    Integer getOldMaxId();

    @Select("SELECT id_blacklist as id ,field,title,level,id_user FROM erp_blacklist where id_blacklist>#{idMin} and id_blacklist<#{idMax} order by id_blacklist asc ")
    List<OldErpBlackList> getByIdRegion(@Param("idMin") int idMin, @Param("idMax") int idMax);
}
