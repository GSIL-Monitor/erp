package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.utils.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public class PurchaseItemBuilder extends AbstractBuilder<PurchaseItem> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, PurchaseItem param) {

    }

    public SQL findPageBase(PurchaseItem purchaseItem) {
        SQL sql = new SQL();
        sql.SELECT("_this.*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "dept_id", "deptId", purchaseItem.getDeptId());
        eq(sql, "state", "state", purchaseItem.getState());
        eq(sql, "product_title", "productTitle", purchaseItem.getProductTitle());
        eq(sql, "sku", "sku", purchaseItem.getSku());
        eq(sql, "auditor", "auditor", purchaseItem.getAuditor());
        if (StringUtils.hasText(purchaseItem.getPurchaseNo())) {
            sql.AND().WHERE(" purchase_id =(SELECT p.id FROM purchase p WHERE p.purchase_no=#{purchaseNo} LIMIT 1)");
        }
        if (StringUtils.hasText(purchaseItem.getStartTime())) {
            sql.AND().WHERE(" DATE_FORMAT(create_at,'%Y-%m-%d')>=#{startTime}");
        }
        if (StringUtils.hasText(purchaseItem.getEndTime())) {
            sql.AND().WHERE(" DATE_FORMAT(create_at,'%Y-%m-%d')<=#{startTime}");
        }
        return sql;
    }

    public String findPageList(PurchaseItem purchaseItem) {
        SQL sql = this.findPageBase(purchaseItem);
        StringBuilder orderBuilder = new StringBuilder();
        if (StringUtils.hasText(purchaseItem.getOrderBy())) {
            orderBuilder.append(" order by ").append(purchaseItem.getOrderBy());
            if (purchaseItem.getOrder()) {
                orderBuilder.append(" desc");
            } else {
                orderBuilder.append(" asc");
            }
        } else {
            orderBuilder.append(" ORDER BY create_at ASC");
        }
        if (purchaseItem.getLimit() != null) {
            if (purchaseItem.getStart() != null) {
                orderBuilder.append(" limit ").append(purchaseItem.getStart()).append(",").append(purchaseItem.getLimit());
            } else {
                orderBuilder.append(" limit ").append(purchaseItem.getLimit());
            }
        }
        return sql.toString() + orderBuilder.toString();
    }

    public String queryReturnedList(Integer purchaseId) {
        SQL sql = new SQL();
        sql.SELECT(" _this.*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "purchase_id", "purchaseId", purchaseId);
        return sql.toString();
    }

    public String findPageListCount(PurchaseItem purchaseItem) {
        SQL sql = this.findPageBase(purchaseItem);
        String result = "select count(*) from (%s)c";
        result = String.format(result, sql.toString());
        return result;
    }

    public String getListByIds(List<Integer> ids) {
        SQL sql = new SQL();
        sql.SELECT("_this.*");
        sql.FROM(this.getTableNameThis());
        if (ArrayUtils.isNotEmpty(ids)) {
            StringBuilder builder = new StringBuilder();
            for (Integer id : ids) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(id);
            }

            sql.WHERE(String.format(" id in (%s)", builder.toString()));
        }
        return sql.toString();
    }


    public String findSpu(@Param("purchases")List<Purchase> purchaseList){
        SQL sql = new SQL();
        sql.SELECT("distinct _this.spu, _this.purchase_id,_this.product_title");
        sql.FROM(this.getTableNameThis());
        if(ArrayUtils.isNotEmpty(purchaseList)){
            StringBuilder builder = new StringBuilder();
            for (Purchase purchase: purchaseList){
                if(builder.length()>0){
                    builder.append(",");
                }
                builder.append(purchase.getId());
            }

            sql.WHERE(String.format("purchase_id in (%s) ",builder.toString()));
        }
        return sql.toString();
    }
}
