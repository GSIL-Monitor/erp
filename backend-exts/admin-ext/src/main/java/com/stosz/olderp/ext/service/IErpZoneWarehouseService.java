package com.stosz.olderp.ext.service;


import com.stosz.olderp.ext.model.ErpZoneWarehouse;

import java.time.LocalDateTime;
import java.util.List;

public interface IErpZoneWarehouseService {

    String url = "/admin/remote/IErpZoneWarehouseService";

    List<ErpZoneWarehouse> findErpWarehouse(Integer offset, Integer limit, LocalDateTime beginTime, LocalDateTime endTime);

    Integer countErpWarehouse(LocalDateTime beginTime, LocalDateTime endTime);
}
