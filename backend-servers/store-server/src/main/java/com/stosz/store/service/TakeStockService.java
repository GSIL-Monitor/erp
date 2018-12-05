package com.stosz.store.service;

import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.store.ext.dto.request.AddTakeStockReq;
import com.stosz.store.ext.dto.request.SearchTakeStockReq;
import com.stosz.store.ext.enums.TakeStockEventEnum;
import com.stosz.store.ext.enums.TakeStockStateEnum;
import com.stosz.store.ext.enums.TakeStockTypeEnum;
import com.stosz.store.ext.model.TakeStock;
import com.stosz.store.ext.model.TakeStockItem;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.mapper.TakeStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄 盘点
 * @Created Time:2017/11/23 15:49
 * @Update Time:
 */
@Service
public class TakeStockService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TakeStockMapper mapper;

    @Resource
    private FsmProxyService<TakeStock> takeStockFsmProxyService;

    @Autowired
    private TakeStockItemService takeStockItemService;

    @Resource
    private TransitStockService transitStockService;

    /**
     * 添加
     *
     * @param stock
     */
    public Integer insert(TakeStock stock) {
        UserDto user = ThreadLocalUtils.getUser();
        stock.setUpdateAt(LocalDateTime.now());
        stock.setCreateAt(LocalDateTime.now());
        stock.setCreatorId(user.getId());
        stock.setCreator(user.getLastName());
        return mapper.insert(stock);
    }

    /**
     * 添加盘点单
     *
     * @param
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(AddTakeStockReq param) {
        TakeStock takeStock = new TakeStock();
        takeStock.setWmsId(param.getWmsId());
        takeStock.setStockName(param.getStockName());
        takeStock.setMemo(param.getMemo());
        takeStock.setState("start");
        takeStock.setTakeStockType(TakeStockTypeEnum.inventoryLosses.ordinal());

        int size = param.getTrackings().size();
        takeStock.setInventoryLossesQty(size);
        insert(takeStock);
        for (int i = 0; i < size; ++i) {
            TakeStockItem item = new TakeStockItem();
            item.setTakeStockId(takeStock.getId());
            item.setTrackingNoOld(param.getTrackings().get(i));
            takeStockItemService.insert(item);
        }
        takeStockFsmProxyService.processEvent(takeStock, TakeStockEventEnum.create, "");
    }

    public int update(TakeStock takeStock) {
        return mapper.update(takeStock);
    }

    /**
     * 搜索查询
     *
     * @param
     */
    public RestResult findList(SearchTakeStockReq param) {
        RestResult rs = new RestResult();
        int count = mapper.count(param);
        rs.setTotal(count);
        if (count == 0) {
            return rs;
        }
        List<TakeStock> lst = mapper.find(param);

        rs.setItem(lst);
        rs.setDesc("查询成功");
        return rs;
    }

    /**
     * 查询明细
     */
    public List<TransitStock> getTransitListById(Integer id) {

        List<TransitStock> list = mapper.getTransitListById(id);
        transitStockService.fillOrderInfo(list);
        return list;

    }

    /**
     * 查询sku明细
     */
    public List<TransitItem> getTransitItemListByTakeStockId(Integer takeStockId) {
        return mapper.getTransitItemListByTakeStockId(takeStockId);
    }


    /**
     * 初审审核通过
     */
    public void firstAuditPass(Integer id) {
        Assert.notNull(id, "id不能为空");
        TakeStock stock = mapper.getById(id);

        Assert.notNull(stock, String.format("id[%s]的盘点单不存在", id));
        takeStockFsmProxyService.processEvent(stock, TakeStockEventEnum.firstAuditPass, "");
    }

    /**
     * 复审审核通过
     */
    public void passFinance(Integer id) {
        Assert.notNull(id, "id不能为空");
        TakeStock stock = mapper.getById(id);

        Assert.notNull(stock, String.format("id[%s]的盘点单不存在", id));
        takeStockFsmProxyService.processEvent(stock, TakeStockEventEnum.passFinance, "");
    }


    /**
     * 初审审核不通过
     */
    public void rejectFirst(Integer id) {
        Assert.notNull(id, "id不能为空");
        TakeStock stock = mapper.getById(id);

        Assert.notNull(stock, String.format("id[%s]的盘点单不存在", id));
        takeStockFsmProxyService.processEvent(stock, TakeStockEventEnum.rejectFirst, "");
    }

    /**
     * 复审审核不通过
     */
    public void rejectFinance(Integer id) {
        Assert.notNull(id, "id不能为空");
        TakeStock stock = mapper.getById(id);

        Assert.notNull(stock, String.format("id[%s]的盘点单不存在", id));
        takeStockFsmProxyService.processEvent(stock, TakeStockEventEnum.rejectFinance, "");
    }


}
