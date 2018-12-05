package com.stosz.store.service;

import com.stosz.plat.utils.BeanUtil;
import com.stosz.store.ext.enums.InvoicingTypeEnum;
import com.stosz.store.ext.enums.StockStateEnum;
import com.stosz.store.ext.model.Invoicing;
import com.stosz.store.ext.model.PlanRecord;
import com.stosz.store.ext.model.SettlementMonth;
import com.stosz.store.ext.model.Stock;
import com.stosz.store.mapper.SettlementMonthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:SettlementMonthService
 * @Created Time:2017/11/23 17:56
 * @Update Time:
 */
@Service
public class SettlementMonthService {

    @Resource
    private SettlementMonthMapper settlementMonthMapper;
    @Autowired
    private StockService stockService;
    @Autowired
    private InvoicingService invoicingService;
    @Autowired
    private PlanRecordService planRecordService;

    /**
     * 每天计算一次商品月结信息
     *
     * @param
     */
    public void addSettlementMonth() {
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonth().getValue();
        int lastYear = LocalDateTime.now().plusMonths(1).getYear();
        int lastMonth = LocalDateTime.now().plusMonths(1).getMonth().getValue();
        PlanRecord planRecord = planRecordService.findOne(year, month);
        PlanRecord lastPlan = planRecordService.findOne(lastYear, lastMonth);
        Integer planId = planRecord.getId();

        Assert.notNull(planRecord, "无当月排程，请联系相关人员");
        Assert.isTrue("0".equals(planRecord.getState()), "排程不可用，请联系相关人员");

        List<Stock> stocks = stockService.queryQty(new Stock());
        for (Stock stock : stocks) {
            SettlementMonth settlementMonth = new SettlementMonth();
            BeanUtil.copy(stock, settlementMonth);
            settlementMonth.setId(null);

            settlementMonth.setPlanId(planId);

            List<SettlementMonth> settlementMonths = settlementMonthMapper.query(settlementMonth);
            if (settlementMonths.size() > 0) {
                /**月结已存在，修改部分数据**/
                settlementMonth = settlementMonths.get(0);
            } else {
                /**月结不存在，添加新数据**/
                Assert.notNull(lastPlan, "无上月排程数据请联系相关人员");
                settlementMonth.setPlanId(lastPlan.getId());
                List<SettlementMonth> settlementLastMonths = settlementMonthMapper.query(settlementMonth);
                Assert.isTrue(settlementLastMonths.size() == 1, "无上月月结数据请联系相关人员");
                settlementMonth.setBeginQty(settlementLastMonths.get(0).getEndQty());
                settlementMonth.setBeginAmount(settlementLastMonths.get(0).getEndAmount());
            }
            /**获取期末数量**/
            int inQty = getQty(settlementMonth, null, StockStateEnum.stock_in.ordinal());
            int outQty = getQty(settlementMonth, null, StockStateEnum.stock_out.ordinal());
            int endQty = settlementMonth.getBeginQty() + inQty - outQty;
            /**获取销售数量总和**/
            int outSellQty = getQty(settlementMonth, InvoicingTypeEnum.order.ordinal(), StockStateEnum.stock_out.ordinal());
            int inSellQty = getQty(settlementMonth, InvoicingTypeEnum.order.ordinal(), StockStateEnum.stock_in.ordinal());
            int sellQty = outSellQty - inSellQty;
            /**获取销售金额**/
            BigDecimal sellAmount = getAmount(settlementMonth, InvoicingTypeEnum.order.ordinal()).negate();
            /**获取采购数量总和**/
            int inPurchaseQty = getQty(settlementMonth, InvoicingTypeEnum.purchase.ordinal(), StockStateEnum.stock_in.ordinal());
            int outPurchaseQty = getQty(settlementMonth, InvoicingTypeEnum.purchase.ordinal(), StockStateEnum.stock_out.ordinal());
            int purchaseQty = inPurchaseQty - outPurchaseQty;
            /**获取采购金额**/
            BigDecimal purchaseAmount = getAmount(settlementMonth, InvoicingTypeEnum.purchase.ordinal());
            /**获取调出数量**/
            int transferOutQty = getQty(settlementMonth, InvoicingTypeEnum.transfer.ordinal(), StockStateEnum.stock_out.ordinal());
            /**获取调拨出库金额**/
            BigDecimal transferOutAmount = getOneAmount(settlementMonth, InvoicingTypeEnum.transfer.ordinal(), StockStateEnum.stock_out.ordinal());
            /**获取调入数量**/
            int transferInQty = getQty(settlementMonth, InvoicingTypeEnum.transfer.ordinal(), StockStateEnum.stock_in.ordinal());
            /**获取调入库金额**/
            BigDecimal transferInAmount = getOneAmount(settlementMonth, InvoicingTypeEnum.transfer.ordinal(), StockStateEnum.stock_in.ordinal());
           /* *//**获取其他出库数量**//*
            int otherOutQty = getQty(settlementMonth, InvoicingTypeEnum.other.ordinal(), StockStateEnum.stock_out.ordinal());
            *//**获取其他出库金额**//*
            BigDecimal otherOutAmount = getOneAmount(settlementMonth, InvoicingTypeEnum.other.ordinal(), StockStateEnum.stock_out.ordinal());
            *//**获取其他入库数量**//*
            int otherInQty = getQty(settlementMonth, InvoicingTypeEnum.other.ordinal(), StockStateEnum.stock_in.ordinal());
            *//**获取其他入库金额**//*
            BigDecimal otherInAmount = getOneAmount(settlementMonth, InvoicingTypeEnum.other.ordinal(), StockStateEnum.stock_in.ordinal());*/
            /**获取成本价**/
            BigDecimal amount = settlementMonth.getBeginAmount().add(purchaseAmount);
            int qty = settlementMonth.getBeginQty() + purchaseQty;
            BigDecimal cost = new BigDecimal(0);
            if (qty != 0) {
                cost = amount.divide(new BigDecimal(qty));
            }
            /**计算期末金额**/
            BigDecimal endAmount = cost.multiply(new BigDecimal(endQty));

            /**添加月结记录**/
            settlementMonth.setEndQty(endQty);
            settlementMonth.setEndAmount(endAmount);
            settlementMonth.setCost(cost);
            settlementMonth.setSellQty(sellQty);
            settlementMonth.setSellAmount(sellAmount);
            settlementMonth.setPurchaseQty(purchaseQty);
            settlementMonth.setPurchaseAmount(purchaseAmount);
            settlementMonth.setTransferInQty(transferInQty);
            settlementMonth.setTransferInAmount(transferInAmount);
            settlementMonth.setTransferOutQty(transferOutQty);
            settlementMonth.setTransferOutAmount(transferOutAmount);
       /*     settlementMonth.setOtherInQty(otherInQty);
            settlementMonth.setOtherInAmount(otherInAmount);
            settlementMonth.setOtherOutQty(otherOutQty);
            settlementMonth.setOtherOutAmount(otherOutAmount);*/

            insertOrUpdate(settlementMonth);
        }
    }

