package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.PurchaseRequired;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/7]
 */
public class PurchaseRequiredBuilder extends AbstractBuilder<PurchaseRequired> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {

	}

	@Override
	public void buildWhere(SQL sql, PurchaseRequired param) {

	}

	public String findSpu(PurchaseRequired purchaseRequired){
		SQL sql = new SQL();
		sql.SELECT("_this.spu");
		sql = this.findCommon(purchaseRequired,sql);
		sql.GROUP_BY("_this.spu");
		String pageSql = buildSearchPageSql(purchaseRequired);
		return sql.toString()+pageSql;
	}

	public String countSpu(PurchaseRequired purchaseRequired){
		SQL sql = new SQL();
		sql.SELECT("_this.* , s.name as supplier_name");
		sql = this.findCommon(purchaseRequired,sql);
		sql.GROUP_BY("_this.spu");
		String result = "select count(*) from (%s)c";
		result = String.format(result, sql.toString());
		return result;
	}

	public String findItem(@Param("purchaseRequired") PurchaseRequired purchaseRequired , @Param("spuList") List<String> spuList){
		SQL sql = new SQL();
		sql.SELECT("_this.* , s.name as supplier_name");
		sql.FROM("purchase_required _this");
		sql.LEFT_OUTER_JOIN(joinString("supplier","s","id","supplier_id"));
		//state不存在那么表示查询全部
		if(purchaseRequired.getState()!=null ) {
			if(purchaseRequired.getState().equals(1)){
				//state为1,表示查询当天前数据
				sql.WHERE("_this.display_at < DATE_ADD(curdate(),interval 1 day)");
			}else if(purchaseRequired.getState().equals(0)){
				//state为0，表示查询明天后数据
				sql.WHERE("_this.display_at >= DATE_ADD(curdate(),interval 1 day)");
			}
		}
		//如果查询条件，查询有库存的，那么采购需求数小于等于零。查询库存不足的，那么采购需求数大于零
		if(purchaseRequired.getHasStock() != null && purchaseRequired.getHasStock()){
			sql.WHERE("_this.purchase_qty <= 0 ");
		}else if(purchaseRequired.getHasStock()!=null && ! purchaseRequired.getHasStock()){
			sql.WHERE("_this.purchase_qty > 0 ");
		}
		eq(sql, "_this.id", "purchaseRequired.id", purchaseRequired.getId());
		eq(sql, "_this.bu_dept_id", "purchaseRequired.buDeptId", purchaseRequired.getBuDeptId());
		eq(sql, "_this.sku", "purchaseRequired.sku", purchaseRequired.getSku());
		eq(sql, "_this.buyer_id", "purchaseRequired.buyerId", purchaseRequired.getBuyerId());
		if(com.stosz.plat.utils.CollectionUtils.isNotNullAndEmpty(spuList)){
			StringBuilder whereSql = new StringBuilder("_this.spu in ( ");
			for (String spu : spuList) {
				whereSql.append("'").append(spu).append("',");
			}
			whereSql.deleteCharAt(whereSql.length() - 1);
			whereSql.append(")");
			sql.WHERE(whereSql+"");
		}

		sql.ORDER_BY("_this.update_at desc");
		return sql.toString();
	}

	public SQL findCommon(PurchaseRequired purchaseRequired,SQL sql){
		sql.FROM("purchase_required _this");
		sql.LEFT_OUTER_JOIN(joinString("supplier","s","id","supplier_id"));
		//state不存在那么表示查询全部
		if(purchaseRequired.getState()!=null ) {
			if(purchaseRequired.getState().equals(1)){
				//state为1,表示查询当天前数据
				sql.WHERE("_this.display_at < DATE_ADD(curdate(),interval 1 day)");
			}else if(purchaseRequired.getState().equals(0)){
				//state为0，表示查询明天后数据
				sql.WHERE("_this.display_at >= DATE_ADD(curdate(),interval 1 day)");
			}
		}
		//如果查询条件，查询有库存的，那么采购需求数小于等于零。查询库存不足的，那么采购需求数大于零
		if(purchaseRequired.getHasStock() != null && purchaseRequired.getHasStock()){
			sql.WHERE("_this.purchase_qty <= 0 ");
		}else if(purchaseRequired.getHasStock()!=null && ! purchaseRequired.getHasStock()){
			sql.WHERE("_this.purchase_qty > 0 ");
		}
		eq(sql, "_this.bu_dept_id", "buDeptId", purchaseRequired.getBuDeptId());
		eq(sql, "_this.sku", "sku", purchaseRequired.getSku());
		eq(sql, "_this.buyer_id", "buyerId", purchaseRequired.getBuyerId());
		return sql;
	}

	public String queryListCount(PurchaseRequired purchaseRequired) {
		SQL sql = this.queryBase(purchaseRequired);
		String result = "select count(*) from (%s)c";
		result = String.format(result, sql.toString());
		return result;
	}

	public SQL queryBase(PurchaseRequired purchaseRequired) {
		SQL sql = new SQL();
		
		sql.SELECT("pc.spu,pc.sku,SUM(case when pc.purchase_qty>0 then  pc.purchase_qty else 0 end) purchase_qty,SUM(pc.avg_sale_qty) avg_sale_qty");
		sql.SELECT("SUM(pc.pending_order_qty) pending_order_qty,SUM(pc.plan_qty) plan_qty,pc.bu_dept_id ");
		sql.FROM("purchase_required pc");
		//state不存在那么表示查询全部
		if(purchaseRequired.getState()!=null ) {
			if(purchaseRequired.getState().equals(1)){
				//state为1,表示查询当天前数据
				sql.WHERE("_this.display_at < DATE_ADD(curdate(),interval 1 day)");
			}else if(purchaseRequired.getState().equals(0)){
				//state为0，表示查询明天后数据
				sql.WHERE("_this.display_at >= DATE_ADD(curdate(),interval 1 day)");
			}
		}
		eq(sql, "pc.bu_dept_id", "buDeptId", purchaseRequired.getBuDeptId());
		eq(sql, "pc.sku", "sku", purchaseRequired.getSku());
		eq(sql, "pc.buyer_id", "buyerId", purchaseRequired.getBuyerId());
		sql.GROUP_BY(" pc.spu,pc.sku,pc.bu_dept_id ");
		return sql;
	}



	public String queryList(PurchaseRequired purchaseRequired) {
		SQL sql = this.queryBase(purchaseRequired);

		StringBuilder orderBuilder = new StringBuilder();
		if (StringUtils.hasText(purchaseRequired.getOrderBy())) {
			orderBuilder.append(" order by ").append(purchaseRequired.getOrderBy());
			if (purchaseRequired.getOrder()) {
				orderBuilder.append(" desc");
			} else {
				orderBuilder.append(" asc");
			}
		} else {
			orderBuilder.append(" ORDER BY pc.create_at ASC");
		}
		if (purchaseRequired.getLimit() != null) {
			if (purchaseRequired.getStart() != null) {
				orderBuilder.append(" limit ").append(purchaseRequired.getStart()).append(",").append(purchaseRequired.getLimit());
			} else {
				orderBuilder.append(" limit ").append(purchaseRequired.getLimit());
			}
		}
		return sql.toString() + orderBuilder.toString();
	}

	public String queryListByParam(@Param("buDeptId") Integer buDeptId, @Param("spu") String spu, @Param("sku") String sku) {
		SQL sql = new SQL();
		sql.SELECT(" _this.*");
		sql.FROM(getTableNameThis());
		eq(sql, "bu_dept_id", "buDeptId", buDeptId);
		eq(sql, "spu", "spu", spu);
		eq(sql, "sku", "sku", sku);
		return sql.toString();
	}

	public String query(PurchaseRequired purchaseRequired) {
		SQL sql = new SQL();
		sql.SELECT(" _this.*");
		sql.FROM(getTableNameThis());
		eq(sql, "sku", "sku", purchaseRequired.getSku());
		eq(sql, "dept_id", "deptId", purchaseRequired.getDeptId());
		return sql.toString();
	}

}
