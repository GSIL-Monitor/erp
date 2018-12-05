package com.stosz.tms.service;

import com.stosz.plat.utils.CollectionUtils;
import com.stosz.tms.mapper.ShippingStoreRelationMapper;
import com.stosz.tms.mapper.ShippingZoneBackStoreMapper;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingZoneBackStore;
import com.stosz.tms.vo.StoreInfoResponseVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("shippingWayStoreService")
public class ShippingWayStoreService implements  IShippingWayStoreService {

    @Resource
    private ShippingZoneBackStoreMapper zoneBackStoreMapper;

    @Override
    public StoreInfoResponseVo findBackStore(int shippingWayId,int wmsId,int zoneId){
        ShippingZoneBackStore paramBean = new ShippingZoneBackStore();
        paramBean.setShippingWayId(shippingWayId);
        paramBean.setWmsId(wmsId);
        paramBean.setZoneId(zoneId);

        final List<ShippingZoneBackStore> zoneBackStores = zoneBackStoreMapper.queryAll(paramBean);

        StoreInfoResponseVo storeInfoResponseVo = null;

        if (CollectionUtils.isNotNullAndEmpty(zoneBackStores)){
            final ShippingZoneBackStore zoneBackStore = zoneBackStores.get(0);
            final Integer backWmsId = zoneBackStore.getWmsId();
            final String backWmsName = zoneBackStore.getWmsName();

            storeInfoResponseVo = new StoreInfoResponseVo();
            storeInfoResponseVo.setId(backWmsId);
            storeInfoResponseVo.setName(backWmsName);
        }

        return storeInfoResponseVo;
    }
}
