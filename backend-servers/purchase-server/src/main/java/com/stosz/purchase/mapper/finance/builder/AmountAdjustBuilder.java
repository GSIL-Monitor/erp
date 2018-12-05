package com.stosz.purchase.mapper.finance.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.finance.AmountAdjust;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/28 15:18
 */
public class AmountAdjustBuilder extends AbstractBuilder<AmountAdjust> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, AmountAdjust param) {

    }

    public String query(AmountAdjust param) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "id", "id", param.getId());

        eq(sql, "goal_bill_no", "goalBillNo", param.getGoalBillNo());
        eq(sql, "payment_id", "paymentId", param.getPaymentId());
        eq(sql, "goal_bill_type", "goalBillType", param.getGoalBillType());

        ge(sql, "create_at", "minCreateAt", param.getMinCreateAt());
        le(sql, "create_at", "maxCreateAt", param.getMaxCreateAt());
        String pageStr = buildSearchPageSql(param);
        return sql.toString() + pageStr;
    }
}
