package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.ErpZoneWarehouseMapper;
import com.stosz.olderp.ext.model.ErpWarehouse;
import com.stosz.olderp.ext.model.ErpZoneWarehouse;
import com.stosz.olderp.ext.service.IErpZoneWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ErpZoneWarehouseService implements IErpZoneWarehouseService {

    @Autowired
    private ErpZoneWarehouseMapper erpZoneWarehouseMapper;

    @Override
    public List<ErpZoneWarehouse> findErpWarehouse(Integer offset, Integer limit, LocalDateTime beginTime, LocalDateTime endTime){
        Assert.isTrue(offset >= 0 , "offset必须大于等于0");
        Assert.isTrue(limit > 0 , "offset必须大于0");
        Assert.notNull(beginTime , "开始时间不能为空");
        Assert.notNull(endTime , "结束时间不能为空");
        return erpZoneWarehouseMapper.findErpWarehouse(offset,limit,beginTime,endTime);
    }


    /**
     * 增量统计老ERP订单联系人数量
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Override
    public Integer countErpWarehouse(LocalDateTime beginTime, LocalDateTime endTime){
        Assert.notNull(beginTime , "开始时间不能为空");
        Assert.notNull(endTime , "结束时间不能为空");
        return erpZoneWarehouseMapper.countErpWarehouse(beginTime,endTime);
    }
}
