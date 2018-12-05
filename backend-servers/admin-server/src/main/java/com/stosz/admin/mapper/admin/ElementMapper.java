package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.Element;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liufeng
 * @version [1.0 , 2017/9/11]
 */
@Repository
public interface ElementMapper {
    /**
     * 插入元素
     *
     * @param element
     * @return
     */
    @InsertProvider(type = ElementBuilder.class, method = "insert")
    int insert(Element element);

    /**
     * 根据id删除元素
     */
    @DeleteProvider(type = ElementBuilder.class, method = "delete")
    Integer delete(Integer id);

    /**
     * 选择更新元素内容
     */
    @UpdateProvider(type = ElementBuilder.class, method = "update")
    Integer update(Element element);

    /**
     * 根据id查询到对应的元素
     */
    @SelectProvider(type = ElementBuilder.class, method = "getById")
    Element getById(Integer id);

    /**
     * 根据keyword查询到对应的元素
     */
    @SelectProvider(type = ElementBuilder.class, method = "getByKeyword")
    Element getByKeyword(@Param("keyword") String keyword);

    @Select("select * from element")
    List<Element> findElements();

    /**
     * 给某个菜单下的元素添加权限
     */
    @InsertProvider(type = ElementBuilder.class, method = "insertMenuElement")
    int insertMenuElement(@Param("id") Integer id, @Param("menu_id") Integer menu_id, @Param("elements") List<Integer> elements);

    /**
     * 根据菜单id查询到对应的元素
     */
    @SelectProvider(type = ElementBuilder.class, method = "findElementsById")
    List<Element> findElementsById(@Param("menu_id") Integer menu_id);

    /**
     * 删除菜单下的某个元素
     */
    @DeleteProvider(type = ElementBuilder.class, method = "deleteMenuElement")
    Integer deleteMenuElement(@Param("menu_id") Integer menu_id, @Param("element_id") Integer element_id);

    /**
     * 查询用户对页面元素的权限
     *
     * @param userId
     * @param menuId
     * @return
     */
    @Select("select * from element a INNER JOIN(select element_id from job_menu where menu_id=#{menuId} and" +
            " job_id IN (select job_id from user_job where user_id=#{userId}) and element_id is not null) b on a.id=b.element_id")
    List<Element> findElementIdByMenuInAndUserId(@Param("menuId") Integer menuId, @Param("userId") Integer userId);


    @Select("select element_id from menu_element me left join menu m on me.menu_id = m.id where m.keyword = #{menuKey}")
    List<Integer> findIdByMenuKey(@Param("menuKey") String menuKey);

    @Select("select e.id from element e left join job_menu jm on e.id = jm.element_id where jm.job_id in(select job_id from user_job where user_id = #{userId}) and menu_id = #{menuId} ")
    List<Integer> findElementPermission(@Param("menuId") Integer menuId, @Param("userId") Integer userId);
}
