package com.stosz.tms.model.api;

import java.io.Serializable;

/**
 * ShipmentDetails -> shipment -> shipments -> aramexContent
 */
public class ShipmentDetails implements Serializable{

    private Integer NumberOfPieces;

    private Weight ActualWeight;

    private String ProductGroup;

    private String ProductType;

    private String PaymentType;

    public Integer getNumberOfPieces() {
        return NumberOfPieces;
    }

    public void setNumberOfPieces(Integer numberOfPieces) {
        NumberOfPieces = numberOfPieces;
    }

    public Weight getActualWeight() {
        return ActualWeight;
    }

    public void setActualWeight(Weight actualWeight) {
        ActualWeight = actualWeight;
    }

    public String getProductGroup() {
        return ProductGroup;
    }

    public void setProductGroup(String productGroup) {
        ProductGroup = productGroup;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }
}
