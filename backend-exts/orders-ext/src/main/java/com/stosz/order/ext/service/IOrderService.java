package com.stosz.order.ext.service;

import com.stosz.order.ext.dto.*;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.order.ext.model.CombOrderItem;

import java.util.List;
import java.util.Map;

/**
 * @auther carter
 * create time    2017-11-09
 * 对外提供的接口,可以在这里定义
 */
public interface IOrderService {

    String url = "/orders/remote/IOrderService";

    /**
     * 业务说明： 获取sku需求
     *
     * @param deptId
     * @param sku
     * @return
     */
    OrderRequiredResponse pullOrderReuired(Integer deptId, String sku);

    /**
     * 业务说明： 测试
     *
     * @param a 参数1
     * @param b 参数2
     * @return 结果
     */
    @Deprecated
    default int test(int a, int b) {
        return 0;
    }


    /**
     * 业务说明： 根据订单单号查询订单信息 转寄仓
     *
     * @param orderIds 订单号
     * @return 订单号，订单状态
     */
    List<TransitOrderDTO> queryOrdersByOrderIds(List<String> orderIds);


    /**
     * 业务说明： 根据订单单号查询订单信息 (提供给物流重新刷一遍数据)
     *
     * @param orderIds 订单号
     * @return 推送给物流的订单信息，订单状态
     */
    Map<Integer, List<CombOrderItem>> queryOrderByOrderIds(List<String> orderIds);

    /**
     * 业务说明： 修改订单状态
     *
     * @param orderId 订单号id
     * @param status  转寄拣货盘亏
     * @return 按数据库成功返回1，失败0
     */
    WmsResponse transferCancel(Long orderId, OrderEventEnum status);

    /**
     * 业务说明： 修改订单状态（转寄仓已发货）
     *
     * @param orderId     订单号id
     * @param trackingNo  运单号
     * @param logisticsId 物流id
     * @return 按数据库成功返回1，失败0
     */
    WmsResponse updateOrderStatusByTransit(String orderId, String trackingNo, String logisticsId, String logisticsName);

    /**
     * 业务说明：转寄仓入库完成 修改RMA状态为已收货
     *
     * @param rmaId           rma申请单号
     * @param storageLocation 库位
     * @param inStorageNo     转寄仓ID
     * @return 按数据库成功返回1，失败0
     */
    WmsResponse updateOrderRmaStatus(Integer rmaId, String storageLocation, Integer inStorageNo);

    /**
     * 业务说明： 取消订单
     *
     * @param OrderId 订单号id
     * @return 按数据库成功返回1，失败0
     */
    @Deprecated
    Integer orderCancel(Long OrderId);


    /**
     * 业务说明：从订单这边判断，得到采购入库的货权归属
     *
     * @param wmsId                 仓库id
     * @param sku                   商品sku
     * @param purchaseQty           购买的数量
     * @param deptMaxPurchaseQtyMap 部门对应的采购需求数量   k= 部门id, v=本次采购的最大数
     * @return
     */
    List<DeptAssignQtyDto> getDeptAssignQtyDetail(Integer wmsId, String sku, Integer purchaseQty, Map<Integer, Integer> deptMaxPurchaseQtyMap);

    /**
     * 回写订单表中的物流信息  物流  add by liushilei
     *
     * @param transportRequest
     * @return
     */
    TransportResponse updateOrderLogisticsInfo(TransportRequest transportRequest);


    /**
     * 业务说明： 修改订单状态（拒收 签收...） 物流轨迹抓取到物流状态（如果是特殊节点的状态，需要更新订单状态）  add by liushilei
     *
     * @param transportDTO
     * @return 按数据库成功返回1，失败0
     */
    TransportResponse updateOrderStatus(TransportDTO transportDTO);


    /**
     * 业务说明： 重新指派物流回调接口
     *
     * @param transportRequest
     * @return
     */
    TransportResponse reAssignmentLogistics(TransportRequest transportRequest);

    /**
     * 采购获取获取部门分配sku流水
     *
     * @param purchaseItemRq
     * @return
     */
    List<PurchaseItemDTO> getPurchaseItem(List<PurchaseItemRq> purchaseItemRq);

}
