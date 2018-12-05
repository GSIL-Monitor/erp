package com.stosz.product.mapper;

import com.google.common.collect.Sets;
import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Set;

public class ProductBuilder extends AbstractBuilder<Product> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, Product param) {
		eq(sql, "state", "state", param.getState());
		eq(sql, _this("creator_id"), "creatorId", param.getCreatorId());
		eq(sql, "category_id", "categoryId", param.getCategoryId());
		eq(sql, _this("id"), "id", param.getId());
		eq(sql, _this("spu"), "spu", param.getSpu());
		like_i(sql, _this("title"), "title", param.getTitle());
		like_i(sql, _this("inner_name"), "innerName", param.getInnerName());
		le(sql, _this("create_at"), "maxCreateAt", param.getMaxCreateAt());
		ge(sql, _this("create_at"), "minCreateAt", param.getMinCreateAt());
	}

	/******************** 两个表关联(产品,品类) *******************************/
	public void buildListingWhere(SQL sql, Product param) {
		like_i(sql, _this("title"), "title", param.getTitle());
		eq(sql, _this("spu"), "spu", param.getSpu());
		eq(sql, _this("id"), "id", param.getId());
		if(param.getCategoryIds() != null){
			if(!param.getCategoryIds().isEmpty()){
				String val = StringUtils.join(param.getCategoryIds(), ',');
			    sql.WHERE("category_id IN ( " + val + " )");
			}
		}
		if (param.getId() == null && param.getSpu() == null) {
			if (!param.getDepartmentIds().isEmpty()) {
				String val = StringUtils.join(param.getDepartmentIds(), ',');
				sql.WHERE("_this.id IN ( SELECT pz.product_id  FROM product_zone pz WHERE pz.department_id IN ( " + val + " ))");
			}
		}
		if(param.getProductStates() != null){
			if (param.getProductStates().isEmpty()) {
				return;
            }
			StringBuilder sb = new StringBuilder();
			sb.append(" _this.state IN ( ");
			for (String val : param.getProductStates()) {
				sb.append("'" + val + "'"+" ,");
            }
			String s = sb.toString();
			if (sb.length() > 0) {
				s = sb.substring(0, sb.length() - " ,".length());
				s += ")";
			}
			sql.WHERE(s);
		}
	}

	/**
	 * 产品列表
	 */
	public String findListing(Product param) {
		SQL sql = new SQL();
		sql.SELECT(" _this.*,c.name FROM product _this LEFT JOIN category c ON _this.category_id = c.id ");
		buildListingWhere(sql, param);
		String pageStr = buildSearchPageSql(param);
		return sql.toString() + pageStr;
	}

	/**
	 * 产品列表总数
	 */
	public String countListing(Product param) {
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) FROM product _this LEFT JOIN category c ON _this.category_id = c.id ");
		buildListingWhere(sql, param);
		return sql.toString();
	}

	/**
	 * 产品列表
	 */
	public String findImage(Product param) {
		SQL sql = new SQL();
		sql.SELECT(" _this.id,_this.main_image_url,_this.title,_this.spu FROM product _this LEFT JOIN category c ON _this.category_id = c.id ");
		buildListingWhere(sql, param);
		String pageStr = buildSearchPageSql(param);
		return sql.toString() + pageStr;
	}

	/**
	 * 产品列表总数
	 */
	public String countImage(Product param) {
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) FROM product _this LEFT JOIN category c ON _this.category_id = c.id ");
		buildListingWhere(sql, param);
		return sql.toString();
	}

