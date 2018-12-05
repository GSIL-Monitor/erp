package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.BlackList;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangqian
 * 黑名单
 */

@Repository
public interface BlackListMapper {

    @InsertProvider(type = BlackListBulider.class,method = "insert")
    int insert(BlackList param);

    @DeleteProvider(type = BlackListBulider.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = BlackListBulider.class, method = "update")
    int update(BlackList param);

    @SelectProvider(type = BlackListBulider.class, method = "find")
    List<BlackList> find(BlackList param);

    @SelectProvider(type = BlackListBulider.class, method = "count")
    int count(BlackList param);

    @SelectProvider(type = BlackListBulider.class, method = "getById")
    BlackList getById(@Param("id") Integer id);


    @Insert("<script>"
            +"insert into black_list(black_level_enum, black_type_enum, content, create_at,creator_id,creator)"
            + "values "
            + " <foreach collection =\"list\" item=\"black\" index= \"index\" separator =\",\"> "
            + "(#{black.blackLevelEnum}, #{black.blackTypeEnum},#{black.content},NOW(),#{black.creatorId},#{black.creator})"
            + "</foreach> "
            + "</script>")
    int insertBatch(List<BlackList> list);

    @Insert("<script>"
            +"insert into black_list(id,black_level_enum, black_type_enum, content, create_at,creator_id,creator)"
            + "values "
            + " <foreach collection =\"list\" item=\"black\" index= \"index\" separator =\",\"> "
            + "(#{black.id},#{black.blackLevelEnum}, #{black.blackTypeEnum},#{black.content},NOW(),#{black.creatorId},#{black.creator})"
            + "</foreach> "
            + "</script>")
    int insertBatch2(List<BlackList> list);


    /**
     * 查找创建黑名单记录的所有人编号和姓名
     * @return
     */
    @Select("select DISTINCT creator_id creatorId, creator from black_list")
    List<BlackList> findDistinctCreator();


    @Select(" select * from black_list b where b.content = #{ip} " +
            " UNION " +
            " select * from black_list b where b.content = #{tel} " +
            " UNION " +
            " select * from black_list b where b.content = #{email} " +
            " UNION " +
            " select * from black_list b where b.content = #{address} " +
            " UNION " +
            " select * from black_list b where b.content = #{name} " +
            " limit 1 ")
    BlackList findByIpOrTelOrEmailOrAddrOrName(@Param("ip") String ip, @Param("tel") String tel, @Param("email") String email, @Param("address") String address,@Param("name") String name);


    @Select(" select * from black_list b where b.content = #{content} ")
    List<BlackList> findByContent(@Param("content") String content);

    @Select("SELECT IFNULL(MAX(id),0) FROM black_list ")
    Integer getNewMaxId();


}
