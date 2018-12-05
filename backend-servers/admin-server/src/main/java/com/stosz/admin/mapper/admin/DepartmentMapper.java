package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.Department;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("DepartmentMapper")
public interface DepartmentMapper {
	
    @Insert("insert into department(id, department_name, parent_id, tlevel, master_id, department_no, ecology_pinyin_search,create_at, status) values (#{id},#{departmentName}, #{parentId}, #{tlevel}, #{masterId}, #{departmentNo}, #{ecologyPinyinSearch}, current_timestamp,#{status})")
    Integer insert(Department record);

    @Delete("delete from department")
    void truncate();

    @DeleteProvider(type = DepartmentBuilder.class, method = "delete")
    Integer delete(Integer id);

	@UpdateProvider(type=DepartmentBuilder.class,method="update")
    Integer update(Department record);

    @Update("update department set old_id = #{oldId} where id = #{id}")
    Integer updateOldId(Department department);

    //    @Update("update department set old_id = #{oldId} where department_name like concat(#{departmentName},'%')")
    @UpdateProvider(type = DepartmentBuilder.class, method = "updateOldIdByOldName")
    Integer updateOldIdByOldName(@Param("department") Department department);

    @SelectProvider(type = DepartmentBuilder.class, method = "getById")
    Department getById(Integer id);

    /**
     * 查询到所有有效的部门
     * @return 有效的部门
     */
    @Select("select * from department")
    List<Department> selectAllDepartment();

    @Select("select * from department where old_id is null order by parent_id asc")
    List<Department> findByNoOldId();

    @Select("select * from department where parent_id = 0")
    List<Department> getTopDepartment();

    /**
     * @param departmentList 批量插入的部门集合
     * @return 插入结果
     */
    @InsertProvider(type=DepartmentBuilder.class,method="insertList")
    Integer insertList(@Param("id") Integer id, @Param("departmentList") List<Department> departmentList);

    /**
     * 根据老erp用户id获取到对应的部门
     * @param oldId 老erp用户id
     * @return 对应的部门
     */
    @Select("SELECT d.* FROM  department d LEFT JOIN user u ON u.department_id = d.id WHERE  u.old_id = #{oldId}")
    Department getByUserOldId(@Param("oldId") Integer oldId);

    @Select("select * from department where old_id = #{oldId}")
    Department getByOldId(@Param("oldId") Integer oldId);

    @Select("select * from department where department_no = #{no}")
    Department getByNo(@Param("no") String no);

    /**
     * 根据部门编号获取到其下面的所有孩子
     * @param no 部门编号
     * @return 返回所有孩子
     */
    @Select("select * from department where department_no LIKE concat(#{no},'%')")
    List<Department> getDepartmentByNo(@Param("no") String no);

    /**
     * 根据部门id获取到老erp的部门id
     * @param id 部门id
     * @return 老部门id
     */
    @Select("SELECT old_id FROM  department WHERE  id = #{id}")
    Integer getOldIdById(@Param("id") Integer id);

    @Select("SELECT *  FROM  department WHERE  parent_id = #{parentId}")
    List<Department> findByParentId(@Param("parentId") Integer parentId);
    
    
    @Select("<script>SELECT * FROM department WHERE id in " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
	List<Department> findByIds(@Param("ids") List<Integer> ids);

    @SelectProvider(type = DepartmentBuilder.class, method = "findByNos")
    List<Department> findByNos(@Param("nos") List<String> nos);

    @Select("select * from department where parent_id = 0 order by id asc")
    List<Department> findAllTopDept();

    @SelectProvider(type = DepartmentBuilder.class,method = "findByDeptList")
    List<Department> findByDeptList(@Param("departmentList") List<Department> departmentList);
}