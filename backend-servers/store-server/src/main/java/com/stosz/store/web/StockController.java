package com.stosz.store.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.store.ext.dto.request.WarehouseBaseReq;
import com.stosz.store.ext.model.Stock;
import com.stosz.store.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:仓库管理
 * @Created Time:2017/11/25 10:10
 * @Update Time:2017/11/25 10:10
 */
@RestController
@RequestMapping("/store/stock")
public class StockController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockService stockService;

    /**
     * 库存列表
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    @ResponseBody
    public RestResult findList(WarehouseBaseReq warehouseBaseReq) {
        RestResult result = new RestResult();
        Stock stock = new Stock();
        BeanUtils.copyProperties(warehouseBaseReq, stock);

        int count = stockService.count(stock);
        result.setTotal(count);
        if (count == 0) {
            return result;
        }
        List<Stock> stocks = stockService.queryQty(stock);

        result.setItem(stocks);
        return result;
    }

}
