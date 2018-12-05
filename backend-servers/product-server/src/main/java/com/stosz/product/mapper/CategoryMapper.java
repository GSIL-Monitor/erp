package com.stosz.product.mapper;

import com.stosz.olderp.ext.model.OldErpCategoryNew;
import com.stosz.plat.enums.CategoryUserTypeEnum;
import com.stosz.product.ext.model.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {

    /**
     * 批量插入产品分类
     */
    @InsertProvider(type = CategoryBuilder.class, method = "insertBatch")
    int insertBatch(@Param("categoryList") List<Category> categoryList);

    /**
     * 品类删除
     */
    @Delete("delete from category where id = #{id}")
    int deleteById(Integer id);

    /**
     * 增加
     */
    @Insert("insert into category set create_at=current_timestamp(),usable=1, name=#{name}, parent_id=#{parentId}, no=#{no}, sort_no=#{sortNo}, creator_id=#{creatorId},creator=#{creator},leaf=#{leaf}")
    @Options(useGeneratedKeys = true)
    Integer insert(Category record);



    @Insert("INSERT INTO category (id, parent_id,name,no,creator_id ,creator,create_at) VALUES (0,-1,'品类','',0,'系统',now());")
    int insertRoot(@Param("id") Integer id);

    @Update("UPDATE category SET id=0 where parent_id=-1;")
    int updateRoot();

    @Update("update category set no = #{no} where id = #{id}")
    int updateNoById(Category category);

    /**
     * 与老erp同步时做的增加操作，需要添加id
     */
    @Insert("insert into category set id = #{id}, create_at=current_timestamp(),usable=1, name=#{name}, parent_id=#{parentId}, no=#{no}, sort_no=#{sortNo}, creator_id=#{creatorId},creator=#{creator},leaf=#{leaf}")
    int insertBySync(Category category);

    /**
     * 品类更新
     */
    @Update("update category set usable=#{usable}, name=#{name}, parent_id=#{parentId}, no=#{no}, sort_no=#{sortNo}, creator_id=#{creatorId},creator=#{creator},leaf=#{leaf} where id=#{id}")
    int updateById(Category record);

    /**
     * 根据id修改是否是叶子
     */
    @Update("update category set leaf=#{leaf} where id=#{id}")
    int updateLeaf(@Param("leaf") Boolean leaf, @Param("id") Integer id);


    /**
     * 根据品类id查询
     */
    @Select("select * from category where id = #{id}")
    Category getById(@Param("id") Integer id);


    @Select("select category_id from old_erp_category_rel where old_category_id = #{id}")
    Integer getNewCategoryById(@Param("id") Integer id);

    /**
     * 根据父编码获取子孙后代
     */
    @Select("select * from category where no like concat(#{no},'%')")
    List<Category> findByCategoryNo(@Param("no") String no);

    @Select("select * from category where no like concat(#{no},'%')")
    List<OldErpCategoryNew> findResultOldCateByNo(@Param("no") String no);

    @Select("select * from category where no like concat(#{no},'%') and parent_id=0 limit 1")
    Category findFirstCategoryByNo(@Param("no") String no);

	@Select("<script>SELECT * FROM category WHERE id IN " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<Category> findByIds(@Param("ids") List<Integer> ids);

	@SelectProvider(type = CategoryBuilder.class,method = "findByCategoryNos")
    List<Category> findByCategoryNos(@Param("nos") List<String> nos);

    @Select("SELECT * FROM category WHERE no = #{no}")
    Category getByNo(@Param("no") String no);

    /**
     * 获取当前分类的最大id值
     */
    @Select("select Max(id) from category")
    Integer getMaxId();

    /**
     * 根据父id获取子节点
     */
    @Select("SELECT * FROM category WHERE parent_id = #{parentId}")
    List<Category> findByParentId(@Param("parentId") Integer parentId);

    /**
     * 品类列表
     */
    @SelectProvider(type = CategoryBuilder.class,method = "find")
    List<Category> find(Category param);

    /**
     * 查询总记录数
     */
    @SelectProvider(type = CategoryBuilder.class,method = "count")
    int count(Category param);


    /**
     * 查询除根节点的所有分类
     */
    @Select("select * from category where id != 0")
    List<Category> findAll();

    @Select("select _this.* from category _this join category_user_rel r on _this.id=r.category_id and r.usable=1 and r.user_id=#{userId} and r.user_type = #{userType}")
    List<Category> findFirstLevelByUserId(@Param("userId") Integer userId, @Param("userType") CategoryUserTypeEnum userType);

    @SelectProvider(type = CategoryBuilder.class,method = "categorySearchCount")
	Integer categorySearchCount(Category param);

    @SelectProvider(type = CategoryBuilder.class,method = "categorySearch")
    List<Category> categorySearch(Category param);


    @Select("select * from category where name = #{name} ")
    Category getByName(String name);

    @Select("<script>SELECT * FROM category WHERE no IN " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach> ORDER BY LENGTH(no) ASC" +
            "</script>")
    List<Category> findByNos(@Param("ids") List<String> ids);
    
    @SelectProvider(type = CategoryBuilder.class,method = "checkCategoryName")
	int checkCategoryName(Category category);

    @Select("SELECT c.* FROM category c where c.id in (SELECT DISTINCT p.category_id FROM product p where p.state !='disappeared') and c.leaf = 0")
    List<Category> findByDivide();
        
}
