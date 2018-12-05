package com.stosz.purchase.ext.model;

import com.stosz.product.ext.model.Product;

import java.io.Serializable;
import java.util.List;

/**
 * 查询库存
 *
 * @author minxiaolong
 * @create 2018-01-08 15:55
 **/
public class StockInfo implements Serializable {

    private static final long serialVersionUID = -4837649583812466438L;

    private Product product;//产品信息

    private List<InStockDepartment> inStockDepartments;//有库存部门集合

    private List<PurchaseRequired> purchaseRequireds;//需求部门集合

    private Integer wmsId;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<InStockDepartment> getInStockDepartments() {
        return inStockDepartments;
    }

    public void setInStockDepartments(List<InStockDepartment> inStockDepartments) {
        this.inStockDepartments = inStockDepartments;
    }

    public List<PurchaseRequired> getPurchaseRequireds() {
        return purchaseRequireds;
    }

    public void setPurchaseRequireds(List<PurchaseRequired> purchaseRequireds) {
        this.purchaseRequireds = purchaseRequireds;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }
}
