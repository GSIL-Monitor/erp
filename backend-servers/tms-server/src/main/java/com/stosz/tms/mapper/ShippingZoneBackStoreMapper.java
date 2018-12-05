package com.stosz.tms.mapper;

import com.stosz.tms.mapper.builder.ShippingBuilder;
import com.stosz.tms.mapper.builder.ShippingZoneBackStoreBuilder;
import com.stosz.tms.model.Shipping;
import com.stosz.tms.model.ShippingZoneBackStore;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ShippingZoneBackStoreMapper {

    @InsertProvider(type = ShippingZoneBackStoreBuilder.class, method = "insert")
    public int add(ShippingZoneBackStore shippingZoneBackStore);

    @SelectProvider(type = ShippingZoneBackStoreBuilder.class, method = "count")
    public int count(ShippingZoneBackStore shippingZoneBackStore);

    @SelectProvider(type = ShippingZoneBackStoreBuilder.class, method = "getById")
    public ShippingZoneBackStore getById(@Param("id") Integer id);

    @UpdateProvider(type = ShippingZoneBackStoreBuilder.class, method = "updateSelective")
    public int update(ShippingZoneBackStore shippingZoneBackStore);


    @SelectProvider(type = ShippingZoneBackStoreBuilder.class, method = "find")
    public List<ShippingZoneBackStore> query(ShippingZoneBackStore shippingZoneBackStore);

    @InsertProvider(type = ShippingZoneBackStoreBuilder.class,method = "batchInsertData")
    public int batchInsert(List<ShippingZoneBackStore> shippingZoneBackStores);

    @SelectProvider(type = ShippingZoneBackStoreBuilder.class, method = "findAll")
    public List<ShippingZoneBackStore> queryAll(ShippingZoneBackStore shippingZoneBackStore);
}
