package com.stosz.product.mapper;

import com.stosz.product.ext.model.Zone;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneMapper {

	@InsertProvider(type = ZoneBuilder.class, method = "insert")
	Integer insert(Zone param);

    /**
     * 同步老erp信息时使用
     *
     * @author xiongchenyang
     */
    @Insert("insert zone set id = #{id}, title = #{title}, code = #{code}, currency =  #{currency}, parent_id = #{parentId}, country_id = #{countryId}")
    Integer insertOld(Zone zone);

    @Insert({"<script>INSERT IGNORE INTO zone(id,title,code,currency,parent_id,country_id) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"zoneList\"  separator=\",\" >" +
            " (#{item.id}, #{item.title}, #{item.code},#{item.currency},#{item.parentId},#{item.countryId})" +
            " </foreach>" +
            "</script>"})
    Integer insertList(@Param("id") Integer id, @Param("zoneList") List<Zone> zoneList);

	@DeleteProvider(type = ZoneBuilder.class, method = "delete")
	Integer delete(@Param("id") Integer id);

	@UpdateProvider(type = ZoneBuilder.class, method = "update")
	Integer update(Zone param);

	@SelectProvider(type = ZoneBuilder.class, method = "getById")
	Zone getById(@Param("id") Integer id);


    @Select("<script>SELECT * FROM zone WHERE id in " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
	List<Zone> findByIds(@Param("ids") List<Integer> ids);

	@SelectProvider(type = ZoneBuilder.class, method = "findList")
	List<Zone> findList(Zone param);

	@SelectProvider(type = ZoneBuilder.class, method = "count")
	Integer count(Zone param);

	@Select("SELECT * FROM zone WHERE parent_id = #{parentId}")
	List<Zone> findByParentId(@Param("parentId") Integer parentId);

	@Select("SELECT * FROM zone WHERE title LIKE CONCAT('%',#{title},'%') ORDER BY sort LIMIT 20 ")
	List<Zone> titleLike(@Param("title") String title);

    @Select("SELECT * FROM zone WHERE title = #{title} and country_id =#{countryId}")
    Zone getByUnique(@Param("countryId") Integer countryId, @Param("title") String title);

    @Select("select * from zone order by id")
    List<Zone> findAll();

    @Select("select * from zone where code = #{code}")
    Zone getByCode(@Param("code") String code);
    
    @Select("SELECT * FROM zone WHERE country_id = #{countryId}")
    List<Zone> findByCountryId(@Param("countryId") Integer countryId);
    
}
