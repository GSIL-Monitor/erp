package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.UserPlatformAccountRel;
import com.stosz.purchase.ext.model.UserProductRel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPlatformAccountRelMapper {
    @InsertProvider(type = UserPlatformAccountRelBuilder.class, method = "insert")
    Integer add(UserPlatformAccountRel userPlatformAccountRel);

    @DeleteProvider(type = UserPlatformAccountRelBuilder.class, method = "delete")
    int delete(Integer id);

    @Update("UPDATE user_platform_account_rel SET state=#{enable},update_at=now() WHERE id=#{id}")
    int update(@Param("id") Integer id, @Param("enable") Integer enable);

    @SelectProvider(type = UserPlatformAccountRelBuilder.class, method = "getById")
    UserPlatformAccountRel getById(Integer id);

    @SelectProvider(type = UserPlatformAccountRelBuilder.class, method = "find")
    List<UserPlatformAccountRel> find(UserPlatformAccountRel userPlatformAccountRel);

    @Select("select up.*,pa.account from user_platform_account_rel up left join platform_account pa on up.plat_account_id = pa.id where up.buyer_id = #{buyerId} and up.plat_id = #{platId} and up.state = 1")
    List<UserPlatformAccountRel> findByPlatAndBuyer(@Param("platId") Integer platId, @Param("buyerId") Integer buyerId);

    @SelectProvider(type = UserPlatformAccountRelBuilder.class, method = "count")
    int count(UserPlatformAccountRel userPlatformAccountRel);

    @SelectProvider(type = UserPlatformAccountRelBuilder.class, method = "queryByAccount")
    Integer queryByAccount(@Param("userPlatformAccountRel") UserPlatformAccountRel userPlatformAccountRel);

    @Select("select pa.account from platform_account pa inner join user_platform_account_rel u on u.plat_account_id=pa.id where u.buyer_id=#{buyerId} and u.plat_id=#{platId}")
    List<String> getAccountByBuyer(UserPlatformAccountRel userPlatformAccountRel);

    @Select("select count(*) from platform_account pa inner join user_platform_account_rel u on u.plat_account_id=pa.id where u.buyer_id=#{buyerId} and u.plat_id=#{platId}")
    Integer getAccountCountByBuyer(UserPlatformAccountRel userPlatformAccountRel);
}
