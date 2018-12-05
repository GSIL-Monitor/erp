package com.stosz.purchase.ext.model;

import com.stosz.product.ext.model.Product;
import com.stosz.store.ext.model.Stock;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PurchaseReturnedExternal implements Serializable{

    private Map<String, String> skuTitleMap;
    
    private Map<String, Product> productMap;
    
    private Map<String, Stock> usableStockMap;
    
    private Map<String, Map<String, List<PurchaseReturnedItem>>> spuMap;
    
    private Map<Integer, PurchaseItem> purchaseItemMap;
    

    public PurchaseReturnedExternal(Map<String, String> skuTitleMap, Map<String, Product> productMap, Map<String, Stock> usableStockMap,
            Map<String, Map<String, List<PurchaseReturnedItem>>> spuMap, Map<Integer, PurchaseItem> purchaseItemMap) {
        super();
        this.skuTitleMap = skuTitleMap;
        this.productMap = productMap;
        this.usableStockMap = usableStockMap;
        this.spuMap = spuMap;
        this.purchaseItemMap = purchaseItemMap;
    }

    public Map<String, String> getSkuTitleMap() {
        return skuTitleMap;
    }

    public void setSkuTitleMap(Map<String, String> skuTitleMap) {
        this.skuTitleMap = skuTitleMap;
    }

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<String, Product> productMap) {
        this.productMap = productMap;
    }

    public Map<String, Stock> getUsableStockMap() {
        return usableStockMap;
    }

    public void setUsableStockMap(Map<String, Stock> usableStockMap) {
        this.usableStockMap = usableStockMap;
    }

    public Map<String, Map<String, List<PurchaseReturnedItem>>> getSpuMap() {
        return spuMap;
    }

    public void setSpuMap(Map<String, Map<String, List<PurchaseReturnedItem>>> spuMap) {
        this.spuMap = spuMap;
    }

    public Map<Integer, PurchaseItem> getPurchaseItemMap() {
        return purchaseItemMap;
    }

    public void setPurchaseItemMap(Map<Integer, PurchaseItem> purchaseItemMap) {
        this.purchaseItemMap = purchaseItemMap;
    }
    
    
}
