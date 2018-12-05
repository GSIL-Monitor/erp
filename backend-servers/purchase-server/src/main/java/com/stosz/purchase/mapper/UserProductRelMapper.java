package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.UserProductRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserProductRelMapper {

    //----------------------------------------------基础数据增删改查-----------------------------------------------//
    @InsertProvider(type = UserProductRelBuilder.class, method = "insert")
    int insert(UserProductRel userProductRel);

    @DeleteProvider(type = UserProductRelBuilder.class, method = "delete")
    int delete(Integer id);

    @Update("update user_product_rel set enable=#{enable},update_at=now() where id=#{id}")
    int update(@Param("id") Integer id, @Param("enable") Integer enable);

    @SelectProvider(type = UserProductRelBuilder.class, method = "count")
    int count(UserProductRel userProductRel);

    @SelectProvider(type = UserProductRelBuilder.class, method = "getById")
    UserProductRel getById(Integer id);

    @Select("SELECT p.user_id,p.user_name FROM user_product_rel p LEFT JOIN user_bu_dept b ON p.user_id=b.user_id"
            + " WHERE p.spu=#{spu} AND b.bu_department_id=#{buDepartmentId} AND p.enable=1 AND b.enable=1 ORDER BY p.update_at DESC LIMIT 1")
    UserProductRel getBySpuAndDeptId(@Param("spu") String spu, @Param("buDepartmentId") Integer buDepartmentId);

    @Select("<script>SELECT id,NAME FROM supplier WHERE id in "
            + "<foreach item=\"item\" index=\"index\" collection=\"idList\" open=\"(\" separator=\",\" close=\")\">" + "#{item}"
            + "</foreach> ORDER BY update_at DESC LIMIT 1 </script>")
    UserProductRel getByIdSet(Set<Integer> idSet);

    @Select("select * from user_product_rel where spu=#{spu} and user_id=#{userId}")
    UserProductRel getBySpuAndUserId(@Param("spu") String spu, @Param("userId") Integer userId);

    @SelectProvider(type = UserProductRelBuilder.class, method = "find")
    List<UserProductRel> find(UserProductRel userProductRel);

}
