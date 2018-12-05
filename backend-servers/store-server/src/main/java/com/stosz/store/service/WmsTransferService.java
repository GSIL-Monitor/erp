package com.stosz.store.service;

import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.utils.RestClient;
import com.stosz.plat.wms.model.WMSResponse;
import com.stosz.plat.wms.model.WmsConfig;
import com.stosz.plat.wms.model.WmsSender;
import com.stosz.store.ext.dto.request.TransferWmsReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 15:33
 * @Update Time:
 */
@Service
public class WmsTransferService implements IWmsTransferService {

    private Logger logger = LoggerFactory.getLogger(WmsTransferService.class);

    @Autowired
    private WmsConfig wmsConfig;


    /**
     * 将实体封装成需要的参数json串
     *
     * @param obj
     */
    public String getParamBySenderObj(String obj) {

        WmsSender wmsSender = new WmsSender(WmsConfig.Service_subCreateTranPlan, obj, wmsConfig);
        String baseContent = wmsSender.getJsonContent();
        WmsConfig wmsConfig = wmsSender.getWmsConfig();
        String param = "appkey=" + wmsConfig.getAPPKEY() + "&service=" + wmsSender.getUrl() + "&format=" + wmsConfig.getFORMAT() + "&encrypt=" + wmsConfig.getENCRYPT() + "&content=" + "{\"head\":" + baseContent + "}&secret=" + null;
        return param;
    }

    /**
     * 创建调拨单
     *
     * @param transferWmsReq
     */
    @Override
    public void subCreateTranPlan(TransferWmsReq transferWmsReq) {

        String jsonContent = JsonUtil.toJson(transferWmsReq);
        String paramBySenderObj = getParamBySenderObj(jsonContent);
        logger.info("WMS创建调拨单接口调用 请求参数 request-->{}", paramBySenderObj);
        String result;
        try {
            result = RestClient.getClient().postForObject(wmsConfig.getSERVICE_URL(), paramBySenderObj, String.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("调用WMS接口连接异常或者系统异常");
        }
        logger.info("WMS创建调拨单接口调用结束 返回参数 response-->{}", result);
        Assert.hasText(result, "未知原因导致取消订单接口返回结果为空，请与wms系统相关人员确认");
        WMSResponse wmsResponse = JsonUtil.readValue(result, WMSResponse.class);
        Assert.isTrue(wmsResponse.isOK(), wmsResponse.getBody());
    }
}
