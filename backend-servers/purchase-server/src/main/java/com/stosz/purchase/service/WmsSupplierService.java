package com.stosz.purchase.service;

import com.stosz.plat.utils.HttpClient;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.wms.bean.SupplierCreateOrUpdateRequest;
import com.stosz.plat.wms.bean.SupplierInfoBean;
import com.stosz.plat.wms.model.WmsConfig;
import com.stosz.plat.wms.model.WmsSender;
import com.stosz.purchase.ext.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * wms关于供应商的推送更新
 * @author xiongchenyang
 * @version [1.0 , 2017/11/20]
 */
@Service
public class WmsSupplierService{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private WmsConfig wmsConfig;


    /**
     * 推送供应商到wms的接口
     * @param supplier 要推送的供应商
     * @return 推送结果
     */
    public boolean subSupplierCreateOrUpdate(Supplier supplier){
        Assert.notNull(supplier,"要推送的供应商不允许为空！");
        SupplierInfoBean supplierInfoBean = new SupplierInfoBean();
        supplierInfoBean.setSupplierCode(supplier.getId()+"");
        supplierInfoBean.setSupplierName(supplier.getName());
        SupplierCreateOrUpdateRequest supplierCreateOrUpdateRequest = new SupplierCreateOrUpdateRequest();
        List<SupplierInfoBean> supplierInfoBeans = new ArrayList<>();
        supplierInfoBeans.add(supplierInfoBean);
        supplierCreateOrUpdateRequest.setItems(supplierInfoBeans);
        String jsonContent = JsonUtil.toJson(supplierCreateOrUpdateRequest);
        logger.info("jsonContent==============》{}",jsonContent);
        try {
            WmsSender wmsSender = new WmsSender(WmsConfig.Service_subSupplierCreateOrUpdate, jsonContent, wmsConfig);
            String baseContent = wmsSender.getJsonContent();
            WmsConfig wmsConfig = wmsSender.getWmsConfig();
            String param = "appkey="+wmsConfig.getAPPKEY()+"&service="+wmsSender.getUrl()+"&format="+wmsConfig.getFORMAT()+"&encrypt="+wmsConfig.getENCRYPT()+"&content="+baseContent+"&secret="+null;
            logger.info("发送出去的参数："+param);
            String responseString = new HttpClient().pub2(wmsConfig.getSERVICE_URL(), param);
            logger.info("responseString ======>{}",responseString);
            return true;
        } catch (Exception e) {
            logger.error("推送wms接口信息异常",e);
            return false;
        }
    }
}  
