package com.stosz.store.ext.service;


import com.stosz.order.ext.dto.TransitStockDTO;
import com.stosz.order.ext.dto.WmsResponse;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.store.ext.dto.response.MatchPackRes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓接口
 * @Created Time:2017/11/23 14:47
 * @Update Time:
 */
public interface ITransitStockService {

    String url = "/store/remote/ITransitStockService";

    /**
     * 业务说明： 转寄仓配货
     * 1，拿到单个订单项，比较 sku， 数量；
     * 2，所有的订单项  order_id_old 相同，表示一个包裹；
     * 满足了以上两个条件的，全单匹配了； 匹配到的数据更新 order_id_new 更新为转寄已匹配状态
     * <p>
     * return
     * <p>
     * order_id_old
     * tacking_no_old
     * stock_name
     * storage_local 库位
     */
    MatchPackRes occupyStockQty(Integer warehouseId, Integer dept, String deptName, List<OrdersItem> orderItemsList, Integer orderId, LocalDateTime localDateTime);

    /**
     * RMA 推送转寄仓入库
     */
    WmsResponse notifyStockTakeGoods(List<TransitStockDTO> transitStocks);

    /**
     * RMA 取消退货申请
     */
    WmsResponse notifyStockCancel(Integer rmaId);
}
