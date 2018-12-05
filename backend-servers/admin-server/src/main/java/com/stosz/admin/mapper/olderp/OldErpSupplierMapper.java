package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpSupplier;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/27]
 */
@Repository
public interface OldErpSupplierMapper {

    @Select("select * from erp_supplier")
    List<OldErpSupplier> findAll();
}  