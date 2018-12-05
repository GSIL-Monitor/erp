package com.stosz.purchase.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.purchase.ext.common.PurchaseDto;
import com.stosz.purchase.ext.model.Purchase;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购单的mapper
 * @author xiongchenyang
 * @version [1.0 , 2017/11/9]
 */
@Repository
public interface PurchaseMapper extends IFsmDao<Purchase> {

    //-----------------------------------基础数据增删改查------------------------------------//

    @InsertProvider(type = PurchaseBuilder.class, method = "insert")
    int insert(Purchase purchase);

    @DeleteProvider(type = PurchaseBuilder.class,method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PurchaseBuilder.class, method = "updateSelective")
    int update(Purchase purchase);

    @UpdateProvider(type = PurchaseBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @SelectProvider(type = PurchaseBuilder.class, method = "getById")
    Purchase getById(Integer id);

    @Select("select p.*,s.name as supplier_name ,pf.name as plat_name  from purchase p left join supplier s on p.supplier_id = s.id left join platform pf on p.plat_id = pf.id where p.id = #{id}")
    Purchase getDtoById(@Param("id") Integer id);


    //-----------------------------------其余复杂业务逻辑------------------------------------//

    /**
     * 更新采购单的提交时间为当前时间
     * @param purchase 要修改的采购单
     */
    @Update("update purchase set submit_time = CURRENT_TIMESTAMP() where id = #{id}")
    void updateTime(Purchase purchase);

    /**
     * 修改审批时间和审批人
     * @param purchase 要修改的采购单
     */
    @Update("update purchase set audit_time = CURRENT_TIMESTAMP(),auditor = #{auditor},auditor_id = #{auditorId} where id = #{id}")
    void updateAuditTime(Purchase purchase);

    /**
     * 修改付款时间为当前时间，修改付款人
     * @param purchase 要修改的采购单
     */
    @Update("update purchase set payment_time = #{paymentTime}, payer = #{payer}, payer_id = #{payerId}")
    void updatePayTime(Purchase purchase);

    /**
     * 修改采购单的运单号
     * @param purchase 运单号
     */
    @Update("update purchase set tracking_no = #{trackingNo} where id = #{id}")
    void updateTrackingNo(Purchase purchase);

    /**
     * 根据采购单号查询采购单
     * @param purchaseNo 采购单号
     * @return 查询结果
     */
    @Select("select p.*,s.name as supplier_name ,pf.name as plat_name  from purchase p left join supplier s on p.supplier_id = s.id left join platform pf on p.plat_id = pf.id where p.purchase_no = #{purchaseNo}")
    Purchase getByPurchaseNo(@Param("purchaseNo") String purchaseNo);

    /**
     * 通过筛选条件获取到采购单
     * @param purchase 采购单筛选条件
     * @param deptList 用户的数据权限
     * @return 采购单筛选结果
     */
    @SelectProvider(type = PurchaseBuilder.class,method = "findByParam")
    List<Purchase> findByParam(@Param("purchase") Purchase purchase, @Param("deptList") List<Integer> deptList);

    /**
     * 通过筛选条件获取到采购单数量
     * @param purchase 采购单筛选条件
     * @param deptList 用户的数据权限
     * @return 采购单筛选条数
     */
    @SelectProvider(type = PurchaseBuilder.class, method = "countByParam")
    int count(@Param("purchase") Purchase purchase, @Param("deptList") List<Integer> deptList);

    @Update("update purchase set supplier_id = #{supplierId} where id = #{id}")
    int updateSupplier(@Param("id") Integer id , @Param("supplierId") Integer supplierId);

}  
