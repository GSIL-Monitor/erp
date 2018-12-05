package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.PlatformAccount;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformAccountMapper {
    @InsertProvider(type = PlatformAccountBuilder.class, method = "insert")
    public int insert(PlatformAccount platformAccount);

    @DeleteProvider(type = PlatformAccountBuilder.class, method = "delete")
    public void delete(Integer id);

    @Update("UPDATE platform_account SET state=#{enable},update_at=now() WHERE id=#{id}")
    int update(@Param("id") Integer id, @Param("enable") Integer enable);

    @SelectProvider(type = PlatformAccountBuilder.class, method = "getById")
    PlatformAccount getById(Integer id);

    @SelectProvider(type = PlatformAccountBuilder.class, method = "find")
    List<PlatformAccount> find(PlatformAccount platformAccount);

    @SelectProvider(type = PlatformAccountBuilder.class, method = "count")
    public int count(PlatformAccount platformAccount);

  /*  @Select("select count(*) from platform_account where plat_id=#{platId} and account=#{account}")
    Integer findPlatIdAccount(@Param("platformAccount") PlatformAccount platformAccount);*/
    @Select("select count(*) from platform_account where plat_id=#{platId} and account=#{account}")
    Integer findPlatIdAccount(@Param("platId") Integer platId,@Param("account") String account);
    @Select("select account from platform_account where platId=#{platId}")
    List<String> getAccountByPlatId(@Param("platId") Integer platId);
    @Select("select count(*) from paltform_account where platId={platId}")
    Integer getAccountCountByPlatId(Integer platId);
}
