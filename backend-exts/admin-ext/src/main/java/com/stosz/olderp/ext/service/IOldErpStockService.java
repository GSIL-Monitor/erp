package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpStock;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/8]
 */
public interface IOldErpStockService {

    String url = "/admin/remote/IOldErpStockService";
    /**
     * 根据限制条件和起始位置获取到对应的库存信息
     */
    List<OldErpStock> findByLimit(Integer limit, Integer start);

    List<OldErpStock> findByDate(Date startTime, Date endTime);

    List<OldErpStock> findStockByParam(OldErpStock oldErpStock);

    Integer getStockByUnique(Integer productId, Integer departmentId, Integer zoneId);


    List<OldErpStock> findOrderAtByParam(OldErpStock oldErpStock);

    LocalDateTime getOrderAtByUnique(Integer productId, Integer departmentId, Integer zoneId);

    List<OldErpStock> findHasStock(OldErpStock oldErpStock);

    List<OldErpStock> findHasOrderAtByProductId(Integer productId);

    /**
     * 获取最后订单记录总数
     */
    Integer countOrderAt();

    /**
     * 获取该产品有库存的数量
     */
    Integer getHasStockCountByProductId(Integer productId);

    /**
     * 获取该产品有订单的数量
     */
    Integer getHasOrderCountByProductId(Integer productId);


}  
