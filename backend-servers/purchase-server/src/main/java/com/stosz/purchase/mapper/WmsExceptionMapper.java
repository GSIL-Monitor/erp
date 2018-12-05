package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.WmsException;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/29]
 */
@Repository
public interface WmsExceptionMapper {

    //-----------------------------------------------------基础增删改查----------------------------------------------//

    @InsertProvider(type = WmsExceptionBuilder.class,method = "insert")
    int insert(WmsException wmsException);

    @UpdateProvider(type = WmsExceptionBuilder.class,method = "update")
    int update(WmsException wmsException);

    @DeleteProvider(type = WmsExceptionBuilder.class,method = "delete")
    int delete(Integer id);

    @SelectProvider(type = WmsExceptionBuilder.class,method = "getById")
    WmsException getById(Integer id);

    @SelectProvider(type = WmsExceptionBuilder.class,method = "find")
    List<WmsException> find(WmsException wmsException);

}  
