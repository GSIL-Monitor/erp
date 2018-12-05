package com.stosz.store.erpOld.sync;


import com.google.common.collect.Lists;
import com.stosz.old.erp.ext.model.OldErpWarehouse;
import com.stosz.order.ext.model.BlackList;
import com.stosz.store.erpOld.sync.core.AbstractSycn;
import com.stosz.store.erpOld.sync.mapper.ErpWarehouseMapper;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.service.WmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther ChenShifeng
 * create time    2017-11-07
 * erp_warehouse=>wms
 */
@Component
public class SyncWms extends AbstractSycn<OldErpWarehouse> {


    //    @Scheduled(fixedRate = 30000)
    public void SyncWms() {
        super.pullById();
    }

    @Autowired
    private WmsService wmsService;

    @Autowired
    private ErpWarehouseMapper erpWarehouseMapper;


    @Override
    protected List<OldErpWarehouse> fetchByIdRegion(int start, int fetch_count) {
        return erpWarehouseMapper.findLimit(start, fetch_count);
    }


    @Override
    protected String dataDesc() {
        return "仓库数据（erp_warehouse=>wms）";
    }

    @Override
    protected int getOldMaxId() {
        return erpWarehouseMapper.getOldMaxId();
    }

    @Override
    protected int getNewMaxId() {
        return wmsService.count(new Wms());
    }

    @Override
    protected Integer batchInsert(List<OldErpWarehouse> itemList) {

        Assert.notNull(itemList, "批量插入的数据不能为空");
        List<Wms> newLists = itemList.stream().map(old -> {
            Wms newWms = new Wms();
            newWms.setAddress(old.getAddress());
//            newWms.setLinkman("张三");
//            newWms.setMobile("1325656485");
            newWms.setName(old.getTitle());
            newWms.setPriority(old.getPriority());
            newWms.setState(old.getStatus());
            newWms.setZoneId(old.getIdZone());
            newWms.setZoneName(old.getZoneName());
            newWms.setType(old.getForward());
            newWms.setWmsSysCode(old.getWarehouseWmsTitle());
//            newWms.setSender("布谷鸟");
//            newWms.setSenderCity("深圳市");
//            newWms.setSenderCountry("中国");
//            newWms.setSenderEmail("bgn@stosz.com.cn");
//            newWms.setSenderProvince("广东省");
//            newWms.setSenderTown("前海");
//            newWms.setSenderZipcode("519000");
            newWms.setUseWms(old.getWmswarehouse());
            newWms.setDeleted(0);//0-否


            newWms.setId(old.getIdWarehouse());

            newWms.setCreateAt(LocalDateTime.now());
            newWms.setCreatorId(0);
            newWms.setCreator("系统同步");
            return newWms;
        }).collect(Collectors.toList());

        return wmsService.insertBatch(newLists);
    }
}
