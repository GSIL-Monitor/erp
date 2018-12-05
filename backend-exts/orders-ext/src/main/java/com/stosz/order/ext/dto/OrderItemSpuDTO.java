package com.stosz.order.ext.dto;


import java.io.Serializable;
import java.util.List;

/**
 * 订单行 每种属性购买的个数及属性名
 * 用于 订单列表 -- 产品名
 */
public class OrderItemSpuDTO implements Serializable{


    private String spu;

    private String title;

    private String innerTitle;

    private String foreignTitle;

    private List<OrderItemSku> skuList;

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public List<OrderItemSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<OrderItemSku> skuList) {
        this.skuList = skuList;
    }

    public OrderItemSpuDTO() {
    }

    public OrderItemSpuDTO(String spu, String title, List<OrderItemSku> skuList) {
        this.spu = spu;
        this.title = title;
        this.skuList = skuList;
    }

    public OrderItemSpuDTO(String spu, String title, String innerTitle, String foreignTitle, List<OrderItemSku> skuList) {
        this.spu = spu;
        this.title = title;
        this.innerTitle = innerTitle;
        this.foreignTitle = foreignTitle;
        this.skuList = skuList;
    }

    public String getInnerTitle() {
        return innerTitle;
    }

    public void setInnerTitle(String innerTitle) {
        this.innerTitle = innerTitle;
    }

    public String getForeignTitle() {
        return foreignTitle;
    }

    public void setForeignTitle(String foreignTitle) {
        this.foreignTitle = foreignTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class OrderItemSku implements Serializable{

        private String sku;

        private String attr;

        private int qty;

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        @Override
        public String toString() {
            return "OrderItemSku{" +
                    "sku='" + sku + '\'' +
                    ", attr='" + attr + '\'' +
                    ", qty=" + qty +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderItemSpuDTO{" +
                "spu='" + spu + '\'' +
                ", title='" + title + '\'' +
                ", skuList=" + skuList +
                '}';
    }
}
