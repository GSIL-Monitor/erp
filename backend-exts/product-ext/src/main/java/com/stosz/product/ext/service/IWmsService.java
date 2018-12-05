package com.stosz.product.ext.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.product.ext.model.ProductSku;
import org.springframework.scheduling.annotation.Async;

/**
 * @auther carter
 * create time    2017-11-29
 */
public interface IWmsService {
    String url="/product/remote/IWmsService";

    //    @Scheduled(fixedRate = 60 * 1000)
    void repostFailWmsMessage();

    void repostBySku(String sku);

    ObjectMapper getObjectMapper();

    void insertOrUpdateProduct(ProductSku productSku);

//    @Async("wmsThreadPoolTaskExecutor")
    void insertOrUpdateProductMessage(ProductSku productSku, boolean wait);
}
