package com.stosz.product.service;


import com.stosz.product.ext.model.ProductSku;

/**
 * @auther carter
 * create time    2017-11-27
 */
public interface IWmsProductService {


    String url="/admin/remote/IWmsProductService";

    /**
     * 推送产品sku信息到wms
     * @param productSku
     */
    void insertOrUpdateProduct(ProductSku productSku);

    /**
     * 推送产品sku信息到wms
      * @param productSku
     * @param wait 是否需要等待
     */
    void insertOrUpdateProductMessage(ProductSku productSku, boolean wait);

}
