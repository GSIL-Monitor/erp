package com.stosz.purchase.mapper.finance.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.finance.PaymentItem;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/28 15:18
 */
public class PaymentItemBuilder extends AbstractBuilder<PaymentItem> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, PaymentItem param) {

    }

    public String query(PaymentItem paymentItem) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "id", "id", paymentItem.getId());
        eq(sql, "payment_id", "paymentId", paymentItem.getPaymentId());
        eq(sql, "goal_bill_no", "goalBillNo", paymentItem.getGoalBillNo());
        eq(sql, "sku", "sku", paymentItem.getSku());
        eq(sql, "change_bill_type", "changeBillType", paymentItem.getChangeBillType());
        ge(sql, " DATE_FORMAT(create_at,'%Y-%m-%d') ", "minCreateAt", paymentItem.getMinCreateAt());
        le(sql, " DATE_FORMAT(create_at,'%Y-%m-%d')", "maxCreateAt", paymentItem.getMaxCreateAt());
        String pageStr = buildSearchPageSql(paymentItem);
        return sql.toString() + pageStr;
    }
}
