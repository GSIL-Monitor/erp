package com.stosz.store.fsm.invalid;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.CalculateTypeEnum;
import com.stosz.store.ext.enums.TransitHandleEnum;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.Invalid;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.fsm.calcUtils.CalcUtils;
import com.stosz.store.service.InvalidService;
import com.stosz.store.service.TransitItemService;
import com.stosz.store.service.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class InvalidAuditPassAfter extends IFsmHandler<Invalid> {

    @Autowired
    private InvalidService invalidService;

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private TransitItemService transitItemService;

    @Resource
    private FsmProxyService<TransitStock> transitFsmProxyService;

    @Override
    public void execute(Invalid invalid, EventModel event) {

        logger.debug("报废单审核通过[{}]", invalid);

        //2.库存减少
        this.changeStockQty(invalid);

        this.changeTransitState(invalid);
    }

    /**
     * 包裹状态修改为已报废
     *
     * @param invalid
     */
    private void changeTransitState(Invalid invalid) {
        List<TransitStock> stockList = invalidService.getTransitListByInvalidId(invalid.getId());
        for (TransitStock stock : stockList) {
            transitFsmProxyService.processEvent(stock, TransitStockEventEnum.invalidPass, "报废审核通过");
        }
    }

    private void changeStockQty(Invalid invalid) {

        List<StockChangeReq> reqList = new ArrayList<>();
        this.calc2StockChangeReqList(invalid, reqList);
        stockService.discardChangeStockQty(reqList);
    }

    /**
     * 分类统计
     *
     * @param invalid
     * @param reqList
     */
    private void calc2StockChangeReqList(Invalid invalid, List<StockChangeReq> reqList) {

        switch (CalculateTypeEnum.fromId(invalid.getCalculateType())) {
            case calculate_for_qty:
                calcForQty(invalid, reqList);
                break;
            case calculate_for_weight:
                calcForWeight(invalid, reqList);
                break;
            default:
        }
    }

    /**
     * 按重量平均统计
     *
     * @param invalid
     * @param reqList
     */
    private void calcForWeight(Invalid invalid, List<StockChangeReq> reqList) {
        //TODO 暂时没重量字段
        //暂时按数量统计返回
        calcForQty(invalid, reqList);
    }

    /**
     * 按数量平均统计
     *
     * @param invalid
     * @param reqList
     */
    private void calcForQty(Invalid invalid, List<StockChangeReq> reqList) {
        BigDecimal total = invalid.getInvalidTotal();
        List<TransitStock> stocks = invalidService.getTransitListByInvalidId(invalid.getId());
        int stockSize = stocks.size();
        List<BigDecimal> packAvgs = CalcUtils.getAvgs(total, stockSize);
        for (int i = 0; i < stockSize; ++i) {
            List<TransitItem> transitItems = transitItemService.getSkusByTransitId(stocks.get(i).getId());
            int transitItemsSize = transitItems.size();
            List<BigDecimal> skuAvgs = CalcUtils.getAvgs(packAvgs.get(i), transitItemsSize);
            for (int j = 0; j < transitItemsSize; ++j) {
                add2ReqList(reqList, invalid, transitItems.get(j), skuAvgs.get(j));
            }
        }

    }

    private void add2ReqList(List<StockChangeReq> reqList, Invalid invalid, TransitItem item, BigDecimal amount) {
        StockChangeReq req = new StockChangeReq();
        req.setWmsId(invalid.getWmsId());
        req.setDeptId(item.getDeptId());
        req.setDeptName(item.getDeptName());
        req.setSpu(item.getSpu());
        req.setSku(item.getSku());
        req.setChangeQty(item.getQty());
        req.setVoucherNo(invalid.getId().toString());
        req.setAmount(amount);
        req.setChangeAt(invalid.getCreateAt());
        req.setType(TransitHandleEnum.discard_apply_pass.name());
        reqList.add(req);
    }
}