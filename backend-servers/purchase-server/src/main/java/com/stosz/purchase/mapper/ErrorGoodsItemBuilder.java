package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtils;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.utils.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public class ErrorGoodsItemBuilder extends AbstractBuilder<ErrorGoodsItem> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, ErrorGoodsItem param) {

    }


    public String findProduct(@Param("errorGoodsList") List<ErrorGoods> errorGoodsList){
        SQL sql = new SQL();
        sql.SELECT("distinct _this.error_id, _this.product_title");
        sql.FROM(this.getTableNameThis());
        if(ArrayUtils.isNotEmpty(errorGoodsList)){
            StringBuilder builder = new StringBuilder();
            for (ErrorGoods errorGoods: errorGoodsList){
                if(builder.length()>0){
                    builder.append(",");
                }
                builder.append(errorGoods.getId());
            }

            sql.WHERE(String.format("error_id in (%s) ",builder.toString()));
        }
        return sql.toString();
    }

    public String findByParam(@Param("errorGoodsItem")ErrorGoodsItem errorGoodsItem ,@Param("deptIds") List<Integer> deptIds){
        SQL sql = new SQL();
        sql.SELECT("_this.*");
        sql.FROM(getTableNameThis());
        eq(sql,"state","errorGoodsItem.state",errorGoodsItem.getState());
        eq(sql,"error_no","errorGoodsItem.errorNo",errorGoodsItem.getErrorNo());
        eq(sql,"dept_id","errorGoodsItem.deptId",errorGoodsItem.getDeptId());
        if(StringUtils.isNotBlank(errorGoodsItem.getProductTitle())){
            sql.WHERE("product_title like concat('%',#{errorGoodsItem.productTitle},'%') ");
        }
//        if(errorGoodsItem.getDeptId() != null){
//            sql.WHERE("(dept_id = #{errorGoodsItem.deptId} or (bu_dept_id = #{errorGoodsItem.deptId})");
//        }
        if(StringUtils.isNotBlank(errorGoodsItem.getOriginalSku())){
            sql.WHERE("(original_sku = #{errorGoodsItem.originalSku} or real_sku = #{errorGoodsItem.originalSku} )");
        }
        eq(sql,"auditor_id","errorGoodsItem.auditorId",errorGoodsItem.getAuditorId());
        le(sql,"create_at","errorGoodsItem.maxCreateAt",errorGoodsItem.getMaxCreateAt());
        ge(sql,"create_at","errorGoodsItem.minCreateAt",errorGoodsItem.getMinCreateAt());
        //带上数据权限
        if(deptIds != null && deptIds.size()>0){
            StringBuilder sb = new StringBuilder();
            sb.append(" dept_id in (");
            for (Integer deptId: deptIds) {
                sb.append(deptId).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
            sql.WHERE(sb.toString());
        }
        String page = buildSearchPageSql(errorGoodsItem);
        return sql.toString() + page;
    };

    public String countByParam(@Param("errorGoodsItem")ErrorGoodsItem errorGoodsItem ,@Param("deptIds") List<Integer> deptIds){
        SQL sql = new SQL();
        sql.SELECT("count(1)");
        sql.FROM(getTableNameThis());
        eq(sql,"state","errorGoodsItem.state",errorGoodsItem.getState());
        eq(sql,"error_no","errorGoodsItem.errorNo",errorGoodsItem.getErrorNo());
        eq(sql,"dept_id","errorGoodsItem.deptId",errorGoodsItem.getDeptId());
//        if(errorGoodsItem.getDeptId() != null){
//            sql.WHERE("(dept_id = #{errorGoodsItem.deptId} or (bu_dept_id = #{errorGoodsItem.deptId})");
//        }
        if(StringUtils.isNotBlank(errorGoodsItem.getProductTitle())){
            sql.WHERE("product_title like concat('%',#{errorGoodsItem.productTitle},'%') ");
        }
        if(StringUtils.isNotBlank(errorGoodsItem.getOriginalSku())){
            sql.WHERE("(original_sku = #{errorGoodsItem.originalSku} or real_sku = #{errorGoodsItem.originalSku} )");
        }
        eq(sql,"auditor_id","errorGoodsItem.auditorId",errorGoodsItem.getAuditorId());
        le(sql,"create_at","errorGoodsItem.maxCreateAt",errorGoodsItem.getMaxCreateAt());
        ge(sql,"create_at","errorGoodsItem.minCreateAt",errorGoodsItem.getMinCreateAt());
        //带上数据权限
        if(deptIds != null && deptIds.size()>0){
            StringBuilder sb = new StringBuilder();
            sb.append(" dept_id in (");
            for (Integer deptId: deptIds) {
                sb.append(deptId).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
            sql.WHERE(sb.toString());
        }
        return sql.toString();
    };

}
