package com.stosz.purchase.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.ErrorGoods;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 错货单的mapper
 * @author xiongchenyang
 * @version [1.0 , 2017/11/9]
 */
@Repository
public interface ErrorGoodsMapper extends IFsmDao<ErrorGoods> {

    //-----------------------------------基础数据增删改查------------------------------------//

    @InsertProvider(type = ErrorGoodsBuilder.class, method = "insert")
    int insert(ErrorGoods errorGoods);

    @DeleteProvider(type = ErrorGoodsBuilder.class,method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = ErrorGoodsBuilder.class, method = "updateSelective")
    int update(ErrorGoods errorGoods);

    @UpdateProvider(type = ErrorGoodsBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @Select("select e.*,s.name as supplier_name from error_goods e left join supplier s on e.supplier_id = s.id where e.id = #{id}")
    ErrorGoods getById(@Param("id") Integer id);



    /**
     * 通过筛选条件获取到错货单
     * @param errorGoods 错货单筛选条件
     * @param deptList 用户的数据权限
     * @return 错货单筛选结果
     */
    @SelectProvider(type = ErrorGoodsBuilder.class,method = "findByParam")
    List<ErrorGoods> findByParam(@Param("errorGoodsFsm") ErrorGoods errorGoods, @Param("deptList") List<Integer> deptList);

    /**
     * 通过筛选条件获取到错货单数量
     * @param errorGoods 错货单筛选条件
     * @param deptList 用户的数据权限
     * @return 错货单筛选条数
     */
    @SelectProvider(type = ErrorGoodsBuilder.class, method = "countByParam")
    int count(@Param("errorGoodsFsm") ErrorGoods errorGoods, @Param("deptList") List<Integer> deptList);

    /**
     * 根据原采购单号获取未完结的错货单数量
     * @param originalPurchaseNo 原采购单号
     * @return 未完结的错货单数量
     */
    @Select("select count(1) from error_goods where original_purchase_no = #{originalPurchaseNo} and (state != 'completed' and state != 'cancel')")
    int countByOriginalPurchaseNo(String originalPurchaseNo);
    
}  
