package com.stosz.order.mapper.order;


import com.google.common.base.Joiner;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author wangqian
 * 2017年11月6日
 *
 */
public class OrdersItemsBuilder extends AbstractBuilder<OrdersItem> {
    private static final Logger logger = LoggerFactory.getLogger(OrdersItemsMapper.class);

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, OrdersItem param) {

    }

    public String getByOrderId() {
        SQL sql = new SQL();
        sql.SELECT(_this("*"));
        buildSelectOther(sql);
        sql.FROM(getTableNameThis());
        buildJoin(sql);
        sql.WHERE(_this("orders_id=#{id}"));
        return sql.toString();
    }

    public String getByOrderIds(List<Integer> orderIds){
        
        StringBuilder sb = new StringBuilder();
        String ids ;
        if(orderIds == null || orderIds.size() == 0){
            ids = "null";
        }else {
            ids = Joiner.on(",").join(orderIds);
        }
        sb.append("select * from orders_item i where i.orders_id in ( "+ ids +")");
        return sb.toString();
    }
//    @Insert("<script>"
//            +"insert into black_list(black_level_enum, black_type_enum, content, create_at,creator_id,creator)"
//            + "values "
//            + " <foreach collection =\"list\" item=\"black\" index= \"index\" separator =\",\"> "
//            + "(#{black.blackLevelEnum}, #{black.blackTypeEnum},#{black.content},NOW(),#{black.creatorId},#{black.creator})"
//            + "</foreach> "
//            + "</script>")
//    int insertBatch(List<BlackList> list);


//    public String insertBatch(List<OrderItems> itemsList){
//        String [] columns = {"id","orders_id","spu","sku","qty","activity_id","single_amount","total_amount","product_id","product_title",
//                "foreign_title","foreign_total_amount","attr_name_array","attr_value_array","item_status_enum","product_image_url","create_at","update_at","creator_id","creator" };
//        SQL sql = new SQL();
//        sql = sql.INSERT_INTO(getTableName())
//                .INTO_COLUMNS(columns);
//                //.VALUES()
////        sql.VALUES()
//        for(OrderItems item: itemsList){
//            String []i = {item.getId(),item.getOrderId(),item.getSpu(),item.getSku(),item.getQty(),
//                    item.getActivityId(),item.getSingleAmount(),item.getTotalAmount(),item.getProductId(),item.getForeignTitle(),
//                    item.getForeignTotalAmount(),item.getAttrNameArray(),item.getAttrValueArray(),item.getItemStatusEnum(),item.getProductImageUrl(),
//                    item.getCreateAt(),item.getUpdateAt(),item.getCreatorId(),item.getCreator()}
//            sql.INTO_VALUES();
//        }
//        return "";
//    }


    public String findByOrderIds(Map map){
        Object o = map.get("orderIds");
        List<Integer> orderIds = null;
        if(o != null){
            orderIds = (List<Integer>) o;
        }
        StringBuilder sb = new StringBuilder();
        String ids ;
        if(orderIds == null || orderIds.size() == 0){
            ids = "null";
        }else {
            ids = Joiner.on(",").join(orderIds);
        }
        sb.append("select * from orders_item i where i.orders_id in ( "+ ids +")");
        logger.info("查询订单项信息sql:{}",sb.toString());

        return sb.toString();
    }
}
