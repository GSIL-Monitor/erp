package com.stosz.purchase.mapper.finance.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.finance.Payable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/28 15:18
 */
public class PayableBuilder extends AbstractBuilder<Payable> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Payable param) {

    }

    public String findByParam(@Param("payable") Payable payable) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "id", "id", payable.getId());

        eq(sql, "change_user_name", "changeUserName", payable.getBuyerName());
        eq(sql, "plat_orders_no", "platOrdersNo", payable.getPlatOrdersNo());
        eq(sql, "goal_bill_no", "goalBillNo", payable.getGoalBillNo());
        eq(sql, "payment_id", "paymentId", payable.getPaymentId());
        eq(sql, "change_bill_type", "changeBillType", payable.getChangeBillType());
        eq(sql, "change_bill_no", "changeBillNo", payable.getChangeBillType());
        ge(sql, "create_at", "minCreateAt", payable.getMinCreateAt());
        le(sql, "create_at", "maxCreateAt", payable.getMaxCreateAt());
        String pageStr = buildSearchPageSql(payable);
        return sql.toString() + pageStr;
    }

    public String countByParam(@Param("payable") Payable payable){
        SQL sql = new SQL();
        sql.SELECT("count(1)");
        sql.FROM(this.getTableNameThis());
        eq(sql, "id", "id", payable.getId());

        eq(sql, "change_user_name", "changeUserName", payable.getBuyerName());
        eq(sql, "plat_orders_no", "platOrdersNo", payable.getPlatOrdersNo());
        eq(sql, "goal_bill_no", "goalBillNo", payable.getGoalBillNo());
        eq(sql, "payment_id", "paymentId", payable.getPaymentId());
        eq(sql, "change_bill_type", "changeBillType", payable.getChangeBillType());
        eq(sql, "change_bill_no", "changeBillNo", payable.getChangeBillType());
        ge(sql, "create_at", "minCreateAt", payable.getMinCreateAt());
        le(sql, "create_at", "maxCreateAt", payable.getMaxCreateAt());
        return sql.toString();
    }

    public String findByPaymentIds(@Param("paymentIds") Integer[] paymentIds){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(this.getTableNameThis());
        if(paymentIds.length>0){
            StringBuilder sb = new StringBuilder();
            for (Integer paymentId : paymentIds){
                if(sb.length()>0){
                    sb.append(",");
                }
                sb.append(paymentId);
            }
            sql.WHERE(String.format("payment_id in (%s)",sb.toString()));
        }
        return sql.toString();
    }
}
