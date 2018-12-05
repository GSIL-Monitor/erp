package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtils;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.Purchase;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public class ErrorGoodsBuilder extends AbstractBuilder<ErrorGoods> {


    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, ErrorGoods param) {

    }


    public String findByParam(@Param("errorGoodsFsm") ErrorGoods errorGoods , @Param("deptList") List<Integer> deptList){
        SQL sql = new SQL();
        sql.SELECT("DISTINCT _this.*, sup.name as supplier_name");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("supplier","sup","id","supplier_id"));
        sql.LEFT_OUTER_JOIN(joinString("error_goods_item","pi","error_id","id"));
        eq(sql,"bu_dept_id","errorGoodsFsm.buDeptId",errorGoods.getBuDeptId());
        eq(sql,"_this.state","errorGoodsFsm.state",errorGoods.getState());
        eq(sql,"no","errorGoodsFsm.no",errorGoods.getNo());
        eq(sql,"pi.sku","errorGoodsFsm.sku",errorGoods.getSku());
        le(sql,"_this.create_at","errorGoodsFsm.maxCreateAt",errorGoods.getMaxCreateAt());
        ge(sql,"_this.create_at","errorGoodsFsm.minCreateAt",errorGoods.getMinCreateAt());

        if(StringUtils.isNotBlank(errorGoods.getProductTitle())){
            sql.WHERE("pi.product_title like concat('%',#{errorGoodsFsm.productTitle},'%') ");
        }
        if(StringUtils.isNotBlank(errorGoods.getSupplierName())){
            sql.WHERE("sup.name like concat('%',#{errorGoodsFsm.supplierName},'%') ");
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
        String page = buildSearchPageSql(errorGoods);
        return sql.toString() + page;
    }

    public String countByParam(@Param("errorGoodsFsm")ErrorGoods errorGoods, @Param("deptList") List<Integer> deptList){
        SQL sql = new SQL();
        sql.SELECT("count(DISTINCT _this.id)");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("supplier","sup","id","supplier_id"));
        sql.LEFT_OUTER_JOIN(joinString("error_goods_item","pi","error_id","id"));
        eq(sql,"bu_dept_id","errorGoodsFsm.buDeptId",errorGoods.getBuDeptId());
        eq(sql,"_this.state","errorGoodsFsm.state",errorGoods.getState());
        eq(sql,"no","errorGoodsFsm.no",errorGoods.getNo());
        eq(sql,"pi.sku","errorGoodsFsm.sku",errorGoods.getSku());
        le(sql,"_this.create_at","errorGoodsFsm.maxCreateAt",errorGoods.getMaxCreateAt());
        ge(sql,"_this.create_at","errorGoodsFsm.minCreateAt",errorGoods.getMinCreateAt());

        if(StringUtils.isNotBlank(errorGoods.getProductTitle())){
            sql.WHERE("pi.product_title like concat('%',#{errorGoodsFsm.productTitle},'%') ");
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
        return sql.toString();
    }


}
