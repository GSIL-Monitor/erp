package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtils;
import com.stosz.purchase.ext.model.Purchase;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public class PurchaseBuilder extends AbstractBuilder<Purchase> {


    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Purchase param) {

    }


    public String findByParam(@Param("purchase") Purchase purchase , @Param("deptList") List<Integer> deptList){
        SQL sql = new SQL();
        sql.SELECT("DISTINCT _this.*, sup.name as supplier_name, pl.name as plat_name");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("supplier","sup","id","supplier_id"));
        sql.LEFT_OUTER_JOIN(joinString("platform","pl","id","plat_id"));
        sql.LEFT_OUTER_JOIN(joinString("purchase_item","pi","purchase_id","id"));
        sql.LEFT_OUTER_JOIN(joinString("purchase_tracking_no_rel","ptnr","purchase_no","purchase_no"));
        eq(sql,"_this.bu_dept_id","purchase.buDeptId",purchase.getBuDeptId());
        eq(sql,"_this.state","purchase.state",purchase.getState());
        eq(sql,"_this.purchase_no","purchase.purchaseNo",purchase.getPurchaseNo());
        eq(sql,"_this.buyer","purchase.buyer",purchase.getBuyer());
        eq(sql,"_this.buyer_id","purchase.buyerId",purchase.getBuyerId());
        eq(sql,"pi.sku","purchase.sku",purchase.getSku());
        le(sql,"_this.create_at","purchase.maxCreateAt",purchase.getMaxCreateAt());
        ge(sql,"_this.create_at","purchase.minCreateAt",purchase.getMinCreateAt());
        le(sql,"_this.purchase_time","purchase.maxPurchaseTime",purchase.getMaxPurchaseTime());
        ge(sql,"_this.purchase_time","purchase.minPurchaseTime",purchase.getMinPurchaseTime());

        if(StringUtils.isNotBlank(purchase.getPlatOrdersNo())){
            sql.WHERE("plat_orders_no like concat('%',#{purchase.platOrdersNo},'%') ");
        }
        if(StringUtils.isNotBlank(purchase.getProductTitle())){
            sql.WHERE("pi.product_title like concat('%',#{purchase.productTitle},'%') ");
        }
        //带上数据权限
        if(deptList != null && deptList.size()>0){
            StringBuilder sb = new StringBuilder();
            sb.append(" bu_dept_id in (");
            for (Integer deptId: deptList) {
                sb.append(deptId).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
            sql.WHERE(sb.toString());
        }
        //勾选未填运单号，那么trackingNo传null，未勾选，没有填，那么传空串，填了，就传数据
        if(StringUtils.isNotBlank(purchase.getTrackingNo()) ){
            sql.WHERE("lower(ptnr.tracking_no) like lower(concat('%',#{purchase.trackingNo},'%'))");
        }else if("".equals(purchase.getTrackingNo())){
            sql.WHERE("ptnr.tracking_no is null");
        }
        String page = buildSearchPageSql(purchase);
        return sql.toString() + page;
    }

    public String countByParam(@Param("purchase")Purchase purchase, @Param("deptList") List<Integer> deptList){
        SQL sql = new SQL();
        sql.SELECT("count(DISTINCT _this.id)");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("supplier","sup","id","supplier_id"));
        sql.LEFT_OUTER_JOIN(joinString("platform","pl","id","plat_id"));
        sql.LEFT_OUTER_JOIN(joinString("purchase_item","pi","purchase_id","id"));
        sql.LEFT_OUTER_JOIN(joinString("purchase_tracking_no_rel","ptnr","purchase_no","purchase_no"));
        eq(sql,"bu_dept_id","purchase.buDeptId",purchase.getBuDeptId());
        eq(sql,"_this.state","purchase.state",purchase.getState());
        eq(sql,"_this.purchase_no","purchase.purchaseNo",purchase.getPurchaseNo());
        eq(sql,"_this.buyer","purchase.buyer",purchase.getBuyer());
        eq(sql,"_this.buyer_id","purchase.buyerId",purchase.getBuyerId());
        eq(sql,"pi.sku","purchase.sku",purchase.getSku());
        le(sql,"_this.create_at","purchase.maxCreateAt",purchase.getMaxCreateAt());
        ge(sql,"_this.create_at","purchase.minCreateAt",purchase.getMinCreateAt());
        le(sql,"_this.purchase_time","purchase.maxPurchaseTime",purchase.getMaxPurchaseTime());
        ge(sql,"_this.purchase_time","purchase.minPurchaseTime",purchase.getMinPurchaseTime());

        if(StringUtils.isNotBlank(purchase.getPlatOrdersNo())){
            sql.WHERE("plat_orders_no like concat('%',#{purchase.platOrdersNo},'%') ");
        }
        if(StringUtils.isNotBlank(purchase.getProductTitle())){
            sql.WHERE("pi.product_title like concat('%',#{purchase.productTitle},'%') ");
        }
        //带上数据权限
        if(deptList != null && deptList.size()>0){
            StringBuilder sb = new StringBuilder();
            sb.append(" bu_dept_id in (");
            for (Integer deptId: deptList) {
                sb.append(deptId).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
            sql.WHERE(sb.toString());
        }
        //勾选未填运单号，那么trackingNo传null，未勾选，没有填，那么传空串，填了，就传数据
        if(StringUtils.isNotBlank(purchase.getTrackingNo()) ){
            sql.WHERE("lower(ptnr.tracking_no) like lower(concat('%',#{purchase.trackingNo},'%'))");
        }else if("".equals(purchase.getTrackingNo())){
            sql.WHERE("ptnr.tracking_no is null");
        }
        return sql.toString();
    }


}