/******************** 两个表关联(产品,产品区域) 产品表做驱动表*******************************/
	public void buildQueryListWhere(SQL sql, Product param) {
		eq(sql, _this("creator_id"), "creatorId", param.getCreatorId());
		eq(sql, _this("id"), "id", param.getId());
		eq(sql, _this("spu"), "spu", param.getSpu());
		eq(sql, "pz.zone_code", "zoneCode", param.getZoneCode());
		eq(sql,_this("state"), "state", param.getState());
		like_i(sql, _this("title"), "title", param.getTitle());
		like_i(sql, _this("inner_name"), "innerName", param.getInnerName());
		le(sql, _this("create_at"), "maxCreateAt", param.getMaxCreateAt());
		ge(sql, _this("create_at"), "minCreateAt", param.getMinCreateAt());
		if(param.getDepartmentIds() != null){
			if (param.getDepartmentIds().isEmpty()) {
				sql.WHERE("1 != 1");
				return;
			}
			String val = StringUtils.join(param.getDepartmentIds(), ',');
			sql.WHERE("department_id IN ( " + val + " )");
		}

	}

	public String queryList(Product param) {
		SQL sql = new SQL();
//		sql.SELECT(" _this.*,pz.department_no,pz.department_name,pz.zone_name,pz.state AS zoneState,pz.stock,pz.last_order_at "
//				+ "FROM product_zone pz INNER JOIN product _this ON pz.product_id = _this.id ");
		sql.SELECT(" _this.* ");
		sql.FROM(getTableNameThis());
		sql.LEFT_OUTER_JOIN(" product_zone pz ON _this.id = pz.product_id ");
		buildQueryListWhere(sql, param);
		sql.GROUP_BY("_this.id");
//		buildQueryListPzWhere(sql, param);
		String pageStr = buildSearchPageSql(param);
		return sql.toString() + pageStr;
	}

	public String countQueryList(Product param) {
		SQL sql = new SQL();
//		sql.SELECT(" COUNT(1) FROM product_zone pz INNER JOIN product _this ON pz.product_id = _this.id ");
		sql.SELECT(" COUNT(1) FROM product _this LEFT JOIN product_zone pz ON _this.id = pz.product_id ");
		buildQueryListWhere(sql, param);
		sql.GROUP_BY("_this.id");
		return "SELECT COUNT(1) FROM ( " + sql.toString() + " ) a ";
	}

