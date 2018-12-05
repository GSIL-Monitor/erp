package com.stosz.purchase.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseIn;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 采购单的mapper
 * @author xiongchenyang
 * @version [1.0 , 2017/11/9]
 */
@Repository
public interface PurchaseInMapper {

    //-----------------------------------基础数据增删改查------------------------------------//

    @InsertProvider(type = PurchaseInBuilder.class, method = "insert")
    int insert(PurchaseIn purchaseIn);

    @DeleteProvider(type = PurchaseInBuilder.class,method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PurchaseInBuilder.class, method = "update")
    int update(PurchaseIn purchase);

    @SelectProvider(type = PurchaseInBuilder.class, method = "getById")
    PurchaseIn getById(Integer id);


    //-----------------------------------其余复杂业务逻辑------------------------------------//



}  
