package com.stosz.product.mapper;


import com.stosz.product.ext.model.LabelObject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelObjectMapper {

	/*=================      公共方法 开始       ===================*/
	@InsertProvider(type = LabelObjectBuilder.class, method = "insert")
	Integer insert(LabelObject param);

	@DeleteProvider(type = LabelObjectBuilder.class, method = "delete")
	Integer delete(@Param("id") Integer id);

	@UpdateProvider(type = LabelObjectBuilder.class, method = "update")
	Integer update(LabelObject param);

	@SelectProvider(type = LabelObjectBuilder.class, method = "getById")
	LabelObject getById(@Param("id") Integer id);

	@SelectProvider(type = LabelObjectBuilder.class, method = "find")
	List<LabelObject> find(LabelObject param);

	@SelectProvider(type = LabelObjectBuilder.class, method = "count")
	Integer count(LabelObject param);
	/*=================      公共方法 结束       ===================*/

	@Delete("<script>DELETE label_object WHERE id IN " +
			" <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	void deleteBatch(@Param("ids") List<Integer> ids);

	@Select("<script>INSERT INTO label_object(object_id,object_type,label_id,label_value,extend_value,create_at,update_at,creator,creator_id, object_memo) values"
			+ "<foreach item=\"item\" collection=\"LabelObjects\"  separator=\",\" >"
			+ "(#{item.objectId},#{item.objectType},#{item.labelId},#{item.labelValue},#{item.extendValue},#{item.createAt},#{item.updateAt},#{item.creator},#{item.creatorId},#{item.objectMemo})"
			+ "</foreach></script>")
	void addLabelObject(@Param("LabelObjects") List<LabelObject> LabelObjects);

	@Select("select * from label_object where object_id=#{objectId} and object_type=#{objectType}")
	List<LabelObject> queryLabel(@Param("objectId") Integer objectId, @Param("objectType") String objectType);

	@Delete("delete from label_object where object_id=#{objectId} and object_type=#{objectType}")
	int delLabel(@Param("objectId") Integer objectId, @Param("objectType") String objectType);

	@Select("select o.*,l.name labelName,l.keyword labelKeyword from label_object o left join label l on o.label_id=l.id where o.object_id=#{objectId} and o.object_type=#{objectType}")
	List<LabelObject> queryLabelObjectAttr(@Param("objectId") Integer objectId, @Param("objectType") String objectType);

	@Select("<script>SELECT * FROM label_object WHERE object_type = #{objectType} AND object_id in "
			+ "<foreach item=\"item\" collection=\"objectIds\" open=\"(\" separator=\",\" close=\")\">"
            +"${item}"
            + "</foreach></script>")
	List<LabelObject> queryLabelByList(@Param("objectIds") List<Integer> objectIds, @Param("objectType") String objectType);

	@Select("<script>SELECT o.*,l.name AS labelName,l.keyword AS labelKeyword " +
			"FROM label_object o LEFT JOIN label l ON o.label_id = l.id " +
			"WHERE o.object_type = #{objectType} AND o.object_id IN "
			+ "<foreach item=\"item\" collection=\"objectIds\" open=\"(\" separator=\",\" close=\")\">"
			+"${item}"
			+ "</foreach></script>")
	List<LabelObject> findLabelByList(@Param("objectIds") List<Integer> objectIds, @Param("objectType") String objectType);

	@Select("SELECT * FROM label_object WHERE label_id = #{labelId}")
	List<LabelObject> findByLabelId(@Param("labelId") Integer labelId);

	@SelectProvider(type = LabelObjectBuilder.class, method = "lableObjectCheck")
	int lableObjectCheck(@Param("id") Integer id, @Param("labelId") Integer labelId,
						 @Param("objectId") Integer objectId, @Param("objectType") String objectType);

}
