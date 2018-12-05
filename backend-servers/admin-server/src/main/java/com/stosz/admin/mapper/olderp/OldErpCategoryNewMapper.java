package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpCategoryNew;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/19]
 */
@Repository
public interface OldErpCategoryNewMapper {

    /*=================      公共方法 开始       ===================*/
    @InsertProvider(type = OldErpCategoryNewBuilder.class, method = "insert")
    int insert(OldErpCategoryNew param);

    @DeleteProvider(type = OldErpCategoryNewBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = OldErpCategoryNewBuilder.class, method = "update")
    int update(OldErpCategoryNew param);

    @SelectProvider(type = OldErpCategoryNewBuilder.class, method = "getById")
    OldErpCategoryNew getById(@Param("id") Integer id);

    @SelectProvider(type = OldErpCategoryNewBuilder.class, method = "find")
    List<OldErpCategoryNew> find(OldErpCategoryNew param);

    @SelectProvider(type = OldErpCategoryNewBuilder.class, method = "count")
    int count(OldErpCategoryNew param);
	/*=================      公共方法 结束       ===================*/

    @Insert("INSERT INTO erp_category_new (id, create_at, name, parent_id, no, sort_no, usable, creator_id, creator, leaf, en_category) VALUES " +
            "(#{id}, #{createAt}, #{name}, #{parentId}, #{no}, #{sortNo}, #{usable}, #{creatorId}, #{creator}, #{leaf}, #{enCategory})")
	int add(OldErpCategoryNew param);

    /**
     * 老erp数据批量插入
     */
    @Insert("<script>INSERT INTO erp_category_new (id, create_at, name, parent_id, no, sort_no, usable, creator_id, creator, leaf, en_category) VALUES " +
        "<foreach item=\"item\" collection=\"list\" separator=\",\" >" +
        " (#{item.id}, #{item.createAt}, #{item.name}, #{item.parentId}, #{item.no}, #{item.sortNo}, #{item.usable}, #{item.creatorId}, #{item.creator}, #{item.leaf}, #{item.enCategory})" +
        "</foreach>" +
        "</script>")
	int insertBatch(List<OldErpCategoryNew> oldErpCategory);

    /**
     * 截断老Erp表
     */
    @Delete("delete from erp_category_new")
    void truncate();

    /**
     * 根据id修改是否是叶子
     */
    @Update("update erp_category_new set leaf=#{leaf} where id=#{id}")
    int updateLeaf(@Param("leaf") Boolean leaf, @Param("id") Integer id);

    /**
     * 更新品类根节点的ID
     */
    @Update("UPDATE erp_category_new SET id = 0 WHERE parent_id = -1;")
    int updateRootId();

    /**
     * 品类更新
     */
    @Update("update erp_category_new set usable=#{usable}, name=#{name}, parent_id=#{parentId}, no=#{no}, sort_no=#{sortNo}, creator_id=#{creatorId},creator=#{creator},leaf=#{leaf} where id=#{id}")
    int updateById(OldErpCategoryNew record);


    /**
     * 获取了老erp品类表中最后的一条数据
     */
    @Select("SELECT * FROM erp_category_new ORDER BY id DESC LIMIT 1;")
    OldErpCategoryNew getLastData();

    /**
     * 查询总数
     */
    @Select("SELECT COUNT(1) FROM erp_category_new")
    int countAll();
}
