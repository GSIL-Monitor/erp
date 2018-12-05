package com.stosz.store.service;

import com.stosz.order.ext.dto.TransitStockDTO;
import com.stosz.order.ext.dto.WmsResponse;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.store.ext.dto.response.MatchPackRes;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.ext.service.ITransitStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓接口
 * @Created Time:2017/11/29 15:33
 * @Update Time:
 */
@Service
public class TransitStockServiceImpl implements ITransitStockService {

    @Autowired
    private TransitStockService transitStockService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public MatchPackRes occupyStockQty(Integer warehouseId, Integer dept, String deptName, List<OrdersItem> orderItemsList, Integer orderId, LocalDateTime orderTime) {
        logger.info(" occupyStockQty req:warehouseId[{}],dept[{}],OrdersItem[{}]", warehouseId, dept, orderItemsList);
        //1,匹配查询sku数量相等，入库状态，库存时间最长排序，订单id List
        //2,循环查询item 比较匹配，匹配上则返回
        //考虑：并发同时匹配

        int skusSize = orderItemsList.size();

        synchronized (TransitStockServiceImpl.class) {
            try {
                List<MatchPackRes> matchList = transitStockService.getMatchPackList(skusSize, warehouseId, dept);
                if (CollectionUtils.isNullOrEmpty(matchList)) {
                    //无匹配
                    logger.info(" occupyStockQty rep:Null");
                    return null;
                }

                String[] skus, qtys;
                int oneMatch, allMatch;
                for (MatchPackRes pack : matchList) {
                    skus = pack.getSkus().split(",");
                    qtys = pack.getQtys().split(",");

                    allMatch = 0;
                    for (OrdersItem item : orderItemsList) {
                        oneMatch = 0;
                        for (int i = 0; i < skusSize; ++i) {
                            if (skus[i].equals(item.getSku()) && qtys[i].equals(item.getQty().toString())) {
                                oneMatch++;
                                allMatch++;
                                break;
                            }
                        }
                        if (oneMatch == 0) {
                            break;
                        }
                    }
                    if (allMatch == skusSize) {
                        //已匹配
                        transitStockService.MatchEvent(warehouseId, dept, deptName, orderItemsList, pack, orderId, orderTime);

                        logger.info(" occupyStockQty rep:pack[{}]", pack);
                        return pack;
                    }
                }
            } catch (Exception e) {
                logger.error("occupyStockQty ERROR:{}", e);
            }

        }
        logger.info(" occupyStockQty rep:Null");
        return null;
    }

    /**
     * RMA通知转寄仓收货
     *
     * @param transitStocks
     * @return
     */
    @Override
    public WmsResponse notifyStockTakeGoods(List<TransitStockDTO> transitStocks) {
        for (TransitStockDTO dto : transitStocks) {
            transitStockService.save(dto);
        }
        return new WmsResponse();
    }

    /**
     * RMA 申请单取消
     * @param rmaId
     * @return
     */
    @Override
    public WmsResponse notifyStockCancel(Integer rmaId) {
        WmsResponse response  = new WmsResponse();
        int i = transitStockService.delByRmaId(rmaId);
        if(i<1){
            response.setCode(WmsResponse.FAIL);
            response.setDesc("取消失败，请确认该申请单是否已入库");
        }
        return  response;
    }

}
