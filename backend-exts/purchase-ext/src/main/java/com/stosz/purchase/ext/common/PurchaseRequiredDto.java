package com.stosz.purchase.ext.common;


import java.math.BigDecimal;
import java.util.List;

public class PurchaseRequiredDto {

    private Integer buDeptId;// 事业部ID
    
    private String spu;//产品SPU

    private BigDecimal totalAmount;// 总金额

    private Integer supplierId;// 供应商ID

    private Integer buyerId;// 采购员ID

    private Integer creatorId;// 创建人ID

    private String creator;// 创建人

    private String productTitle;

    private String mainImageUrl;

    private String checkedSku;

    private String buyer;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getCheckedSku() {
        return checkedSku;
    }

    public void setCheckedSku(String checkedSku) {
        this.checkedSku = checkedSku;
    }

    private List<PurchaseRequiredItemDto> requiredItemDtos;// 采购需求明细

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public Integer getBuDeptId() {
        return buDeptId;
    }

    public void setBuDeptId(Integer buDeptId) {
        this.buDeptId = buDeptId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PurchaseRequiredItemDto> getRequiredItemDtos() {
        return requiredItemDtos;
    }

    
    
    public void setRequiredItemDtos(List<PurchaseRequiredItemDto> requiredItemDtos) {
        this.requiredItemDtos = requiredItemDtos;
    }

//    public static void main(String[] args) {
//        PurchaseRequiredDto purchaseRequiredDto=new PurchaseRequiredDto();
//        purchaseRequiredDto.setBuDeptId(12);
//        purchaseRequiredDto.setBuyerId(88);
//        purchaseRequiredDto.setSpu("ST50090");
//        purchaseRequiredDto.setSupplierId(1);
//        purchaseRequiredDto.setTotalAmount(new BigDecimal(50000));
//
//        PurchaseRequiredItemDto purchaseRequiredItemDto=new PurchaseRequiredItemDto();
//        purchaseRequiredItemDto.setId(1);
//        purchaseRequiredItemDto.setSku("1062980");
//        purchaseRequiredItemDto.setPlanQty(40);
//        purchaseRequiredItemDto.setPurchaseQty(38);
//        purchaseRequiredItemDto.setPurchasePrice(new BigDecimal(299.1));
//
//        PurchaseRequiredItemDto purchaseRequiredItemDto1=new PurchaseRequiredItemDto();
//        purchaseRequiredItemDto1.setId(3);
//        purchaseRequiredItemDto1.setSku("1062982");
//        purchaseRequiredItemDto1.setPlanQty(20);
//        purchaseRequiredItemDto1.setPurchaseQty(-74);
//        purchaseRequiredItemDto1.setPurchasePrice(new BigDecimal(299.1));
//
//
//        PurchaseRequiredItemDto purchaseRequiredItemDto2=new PurchaseRequiredItemDto();
//        purchaseRequiredItemDto2.setId(4);
//        purchaseRequiredItemDto2.setSku("1062982");
//        purchaseRequiredItemDto2.setPlanQty(107);
//        purchaseRequiredItemDto2.setPurchaseQty(107);
//        purchaseRequiredItemDto2.setPurchasePrice(new BigDecimal(299.1));
//
//        purchaseRequiredDto.setRequiredItemDtos(Arrays.asList(purchaseRequiredItemDto,purchaseRequiredItemDto1,purchaseRequiredItemDto2));
//        logger.info(JsonUtils.toJson(purchaseRequiredDto));
//
//
////        private Integer deptId;// 部门ID
////
////        private Integer id;// 记录ID
////
////        private String sku;// 产品SKu
////
////        private Integer planQty;//计划采购数
////
////        private Integer purchaseQty;//采购需求数
////
////        private BigDecimal purchasePrice;// 采购单价
//    }


}
