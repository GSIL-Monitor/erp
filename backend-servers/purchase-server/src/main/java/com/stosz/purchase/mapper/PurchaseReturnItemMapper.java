package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import org.apache.ibatis.annotations.SelectProvider;

public interface PurchaseReturnItemMapper {

    @SelectProvider(type = PurchaseReturnItemBuilder.class, method = "insert")
    public int add(PurchaseReturnedItem returnedItem);
}
