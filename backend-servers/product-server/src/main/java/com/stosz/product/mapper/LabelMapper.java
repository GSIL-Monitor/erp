package com.stosz.product.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.*;

import com.stosz.product.ext.model.Label;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public interface LabelMapper {

	/*=================      公共方法 开始       ===================*/
	@InsertProvider(type = LabelBuilder.class, method = "insert")
	Integer insert(Label param);

	@DeleteProvider(type = LabelBuilder.class, method = "delete")
	Integer delete(@Param("id") Integer id);

	@UpdateProvider(type = LabelBuilder.class, method = "update")
	Integer update(Label param);

	@SelectProvider(type = LabelBuilder.class, method = "getById")
	Label getById(@Param("id") Integer id);

	@SelectProvider(type = LabelBuilder.class, method = "find")
	List<Label> find(Label param);

	@SelectProvider(type = LabelBuilder.class, method = "count")
	Integer count(Label param);
	/*=================      公共方法 结束       ===================*/

	@Select("select * from label where keyword=#{keyword}")
	Label queryLabel(String keyword);

	@Select("SELECT * FROM label WHERE keyword = #{keyword} ")
	List<Label> findLabel(String keyword);

	@Select("select * from label where parent_id=#{parentId}")
	List<Label> queryLabelByParent(Integer parentId);

	@Select("SELECT * FROM label")
	List<Label> findAll();

	@SelectProvider(type = LabelBuilder.class, method = "countByName")
	int countByName(@Param("id") Integer id, @Param("parentId") Integer parentId, @Param("name") String name);

	@Select("SELECT l.*, lo.id AS labelObjectId, IF(lo.id IS NULL,FALSE,TRUE) checked " +
			"FROM label l LEFT OUTER JOIN label_object lo ON l.id = lo.label_id AND lo.object_id = #{objectId} AND lo.object_type = #{objectType} " +
			"WHERE l.parent_id = #{parentId}")
	List<Label> findLabelBind(@Param("objectId") Integer objectId, @Param("objectType") String objectType, @Param("parentId") Integer parentId);

	@Select("<script>SELECT l.*, lo.id AS labelObjectId, IF(lo.id IS NULL,FALSE,TRUE) checked " +
			"FROM label l LEFT OUTER JOIN label_object lo ON l.id = lo.label_id AND lo.object_id = #{objectId} AND lo.object_type = #{objectType} " +
			"WHERE l.parent_id IN "+
			"<foreach item=\"item\" index=\"index\" collection=\"parentIds\" open=\"(\" separator=\",\" close=\")\">"
			+"#{item}"
			+ "</foreach></script>")
	List<Label> findLabelBinds(@Param("objectId") Integer objectId, @Param("objectType") String objectType, @Param("parentIds") Set<Integer> parentIds);
}
