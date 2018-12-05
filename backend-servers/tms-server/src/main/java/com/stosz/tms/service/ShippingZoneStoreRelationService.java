package com.stosz.tms.service;

import com.stosz.plat.utils.BeanMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.tms.mapper.ShippingWayMapper;
import com.stosz.tms.mapper.ShippingZoneStoreRelMapper;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.model.ShippingZoneStoreRel;
import com.stosz.tms.vo.ShippingZoneStoreRelationListVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShippingZoneStoreRelationService {

    @Resource
    private ShippingZoneStoreRelMapper mapper;

    @Resource
    private ShippingWayMapper shippingWayMapper;

    @Resource
    private IZoneService zoneService;

    @Resource
    private IStockService stockService;

    @Resource
    private IStorehouseService storehouseService;

    public List<ShippingZoneStoreRelationListVo> queryList(ShippingZoneStoreRel shippingZoneStoreRel){
        final List<ShippingZoneStoreRel> shippingZoneStoreRels = mapper.queryList(shippingZoneStoreRel);
        Set<Integer> shippingWayIds = shippingZoneStoreRels.stream().map(ShippingZoneStoreRel::getShippingWayId).collect(Collectors.toSet());

        List<ShippingWay> shippingWays = shippingWayMapper.queryByIds(shippingWayIds);

        final Map<Integer, List<ShippingWay>> shippingWayMapById = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getId));

        final List<ShippingZoneStoreRelationListVo> zoneStoreRelationListVos = BeanMapper.mapList(shippingZoneStoreRels, ShippingZoneStoreRelationListVo.class);

        zoneStoreRelationListVos.forEach(e -> {
            final Integer shippingWayId = e.getShippingWayId();

            final List<ShippingWay> wayList = shippingWayMapById.get(shippingWayId);

            //没有找到数据
            if (wayList == null || wayList.isEmpty())
                return;

            final ShippingWay shippingWay = wayList.get(0);

            e.setShippingWayCode(shippingWay.getShippingWayCode());
            e.setShippingWayName(shippingWay.getShippingWayName());
        });

        return zoneStoreRelationListVos;
    }


    public int count(ShippingZoneStoreRel shippingZoneStoreRel){
        return mapper.count(shippingZoneStoreRel);
    }


    public void add(ShippingZoneStoreRel shippingZoneStoreRel){
        checkDataExist(shippingZoneStoreRel);
        Assert.isTrue(mapper.add(shippingZoneStoreRel) >0 ,"新增物流配送区域失败!");
    }

    private void checkDataExist(ShippingZoneStoreRel shippingZoneStoreRel) {
        ShippingZoneStoreRel paramBean =  new ShippingZoneStoreRel();
        paramBean.setWmsId(shippingZoneStoreRel.getWmsId());
        paramBean.setShippingWayId(shippingZoneStoreRel.getShippingWayId());
        paramBean.setZoneId(shippingZoneStoreRel.getZoneId());
        paramBean.setStart(0);
        paramBean.setLimit(1);

        final List<ShippingZoneStoreRel> storeRels = mapper.queryList(paramBean);

        Assert.isTrue( (storeRels == null || storeRels.isEmpty()) || (shippingZoneStoreRel.getId() != null && storeRels.get(0).getId().equals(shippingZoneStoreRel.getId()) )  ,"同一仓库下，同一物流公司，已存在该地址的配送区域");
    }


    public void updateEnable(ShippingZoneStoreRel shippingZoneStoreRel){
        Assert.isTrue(mapper.update(shippingZoneStoreRel) > 0 ,"更新物流配送区域状态失败");
    }

    public void update(ShippingZoneStoreRel shippingZoneStoreRel){
        checkDataExist(shippingZoneStoreRel);
        Assert.isTrue(mapper.update(shippingZoneStoreRel) > 0 ,"更新物流配送区域失败");
    }



    public void importExecl(List<ShippingZoneStoreRel> zoneStoreRels){
        final List<String> shippingWayCodes = new ArrayList<>(zoneStoreRels.stream().map(ShippingZoneStoreRel::getShippingWayCode).collect(Collectors.toSet()));
        final List<Integer> wmsIds = new ArrayList<>(zoneStoreRels.stream().map(ShippingZoneStoreRel::getWmsId).collect(Collectors.toSet()));
        final List<Integer> zoneIds = new ArrayList<>(zoneStoreRels.stream().map(ShippingZoneStoreRel::getZoneId).collect(Collectors.toSet()));

        final List<ShippingWay> shippingWays = shippingWayMapper.queryByCodes(shippingWayCodes);
        final Map<String, List<ShippingWay>> shippingWayMapByCode = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getShippingWayCode));

        final List<Zone> zones = zoneService.queryByIds(zoneIds);
        final Map<Integer, List<Zone>> zoneMapById = zones.stream().collect(Collectors.groupingBy(Zone::getId));

        final List<Wms> wmsList = storehouseService.findWmsByIds(wmsIds);
        final Map<Integer, List<Wms>> wmsMapById = wmsList.stream().collect(Collectors.groupingBy(Wms::getId));

        ShippingZoneStoreRel paramBean =  new ShippingZoneStoreRel();
        paramBean.setStart(0);
        paramBean.setLimit(Integer.MAX_VALUE);

        final List<ShippingZoneStoreRel> existStoreRels = mapper.queryList(paramBean);


        for (int i = 0; i< zoneStoreRels.size() ; i++){
            final ShippingZoneStoreRel zoneStoreRel = zoneStoreRels.get(i);


            final String shippingWayCode = zoneStoreRel.getShippingWayCode();
            final Integer zoneId = zoneStoreRel.getZoneId();
            final Integer wmsId = zoneStoreRel.getWmsId();

            final List<ShippingWay> shippingWayList = shippingWayMapByCode.get(shippingWayCode);
            final List<Zone> zoneList = zoneMapById.get(zoneId);
            final List<Wms> wmss = wmsMapById.get(wmsId);

            Assert.notEmpty(shippingWayList,"第"+(i+1)+"的物流方式代码错误，无法找到该数据");
            Assert.notEmpty(zoneList,"第"+(i+1)+"的地区ID错误，无法找到该数据");
            Assert.notEmpty(wmss,"第"+(i+1)+"的仓库ID错误，无法找到该数据");

            zoneStoreRel.setShippingWayId(shippingWayList.get(0).getId());
            zoneStoreRel.setZoneName(zoneList.get(0).getTitle());
            zoneStoreRel.setWmsName(wmss.get(0).getName());

            final List<ShippingZoneStoreRel> storeRels = existStoreRels.stream().filter(e -> {
                if (e.getZoneId().equals(zoneId) && e.getWmsId().equals(wmsId) && e.getShippingWayId().equals(e.getShippingWayId()))
                    return true;
                return false;
            }).collect(Collectors.toList());

            Assert.isTrue(CollectionUtils.isNullOrEmpty(storeRels),"第"+(i+1)+"的数据重复,同一仓库下，同一物流公司，已存在该地址的配送区域");


        }

        Assert.isTrue(mapper.batchInsert(zoneStoreRels) > 0 ,"导入execl数据失败");
    }

}
