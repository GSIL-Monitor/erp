package com.stosz.store.service;

import com.google.common.collect.Lists;
import com.stosz.plat.utils.BeanUtil;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.plat.wms.model.WMSResponse;
import com.stosz.store.ext.dto.request.*;
import com.stosz.store.ext.dto.response.PurchaseQueryRes;
import com.stosz.store.ext.dto.response.StockChangeRes;
import com.stosz.store.ext.dto.response.StockRes;
import com.stosz.store.ext.enums.InvoicingTypeEnum;
import com.stosz.store.ext.enums.StockStateEnum;
import com.stosz.store.ext.model.ChangeStock;
import com.stosz.store.ext.model.Stock;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 15:33
 * @Update Time:
 */

@Component
public class StockServiceImpl implements IStockService {

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferItemService transferItemService;

    @Autowired
    private PlanRecordService planRecordService;

    @Autowired
    private SettlementMonthService settlementMonthService;

    @Autowired
    private WmsService wmsService;

    @Autowired
    private StockService stockService;

    private Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);


    @Override
    public List<StockRes> queryQty(QueryQtyReq queryQtyReq) {

        Stock stock = new Stock();
        BeanUtils.copyProperties(queryQtyReq, stock);
        List<StockRes> stockResList = new ArrayList<>();
        List<Stock> stocks = stockService.queryQty(stock);
        for (Stock stock1 : stocks) {
            StockRes stockRes = new StockRes();
            BeanUtils.copyProperties(stock1, stockRes);
            stockResList.add(stockRes);
        }
        return stockResList;
    }

    @Override
    public List<StockChangeRes> orderChangeStockQty(List<StockChangeReq> stockChangeReqs) {

        List<ChangeStock> changeStocks = new ArrayList<>();
        for (StockChangeReq stockChangeReq : stockChangeReqs) {
            logger.info("订单信息orderChangeReq:{}操作库存", stockChangeReq.toString());
            validData(stockChangeReq, "订单操作库存");
            int changeQty = stockChangeReq.getChangeQty();
            ChangeStock changeStock = getChangeStock(stockChangeReq);
            switch (stockChangeReq.getType()) {
                case "order_undelivered":
                    changeStock.setUsableChangeQty(-changeQty);
                    changeStock.setOccupyChangeQty(changeQty);
                    changeStock.setRecord(false);
                    break;
                case "order_delivered":
                    validData1(stockChangeReq, "订单操作库存");
                    changeStock.setInstockChangeQty(-changeQty);
                    changeStock.setOccupyChangeQty(-changeQty);
                    changeStock.setState(StockStateEnum.stock_out.ordinal());
                    changeStock.setRecord(true);
                    break;
                case "order_cancel":
                    changeStock.setUsableChangeQty(changeQty);
                    changeStock.setOccupyChangeQty(-changeQty);
                    changeStock.setRecord(false);
                    break;
                case "return_apply":
                    break;
                case "return_apply_fail":
                    break;
                case "order_in_stock":
                    validData1(stockChangeReq, "订单操作库存");
                    changeStock.setInstockChangeQty(changeQty);
                    changeStock.setUsableChangeQty(changeQty);
                    changeStock.setState(StockStateEnum.stock_in.ordinal());
                    changeStock.setRecord(true);
                    break;
                default:
                    Assert.isTrue(false, "不存在该" + stockChangeReq.getType() + "操作类型，请核查");
            }

            changeStock.setType(InvoicingTypeEnum.order.ordinal());
            changeStocks.add(changeStock);
        }
        return stockService.updateStockDatas(changeStocks);
    }

    @Override
    public List<StockChangeRes> purchaseChangeStockQty(List<StockChangeReq> stockChangeReqs) {

        List<ChangeStock> changeStocks = new ArrayList<>();
        for (StockChangeReq stockChangeReq : stockChangeReqs) {

            logger.info("采购信息purchaseChangeStockQty Req:{}操作库存", stockChangeReq.toString());
            validData(stockChangeReq, "采购操作库存");
            int changeQty = stockChangeReq.getChangeQty();
            ChangeStock changeStock = getChangeStock(stockChangeReq);
            switch (stockChangeReq.getType()) {
                case "ready_purchase":
                    changeStock.setIntransitChangeQty(changeQty);
                    changeStock.setRecord(false);
                    break;
                case "purchase_cancel":
                    changeStock.setIntransitChangeQty(-changeQty);
                    changeStock.setRecord(false);
                    break;
                case "purchase_in_stock":
                    validData1(stockChangeReq, "采购操作库存");
                    changeStock.setInstockChangeQty(changeQty);
                    changeStock.setUsableChangeQty(changeQty);
                    changeStock.setIntransitChangeQty(-changeQty);
                    changeStock.setState(StockStateEnum.stock_in.ordinal());
                    changeStock.setRecord(true);
                    break;
                case "purchase_lock_stock":
                    validData1(stockChangeReq, "采购操作库存");
                    changeStock.setInstockChangeQty(changeQty);
                    changeStock.setOccupyChangeQty(changeQty);
                    changeStock.setIntransitChangeQty(-changeQty);
                    changeStock.setState(StockStateEnum.stock_in.ordinal());
                    changeStock.setRecord(true);
                    break;
                case "intransit_minus":
                    changeStock.setIntransitChangeQty(-changeQty);
                    changeStock.setRecord(false);
                    break;
                case "intransit_add":
                    changeStock.setIntransitChangeQty(changeQty);
                    changeStock.setRecord(false);
                    break;
                case "return_apply":
                    changeStock.setUsableChangeQty(-changeQty);
                    changeStock.setOccupyChangeQty(changeQty);
                    changeStock.setRecord(false);
                    break;
                case "return_final":
                    validData1(stockChangeReq, "采购操作库存");
                    changeStock.setInstockChangeQty(-changeQty);
                    changeStock.setOccupyChangeQty(-changeQty);
                    changeStock.setState(StockStateEnum.stock_out.ordinal());
                    changeStock.setRecord(true);
                    break;
                case "return_fail":
                    changeStock.setUsableChangeQty(changeQty);
                    changeStock.setOccupyChangeQty(-changeQty);
                    changeStock.setRecord(false);
                    break;
            }
            changeStock.setType(InvoicingTypeEnum.purchase.ordinal());
            changeStocks.add(changeStock);
        }
        return stockService.updateStockDatas(changeStocks);
    }

    @Override
    public List<StockChangeRes> takeStockChangeStockQty(List<StockChangeReq> stockChangeReqs) {

        List<ChangeStock> changeStocks = new ArrayList<>();
        for (StockChangeReq stockChangeReq : stockChangeReqs) {
            logger.info("盘点单:{}操作库存", stockChangeReq.toString());
            validData(stockChangeReq, "转寄仓操作库存");
            int changeQty = stockChangeReq.getChangeQty();
            ChangeStock changeStock = getChangeStock(stockChangeReq);
            switch (stockChangeReq.getType()) {
                case "create_takeStock":
                    changeStock.setUsableChangeQty(-changeQty);
                    changeStock.setOccupyChangeQty(changeQty);
                    changeStock.setRecord(false);
                    break;
                case "takeStock_apply_fail":
                    changeStock.setUsableChangeQty(changeQty);
                    changeStock.setOccupyChangeQty(-changeQty);
                    changeStock.setRecord(false);
                    break;
                case "takeStock_apply_pass":
                    validData1(stockChangeReq, "盘点操作库存");
                    changeStock.setInstockChangeQty(-changeQty);
                    changeStock.setOccupyChangeQty(-changeQty);
                    changeStock.setState(StockStateEnum.stock_out.ordinal());
                    changeStock.setRecord(true);
                    break;
                default:
                    Assert.isTrue(false, "不存在该" + stockChangeReq.getType() + "操作类型，请核查");
            }

            changeStock.setType(InvoicingTypeEnum.takeStock.ordinal());
            changeStocks.add(changeStock);
        }
        return stockService.updateStockDatas(changeStocks);
    }

    @Override
    public List<StockChangeRes> discardChangeStockQty(List<StockChangeReq> stockChangeReqs) {

        List<ChangeStock> changeStocks = new ArrayList<>();
        for (StockChangeReq stockChangeReq : stockChangeReqs) {
            logger.info("转寄仓单据信息discardChangeStockQty Req:{}操作库存", stockChangeReq.toString());
            validData(stockChangeReq, "转寄仓操作库存");
            int changeQty = stockChangeReq.getChangeQty();
            ChangeStock changeStock = getChangeStock(stockChangeReq);

            switch (stockChangeReq.getType()) {
                case "create_discard":
                    changeStock.setUsableChangeQty(-changeQty);
                    changeStock.setOccupyChangeQty(changeQty);
                    changeStock.setRecord(false);
                    break;
                case "discard_apply_fail":
                    changeStock.setUsableChangeQty(changeQty);
                    changeStock.setOccupyChangeQty(-changeQty);
                    changeStock.setRecord(false);
                    break;
                case "discard_apply_pass":
                    validData1(stockChangeReq, "报废操作库存");
                    changeStock.setInstockChangeQty(-changeQty);
                    changeStock.setOccupyChangeQty(-changeQty);
                    changeStock.setState(StockStateEnum.stock_out.ordinal());
                    changeStock.setRecord(true);
                    break;
                default:
                    Assert.isTrue(false, "不存在该" + stockChangeReq.getType() + "操作类型，请核查");
            }
            changeStock.setType(InvoicingTypeEnum.discard.ordinal());
            changeStocks.add(changeStock);
        }
        return stockService.updateStockDatas(changeStocks);
    }


    @Override
    public ChangeStock transferChangeStockQty(StockChangeReq stockChangeReq) {

        logger.info("调拨信息 transferChangeStockQty Req:{}操作库存", stockChangeReq.toString());
        validData(stockChangeReq, "调拨操作库存");
        int changeQty = stockChangeReq.getChangeQty();
        ChangeStock changeStock = getChangeStock(stockChangeReq);
        switch (stockChangeReq.getType()) {
            case "transfer_apply":
                changeStock.setUsableChangeQty(-changeQty);
                changeStock.setOccupyChangeQty(changeQty);
                changeStock.setRecord(false);
                break;
            case "transfer_cancel":
                changeStock.setUsableChangeQty(changeQty);
                changeStock.setOccupyChangeQty(-changeQty);
                changeStock.setRecord(false);
                break;
            case "transfer_out_stock":
                validData1(stockChangeReq, "调拨操作库存");
                changeStock.setInstockChangeQty(-changeQty);
                changeStock.setOccupyChangeQty(-changeQty);
                changeStock.setState(StockStateEnum.stock_out.ordinal());
                changeStock.setRecord(true);
                break;
            case "transfer_in_stock":
                validData1(stockChangeReq, "调拨操作库存");
                changeStock.setInstockChangeQty(changeQty);
                changeStock.setUsableChangeQty(changeQty);
                changeStock.setIntransitChangeQty(-changeQty);
                changeStock.setState(StockStateEnum.stock_in.ordinal());
                changeStock.setRecord(true);
                break;
            case "transfer_intransit":
                changeStock.setIntransitChangeQty(changeQty);
                changeStock.setRecord(false);
                break;
            case "one_store_in":
                validData1(stockChangeReq, "调拨操作库存");
                changeStock.setInstockChangeQty(changeQty);
                changeStock.setUsableChangeQty(changeQty);
                changeStock.setState(StockStateEnum.stock_in.ordinal());
                changeStock.setRecord(true);
                break;
            default:
                Assert.isTrue(false, "不存在该" + stockChangeReq.getType() + "操作类型，请核查");
        }

        changeStock.setType(InvoicingTypeEnum.transfer.ordinal());
        return changeStock;
    }

    @Override
    public WMSResponse wmsTransferRequest(WmsReq wmsReq) {
        WMSResponse wmsResponse = new WMSResponse();
        try {
            String resultData = wmsReq.getResult_data();
            WmsTransferReq wmsTransferReq = new WmsTransferReq();
            WmsTransferReq wmsTransfer = JsonUtil.readValue(resultData, wmsTransferReq.getClass());

            String tranNo = wmsTransfer.getTransferNo();
            String type = wmsTransfer.getType();
            if (StringUtils.isBlank(tranNo)) {
                wmsResponse.setIsSuccess("fail");
                wmsResponse.setBody("transferNo不能为空");
                return wmsResponse;
            }
            Integer tranId = Integer.parseInt(tranNo);
            Transfer transfer = transferService.findById(tranId);
            if (transfer == null) {
                wmsResponse.setIsSuccess("fail");
                wmsResponse.setBody("无调拨单[" + tranId + "]的数据");
                return wmsResponse;
            }
            List<WmsReqSku> data = wmsTransfer.getData();

            List<TransferItem> transferItems = transferItemService.findByTranId(tranId);
            if (data.size() != transferItems.size()) {
                wmsResponse.setIsSuccess("fail");
                wmsResponse.setBody("data不能为空");
                return wmsResponse;
            }
            List<TransferItem> transferItemsUpdate = new LinkedList<>();

            for (WmsReqSku sku : data) {

                TransferItem transferItem = transferItemService.findBySkuAndTranId(tranId, sku.getSku());
                if (transferItem == null) {
                    wmsResponse.setIsSuccess("fail");
                    wmsResponse.setBody("未查询到sku[" + sku.getSku() + "]信息");
                    return wmsResponse;
                }
                if ("1".equals(type))
                    transferItem.setFactOutQty(sku.getOutSkuReceived());
                else
                    transferItem.setFactInQty(sku.getOutSkuReceived());

                transferItem.setUpdateAt(LocalDateTime.now());
                transferItemsUpdate.add(transferItem);
            }
            if ("1".equals(type)) {
                //调拨出库接口
                transferService.transferOutStock(tranId, transferItemsUpdate);
            } else {
                //调拨入库接口
                transferService.transferInStock(tranId, transferItemsUpdate);
            }
        } catch (Exception e) {
            wmsResponse.setIsSuccess("fail");
            wmsResponse.setBody("系统操作异常");
        }

        return wmsResponse;
    }

    private void validData(StockChangeReq stockChangeReq, String orderType) {

        Assert.notNull(stockChangeReq.getVoucherNo(), orderType + "无效，无单据编号！");
        String s = orderType + "单据编号：" + stockChangeReq.getVoucherNo() + "无效,";
        Assert.notNull(stockChangeReq.getWmsId(), s + "仓库id不允许为空！");
        Assert.notNull(stockChangeReq.getDeptId(), s + "部门id不允许为空！");
        Assert.notNull(stockChangeReq.getDeptName(), s + "部门名称不允许为空！");
//        Assert.notNull(stockChangeReq.getDeptNo(), s + "部门编码不允许为空！");
        Assert.notNull(stockChangeReq.getSku(), s + "suk不允许为空！");
        Assert.notNull(stockChangeReq.getType(), s + "type不允许为空！");
        Assert.notNull(stockChangeReq.getSpu(), s + "spu不允许为空！");
        Assert.isTrue(stockChangeReq.getChangeQty() >= 0, s + "数量改变必须大于0");
    }

    private void validData1(StockChangeReq stockChangeReq, String orderType) {
        String s = orderType + "单据编号：" + stockChangeReq.getVoucherNo() + "无效,";
        Assert.notNull(stockChangeReq.getAmount(), s + "金额为空无法记进销存！");
        Assert.notNull(stockChangeReq.getChangeAt(), s + "单据发生时间不能为空！");
    }

    private ChangeStock getChangeStock(StockChangeReq stockChangeReq) {
        ChangeStock changeStock = new ChangeStock();
        BeanUtils.copyProperties(stockChangeReq, changeStock);
        changeStock.setQuantity(stockChangeReq.getChangeQty());
        return changeStock;
    }

    @Override
    public List<PurchaseQueryRes> purchaseQuery(List<QueryQtyReq> queryQtyReqs) {

        LinkedList<PurchaseQueryRes> purchaseQueryResList = Lists.newLinkedList();
        for (QueryQtyReq queryQtyReq : queryQtyReqs) {

            Integer wmsId = queryQtyReq.getWmsId();
            Integer deptId = queryQtyReq.getDeptId();
            String departmentName = queryQtyReq.getDepartmentName();
            String sku = queryQtyReq.getSku();
            Assert.notNull(wmsId, "仓库id不能为空");
            Assert.notNull(deptId, "部门id不能为空");
            Assert.notNull(sku, "sku不能为空");

            List<Stock> stocks = stockService.queryQty(new Stock(wmsId, deptId, sku));
            int usableQty = 0;
            Integer otherQty = 0;
            if (stocks.size() > 0)
                usableQty = stocks.get(0).getUsableQty();
            Integer otherQty1 = stockService.getOtherQty(wmsId, deptId, sku);
            if (otherQty != null)
                otherQty = otherQty1;
            //获取当前时间下sku的单价
            BigDecimal cost = settlementMonthService.getSkuCost(wmsId, deptId, sku);
            if (usableQty > 0 || (otherQty != null && otherQty > 0)) {
                PurchaseQueryRes purchaseQueryRes = new PurchaseQueryRes();
                BeanUtil.copy(queryQtyReq, purchaseQueryRes);
                purchaseQueryRes.setDeptId(deptId);
                purchaseQueryRes.setDepartmentName(departmentName);
                purchaseQueryRes.setUsableQty(usableQty);
                purchaseQueryRes.setOtherUsableQty(otherQty);
                purchaseQueryRes.setPrice(cost);
                purchaseQueryResList.add(purchaseQueryRes);
            }
        }
        return purchaseQueryResList;
    }
}

