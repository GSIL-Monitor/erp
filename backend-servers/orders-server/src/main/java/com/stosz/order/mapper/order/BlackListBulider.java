package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.BlackList;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author wangqian
 *  黑名单
 */
public class BlackListBulider extends AbstractBuilder<BlackList> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, BlackList param) {
        eq(sql, "black_type_enum", "blackTypeEnum", param.getBlackTypeEnum());
//        eq(sql, "black_level_enum", "blackLevelEnum", param.getBlackLevelEnum());
        eq(sql, "creator_id", "creatorId", param.getCreatorId());
        like_i(sql, "content", "content", param.getContent());
    }

}
