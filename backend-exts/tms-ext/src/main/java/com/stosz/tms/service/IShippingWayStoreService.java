package com.stosz.tms.service;

import com.stosz.tms.vo.StoreInfoResponseVo;

public interface IShippingWayStoreService {

    String url = "/tms/remote/IShippingWayStoreService";



    /**
     * 查看退回仓
     * @param shippingWayId 物流方式ID
     * @param wmsId 仓库ID
     * @param zoneId 区域ID
     * @return 退回仓仓库ID,查询不到则返回null
     *
     */
     StoreInfoResponseVo findBackStore(int shippingWayId,int wmsId,int zoneId);

}
