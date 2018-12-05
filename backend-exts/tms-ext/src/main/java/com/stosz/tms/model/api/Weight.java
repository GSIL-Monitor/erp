package com.stosz.tms.model.api;

/**
 * weight -> ShipmentDetails -> shipment -> shipments -> aramexContent
 */
public class Weight {
    private Double Value;
    private String Unit;

    public Double getValue() {
        return Value;
    }

    public void setValue(Double value) {
        Value = value;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}
