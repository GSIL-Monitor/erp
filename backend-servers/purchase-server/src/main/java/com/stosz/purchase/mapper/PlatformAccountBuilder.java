package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtil;
import com.stosz.purchase.ext.model.PlatformAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class PlatformAccountBuilder extends AbstractBuilder<PlatformAccount> {
    @Override
    public void buildSelectOther(SQL sql) {
        sql.SELECT("p.name");
    }

    @Override
    public void buildJoin(SQL sql) {

        sql.LEFT_OUTER_JOIN(joinString("platform", "p", "id", "plat_id"));
    }
    @Override
    public void buildWhere(SQL sql, PlatformAccount param) {
//        eq(sql, _this("user_id"), "userId", param.ge); // 用户查询
        if (param.getState()==1){
            eq(sql,_this("state"),"state",param.getState());
        }
        if (param.getPlatId()!=0) {
            eq(sql,_this("plat_id"),"platId",param.getPlatId());
        }

        like_r(sql, _this("account"), "account", param.getAccount());
//        eq(sql, _this("enable"), "enable", param.getEnable());
    }

}