    /**
     * 插入仓库流水
     *
     * @param
     */
    public void insertOrUpdate(SettlementMonth settlementMonth) {

        Assert.notNull(settlementMonth.getWmsId(), "月结表联合主键查询，wmsId不能为空");
        Assert.notNull(settlementMonth.getPlanId(), "月结表联合主键查询，planId不能为空");
        Assert.notNull(settlementMonth.getDeptId(), "月结表联合主键查询，deptId不能为空");
        Assert.notNull(settlementMonth.getSku(), "月结表联合主键查询，sku不能为空");

        if (settlementMonth.getId() == null)
            settlementMonthMapper.insert(settlementMonth);
        else
            settlementMonthMapper.update(settlementMonth);
    }


    /**
     * 查询仓库流水
     *
     * @param settlementMonth
     */
    public SettlementMonth queryOne(SettlementMonth settlementMonth) {

        Assert.notNull(settlementMonth.getWmsId(), "月结表联合主键查询，wmsId不能为空");
        Assert.notNull(settlementMonth.getPlanId(), "月结表联合主键查询，planId不能为空");
        Assert.notNull(settlementMonth.getDeptId(), "月结表联合主键查询，deptId不能为空");
        Assert.notNull(settlementMonth.getSku(), "月结表联合主键查询，sku不能为空");

        List<SettlementMonth> settlementMonths = settlementMonthMapper.query(settlementMonth);
        SettlementMonth settlementMonthInfo = new SettlementMonth();
        if (settlementMonths.size() > 0) {
            settlementMonthInfo = settlementMonths.get(0);
        }
        return settlementMonthInfo;
    }

