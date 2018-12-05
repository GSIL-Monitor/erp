package com.stosz.store.web;

import com.stosz.plat.web.AbstractController;
import com.stosz.plat.wms.model.WMSResponse;
import com.stosz.store.ext.dto.request.WmsReq;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:yangqinghui
 * @Description:仓库管理
 * @Created Time:2017/11/25 10:10
 * @Update Time:2017/11/25 10:10
 */
@RestController
@RequestMapping("/store/wms/api")
public class WmsController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IStockService iStockService;

    /**
     * 普通仓入库
     */
    @RequestMapping(value = "/transfer_in_stock", method = RequestMethod.POST)
    @ResponseBody
    public WMSResponse transferInStock(WmsReq wmsReq) {
        logger.info("wms 回调[{}]", wmsReq);
        return iStockService.wmsTransferRequest(wmsReq);
    }

}
