package com.stosz.tms.service.facebill.impl;

import com.stosz.plat.common.ResultBean;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.Shipping;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.service.ExpressSheetService;
import com.stosz.tms.service.ShippingService;
import com.stosz.tms.service.facebill.IFaceBillArrange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2018/1/24 14:37
 */
public class FaceBillNIM implements IFaceBillArrange {

    private Logger logger = LoggerFactory.getLogger(ExpressSheetService.class);

    @Resource
    private ShippingService shippingService;

    @Override
    public ResultBean<HashMap<String, Object>> getFaceBill(ShippingParcel shippingParcel) {

        ResultBean<HashMap<String, Object>> resultBean = new ResultBean<>();
        Shipping shipping = shippingService.queryShippingByWayId(shippingParcel.getShippingWayId());
        if (shipping == null) {
            resultBean.setCode(ResultBean.FAIL);
            resultBean.setDesc(String.format("物流方式：%s,对应的物流商不存在", shippingParcel.getShippingWayId()));
            return resultBean;
        }
        HashMap<String, Object> sheetDataMap = resultBean.getItem();
        String shippingCode = shipping.getShippingCode();
        String responseData = shippingParcel.getResponseData();
        try {
            fillSheetByNimShipping(sheetDataMap, responseData);
            resultBean.setCode(ResultBean.OK);
            resultBean.setDesc("面单信息获取成功");
        } catch (Exception e) {
            resultBean.setCode(ResultBean.FAIL);
            resultBean.setDesc(e.getMessage());
            logger.error("assignExpressSheet() 获取面单信息失败,shippingCode={} Exception={}", shippingCode, e);
        }
        return resultBean;
    }

    /**
     * 获取NIM的面单信息
     *
     * @param
     * @param sheetDataMap
     * @param responseData
     */
    private void fillSheetByNimShipping(HashMap<String, Object> sheetDataMap, String responseData) {
        String dest = null;
        String zone = null;
        String route = null;
        String bcNo = null;
        String bcRunNo = null;
        String nimZoneNumber = null;
        if (StringUtils.hasText(responseData)) {
            Parameter<String, Object> hashMap = JsonUtils.jsonToObject(responseData, Parameter.class);
            nimZoneNumber = hashMap.getString("nim_zone_number");
            dest = hashMap.getString("dest");
            zone = hashMap.getString("zone");
            route = hashMap.getString("route");
            if (hashMap.get("barcode") instanceof ArrayList) {
                ArrayList<HashMap<String, String>> barCodeList = (ArrayList<HashMap<String, String>>) hashMap.get("barcode");
                HashMap<String, String> bcCodeMap = barCodeList.get(0);
                bcNo = bcCodeMap.get("bc_no");
                bcRunNo = bcCodeMap.get("bc_run_no");
            }
        }
        sheetDataMap.put("GWF3", dest);// dest
        sheetDataMap.put("GWF5", nimZoneNumber);// 泰国NIM物流的nim_zone_number字段
        sheetDataMap.put("GWF8", zone);// GWF8 泰国NIM物流的zone字段
        sheetDataMap.put("GWF9", route); // GWF9 泰国NIM物流的route字段
        sheetDataMap.put("GWF6", bcNo);// GWF6 泰国NIM物流的bc_no字段
        sheetDataMap.put("GWF7", bcRunNo);// GWF7 泰国NIM物流的bc_run_no字段

        Assert.hasText(dest, "dest不允许为空");
        Assert.hasText(nimZoneNumber, "nim_zone_number不允许为空");
        Assert.hasText(bcNo, "bcNo不允许为空");
        Assert.hasText(bcRunNo, "bc_run_no 不允许为空");
    }
}
