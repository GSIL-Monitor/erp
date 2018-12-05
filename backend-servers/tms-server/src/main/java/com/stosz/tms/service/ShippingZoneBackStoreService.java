package com.stosz.tms.service;

import com.google.common.collect.Lists;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.tms.mapper.ShippingStoreRelationMapper;
import com.stosz.tms.mapper.ShippingWayMapper;
import com.stosz.tms.mapper.ShippingZoneBackStoreMapper;
import com.stosz.tms.model.*;
import com.stosz.tms.vo.ShippingZoneBackStoreExportVo;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShippingZoneBackStoreService  {

    private Logger logger = LoggerFactory.getLogger(TrackingTaskService.class);


    @Resource
    private ShippingZoneBackStoreMapper mapper;

    @Resource
    private ShippingStoreRelationMapper storeRelationMapper;

    @Resource
    private IStorehouseService storehouseService;

    @Resource
    private IZoneService zoneService;

    @Resource
    private ShippingWayMapper shippingWayMapper;





    public int count(ShippingZoneBackStore zoneBackStore) {
        return mapper.count(zoneBackStore);
    }

    public List<ShippingZoneBackStore> queryList(ShippingZoneBackStore zoneBackStore) {
        if (!StringUtils.hasText(zoneBackStore.getOrderBy())) {
            zoneBackStore.setOrderBy(" update_at desc,create_at");
        }
        return mapper.query(zoneBackStore);
    }


    public void add(ShippingZoneBackStore shippingZoneBackStore){
        checkDataExist(shippingZoneBackStore);
        Assert.isTrue(mapper.add(shippingZoneBackStore) >0 ,"新增物流退回仓失败!");
    }

    private void checkDataExist(ShippingZoneBackStore shippingZoneBackStore) {
        ShippingStoreRel storeRelParamBean = new ShippingStoreRel();
        storeRelParamBean.setShippingWayId(shippingZoneBackStore.getShippingWayId());
        storeRelParamBean.setWmsId(shippingZoneBackStore.getWmsId());

        final List<ShippingStoreRel> shippingStoreRels = storeRelationMapper.select(storeRelParamBean);

        Assert.notEmpty(shippingStoreRels,"该仓库下没有配置对应的物流线路");

        ShippingZoneBackStore paramBean =  new ShippingZoneBackStore();
        paramBean.setWmsId(shippingZoneBackStore.getWmsId());
        paramBean.setShippingWayId(shippingZoneBackStore.getShippingWayId());
        paramBean.setZoneId(shippingZoneBackStore.getZoneId());
        paramBean.setStart(0);
        paramBean.setLimit(1);

        final List<ShippingZoneBackStore> storeRels = mapper.query(paramBean);

        Assert.isTrue( (storeRels == null || storeRels.isEmpty()) || (shippingZoneBackStore.getId() != null && storeRels.get(0).getId().equals(shippingZoneBackStore.getId()) )  ,"同仓库、物流线路、覆盖区域下，已存在该相应的退件仓");
    }


    public void updateEnable(ShippingZoneBackStore shippingZoneBackStore){
        Assert.isTrue(mapper.update(shippingZoneBackStore) > 0 ,"更新物流退回仓状态失败");
    }

    public void update(ShippingZoneBackStore shippingZoneBackStore){
        checkDataExist(shippingZoneBackStore);
        Assert.isTrue(mapper.update(shippingZoneBackStore) > 0 ,"更新物流退回仓失败");
    }


    @Transactional(rollbackFor = Exception.class)
    public List<ShippingZoneBackStoreExportVo> importExeclData(List<List<String>> importData){

        List<ShippingZoneBackStoreExportVo> failData = Lists.newArrayList();

        ShippingWay shippingWayParamBean = new ShippingWay();
        shippingWayParamBean.setStart(0);
        shippingWayParamBean.setLimit(Integer.MAX_VALUE);

        final List<ShippingWay> shippingWays = shippingWayMapper.queryList(shippingWayParamBean);

        final Map<String, List<ShippingWay>> shippingWayMapByCode = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getShippingWayCode));

        Wms wmsParamBean = new Wms();
        wmsParamBean.setStart(0);
        wmsParamBean.setLimit(Integer.MAX_VALUE);

        final List<Wms> wmsList = storehouseService.wmsTransferRequest(wmsParamBean);

        final Map<Integer, List<Wms>> wmsMapById = wmsList.stream().collect(Collectors.groupingBy(Wms::getId));

        final List<Zone> zoneList = zoneService.findAll();
        final Map<Integer, List<Zone>> zoneMapById = zoneList.stream().collect(Collectors.groupingBy(Zone::getId));


        final List<ShippingZoneBackStore> zoneBackStores = mapper.queryAll(new ShippingZoneBackStore());

        final List<String> existBackStoreList = zoneBackStores.stream().map(e -> {
            final Integer wmsId = e.getWmsId();
            final Integer zoneId = e.getZoneId();
            final String shippingWayCode = e.getShippingWayCode();
            return wmsId + shippingWayCode + zoneId;
        }).collect(Collectors.toList());

        ShippingStoreRel storeRelParamBean = new ShippingStoreRel();
        storeRelParamBean.setStart(0);
        storeRelParamBean.setLimit(Integer.MAX_VALUE);

        final List<ShippingStoreRel> shippingStoreRels = storeRelationMapper.select(storeRelParamBean);

        final List<String> existShippingWayStoreList = shippingStoreRels.stream().map(e -> {
            return e.getShippingWayId() + "" + e.getWmsId();
        }).collect(Collectors.toList());


        final List<ShippingZoneBackStore> zoneBackStoreDataList = Lists.newArrayList();

        importData.forEach( e -> {
            ShippingZoneBackStoreExportVo zoneBackStoreExportVo = new ShippingZoneBackStoreExportVo();

            try {
                final String wmsIdStr = e.get(0);
                final String shippingWayCode = e.get(1);
                final String zoneIdStr = e.get(2);
                final String backWmsIdStr = e.get(3);

                zoneBackStoreExportVo.setWmsId(wmsIdStr);
                zoneBackStoreExportVo.setShippingWayCode(shippingWayCode);
                zoneBackStoreExportVo.setZoneId(zoneIdStr);
                zoneBackStoreExportVo.setBackWmsId(backWmsIdStr);


                if (StringUtils.isEmpty(wmsIdStr)){
                    zoneBackStoreExportVo.setFailMessage("仓库ID不能为空");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }
                if (StringUtils.isEmpty(shippingWayCode)){
                    zoneBackStoreExportVo.setFailMessage("物流线路代码不能为空");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }
                if (StringUtils.isEmpty(zoneIdStr)){
                    zoneBackStoreExportVo.setFailMessage("配送区域ID不能为空");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }
                if (StringUtils.isEmpty(backWmsIdStr)){
                    zoneBackStoreExportVo.setFailMessage("退回仓库ID不能为空");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }



                int wmsId = 0;
                Wms wmsInfo = null;

                try {
                    wmsId =   Integer.valueOf(wmsIdStr);
                    final List<Wms> wmss = wmsMapById.get(wmsId);

                    if (CollectionUtils.isNullOrEmpty(wmss)){
                        zoneBackStoreExportVo.setFailMessage("通过仓库ID查找数据失败");
                        failData.add(zoneBackStoreExportVo);
                        return;
                    }
                    wmsInfo =  wmss.get(0);
                }catch (Exception e1){
                    zoneBackStoreExportVo.setFailMessage("仓库ID不为数字");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }

                ShippingWay shippingWayInfo = null;

                final List<ShippingWay> shippingWayList = shippingWayMapByCode.get(shippingWayCode);
                if (CollectionUtils.isNullOrEmpty(shippingWayList)){
                    zoneBackStoreExportVo.setFailMessage("通过物流线路代码查找数据失败");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }
                shippingWayInfo =  shippingWayList.get(0);


                int zoneId = 0;
                Zone zoneInfo = null;

                try {
                    zoneId =   Integer.valueOf(zoneIdStr);
                    final List<Zone> zones = zoneMapById.get(zoneId);
                    if (CollectionUtils.isNullOrEmpty(zones)){
                        zoneBackStoreExportVo.setFailMessage("通过区域ID查找数据失败");
                        failData.add(zoneBackStoreExportVo);
                        return;
                    }
                    zoneInfo =  zones.get(0);
                }catch (Exception e1){
                    zoneBackStoreExportVo.setFailMessage("配送区域ID不为数字");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }

                int backWmsId = 0;
                Wms backWmsInfo = null;

                try {
                    backWmsId =   Integer.valueOf(backWmsIdStr);
                    final List<Wms> wmss = wmsMapById.get(backWmsId);

                    if (CollectionUtils.isNullOrEmpty(wmss)){
                        zoneBackStoreExportVo.setFailMessage("通过退回仓库ID查找数据失败");
                        failData.add(zoneBackStoreExportVo);
                        return;
                    }
                    backWmsInfo =  wmss.get(0);
                }catch (Exception e1){
                    zoneBackStoreExportVo.setFailMessage("退回仓库ID不为数字");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }

                if (!existShippingWayStoreList.contains(shippingWayInfo.getId()+wmsIdStr)){
                    zoneBackStoreExportVo.setFailMessage("该仓库下没有配置对应的物流线路");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }

                if(existBackStoreList.contains(wmsIdStr+shippingWayCode+zoneIdStr)){
                    zoneBackStoreExportVo.setFailMessage("同仓库、物流线路、覆盖区域下，已存在该相应的退件仓");
                    failData.add(zoneBackStoreExportVo);
                    return;
                }

                existBackStoreList.add(wmsIdStr+shippingWayCode+zoneIdStr);

                UserDto userDto= ThreadLocalUtils.getUser();


                ShippingZoneBackStore zoneBackStore = new ShippingZoneBackStore();
                zoneBackStore.setBackWmsId(backWmsId);
                zoneBackStore.setBackWmsName(backWmsInfo.getName());
                zoneBackStore.setCreatorId(userDto.getId());
                zoneBackStore.setCreator(userDto.getLastName());
                zoneBackStore.setEnable(1);
                zoneBackStore.setShippingWayId(shippingWayInfo.getId());
                zoneBackStore.setWmsId(wmsId);
                zoneBackStore.setWmsName(wmsInfo.getName());
                zoneBackStore.setZoneId(zoneId);
                zoneBackStore.setZoneName(zoneInfo.getTitle());

                zoneBackStoreDataList.add(zoneBackStore);
            } catch (Exception e1) {
                logger.error("保存物流轨迹信息失败", e1);
                zoneBackStoreExportVo.setFailMessage(String.format("出现异常，异常信息:%s", e1.getMessage()));
                failData.add(zoneBackStoreExportVo);
                return;

            }
        });

        if (zoneBackStoreDataList.size() > 0)
            Assert.isTrue(mapper.batchInsert(zoneBackStoreDataList) == zoneBackStores.size() , "保存退回仓信息失败");

        return failData;
    }

}
