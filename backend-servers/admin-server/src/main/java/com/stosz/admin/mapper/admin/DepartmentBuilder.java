package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.Department;
import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;

public class DepartmentBuilder extends AbstractBuilder<Department> {

    public static final Logger logger = LoggerFactory.getLogger(DepartmentBuilder.class);

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, Department param) {
		
	}


    public String insertList(@Param("id") Integer id, @Param("departmentList") List<Department> departmentList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO department ");
        sb.append("(id,department_name,master_id, department_no,ecology_pinyin_search,parent_id,tlevel)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'departmentList[{0}].id}, #'{'departmentList[{0}].departmentName},#'{'departmentList[{0}].masterId},#'{'departmentList[{0}].departmentNo},#'{'departmentList[{0}].ecologyPinyinSearch}, #'{'departmentList[{0}].parentId}, #'{'departmentList[{0}].tlevel})");
        for (int i = 0; i < departmentList.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < departmentList.size() - 1) {
                sb.append(",");
            }
        }
        logger.info("=== insertList sql:" + sb.toString());
        return sb.toString();
    }

    public String updateOldIdByOldName(@Param("department") Department department) {
        SQL sql = new SQL();
        sql.UPDATE(getTableName());
        sql.SET("old_id = #{department.oldId}");
        sql.WHERE("department_name like concat(#{department.departmentName},'%')");
        eq(sql, "parent_id", "department.parentId", department.getParentId());
        return sql.toString();
    }

    public String findByNos(@Param("nos") List<String> nos) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableName());
        findByNosWhere(sql, nos);
        return sql.toString();
    }

    private void findByNosWhere(SQL sql, List<String> nos) {
        if (nos != null) {
            if (nos.isEmpty()) {
                sql.WHERE("1 != 1");
                return;
            }
            if (nos.size() == 1) {
                sql.WHERE(String.format("department_no like '%s%%'", nos.get(0)));
            } else {
                StringBuilder sb = new StringBuilder();
                for (String no : nos) {
                    sb.append(" department_no like CONCAT('" + no + "','%') or");
                }
                String s = sb.toString();
                if (sb.length() > 0) {
                    s = sb.substring(0, sb.length() - " or".length());
                }
                sql.WHERE(s);
            }
        }
    }


    public String findByDeptList(@Param("departmentList") List<Department> departmentList){
	    SQL sql = new SQL();
	    sql.SELECT("_this.*");
        sql.FROM(getTableNameThis());
        StringBuilder stringBuilder = new StringBuilder();
        if(CollectionUtils.isNotNullAndEmpty(departmentList)){
            Department department = departmentList.get(0);
            sql.WHERE("department_no like concat('"+department.getDepartmentNo()+"','%')");
            if(departmentList.size()>1){
                for (int i = 1;i<departmentList.size();i++){
                    stringBuilder.append("OR department_no like concat('").append(departmentList.get(i).getDepartmentNo()).append("','%')");
                }
            }
        }
	    return sql.toString()+ stringBuilder.toString();
    }
}
