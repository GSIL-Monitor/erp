package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.OrdersLink;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrdersLinkBuilder extends AbstractBuilder<OrdersLink> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, OrdersLink param) {

    }
    
    public String getByOrderId() {
        SQL sql = new SQL();
        sql.SELECT(_this("*"));
        buildSelectOther(sql);
        sql.FROM(getTableNameThis());
        buildJoin(sql);
        sql.WHERE(_this("orders_id=#{id}"));

        return sql.toString() + " limit 1 ";
    }



    public String findOrderLinkByTels(Map map){
        Object o = map.get("tels");
        List<String> trackingIds = null;
        if(o != null){
            trackingIds = (List<String>)o;
        }
        StringBuilder sb = new StringBuilder();
        String tels ;
        if(trackingIds == null || trackingIds.size() == 0){
            tels = "null";
        }else {
            tels = trackingIds.stream().map(s -> "'" + s + "'")
                    .collect(Collectors.joining(", "));
        }
        sb.append("select * from orders_link i where i.telphone in ( "+ tels +")");
        return sb.toString();
    }


}
