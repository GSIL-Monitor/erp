package com.stosz.plat.mapper;

import com.google.common.collect.Lists;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.utils.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class AbstractBuilder<T extends AbstractParamEntity> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Class<T> entityClass;

	public Class<T> getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("unchecked")
	public AbstractBuilder() {
		Class<?> c = getClass();
		while (true) {
			Class<?> sc = c.getSuperclass();
			if (sc.equals(AbstractBuilder.class)) {
				entityClass = (Class<T>) ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[0];
				break;
			}
			c = c.getSuperclass();
		}
	}

	public boolean notEmpty(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			str = str.trim();
			return str.length() != 0;
		}
		if (obj instanceof Collection) {
			Collection coll = (Collection) obj;
			return !coll.isEmpty();
		}
		return true;
	}

	public List<Pair<String, String>> getColumnField(Class<?> cls) {
		List<Pair<String, String>> rs = new ArrayList<>();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			if (f.getModifiers() == 2) {
				if (!f.isAnnotationPresent(DBColumn.class)) {
					continue;
				}
				rs.add(Pair.of(entityAttributeToDbColumn(f.getName()), f.getName()));
			}
		}
		return rs;
	}

	public List<Pair<String, String>> getColumnFiledSelective(T param) {
		try {
			final Class<? extends AbstractParamEntity> cls = param.getClass();
			List<Pair<String, String>> rs = new ArrayList<>();
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields) {
				if (f.getModifiers() == 2) {
					if (!f.isAnnotationPresent(DBColumn.class)) {
						continue;
					}
					f.setAccessible(true);
					Object getMethodReturn=f.get(param);
					if (getMethodReturn == null)
						continue;

					rs.add(Pair.of(entityAttributeToDbColumn(f.getName()), f.getName()));
				}
			}
			return rs;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String entityAttributeToDbColumn(String attributeName) {
		String[] names = StringUtils.splitByCharacterTypeCamelCase(attributeName);
		StringBuilder sb = new StringBuilder();
		for (String name : names) {
			name = StringUtils.uncapitalize(name);
			sb.append(name).append("_");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public void valuesExcludeId(SQL sql, Class<?> cls) {
		List<Pair<String, String>> rs = getColumnField(cls);
		for (Pair<String, String> pair : rs) {
			if (pair.getRight().equals("id")) {
				continue;
			}
			if (pair.getRight().equals("createAt")) {
				sql.VALUES(pair.getLeft(), "current_timestamp()");
				continue;
			}
			if (pair.getRight().equals("password")) {
				sql.VALUES(pair.getLeft(), String.format("md5(#{%s})", pair.getRight()));
			} else {

				sql.VALUES(pair.getLeft(), String.format("#{%s}", pair.getRight()));
			}
		}
	}

	public void valuesExcludeNullFiled(SQL sql, T param) {
		List<Pair<String, String>> rs = getColumnFiledSelective(param);
		for (Pair<String, String> pair : rs) {
			if (pair.getRight().equals("id")) {
				continue;
			}
			if (pair.getRight().equals("createAt")) {
				sql.VALUES(pair.getLeft(), "current_timestamp()");
				continue;
			}
			if (pair.getRight().equals("password")) {
				sql.VALUES(pair.getLeft(), String.format("md5(#{%s})", pair.getRight()));
			} else {

				sql.VALUES(pair.getLeft(), String.format("#{%s}", pair.getRight()));
			}
		}
	}



	public String batchValuesExcludeId( Class<?> cls,List<? extends T> dataList){
		StringBuilder columnSb = new StringBuilder(" (");
		List<String> values = Lists.newArrayList();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			if (f.getModifiers() == 2) {
				if (!f.isAnnotationPresent(DBColumn.class)) {
					continue;
				}
				if (f.getName().equals("id")) {
					continue;
				}
				columnSb.append(entityAttributeToDbColumn(f.getName())+",");
			}
		}
		dataList.forEach(e -> {
			StringBuilder valueSb = new StringBuilder("(");
			for (Field f : fields) {

				if (!f.isAnnotationPresent(DBColumn.class)) {
					continue;
				}

				if (f.getName().equals("id")) {
					continue;
				}
				f.setAccessible(true);
				try {
					final Object fieldReturn = f.get(e);
					if (f.getName().equals("createAt")) {
						valueSb.append("current_timestamp()");
					}else if (f.getName().equals("password")) {
						valueSb.append(String.format("md5(#{%s})", fieldReturn));
					}else{
						if ((fieldReturn instanceof  String) || (fieldReturn instanceof  Date)  || (fieldReturn instanceof LocalDateTime) )
							valueSb.append(String.format("'%s'",fieldReturn));
						else
							valueSb.append(fieldReturn);
					}
					valueSb.append(",");
				} catch (IllegalAccessException e1) {
					throw new RuntimeException(e1);
				}
			}
			values.add(valueSb.substring(0,valueSb.length()-1)+")");
		});

        final String columnStr = columnSb.substring(0, columnSb.length() - 1) + ")";

		return columnStr +" values "+ StringUtils.join(values,",");
    }


	public void setsExcludeId(SQL sql, Class<?> cls) {
		List<Pair<String, String>> rs = getColumnField(cls);
		for (Pair<String, String> pair : rs) {
			if (pair.getRight().equals("id")) {
				continue;
			}
			if (pair.getRight().equals("createAt")) {
				continue;
			}
			if (pair.getRight().equals("password")) {
				sql.SET(String.format("%s=md5(#{%s})", pair.getLeft(), pair.getRight()));
			} else {
				sql.SET(String.format("%s=#{%s}", pair.getLeft(), pair.getRight()));
			}
		}
	}

	public void setsExcludeNullFiled(SQL sql, T param) {
		List<Pair<String, String>> columnFiledSelective = getColumnFiledSelective(param);
		for (Pair<String, String> pair : columnFiledSelective) {
			if (pair.getRight().equals("id")) {
				continue;
			}
			if (pair.getRight().equals("createAt")) {
				continue;
			}
			if (pair.getRight().equals("password")) {
				sql.SET(String.format("%s=md5(#{%s})", pair.getLeft(), pair.getRight()));
			} else {
				sql.SET(String.format("%s=#{%s}", pair.getLeft(), pair.getRight()));
			}
		}
	}

	public String _this(String column) {
		return "_this." + column;
	}

	public void where(SQL sql, String column, String field) {
		sql.WHERE(String.format("%s=#{%s}", column, field));
	}

	public void notNull(SQL sql, boolean useNotNullCondition,List<String> notNullFields) {
		if (useNotNullCondition) {
			if (CollectionUtils.isNotNullAndEmpty(notNullFields)){
				notNullFields.forEach(e -> {
					sql.WHERE(String.format("%s is not null ", entityAttributeToDbColumn(e)));
				});
			}
		}
	}

	public void eq(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s=#{%s}", column, field));
		}
	}


	public void lt(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s<#{%s}", column, field));
		}
	}

	public void le(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s<=#{%s}", column, field));
		}
	}

	public void ge(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s>=#{%s}", column, field));
		}
	}

	public void like_l(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s like concat('%%',#{%s})", column, field));
		}
	}

	public void like_i(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s like concat('%%',#{%s},'%%')", column, field));
		}
	}

	public void like_r(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s like concat(#{%s},'%%')", column, field));
		}
	}

	public void in(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			StringBuilder sqlBuilder = new StringBuilder();
			String[] tracks = ((String) value).split(",");
			int max = tracks.length;
			for (int i = 0; i < max; ++i) {
				sqlBuilder.append(tracks[i]);
				sqlBuilder.append(",");
			}
			sql.WHERE(String.format("%s in(%s)", column, sqlBuilder.substring(0, sqlBuilder.length() - 1)));
		}
	}

	public <O> void in(SQL sql, String column, String field, List<O> dataList) {
		if (notEmpty(dataList)) {
			StringBuilder sqlBuilder = new StringBuilder();
			try {
				final Type genericType = entityClass.getDeclaredField(field).getGenericType();
				System.out.print(genericType.equals(Integer.class));
				dataList.forEach(e -> {
					if (genericType.equals(String.class) || genericType.equals(Date.class) || genericType.equals(LocalDate.class)) {
						sqlBuilder.append(String.format("'%s'", e));
					} else {
						sqlBuilder.append(String.format("%s", e));
					}
					sqlBuilder.append(",");
				});
				sql.WHERE(String.format("%s in(%s)", column, sqlBuilder.substring(0, sqlBuilder.length() - 1)));
			} catch (NoSuchFieldException e) {
				logger.error(e.getMessage(),e);
				throw new RuntimeException(e);
			}
		}
	}

	public void set(SQL sql, String column, String field) {
		sql.SET(String.format("%s=#{%s}", column, field));
	}

	public String getTableName() {
		try {
			T obj = entityClass.newInstance();
			Method getTable = entityClass.getMethod("getTable");
			Object val = getTable.invoke(obj);
			if (obj == null || obj.toString().length() == 0) {
				throw new RuntimeException("实体:" + entityClass + " 没有找到<getTable> 的方法");
			}
			return val.toString();
		} catch (Exception e) {
			throw new RuntimeException("实体:" + entityClass + " 没有找到<getTable> 的方法", e);
		}
	}

	public String getTableNameThis() {
		return getTableName() + " _this";
	}

	public String buildSearchPageSql(T page) {
		StringBuilder searchPageSql = new StringBuilder();
		if (page == null) {
			return "";
		}
		if (StringUtils.isNotBlank(page.getOrderBy())) {
			searchPageSql.append(" order by ").append(page.getOrderBy());
			if (page.getOrder()) {
				searchPageSql.append(" desc");
			} else {
				searchPageSql.append(" asc");
			}
		} else {
			searchPageSql.append(" order by _this.id desc");
		}
		if (page.getLimit() != null) {
			if (page.getStart() != null) {
				searchPageSql.append(" limit ").append(page.getStart()).append(",").append(page.getLimit());
			} else {
				searchPageSql.append(" limit ").append(page.getLimit());
			}
		}
		return searchPageSql.toString();
	}

	public String joinString(String otherTable, String otherAlias, String otherColumn, String selfColumn) {
		return String.format(" %s %s on %s.%s=%s ", otherTable, otherAlias, otherAlias, otherColumn, _this(selfColumn));
	}

	public String combine(String alias, String column) {
		return String.format("%s.%s", alias, column);
	}

	public abstract void buildSelectOther(SQL sql);

	public abstract void buildJoin(SQL sql);

	public abstract void buildWhere(SQL sql, T param);

	public void buildWhereBase(SQL sql, T param) {
		eq(sql, _this("id"), "id", param.getId());
		ge(sql, _this("create_at"), "minCreateAt", param.getMinCreateAt());
		le(sql, _this("create_at"), "maxCreateAt", param.getMaxCreateAt());
		notNull(sql,param.isUseNotNullCondition(),param.getNotNullFields());
	}

	protected SQL findSQL(T param) {
		SQL sql = new SQL();
		sql.SELECT(_this("*"));
		buildSelectOther(sql);

		final String tableNameThis = getTableNameThis();

		StringBuilder forceUseIndex = new StringBuilder(" ");

		String forceIndex = "";

		if (param.isForceUseIndex()){
			if (CollectionUtils.isNotNullAndEmpty(param.getForceIndexName())){
				forceUseIndex.append("FORCE INDEX (");

				param.getForceIndexName().forEach(e -> {
					forceUseIndex.append(e+",");
				});

				forceIndex = forceUseIndex.substring(0,forceUseIndex.length()-1).toString() +")";
			}
		}

		sql.FROM(tableNameThis + forceIndex);


		buildJoin(sql);
		buildWhere(sql, param);
		return sql;
	}

	/*********************************************************************************
	 *  可以直接使用的方法
	 *********************************************************************************/

	public String insert() {
		SQL sql = new SQL();
		sql.INSERT_INTO(getTableName());
		valuesExcludeId(sql, entityClass);
		return sql.toString();
	}

	/**
	 * 生成不包含非空属性的SQL
	 *
	 * @return
	 */
	public String insertSelective(T param) {
		SQL sql = new SQL();
		sql.INSERT_INTO(getTableName());
		valuesExcludeNullFiled(sql, param);
		return sql.toString();
	}

	@Options(useGeneratedKeys = false)
	public String batchInsertData(@Param("list") List<T> list){
		SQL sql = new SQL();
		sql.INSERT_INTO(getTableName());
		final String values = batchValuesExcludeId(entityClass, list);
		return sql.toString() + values;
	}


	public String delete() {
		SQL sql = new SQL();
		sql.DELETE_FROM(getTableName());
		sql.WHERE("id=#{id}");
		return sql.toString();
	}

	public String update() {
		SQL sql = new SQL();
		sql.UPDATE(getTableName());
		setsExcludeId(sql, entityClass);
		sql.WHERE("id=#{id}");
		return sql.toString();
	}

	/**
	 * 生成不包含非空属性的SQL
	 *
	 * @return
	 */
	public String updateSelective(T param) {
		SQL sql = new SQL();
		sql.UPDATE(getTableName());
		setsExcludeNullFiled(sql, param);
		sql.WHERE("id=#{id}");
		return sql.toString();
	}

	public String updateState() {
		SQL sql = new SQL();
		sql.UPDATE(getTableName());
		this.set(sql, "state", "state");
		this.set(sql, "state_time", "stateTime");
		this.where(sql, "id", "id");
		return sql.toString();
	}

	public String find(T param) {
		SQL sql = findSQL(param);

		// buildWhereBase(sql, param);
		String pageStr = buildSearchPageSql(param);
		return sql.toString() + pageStr;
	}

	public String findAll(T param) {
		SQL sql = findSQL(param);
		return sql.toString();
	}

	public String count(T param) {
		SQL sql = new SQL();
		sql.SELECT(" count(1) ");
		sql.FROM(getTableNameThis());
		buildJoin(sql);
		buildWhere(sql, param);
		return sql.toString();
	}

	public String getById() {
		SQL sql = new SQL();
		sql.SELECT(_this("*"));
		buildSelectOther(sql);
		sql.FROM(getTableNameThis());
		buildJoin(sql);
		sql.WHERE(_this("id=#{id}"));
		return sql.toString();
	}

	// 乐观锁
	public String updateVersion() {
		SQL sql = new SQL();
		sql.UPDATE(getTableName());
		setsExcludeId(sql, entityClass);
		sql.WHERE("id=#{id} and version=#{version}-1");
		return sql.toString();
	}

}
