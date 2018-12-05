package com.stosz.store.ext.service;


import com.stosz.store.ext.model.Wms;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:仓库模块对外接口
 * @Created Time:2017/11/23 14:47
 */
public interface IStorehouseService {

    String url = "/store/remote/IStorehouseService";


    /**
     * 根据id查询wms信息
     * @param wmsId
     */
    Wms wmsTransferRequest(Integer wmsId);

    /**
     * 查询wms信息
     * @param wms
     */
    List<Wms> wmsTransferRequest(Wms wms);

    /**
     * 查询wms信息
     * @param wmsIds
     */
    List<Wms> findWmsByIds(List<Integer> wmsIds);

}
