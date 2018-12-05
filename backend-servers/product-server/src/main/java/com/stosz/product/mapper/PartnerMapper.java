package com.stosz.product.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.product.ext.model.Partner;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerMapper extends IFsmDao<Partner> {

    @InsertProvider(type = PartnerBuilder.class,method = "insertSelective")
    int insertSelective(Partner param);

    @DeleteProvider(type = PartnerBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = PartnerBuilder.class,method = "updateSelective")
    int updateSelective(Partner param);

    @SelectProvider(type = PartnerBuilder.class,method = "find")
    List<Partner> find(Partner param);

    @SelectProvider(type = PartnerBuilder.class,method = "getById")
    Partner getById(@Param("id") Integer id);

    @SelectProvider(type = PartnerBuilder.class,method = "count")
    int count(Partner param);

    @SelectProvider(type = PartnerBuilder.class, method = "findByIds")
    List<Partner> findByIds(@Param("ids") List<Integer> ids);

}
