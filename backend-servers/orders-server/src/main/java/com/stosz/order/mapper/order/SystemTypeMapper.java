package com.stosz.order.mapper.order;

import com.google.common.base.Strings;
import com.stosz.order.ext.model.SystemType;
import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.CommonUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @auther carter
 * create time    2017-10-31
 * 系统类型mapper接口
 */
@Repository
public interface SystemTypeMapper {

    /**
     * 增加记录
     * @param record
     */
    @Insert("INSERT INTO system_type(parent_id,type_key,type_value,type_desc,create_at,update_at,creator_id,creator) VALUES(#{parentId},#{typeKey},#{typeValue},#{typeDesc},now(),now(),#{creatorId},#{creator})")
    void insert(SystemType record);



    @Delete("DELETE FROM system_type WHERE id=#{id}")
    Long delete(@Param("id") Long id);

    @Update("UPDATE system_type SET parent_id=#{parentId},type_key=#{typeKey},type_value=#{typeValue},type_desc=#{typeDesc},update_at=now(),creator_id=#{creatorId} ,creator=#{creator} WHERE id=#{id}")
    Long update(SystemType record);

    @Select("SELECT * FROM system_type WHERE id=#{id}")
    SystemType findById(@Param("id") Long id);

    @SelectProvider(type = SystemTypeBuilder.class, method = "findByParam")
    List<SystemType> findByParam(@Param("param")SystemType param);


    @Select("SELECT * FROM system_type")
    List<SystemType> findAll();


    @SelectProvider(type = SystemTypeBuilder.class, method = "findCountByParam")
    Integer findCountByParam(@Param("param")SystemType systemType);




    class SystemTypeBuilder extends AbstractBuilder<SystemType> {
        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, SystemType param) {

        }


        public String findByParam(@Param("param") SystemType param) {


            SQL sql = new SQL();
            sql.SELECT(_this("*"));
            sql.FROM(getTableNameThis());
            buildWhereByParam(sql, param);
            StringBuilder sb = new StringBuilder();
            if (notEmpty(param.getStart())) {
                sb.append(" order by id desc limit ").append(param.getStart()).append(",").append(param.getLimit());
            } else {
                sb.append(" order by id desc limit ").append(param.getLimit());
            }
            String s = sql.toString() + sb.toString();
            logger.info("condition sql:"+s);
            return s;
        }

        public String findCountByParam(@Param("param")SystemType param) {
            SQL sql = new SQL();
            sql.SELECT("count(1)");
            sql.FROM(getTableNameThis());
            buildWhereByParam(sql, param);
            StringBuilder sb = new StringBuilder();
            return sql.toString() + sb.toString();
        }



        public void buildWhereByParam(SQL sql, SystemType param) {

            Assert.notNull(param,"查询参数不能为空");
            if (null!=param.getId() && param.getId().longValue()> 0 ) sql.AND().WHERE("id="+ CommonUtils.getLongValue(param.getId()));

            if (null!=param.getParentId()  ) sql.AND().WHERE("parent_id="+ CommonUtils.getLongValue(param.getParentId()));

            if (!Strings.isNullOrEmpty(param.getTypeKey())) sql.AND().WHERE("type_key=\'"+ param.getTypeKey()+"\'");

            if (!Strings.isNullOrEmpty(param.getTypeValue())) sql.AND().WHERE("type_value=\'"+ param.getTypeValue()+"\'");

            if (!Strings.isNullOrEmpty(param.getTypeDesc())) sql.AND().WHERE("type_desc=\'"+ param.getTypeDesc()+"\'");


            if (null!=param.getCreatorId()  && param.getCreatorId().intValue()> 0 ) sql.AND().WHERE("creator_id="+ CommonUtils.getIntValue(param.getCreatorId()));

//            if (null!=param.getSystemId()  && param.getSystemId().intValue()> 0) sql.AND().WHERE("system_id="+ CommonUtils.getIntValue(param.getSystemId()));


            logger.info("条件查询语句：" + sql.toString());


        }

    }

}