    /**
     * 进销流水的入库数减出库数
     **/
    private int getQty(SettlementMonth settlementMonth, Integer type, Integer state) {

        Assert.notNull(settlementMonth.getWmsId(), "月结过程中，仓库id不能为空");
        Assert.notNull(settlementMonth.getDeptId(), "月结过程中，部门id不能为空");
        Assert.notNull(settlementMonth.getSku(), "月结过程中，sku不能为空");
        Assert.notNull(settlementMonth.getPlanId(), "月结过程中，排程id不能为空");

        Invoicing invoicing = new Invoicing();
        int qty = 0;
        BeanUtil.copy(settlementMonth, invoicing);
        invoicing.setType(type);
        invoicing.setState(state);
        List<Invoicing> inInvoicings = invoicingService.query(invoicing);
        for (Invoicing inInvoicing : inInvoicings)
            qty = qty + inInvoicing.getQuantity();

        return qty;
    }

    /**
     * 获取进出总金额金额
     **/
    private BigDecimal getAmount(SettlementMonth settlementMonth, Integer type) {

        Assert.notNull(settlementMonth.getWmsId(), "月结过程中，仓库id不能为空");
        Assert.notNull(settlementMonth.getDeptId(), "月结过程中，部门id不能为空");
        Assert.notNull(settlementMonth.getSku(), "月结过程中，sku不能为空");
        Assert.notNull(settlementMonth.getPlanId(), "月结过程中，排程id不能为空");

        Invoicing invoicing = new Invoicing();
        BigDecimal inAmount = new BigDecimal(0);
        BigDecimal outAmount = new BigDecimal(0);
        BeanUtil.copy(settlementMonth, invoicing);
        invoicing.setType(type);
        invoicing.setState(StockStateEnum.stock_in.ordinal());
        List<Invoicing> inInvoicings = invoicingService.query(invoicing);
        for (Invoicing inInvoicing : inInvoicings)
            inAmount = inAmount.add(inInvoicing.getAmount());

        invoicing.setState(StockStateEnum.stock_out.ordinal());
        List<Invoicing> outInvoicings = invoicingService.query(invoicing);
        for (Invoicing outInvoicing : outInvoicings)
            outAmount = outAmount.add(outInvoicing.getAmount());

        return inAmount.subtract(outAmount);
    }

    /**
     * 获取单向金额金额
     **/
    private BigDecimal getOneAmount(SettlementMonth settlementMonth, Integer type, Integer state) {

        Assert.notNull(settlementMonth.getWmsId(), "月结过程中，仓库id不能为空");
        Assert.notNull(settlementMonth.getDeptId(), "月结过程中，部门id不能为空");
        Assert.notNull(settlementMonth.getSku(), "月结过程中，sku不能为空");
        Assert.notNull(settlementMonth.getPlanId(), "月结过程中，排程id不能为空");

        Invoicing invoicing = new Invoicing();
        BigDecimal amount = new BigDecimal(0);
        BeanUtil.copy(settlementMonth, invoicing);
        invoicing.setType(type);
        invoicing.setState(state);

        List<Invoicing> invoicings = invoicingService.query(invoicing);
        for (Invoicing inInvoicing : invoicings)
            amount = amount.add(inInvoicing.getAmount());

        return amount;
    }

    //获取当前时间点sku的成本价
    public BigDecimal getSkuCost(Integer wmsId, Integer deptId, String sku) {
        //先发起一次月结计算
        Integer planId = planRecordService.nowPlanId();
        SettlementMonth settlementMonth = new SettlementMonth();
        settlementMonth.setPlanId(planId);
        settlementMonth.setWmsId(wmsId);
        settlementMonth.setDeptId(deptId);
        settlementMonth.setSku(sku);
        SettlementMonth settlementMonth1 = queryOne(settlementMonth);
        BigDecimal cost = null;
        if (settlementMonth1 != null)
            cost = settlementMonth1.getCost();
        return cost;
    }
}