/******************** 两个表关联(产品,产品区域) 产品区域表做驱动表*******************************/
    //内连接是因为产品都有对应的区域  否则 就以产品表为驱动表
	public void buildQueryListPzWhere(SQL sql, Product param) {
		eq(sql, "pz.creator_id", "creatorId", param.getCreatorId());
		eq(sql, _this("id"), "id", param.getId());
		eq(sql, _this("spu"), "spu", param.getSpu());
		eq(sql, _this("checker_id"), "checkerId", param.getCheckerId());
        eq(sql, "_this.state", "state", param.getState());
        like_i(sql, _this("title"), "title", param.getTitle());
		like_i(sql, _this("inner_name"), "innerName", param.getInnerName());
		eq(sql, "pz.zone_code", "zoneCode", param.getZoneCode());
		le(sql, _this("create_at"), "maxCreateAt", param.getMaxCreateAt());
		ge(sql, _this("create_at"), "minCreateAt", param.getMinCreateAt());
		if(param.getCategoryIds() != null){
			if(!param.getCategoryIds().isEmpty()){
				String val = StringUtils.join(Sets.newHashSet(param.getCategoryIds()), ',');
				sql.WHERE("_this.category_id IN ( " + val + " )");
			}
		}
//		if (param.getDepartmentIds() != null) {
//			if (param.getDepartmentIds().isEmpty()) {
//				sql.WHERE("1 != 1");
//				return;
//			}
//			String val = StringUtils.join(param.getDepartmentIds(), ',');
//			sql.WHERE("pz.department_id IN ( " + val + " )");
//		}
		if (param.getDepartmentIds() != null) {
			if (!param.getDepartmentIds().isEmpty()) {
				String val = StringUtils.join(Sets.newHashSet(param.getDepartmentIds()), ',');
				sql.WHERE("pz.department_id IN ( " + val + " )");
			}
		}


	}
	
	public String queryListPz(Product param) {
		SQL sql = new SQL();
		sql.SELECT("_this.* FROM product _this LEFT JOIN product_zone pz ON pz.product_id = _this.id");
		buildQueryListPzWhere(sql, param);
		sql.GROUP_BY("_this.id");
		String pageStr = buildSearchPageSql(param);
		buildSearchPageSql(param);
		return sql.toString() + pageStr;
	}

	public String countQueryListPz(Product param) {
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) FROM product _this LEFT JOIN product_zone pz ON pz.product_id = _this.id ");
		buildQueryListPzWhere(sql, param);
		sql.GROUP_BY("_this.id");
		return "SELECT COUNT(1) FROM ( " + sql.toString() + " ) a ";
	}
	
	// ----------------------------------------------------------------
	public String findByDate(@Param("param") Product param) {
		SQL sql = new SQL();
		sql.SELECT("_this.* , pz.department_id , z.id ");
		sql.FROM(getTableNameThis());
		sql.LEFT_OUTER_JOIN(joinString("product_zone", "pz", "product_id", "id"));
		sql.LEFT_OUTER_JOIN("zone z on pz.zone_code = z.code");
		ge(sql, "_this.update_at", "param.minCreateAt", param.getMinCreateAt());
		le(sql, "_this.update_at", "param.maxCreateAt", param.getMaxCreateAt());
		String page = buildSearchPageSql(param);
		return sql.toString() + page;
	}

	public String getBySpu(@Param("spu") String spu) {
		SQL sql = new SQL();
		sql.SELECT(_this("*"));
		buildSelectOther(sql);
		sql.FROM(getTableNameThis());
		buildJoin(sql);
		sql.WHERE(_this("spu=#{spu}"));
		return sql.toString();
	}

	/**
	 * 同步产品最后下单时间时，批量将产品状态修改为onsale
	 */
    public String updateStateByIds(@Param("ids") List<Integer> ids, @Param("state") String sate) {
		/**
		 * list不太合适，容易出现重复 add by carter 20171011
		 */
		Set<Integer> idsSet = Sets.newHashSet();
    	if(null!=ids)
		{
			idsSet.addAll(ids);
		}

        SQL sql = new SQL();
		sql.UPDATE(getTableNameThis());
		sql.SET("state = #{state}, state_time = current_timestamp()");
		StringBuilder whereSql = new StringBuilder("_this.id in ( ");
		for (Integer id : idsSet) {
			whereSql.append(id).append(",");
		}
		whereSql.deleteCharAt(whereSql.length() - 1);
		whereSql.append(")");
		sql.WHERE(whereSql.toString());
		return sql.toString();
	}

	public String findByProductNew(@Param("product") Product product) {
		SQL sql = new SQL();
		sql.SELECT("_this.*,c.name as category_name, pn.sift_value/10 as sift_value ");
		sql.FROM(getTableNameThis());
		sql.INNER_JOIN(joinString("product_new_sift_image", "pn", "product_id", "id"));
		sql.LEFT_OUTER_JOIN(" category c on c.id = _this.category_id ");
		sql.WHERE("pn.product_new_id = #{product.productNewId}");
		product.setOrderBy("pn.sift_value");
		String page = buildSearchPageSql(product);
		return sql.toString() + page;
	}


    public String countByDivide(@Param("product") Product product) {
        SQL sql = new SQL();
        sql.SELECT("count(1)");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("category", "c", "id", "category_id"));
        sql.WHERE("_this.state != 'disappeared' and c.leaf = 0");
		eq(sql, "_this.category_id", "product.categoryId", product.getCategoryId());
		eq(sql, "_this.id", "product.id", product.getId());
		eq(sql, "_this.title", "product.title", product.getTitle());
		eq(sql, "_this.state", "product.state", product.getStateEnum());
		return sql.toString();

    }

    public String findByDivide(@Param("product") Product product) {
        SQL sql = new SQL();
		sql.SELECT("_this.*,c.name ");
		sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("category", "c", "id", "category_id"));
        sql.WHERE("_this.state != 'disappeared' and c.leaf = 0");
		eq(sql, "_this.category_id", "product.categoryId", product.getCategoryId());
		eq(sql, "_this.id", "product.id", product.getId());
		eq(sql, "_this.title", "product.title", product.getTitle());
		eq(sql, "_this.state", "product.state", product.getStateEnum());
		String page = buildSearchPageSql(product);
        return sql.toString() + page.toString();
    }

}