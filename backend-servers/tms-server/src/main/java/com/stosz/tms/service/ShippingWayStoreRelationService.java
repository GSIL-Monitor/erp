package com.stosz.tms.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.stosz.plat.enums.LabelTypeEnum;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.model.Label;
import com.stosz.product.ext.model.LabelObject;
import com.stosz.product.ext.service.ILabelObjectServcie;
import com.stosz.tms.dto.ShippingStoreRelLableAddDto;
import com.stosz.tms.enums.ShippingWayLableRelTypeEnum;
import com.stosz.tms.vo.ShippingWayStoreRelLableListVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.stosz.plat.utils.BeanMapper;
import com.stosz.tms.mapper.ShippingAllocationTemplateMapper;
import com.stosz.tms.mapper.ShippingStoreRelationMapper;
import com.stosz.tms.mapper.ShippingWayMapper;
import com.stosz.tms.mapper.ShippingZoneStoreRelMapper;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.vo.ShippingStoreRelationListVo;
import org.springframework.util.StringUtils;

@Service
public class ShippingWayStoreRelationService {


    @Resource
    private ShippingStoreRelationMapper mapper;

    @Resource
    private ShippingWayMapper shippingWayMapper;

    @Resource
    private ShippingAllocationTemplateMapper allocationTemplateMapper;

    @Resource
    private ShippingZoneStoreRelMapper zoneStoreRelMapper;

    @Resource
    private ILabelObjectServcie LabelObjectServcie;

