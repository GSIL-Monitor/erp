package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpStockMapper;
import com.stosz.olderp.ext.model.OldErpStock;
import com.stosz.olderp.ext.service.IOldErpStockService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/8]
 */
@Service
public class OldErpStockService implements IOldErpStockService {

    @Resource
    private OldErpStockMapper oldErpStockMapper;

    @Override
    public List<OldErpStock> findByLimit(Integer limit, Integer start) {
        Assert.notNull(limit, "限制条数不允许为空！");
        Assert.notNull(start, "起始位置不允许为空！");
        return oldErpStockMapper.findByLimit(limit, start);
    }


    @Override
    public List<OldErpStock> findByDate(Date startTime, Date endTime) {
        return oldErpStockMapper.findByDate(startTime, endTime);
    }

    @Override
    public List<OldErpStock> findStockByParam(OldErpStock oldErpStock) {
        return oldErpStockMapper.findStockByParam(oldErpStock);
    }

    @Override
    public Integer getStockByUnique(Integer productId, Integer departmentId, Integer zoneId) {
        Assert.notNull(productId, "产品id不允许为空！");
        Assert.notNull(departmentId, "部门id不允许为空！");
        Assert.notNull(zoneId, "区域id不允许为空！");
        return oldErpStockMapper.getByUnique(productId, departmentId, zoneId);
    }

    @Override
    public List<OldErpStock> findOrderAtByParam(OldErpStock oldErpStock) {
        Assert.notNull(oldErpStock, "查询对象不允许为空！");
        return oldErpStockMapper.findOrderDateByParam(oldErpStock);
    }

    @Override
    public LocalDateTime getOrderAtByUnique(Integer productId, Integer departmentId, Integer zoneId) {
        return oldErpStockMapper.getOrderDateByUnique(productId, departmentId, zoneId);
    }

    @Override
    public List<OldErpStock> findHasStock(OldErpStock oldErpStock) {
        Assert.notNull(oldErpStock, "插入有库存的老erp记录，入参不允许为空！");
        return oldErpStockMapper.findHasStock(oldErpStock);
    }

    @Override
    public List<OldErpStock> findHasOrderAtByProductId(Integer productId) {
        return oldErpStockMapper.findHasLastOrderAtByProductId(productId);
    }

    @Override
    public Integer countOrderAt() {
        return oldErpStockMapper.countOrderAt();
    }

    @Override
    public Integer getHasStockCountByProductId(Integer productId) {
        return oldErpStockMapper.getHasStockCountByProductId(productId);
    }

    @Override
    public Integer getHasOrderCountByProductId(Integer productId) {
        return oldErpStockMapper.getHasOrderCountByProductId(productId);
    }


}
