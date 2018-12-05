package com.stosz.plat.mapper;

import com.stosz.plat.model.AbstractParamEntityExt;
import com.stosz.plat.model.DBColumn;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractBuilderExt<T extends AbstractParamEntityExt> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Class<T> entityClass;

    public Class<T> getEntityClass() {
        return entityClass;
    }


    @SuppressWarnings("unchecked")
    public AbstractBuilderExt() {
        Class<?> c = getClass();
        while (true) {
            Class<?> sc = c.getSuperclass();
            if (sc.equals(AbstractBuilderExt.class)) {
                entityClass = (Class<T>) ((ParameterizedType) c.getGenericSuperclass())
                        .getActualTypeArguments()[0];
                break;
            }
            c = c.getSuperclass();
        }
    }

    public boolean notEmpty(Object obj){
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
                //				if(f.getName().equals("id")){
                //					continue;
                //				}
                if (!f.isAnnotationPresent(DBColumn.class)) {
                    continue;
                }
                rs.add(Pair.of(entityAttributeToDbColumn(f.getName()), f.getName()));
            }
        }
        return rs;
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


    public void setsExcludeId(SQL sql, Class<?> cls) {
        List<Pair<String, String>> rs = getColumnField(cls);
        for (Pair<String, String> pair : rs) {
            if (pair.getRight().equals("id")) {
                continue;
            }
            if(pair.getRight().equals("createAt")){
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

    public void where(SQL sql , String column , String field){
        sql.WHERE(String.format("%s=#{%s}", column, field));
    }

    public void eq(SQL sql ,String column ,String field , Object value){
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
    public void set(SQL sql ,String column ,String field ){
        sql.SET(String.format("%s=#{%s}", column, field));
    }



    public String getTableName() {
        try {
            T obj = entityClass.newInstance();
            Method getTable = entityClass.getMethod("getTable");
            Object val = getTable.invoke(obj);
            //			Field f = entityClass.getField("table");
            //			Object obj = f.get(entityClass);
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
    }

    protected SQL findSQL(T param) {
        SQL sql = new SQL();
        sql.SELECT(_this("*"));
        buildSelectOther(sql);
        sql.FROM(getTableNameThis());
        buildJoin(sql);
        buildWhere(sql, param);
        return sql;
    }

    /*********************************************************************************
     *  可以直接使用的方法
     *********************************************************************************/


    public String insert(){
        SQL sql = new SQL();
        sql.INSERT_INTO(getTableName());
        valuesExcludeId(sql, entityClass);
        logger.info("=== insert sql:" + sql.toString());
        return sql.toString();
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



    public String updateState(){
        SQL sql = new SQL();
        sql.UPDATE(getTableName());
        this.set(sql, "state", "state");
        this.set(sql, "state_time", "stateTime");
        this.where(sql, "id", "id");
        return sql.toString();
    }

    public String find(T param) {
        SQL sql = findSQL(param);
//        buildWhereBase(sql, param);
        String pageStr = buildSearchPageSql(param);
        return sql.toString() + pageStr;
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





}
