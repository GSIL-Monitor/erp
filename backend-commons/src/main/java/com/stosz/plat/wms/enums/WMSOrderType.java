package com.stosz.plat.wms.enums;

import com.stosz.plat.utils.IEnum;

public enum WMSOrderType implements IEnum {
    
    saleOrder("JYCK"), saleReturnOrder("XTRK"), purchaseOrder("CGRK"), purchaseReturnOrder("CGTH"), transferOrdre("TRAN");
    private String display;

    WMSOrderType(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static WMSOrderType fromName(String name) {
        for (WMSOrderType en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }
}
