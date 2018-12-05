package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 老erp产品分类
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
@Repository
public interface OldErpCategoryMapper {

    /**
     * 新增产品分类, 做同步用的，为保证id完全相同，所以需要插入id字段
     */
    @Insert("insert into erp_check_category(id_category, parent_id, title, status, created_at,updated_at,sort) values (#{id}, #{parentId}, #{title}, #{status},current_timestamp(),#{updateAt},#{sort} )")
    int insert(OldErpCategory oldErpCategory);

    @Delete("delete from erp_check_category where id_category = #{id}")
    int delete(Integer id);

    /**
     * 修改产品分类
     */
    @Update("update erp_check_category set parent_id = #{parentId}, title = #{title}, status = #{status}, created_at = #{createAt},updated_at = #{updateAt} , sort=#{sort}  where id_category = #{id}")
    int update(OldErpCategory oldErpCategory);

    /**
     * 修改产品分类的状态
     */
    @Update("update erp_check_category set status = #{status} where id = #{id} ")
    int updateStatus(OldErpCategory oldErpCategory);

    /**
     * 根据id查找
     */
    @Select("select * from erp_check_category where id_category = #{id}")
    OldErpCategory getById(Integer id);
    /**
     * 查询所有的产品分类
     */
    @Select("select id_category, parent_id, title, status from erp_check_category order by parent_id asc ")
    List<OldErpCategory> findAll();

    @Select("select * from erp_check_category where title = #{name}")
    OldErpCategory getByName(String name);
}
