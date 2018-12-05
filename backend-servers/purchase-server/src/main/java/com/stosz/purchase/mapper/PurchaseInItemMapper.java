package com.stosz.purchase.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.purchase.ext.model.PurchaseInItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购详情的mapper
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/11/9]
 */
@Repository
public interface PurchaseInItemMapper  {

    // -----------------------------------基础数据增删改查------------------------------------//

    @InsertProvider(type = PurchaseInItemBuilder.class, method = "insert")
    int insert(PurchaseInItem purchaseInItem);

    @DeleteProvider(type = PurchaseInItemBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PurchaseInItemBuilder.class, method = "update")
    int update(PurchaseInItem purchaseInItem);

    @SelectProvider(type = PurchaseInItemBuilder.class, method = "getById")
    PurchaseInItem getById(Integer id);

    // -----------------------------------复杂业务逻辑------------------------------------//

    @Select("select * from purchase_in_item where purchase_id = #{purchaseId}")
    List<PurchaseInItem> findByPurchaseId(@Param("purchaseId") Integer purchaseId);


}
