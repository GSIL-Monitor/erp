package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.Element;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author liufeng
 * @version [1.0 , 2017/9/11]
 */
public class ElementBuilder extends AbstractBuilder<Element> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Element param) {

    }

    public String insertMenuElement(@Param("id") Integer id, @Param("menu_id") Integer menu_id, @Param("elements") List<Integer> elements) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO menu_element ");
        sb.append("(menu_id,element_id)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'menu_id}, #'{'elements[{0}]})");
        for (int i = 0; i < elements.size(); i++) {
            String j = i + "";
            sb.append(mf.format(new Object[]{j}));
            if (i < elements.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String findElementsById(@Param("menu_id") Integer menu_id) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("menu_element", "me", "element_id", "id"));
        sql.WHERE("me.menu_id=#{menu_id}");
        return sql.toString();
    }

    public String deleteMenuElement(@Param("menu_id") Integer menu_id, @Param("element_id") Integer element_id) {
        SQL sql = new SQL();
        sql.DELETE_FROM("menu_element");
        sql.WHERE("menu_id=#{menu_id} and element_id=#{element_id}");
        return sql.toString();
    }

    public String getByKeyword(@Param("keyword") String keyword) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableNameThis());
        sql.WHERE("keyword=#{keyword}");
        return sql.toString();
    }
}
