package com.stosz.purchase.mapper.finance.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.finance.Payment;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/28 15:18
 */
public class PaymentBuilder extends AbstractBuilder<Payment> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Payment param) {

    }

    public String query(Payment payment) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "id", "id", payment.getId());
        eq(sql, "pay_type", "payType", payment.getPayType());
        eq(sql, "state", "state", payment.getState());
        eq(sql, "change_user_name", "changeUserName", payment.getBuyerName());
        ge(sql, " DATE_FORMAT(create_at,'%Y-%m-%d') ", "minCreateAt", payment.getMinCreateAt());
        le(sql, " DATE_FORMAT(create_at,'%Y-%m-%d')", "maxCreateAt", payment.getMaxCreateAt());
        String pageStr = buildSearchPageSql(payment);
        return sql.toString() + pageStr;
    }
}
