import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * @auther carter
 * create time    2017-12-29
 */
public class TestDataSql {


    @Test
    public void  insertOrderSql()
    {
        //1,生成插入订单的sql

        StringBuffer stringBuffer = new StringBuffer();

        Map<String,String> fieldValueMap = Maps.newHashMap();
        fieldValueMap.put("","");

//        merchant_enum,
//                merchant_order_no,
//                order_type_enum,
//                zone_id,
//                zone_code,
//                zone_name,
//                currency_code,
//                order_exchange_rate,
//                order_amount,
//                confirm_amount,
//                goods_qty, ip,
//                ip_name,
//                payment_method,
//                pay_state,
//                memo,
//                purchaser_at,
//                get_code,
//                input_code, order_status_enum,
//                seo_department_id, department_user_info, bu_department_id, black_fields, repeat_info,
//                code_type, ip_order_qty, seo_user_id, seo_user_name, create_at, update_at, creator_id,
//                creator, old_id, state_time, order_title, domain, warehouse_id, warehouse_name, tracking_no,
//                logistics_name, logistics_id, tracking_memo, tracking_status, tracking_status_show,
//                warehouse_memo, warehouse_type_enum, order_inner_title, amount_show, invalid_reason_type,
//                audit_time, assign_product_time, combo_id, combo_name, cancel_reason_enum, customer_message
//





    }
}