    /**
     * 查询物流线路仓库关联并封装数据
     * @param shippingStoreRel
     * @return
     */
    public List<ShippingStoreRelationListVo> query(ShippingStoreRel shippingStoreRel){
        List<ShippingStoreRel> shippingStoreRels = mapper.select(shippingStoreRel);

        Set<Integer> shippingWayIds = shippingStoreRels.stream().map(ShippingStoreRel::getShippingWayId).collect(Collectors.toSet());

        List<ShippingWay> shippingWays = shippingWayMapper.queryByIds(shippingWayIds);

        final Map<Integer, List<ShippingWay>> shippingWayMapById = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getId));

        final List<ShippingStoreRelationListVo> storeRelationListVos = BeanMapper.mapList(shippingStoreRels, ShippingStoreRelationListVo.class);


        storeRelationListVos.forEach(e -> {
            final Integer shippingWayId = e.getShippingWayId();

            final List<ShippingWay> wayList = shippingWayMapById.get(shippingWayId);

            //没有找到数据
            if (wayList == null || wayList.isEmpty())
                return;

            final ShippingWay shippingWay = wayList.get(0);

//            e.setAllowedProductType(shippingWay.getAllowedProductType());
            e.setShippingWayCode(shippingWay.getShippingWayCode());
            e.setShippingWayName(shippingWay.getShippingWayName());

        });
        return storeRelationListVos;
    }

    public int count(ShippingStoreRel shippingStoreRel){
        return mapper.count(shippingStoreRel);
    }


    @Transactional(transactionManager = "tmsTransactionManager",rollbackFor = Exception.class)
    public void add(ShippingStoreRel shippingStoreRel){

        ShippingStoreRel paramBean = null;

        if (!StringUtils.isEmpty(shippingStoreRel.getShippingGeneralName())){
            paramBean=  new ShippingStoreRel();
            paramBean.setShippingGeneralName(shippingStoreRel.getShippingGeneralName());
            Assert.isTrue(CollectionUtils.isNullOrEmpty(mapper.select(paramBean)),"物流别名已存在!");
        }
        if (!StringUtils.isEmpty(shippingStoreRel.getShippingSpeciaName())){
            paramBean=  new ShippingStoreRel();
            paramBean.setShippingSpeciaName(shippingStoreRel.getShippingSpeciaName());
            Assert.isTrue(CollectionUtils.isNullOrEmpty(mapper.select(paramBean)),"物流别名已存在!");
        }
        if (!StringUtils.isEmpty(shippingStoreRel.getShippingGeneralCode())){
            paramBean=  new ShippingStoreRel();
            paramBean.setShippingGeneralCode(shippingStoreRel.getShippingGeneralCode());
            Assert.isTrue(CollectionUtils.isNullOrEmpty(mapper.select(paramBean)),"物流别名code已存在!");
        }
        if (!StringUtils.isEmpty(shippingStoreRel.getShippingSpeciaCode())){
            paramBean=  new ShippingStoreRel();
            paramBean.setShippingSpeciaCode(shippingStoreRel.getShippingSpeciaCode());
            Assert.isTrue(CollectionUtils.isNullOrEmpty(mapper.select(paramBean)),"物流别名code已存在!");
        }

        Assert.isTrue(mapper.add(shippingStoreRel) >0 ,"新增物流线路仓库关系失败!");
        final List<ShippingStoreRelLableAddDto> lableDataList = shippingStoreRel.getLableDataList();
        if (CollectionUtils.isNotNullAndEmpty(lableDataList)){
            List<LabelObject> LabelObjects = Lists.newArrayList();
            lableDataList.forEach( e -> {
                final Integer type = e.getType();
                final List<Integer> lableList = e.getLableList();
                if (CollectionUtils.isNotNullAndEmpty(lableList)){
                    lableList.forEach(l -> {
                        LabelObject LabelObject = new LabelObject();
                        LabelObject.setLabelId(l);
                        LabelObject.setExtendValue(String.valueOf(type));
                        LabelObject.setObjectId(shippingStoreRel.getId());
                        LabelObject.setObjectType(LabelTypeEnum.Logistics.name());
                        LabelObject.setCreator(shippingStoreRel.getCreator());
                        LabelObject.setCreatorId(shippingStoreRel.getCreatorId());

                        LabelObjects.add(LabelObject);
                    });
                }
            });
            LabelObjectServcie.addLabel(LabelObjects);
        }
    }

    public void updateEnable(ShippingStoreRel shippingStoreRel){
        if (shippingStoreRel.getEnable() == 1){
            final ShippingStoreRel shippingStoreRelById = mapper.getById(shippingStoreRel.getId());
            final Integer shippingWayId = shippingStoreRelById.getShippingWayId();
            final Integer wmsId = shippingStoreRelById.getWmsId();
            Assert.notEmpty(zoneStoreRelMapper.queryByShippingWayIdAndWmsId(shippingWayId,wmsId),"未设置配送区域，不能启用该物流");
            Assert.notEmpty(allocationTemplateMapper.queryByShippingWayIdAndWmsId(shippingWayId,wmsId),"相应地区末设置派单数量规则，不能启用该物流");
        }
        Assert.isTrue(mapper.update(shippingStoreRel) > 0 ,"更新物流线路仓库关系状态失败");
    }

    public void update(ShippingStoreRel shippingStoreRel){
        ShippingStoreRel paramBean = new ShippingStoreRel();
        paramBean.setShippingWayId(shippingStoreRel.getShippingWayId());
        paramBean.setWmsId(shippingStoreRel.getWmsId());

         List<ShippingStoreRel> storeRels = mapper.select(paramBean);

        Assert.isTrue(CollectionUtils.isNullOrEmpty(storeRels)  || storeRels.get(0).getId().equals(shippingStoreRel.getId()) ,"该仓库下已存在该物流线路");


        if (!StringUtils.isEmpty(shippingStoreRel.getShippingGeneralName())){
            paramBean=  new ShippingStoreRel();
            paramBean.setShippingGeneralName(shippingStoreRel.getShippingGeneralName());
            storeRels = mapper.select(paramBean);
            Assert.isTrue(CollectionUtils.isNullOrEmpty(storeRels) || storeRels.get(0).getId().equals(shippingStoreRel.getId()),"物流别名已存在!");
        }
        if (!StringUtils.isEmpty(shippingStoreRel.getShippingSpeciaName())){
            paramBean=  new ShippingStoreRel();
            paramBean.setShippingSpeciaName(shippingStoreRel.getShippingSpeciaName());
            storeRels = mapper.select(paramBean);
            Assert.isTrue(CollectionUtils.isNullOrEmpty(storeRels) || storeRels.get(0).getId().equals(shippingStoreRel.getId()),"物流别名已存在!");
        }
        if (!StringUtils.isEmpty(shippingStoreRel.getShippingGeneralCode())){
            paramBean=  new ShippingStoreRel();
            paramBean.setShippingGeneralCode(shippingStoreRel.getShippingGeneralCode());
            storeRels = mapper.select(paramBean);
            Assert.isTrue(CollectionUtils.isNullOrEmpty(storeRels) || storeRels.get(0).getId().equals(shippingStoreRel.getId()),"物流别名code已存在!");
        }
        if (!StringUtils.isEmpty(shippingStoreRel.getShippingSpeciaCode())){
            paramBean=  new ShippingStoreRel();
            paramBean.setShippingSpeciaCode(shippingStoreRel.getShippingSpeciaCode());
            storeRels = mapper.select(paramBean);
            Assert.isTrue(CollectionUtils.isNullOrEmpty(storeRels) || storeRels.get(0).getId().equals(shippingStoreRel.getId()),"物流别名code已存在!");
        }

        Assert.isTrue(mapper.update(shippingStoreRel) > 0 ,"更新物流线路仓库关系失败");
        LabelObjectServcie.deleteLabel(shippingStoreRel.getId(),LabelTypeEnum.Logistics.name());
        final List<ShippingStoreRelLableAddDto> lableDataList = shippingStoreRel.getLableDataList();
        if (CollectionUtils.isNotNullAndEmpty(lableDataList)){
            List<LabelObject> LabelObjects = Lists.newArrayList();
            lableDataList.forEach( e -> {
                final Integer type = e.getType();
                final List<Integer> lableList = e.getLableList();
                if (CollectionUtils.isNotNullAndEmpty(lableList)){
                    lableList.forEach(l -> {
                        LabelObject LabelObject = new LabelObject();
                        LabelObject.setLabelId(l);
                        LabelObject.setExtendValue(String.valueOf(type));
                        LabelObject.setObjectId(shippingStoreRel.getId());
                        LabelObject.setObjectType(LabelTypeEnum.Logistics.name());
                        LabelObject.setCreator(shippingStoreRel.getCreator());
                        LabelObject.setCreatorId(shippingStoreRel.getCreatorId());

                        LabelObjects.add(LabelObject);
                    });
                }
            });
            LabelObjectServcie.addLabel(LabelObjects);
        }
    }

    public List<ShippingWayStoreRelLableListVo> getLableRelList(
            Integer shippingWayStoreRelId
    ){


        final List<Label> labelList = LabelObjectServcie.queryLabelByKey(LabelTypeEnum.Logistics.getKeyword());

        if (CollectionUtils.isNullOrEmpty(labelList))
            return Collections.emptyList();


        final ShippingWayLableRelTypeEnum[] lableRelTypeEnums = ShippingWayLableRelTypeEnum.values();

        List<ShippingWayStoreRelLableListVo> lableListVos = Lists.newArrayList();

         List<LabelObject> LabelObjects = null;

        if (shippingWayStoreRelId != null){
            LabelObjects =  LabelObjectServcie.queryLabelObject(shippingWayStoreRelId, LabelTypeEnum.Logistics);
        }else{
            LabelObjects = Lists.newArrayList();
        }

        final Map<Integer, List<LabelObject>> lableRelMapByType = LabelObjects.stream().collect(Collectors.groupingBy(v -> Integer.valueOf(v.getExtendValue())));

        for (ShippingWayLableRelTypeEnum typeEnum:lableRelTypeEnums
             ) {
            final Integer type = typeEnum.getType();

            final List<LabelObject> LabelObjectList = lableRelMapByType.get(type);

            ShippingWayStoreRelLableListVo lableListVo = new ShippingWayStoreRelLableListVo();
            lableListVo.setType(type);
            lableListVo.setTypeName(typeEnum.getTypeName());

            List<ShippingWayStoreRelLableListVo.LableRelationVo> lableRelationVos = Lists.newArrayList();

             List<Integer> existRelLableIds = null;

            if (CollectionUtils.isNotNullAndEmpty(LabelObjectList))
                existRelLableIds = LabelObjectList.stream().map(LabelObject::getLabelId).collect(Collectors.toList());
            else
                existRelLableIds = Lists.newArrayList();

            for (Label label:labelList
                 ) {
                ShippingWayStoreRelLableListVo.LableRelationVo lableRelationVo = new ShippingWayStoreRelLableListVo.LableRelationVo();
                lableRelationVo.setId(label.getId());
                lableRelationVo.setName(label.getName());

                if (existRelLableIds.contains(label.getId())){
                    lableRelationVo.setSelected(true);
                }

                lableRelationVos.add(lableRelationVo);
            }
            lableListVo.setLableRelationVos(lableRelationVos);

            lableListVos.add(lableListVo);
        }
        return lableListVos;
    }
}
