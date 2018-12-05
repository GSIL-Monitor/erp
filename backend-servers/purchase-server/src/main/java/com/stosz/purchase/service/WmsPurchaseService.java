package com.stosz.purchase.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.HttpClient;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.utils.RestClient;
import com.stosz.plat.wms.bean.*;
import com.stosz.plat.wms.enums.WMSOrderType;
import com.stosz.plat.wms.model.WmsConfig;
import com.stosz.plat.wms.model.WmsSender;
import com.stosz.purchase.ext.common.PurchaseDto;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/20]
 */
@Service
public class WmsPurchaseService {

    @Autowired
    private WmsConfig wmsConfig;

    public static final String warehouseCode = "Y";

    private static ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(WmsPurchaseService.class);

    /**
     * 提交采购单到wms
     * @param purchaseDto 采购单和采购单明细
     * @return 推送结果
     */
    public Boolean subCreatePurchaseOrder(PurchaseDto purchaseDto) {
        Assert.notNull(purchaseDto, "推送到wms的采购单不允许为空！");
        PurchaseCreateOrUpdate purchaseCreateOrUpdate = new PurchaseCreateOrUpdate();
        PurchaseHeadInfoBean purchaseHeadInfoBean = new PurchaseHeadInfoBean();
        purchaseHeadInfoBean.setOrderCode(purchaseDto.getPurchaseNo());
        purchaseHeadInfoBean.setSupplierCode(purchaseDto.getSupplierId() + "");
        purchaseHeadInfoBean.setGoodsOwner("1000"); //固定推1000， 布谷鸟的货主
        purchaseHeadInfoBean.setWarehouseCode(warehouseCode);
        purchaseHeadInfoBean.setPlanDate(purchaseDto.getPaymentTime().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        purchaseHeadInfoBean.setGwf1(purchaseDto.getPurchaseNo());
        purchaseHeadInfoBean.setGwf2(purchaseDto.getTrackingNo());
        purchaseHeadInfoBean.setRemark(purchaseDto.getMemo());

        List<PurchaseItem> purchaseItemList = purchaseDto.getItems();
        List<PurchaseDetailInfoBean> detailList = new ArrayList<>();
        Assert.notNull(purchaseItemList, "推送到wms的采购单对应明细不允许为空！");
        for (PurchaseItem item : purchaseItemList) {
            PurchaseDetailInfoBean purchaseDetailInfoBean = new PurchaseDetailInfoBean();
            purchaseDetailInfoBean.setSku(item.getSku());
            purchaseDetailInfoBean.setPrice(item.getPrice() == null ? "0" : item.getPrice() + "");
            purchaseDetailInfoBean.setQty(item.getQuantity() + "");
            detailList.add(purchaseDetailInfoBean);
        }
        purchaseHeadInfoBean.setDetailList(detailList);
        purchaseCreateOrUpdate.setHead(purchaseHeadInfoBean);
        String jsonContent = JsonUtil.toJson(purchaseCreateOrUpdate);
        logger.info("推送到wms的采购单为{}", jsonContent);
        try {
            WmsSender wmsSender = new WmsSender(WmsConfig.Service_subCreatePurchaseOrder, jsonContent, wmsConfig);
            String baseContent = wmsSender.getJsonContent();
            WmsConfig wmsConfig = wmsSender.getWmsConfig();
            String param = "appkey=" + wmsConfig.getAPPKEY() + "&service=" + wmsSender.getUrl() + "&format=" + wmsConfig.getFORMAT() + "&encrypt="
                    + wmsConfig.getENCRYPT() + "&content=" + baseContent + "&secret=" + null;
            logger.info("采购单发送出去的参数：" + param);
            String responseString = new HttpClient().pub2(wmsConfig.getSERVICE_URL(), param);
            logger.info("采购单responseString ======>{}", responseString);
            HashMap<String, Object> resultMap = JsonUtil.readValue(responseString, HashMap.class);
            return resultMap != null && (Boolean) resultMap.get("isSuccess");
        } catch (Exception e) {
            logger.error("推送采购单的wms接口信息异常", e);
            return false;
        }

    }

    /**
     * 修改采购单的运单号
     * @param purchase 采购单dto实体
     * @return 修改结果
     */
    public Boolean subUpdatePurchaseExpress(Purchase purchase) {
        Assert.notNull(purchase,"要推送的采购单不允许为空！");
        String purchaseNo = purchase.getPurchaseNo();
        String expressNo = purchase.getTrackingNo();
        PurchaseUpdateInfoRequest purchaseUpdateInfoRequest = new PurchaseUpdateInfoRequest();
        purchaseUpdateInfoRequest.setOrderCode(purchaseNo);
        purchaseUpdateInfoRequest.setExpressNo(expressNo);
        String jsonContent = JsonUtil.toJson(purchaseUpdateInfoRequest);
        logger.info("推送到wms的采购单为{},更新采购单的运单号", jsonContent);
        try {
            WmsSender wmsSender = new WmsSender(WmsConfig.Service_subUpdatePurchaseExpress, jsonContent, wmsConfig);
            String baseContent = wmsSender.getJsonContent();
            WmsConfig wmsConfig = wmsSender.getWmsConfig();
            String param = "appkey=" + wmsConfig.getAPPKEY() + "&service=" + wmsSender.getUrl() + "&format=" + wmsConfig.getFORMAT() + "&encrypt="
                    + wmsConfig.getENCRYPT() + "&content=" + baseContent + "&secret=" + null;
            logger.info("修改采购单发送出去的参数：" + param);
            String responseString = new HttpClient().pub2(wmsConfig.getSERVICE_URL(), param);
            logger.info("修改采购单responseString ======>{}", responseString);
            HashMap<String, Object> resultMap = JsonUtil.readValue(responseString, HashMap.class);
            return resultMap != null && (Boolean) resultMap.get("isSuccess");
        } catch (Exception e) {
            logger.error("推送修改采购单运单号的wms接口信息异常", e);
            return false;
        }
    }

    //推送采购退货计划
    public void subCreatePurchaseReturnPlan(PurchaseReturned purchaseReturned, List<PurchaseReturnedItem> purchaseReturnedItems) {

        PurchaseReturnPlanHeadInfoBean returnPlanInfo = getReturnPlanInfo(purchaseReturned, purchaseReturnedItems);
        String jsonContent = JsonUtil.toJson(returnPlanInfo);
        String paramBySenderObj = getParamBySenderObj(jsonContent);
        logger.info("WMS创建采退单接口调用 请求参数 request-->{}", paramBySenderObj);
        logger.info(paramBySenderObj);
        String result = RestClient.getClient().postForObject(wmsConfig.getSERVICE_URL(), paramBySenderObj, String.class);
        Assert.hasText(result, "未知原因导致取消订单接口返回结果为空，请与wms系统相关人员确认");
        logger.info(result);
        logger.info("WMS创建采退单接口调用结束 返回参数 response-->{}", result);
    }

    //推送单据取消
    public HashMap<String, Object> subCancelOrder(String orderNo, WMSOrderType orderType) {

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("orderCode", orderNo);
        paramMap.put("orderType", orderType.display());

        String jsonContent = JsonUtil.toJson(paramMap);
        logger.info("单据取消={}", jsonContent);
        try {
            WmsSender wmsSender = new WmsSender(WmsConfig.Service_subCancelOrder, jsonContent, wmsConfig);
            String baseContent = wmsSender.getJsonContent();
            WmsConfig wmsConfig = wmsSender.getWmsConfig();
            String param = "appkey=" + wmsConfig.getAPPKEY() + "&service=" + wmsSender.getUrl() + "&format=" + wmsConfig.getFORMAT() + "&encrypt="
                    + wmsConfig.getENCRYPT() + "&content=" + baseContent + "&secret=" + null;
            logger.info("单据取消发送出去的参数：" + param);
            String responseString = new HttpClient().pub2(wmsConfig.getSERVICE_URL(), param);
            logger.info("单据取消responseString ======>{}", responseString);
            HashMap<String, Object> resultMap = JsonUtil.readValue(responseString, HashMap.class);
            return resultMap;
        } catch (Exception e) {
            logger.error("采购退货计划推送失败,orderNo={},orderType={},Exception={}", orderNo, orderType, e);
        }
        return null;
    }

    private PurchaseReturnPlanHeadInfoBean getReturnPlanInfo(PurchaseReturned purchaseReturned,List<PurchaseReturnedItem> purchaseReturnedItems){
        PurchaseReturnPlanHeadInfoBean returnPlanInfo = new PurchaseReturnPlanHeadInfoBean();
        returnPlanInfo.setOrderCode(purchaseReturned.getId().toString());
        returnPlanInfo.setWarehouseCode(warehouseCode);
        returnPlanInfo.setCreateDate(purchaseReturned.getCreateAt().toString());
        returnPlanInfo.setCreateUserCode(purchaseReturned.getCreatorId());
        returnPlanInfo.setCreateUserName(purchaseReturned.getCreator());
        returnPlanInfo.setGoodsOwner("1000"); // 虚拟货主，布谷鸟
        returnPlanInfo.setOperuserDate(purchaseReturned.getRefundTime().toString());
        returnPlanInfo.setOperuserCode("1");
        returnPlanInfo.setOperuserName(purchaseReturned.getRefundUser());
        returnPlanInfo.setSupplierName(purchaseReturned.getSupplierName());
        returnPlanInfo.setSupplierCode(purchaseReturned.getSupplierCode());

        List<PurchaseReturnPlanDetailInfoBean> detailList = new ArrayList<>();
        for (PurchaseReturnedItem returnedItem : purchaseReturnedItems) {
            PurchaseReturnPlanDetailInfoBean planDetailInfoBean = new PurchaseReturnPlanDetailInfoBean();
            planDetailInfoBean.setPoOrderCode(purchaseReturned.getPurchaseNo());
            planDetailInfoBean.setSku(returnedItem.getSku());
            planDetailInfoBean.setQty(Integer.toString(returnedItem.getReturnedQty()));
            detailList.add(planDetailInfoBean);
        }
        returnPlanInfo.setDetailList(detailList);

        return returnPlanInfo;
    }

    /**
     * 将实体封装成需要的参数json串
     *
     * @param obj
     */
    public String getParamBySenderObj(String obj) {

        WmsSender wmsSender = new WmsSender(WmsConfig.Service_subCreatePurchaseReturnPlan, obj, wmsConfig);
        String baseContent = wmsSender.getJsonContent();
        WmsConfig wmsConfig = wmsSender.getWmsConfig();
        String param = "appkey=" + wmsConfig.getAPPKEY() + "&service=" + wmsSender.getUrl() + "&format=" + wmsConfig.getFORMAT() + "&encrypt=" + wmsConfig.getENCRYPT() + "&content=" + "{\"head\":" + baseContent + "}&secret=" + null;
        return param;
    }
}
