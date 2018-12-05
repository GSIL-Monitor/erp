package com.stosz.store.ext.service;


import com.stosz.plat.wms.model.WMSResponse;
import com.stosz.store.ext.dto.request.QueryQtyReq;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.dto.request.WmsReq;
import com.stosz.store.ext.dto.response.PurchaseQueryRes;
import com.stosz.store.ext.dto.response.StockChangeRes;
import com.stosz.store.ext.dto.response.StockRes;
import com.stosz.store.ext.model.ChangeStock;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:仓库模块对外接口
 * @Created Time:2017/11/23 14:47
 */
public interface IStockService {

    String url = "/store/remote/IStockService";

    /**
     * 仓库模块查询接口 wmsId deptId spu sku
     *
     * @param queryQtyReq
     */
    List<StockRes> queryQty(QueryQtyReq queryQtyReq);

    /**
     * 订单模块更新仓库数据接口 wmsId deptId spu sku num type
     *
     * @param stockChangeReqs
     */
    List<StockChangeRes> orderChangeStockQty(List<StockChangeReq> stockChangeReqs);

    /**
     * 采购模块更新仓库数据接口 wmsId deptId spu sku num type
     *
     * @param stockChangeReqs
     */
    List<StockChangeRes> purchaseChangeStockQty(List<StockChangeReq> stockChangeReqs);

    /**
     * 转寄仓盘点更新仓库库存数据接口 wmsId deptId spu sku num type
     *
     * @param stockChangeReqs
     */
    List<StockChangeRes> takeStockChangeStockQty(List<StockChangeReq> stockChangeReqs);

    /**
     * 报废更新仓库库存数据接口 wmsId deptId spu sku num type
     *
     * @param stockChangeReqs
     */
    List<StockChangeRes> discardChangeStockQty(List<StockChangeReq> stockChangeReqs);

    /**
     * 调拨模块更新仓库数据接口 wmsId deptId spu sku num type
     *
     * @param stockChangeReq
     */
    ChangeStock transferChangeStockQty(StockChangeReq stockChangeReq);

    /**
     * wms出入库调用接口 wmsId deptId spu sku num type
     *
     * @param wmsTransferReq
     */
    WMSResponse wmsTransferRequest(WmsReq wmsTransferReq);

    /**
     * 仓库模块查询接口 wmsId deptId  sku
     *
     * @param queryQtyReqs
     */
    List<PurchaseQueryRes> purchaseQuery(List<QueryQtyReq> queryQtyReqs);

}
