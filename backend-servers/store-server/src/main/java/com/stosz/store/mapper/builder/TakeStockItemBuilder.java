package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.dto.request.AddTakeStockReq;
import com.stosz.store.ext.model.TakeStockItem;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:ChenShifeng
 * @Description:TransitStockItemBuilder
 * @Created Time:2017/11/23 14:21
 * @Update Time:
 */
public class TakeStockItemBuilder extends AbstractBuilder<TakeStockItem> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, TakeStockItem param) {

    }

    @Deprecated  //
    public String insertBat(AddTakeStockReq param) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO take_stock_item (take_stock_id,tracking_no_old) values ");
        int size = param.getTrackings().size();
        for (int i = 0; i < size; i++) {
            stringBuilder.append("(").append(param.getTakeStockId()).append(",")
                    .append(param.getTrackings().get(i)).append("),");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

}
