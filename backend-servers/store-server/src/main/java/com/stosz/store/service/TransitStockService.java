package com.stosz.store.service;

import com.google.common.collect.Lists;
import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.dto.TransitItemDTO;
import com.stosz.order.ext.dto.TransitOrderDTO;
import com.stosz.order.ext.dto.TransitStockDTO;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.dto.response.MatchPackRes;
import com.stosz.store.ext.enums.OrderHandleEnum;
import com.stosz.store.ext.enums.TransitStateEnum;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.model.TransitDetail;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.mapper.TransitStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓库
 * @Created Time:2017/11/23 15:49
 * @Update Time:
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class TransitStockService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private TransitStockMapper mapper;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private TransitDetailService transitDetailService;

    @Autowired
    private TransitItemService transitItemService;

    @Autowired
    private StockServiceImpl stockServiceImpl;

    @Resource
    private FsmProxyService<TransitStock> transitStockFsmProxyService;

    /**
     * 保存订单信息到转寄仓表
     *
     * @param
     * @param
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(TransitStockDTO dto) {
        TransitStock stock = new TransitStock();
        BeanUtils.copyProperties(dto, stock);
        stock.setState(TransitStateEnum.wait_inTransit.name());
        int update = this.updateByRmaId(stock);

        if (update > 0) {
            transitItemService.updateTrack(stock);
            return;
        }
        this.insert(stock);
        List<TransitItem> saveItemList = new ArrayList<>();
        List<TransitItemDTO> transitItemList = dto.getTransitItemList();
        for (TransitItemDTO itemDTO : transitItemList) {
            TransitItem item = new TransitItem();
            BeanUtils.copyProperties(itemDTO, item);
            item.setTransitId(stock.getId());
            item.setRmaId(stock.getRmaId());
            item.setDeptName(stock.getDeptName());
            saveItemList.add(item);
        }
        transitItemService.insertBat(saveItemList);
    }

    private int updateByRmaId(TransitStock stock) {
        return mapper.updateByRmaId(stock);
    }

    /**
     * 添加
     *
     * @param stock
     */
    public Integer insert(TransitStock stock) {
        stock.setUpdateAt(LocalDateTime.now());
        stock.setCreateAt(LocalDateTime.now());
        return mapper.insert(stock);
    }

    /**
     * 修改状态
     *
     * @param stock
     */
    public Integer update(TransitStock stock) {
        stock.setUpdateAt(LocalDateTime.now());
        this.addTransitDetail(stock);
        return mapper.update(stock);
    }

    /**
     * 根据id更新转寄库存状态和新订单号
     *
     * @param stock
     */
    public Integer updateTransitStateById(TransitStock stock) {
        logger.debug("修改转寄库存状态：{}", stock);
        int code = mapper.updateTransitStateById(stock);
        if (code < 1) {
            logger.error("result code:{} 插入{}失败", code, stock);
        }
        logger.info("result code:{} 插入{}成功！", code, stock);
        this.addTransitDetail(stock);
        return code;
    }

    /**
     * 根据旧运单号查询
     *
     * @param
     */
    public TransitStock findByTrackNoOld(String trackNoOld) {
        return mapper.getByTrackNoOld(trackNoOld);
    }

    /**
     * 根据新订单号查询
     *
     * @param
     */
    public TransitStock findByOrdersNew(String OrdersNew) {
        return mapper.getByOrdersNew(OrdersNew);
    }

    /**
     * 查询
     *
     * @param
     */
    public TransitStock find(TransitStock stock) {
        return mapper.find(stock).get(0);
    }

    /**
     * 搜索查询
     *
     * @param
     */
    public RestResult findList(TransitStock stock) {
        RestResult rs = new RestResult();
        int count = mapper.count(stock);
        rs.setTotal(count);
        if (count == 0) {
            return rs;
        }
        List<TransitStock> lst = mapper.find(stock);

        this.fillOrderInfo(lst);

        rs.setItem(lst);
        rs.setDesc("查询成功");
        return rs;
    }

    /**
     * 根据条件导出excel
     *
     * @param
     */
    public List<TransitStock> getTransitList(TransitStock transitParam) {
        List<TransitStock> stockList = mapper.getTransitList(transitParam);
        this.fillOrderInfo(stockList);
        return stockList;
    }


    /**
     * 根据调拨单id 关联查询
     *
     * @param
     */
    public List<TransitStock> getTransitByTransferId(Integer transferId) {
        List<TransitStock> stockList = mapper.getTransitByTransferId(transferId);
        this.fillOrderInfo(stockList);
        return stockList;
    }

    /**
     * 匹配查询sku数量相等，入库状态，库存时间最长排序，订单id List
     *
     * @param
     */
    public List<MatchPackRes> getMatchPackList(Integer skuCount, Integer warehouseId, Integer dept) {
        return mapper.getMatchPackList(skuCount, TransitStateEnum.inTransit.name(), warehouseId, dept);
    }

    /**
     * 填充订单信息
     *
     * @param lst
     */
    public void fillOrderInfo(List<TransitStock> lst) {
        List<String> orderIds = Lists.newLinkedList();
        for (TransitStock ts : lst) {
            if (null != ts.getOrderIdNew()) {
                orderIds.add(ts.getOrderIdNew().toString());
            }
            if (null != ts.getOrderIdOld()) {
                orderIds.add(ts.getOrderIdOld().toString());
            }
        }
        this.getOrdersByIds(lst, orderIds);

    }

    /**
     * 根据订单ids 查询订单中心信息
     */
    private void getOrdersByIds(List<TransitStock> lst, List<String> orderIds) {
        List<TransitOrderDTO> ordersList = null;
        try {
            ordersList = orderService.queryOrdersByOrderIds(orderIds);
        } catch (Exception e) {
            logger.error("订单数据异常，请检查订单系统数据或者网络");
        }
        if (CollectionUtils.isNotNullAndEmpty(ordersList)) {
            for (TransitOrderDTO ord : ordersList) {
                for (TransitStock ts : lst) {
                    if (Objects.equals(ord.getOrderId(), ts.getOrderIdOld())) {
                        ts.setZoneName(ord.getZoneName());
                        ts.setLogisticsNameOld(ord.getLogisticsName());
                        ts.setSpuList(ord.getSpuList());
                        break;
                    }
                    if (Objects.equals(ord.getOrderId(), ts.getOrderIdNew())) {
                        ts.setLogisticsNameNew(ord.getLogisticsName());
                        ts.setTrackingStatus(ord.getTrackingStatusShow());
                        ts.setTrackingTime(ord.getDeliveryTime());  //发货时间

                        ts.setZoneName(ord.getZoneName());
                        ts.setCustomerName(ord.getCustomerName());
                        ts.setMobile(ord.getMobile());
                        ts.setOrderAmount(ord.getOrderAmount());
                        ts.setGoodQty(ord.getGoodQty());
                        ts.setCustomerMessage(ord.getCustomerMessage());
                        ts.setAddress(ord.getAddress());
                        ts.setZipCode(ord.getZipCode());
                        ts.setProvince(ord.getProvince());
                        ts.setCustomerMessage(ord.getCity());
                        ts.setArea(ord.getArea());
                        break;
                    }
                }
            }
        }
    }


    /**
     * 记录流水
     *
     * @param stock
     */
    private void addTransitDetail(TransitStock stock) {
        TransitDetail detail = new TransitDetail();
        BeanUtils.copyProperties(stock, detail);
        transitDetailService.insert(detail);
    }

    /**
     * 订单匹配包裹后操作 1、锁库 2、更新转寄仓匹配状态
     *
     * @param warehouseId
     * @param dept
     * @param deptName
     * @param orderItemsList
     * @param pack
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void MatchEvent(Integer warehouseId, Integer dept, String deptName, List<OrdersItem> orderItemsList, MatchPackRes pack, Integer orderId, LocalDateTime orderTime) {
        //1、锁库
        List<StockChangeReq> reqList = new ArrayList<>();
        for (OrdersItem ordersItem : orderItemsList) {
            StockChangeReq req = new StockChangeReq();
            req.setWmsId(warehouseId);
            req.setDeptId(dept);
            req.setDeptName(deptName);
            req.setSku(ordersItem.getSku());
            req.setChangeQty(ordersItem.getQty());
            req.setType(OrderHandleEnum.order_undelivered.name());
            req.setVoucherNo(orderId.toString());
            req.setSpu(ordersItem.getSpu());
            reqList.add(req);
        }
        stockServiceImpl.orderChangeStockQty(reqList);

        //2、更新转寄仓匹配状态、下单时间
        TransitStock stock = new TransitStock();
        stock.setId(pack.getId());
        stock.setOrderIdNew(orderId.longValue());
        stock.setState(TransitStateEnum.matched.name());
        stock.setUpdateAt(LocalDateTime.now());
        stock.setOrderAt(orderTime);
        this.updateTransitStateById(stock);

    }

    /**
     * 是否已入库
     *
     * @param track
     * @return
     */
    public boolean isInserted(String track) {
        if (mapper.countByState(track, TransitStateEnum.inTransit.name()) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否已调拨发货导入
     *
     * @param track
     * @return
     */
    public boolean isTransferDeliver(String track) {
        if (mapper.countByState(track, TransitStateEnum.delivered.name()) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否已发货导入
     *
     * @param
     * @param
     * @return
     */
    public boolean isImportDeliver(String orderNew, String trackNew) {
        if (mapper.countByOrderNew(orderNew, trackNew) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 入库
     *
     * @param transitStock
     */
    public void inStock(TransitStock transitStock) {
        transitStock.setCreateAt(LocalDateTime.now());
        transitStockFsmProxyService.processEvent(transitStock, TransitStockEventEnum.inStock, TransitStockEventEnum.inStock.display());
    }

    /**
     * 拣货导出
     *
     * @param transitStock
     */
    public void exportPack(TransitStock transitStock) {
        transitStockFsmProxyService.processEvent(transitStock, TransitStockEventEnum.exportPack, TransitStockEventEnum.exportPack.display());
    }

    /**
     * 发货导入
     *
     * @param transitStock
     */
    public void importDeliver(TransitStock transitStock) {
        transitStockFsmProxyService.processEvent(transitStock, TransitStockEventEnum.importDeliver, TransitStockEventEnum.importDeliver.display());
    }

    /**
     * 统计调拨入库数
     *
     * @param itemList
     * @return
     */
    public int countByInStock(List<TransferItem> itemList) {
        return mapper.countByInStock(itemList, TransitStateEnum.inTransit.name());
    }

    public int delByRmaId(Integer rmaId) {
        return mapper.delByRmaId(rmaId, TransitStateEnum.wait_inTransit.name());
    }
}
