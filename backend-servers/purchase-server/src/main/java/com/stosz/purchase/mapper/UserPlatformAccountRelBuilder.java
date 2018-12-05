package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.purchase.ext.model.UserPlatformAccountRel;
import com.stosz.purchase.utils.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UserPlatformAccountRelBuilder extends AbstractBuilder<UserPlatformAccountRel> {

    @Override
    public void buildSelectOther(SQL sql) {
        sql.SELECT("p.name,pa.account");
    }

    @Override
    public void buildJoin(SQL sql) {

        sql.LEFT_OUTER_JOIN(joinString("platform", "p", "id", "plat_id"));
        sql.LEFT_OUTER_JOIN(joinString("platform_account", "pa", "id", "plat_account_id"));
    }

    @Override
    public void buildWhere(SQL sql, UserPlatformAccountRel userPlatformAccountRel) {
        like_r(sql, "pa.account", "account", userPlatformAccountRel.getAccount());
        if (userPlatformAccountRel.getBuyerId()!=null) {
            sql.WHERE(String.format("buyer_id=%s", userPlatformAccountRel.getBuyerId()));
        }
//        eq(sql,"_this.buyer_id","buyId",userPlatformAccountRel.getBuyerId());
    }

    public String queryByAccount(@Param("userPlatformAccountRel") UserPlatformAccountRel userPlatformAccountRel) {
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM(this.getTableNameThis());
        sql.WHERE(String.format("plat_id=%s", userPlatformAccountRel.getPlatId()));
        if (StringUtils.isNotBlank(userPlatformAccountRel.getBuyer())) {
            sql.WHERE(String.format("buyer='%s'", userPlatformAccountRel.getBuyer()));
        }
        sql.WHERE(String.format("buyer_id=%s", userPlatformAccountRel.getBuyerId()));
        sql.WHERE(String.format("plat_account_id=%s", userPlatformAccountRel.getPlatAccountId()));
        return sql.toString();
    }
}
