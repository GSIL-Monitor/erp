package com.stosz.purchase.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.purchase.ext.common.PurchaseSkuDto;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
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
public interface ErrorGoodsItemMapper extends IFsmDao<ErrorGoodsItem> {

    // -----------------------------------基础数据增删改查------------------------------------//

    @InsertProvider(type = ErrorGoodsItemBuilder.class, method = "insert")
    int insert(ErrorGoodsItem errorGoodsItem);

    @DeleteProvider(type = ErrorGoodsItemBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = ErrorGoodsItemBuilder.class, method = "updateSelective")
    int update(ErrorGoodsItem errorGoodsItem);

    @UpdateProvider(type = ErrorGoodsItemBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @SelectProvider(type = ErrorGoodsItemBuilder.class, method = "getById")
    ErrorGoodsItem getById(Integer id);

    @Select("select * from error_goods_item where error_id = #{errorId}")
    List<ErrorGoodsItem> findByErrorId(@Param("errorId") Integer errorId);

    @SelectProvider(type = ErrorGoodsItemBuilder.class,method = "findProduct")
    List<ErrorGoodsItem> findProduct(@Param("errorGoodsList") List<ErrorGoods> errorGoodsList);

    @SelectProvider(type = ErrorGoodsItemBuilder.class,method = "findByParam")
    List<ErrorGoodsItem> findByParam(@Param("errorGoodsItem")ErrorGoodsItem errorGoodsItem ,@Param("deptIds") List<Integer> deptIds);

    @SelectProvider(type = ErrorGoodsItemBuilder.class,method = "countByParam")
    Integer countByParam(@Param("errorGoodsItem")ErrorGoodsItem errorGoodsItem ,@Param("deptIds") List<Integer> deptIds);
}
