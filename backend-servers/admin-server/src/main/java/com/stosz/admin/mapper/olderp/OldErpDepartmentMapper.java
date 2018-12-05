package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpDepartment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by XCY on 2017/8/22.
 * desc: 老erp的mapper
 */
@Repository
public interface OldErpDepartmentMapper {

    /**
     * @return 老erp的全部部门
     */
    @Select("select * from erp_department where title in (select title from erp_department group by title having count(1)=1) order by parent_id")
    List<OldErpDepartment> findAll();

    /**
     * 新增老erp部门信息
     *
     * @param oldErpDepartment 老erp
     * @return 新增结果
     */
    @Insert("INSERT INTO erp_department(parent_id, title ,updated_at) VALUES (#{parentId}, #{title}, now() )")
    Integer insert(OldErpDepartment oldErpDepartment);

    /**
     * 根据id删除对应的部门信息
     *
     * @param id id
     * @return 删除部门信息
     */
    @Delete("DELETE FROM erp_department WHERE  id = #{id}")
    Integer delete(@Param("id") Integer id);

    @Select("select * from erp_department where title in (select title from erp_department group by title having count(1)>1) order by parent_id")
    List<OldErpDepartment> findByRepeat();
}
