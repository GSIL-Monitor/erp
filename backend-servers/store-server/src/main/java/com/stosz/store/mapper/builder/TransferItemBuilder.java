package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.model.TransferItem;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/11/28 16:52
 */
public class TransferItemBuilder extends AbstractBuilder<TransferItem> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, TransferItem param) {

    }

    public String findByTranId(Integer tranId) {
        SQL sql = new SQL();
        sql.SELECT(" _this.*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "tran_id", "tranId", tranId);
        return sql.toString();
    }

    public String findByTranIdAndNullTrack(Integer tranId) {
        SQL sql = new SQL();
        sql.SELECT(" _this.*");
        sql.FROM(this.getTableNameThis());
        sql.WHERE("tracking_no is null");
        eq(sql, "tran_id", "tranId", tranId);
        return sql.toString();
    }

}
