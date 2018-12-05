package com.stosz.tms.service.waybill;

import com.stosz.tms.model.Shipping;
import com.stosz.tms.model.ShippingParcel;

import java.io.OutputStream;

public interface ShippingBillHandler {

    /**
     * 根据包裹数据创建一个面单PDF并输入到指定流
     * @param parcel 包裹数据
     * @param os
     */
    void createShippingBill(ShippingParcel parcel, OutputStream os) throws Exception;



}
