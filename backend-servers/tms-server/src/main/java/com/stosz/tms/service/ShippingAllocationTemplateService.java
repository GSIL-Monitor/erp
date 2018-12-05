package com.stosz.tms.service;

import com.stosz.plat.utils.BeanMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.tms.mapper.ShippingAllocationTemplateMapper;
import com.stosz.tms.mapper.ShippingWayMapper;
import com.stosz.tms.model.ShippingAllocationTemplate;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.vo.ShippingAllocationTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShippingAllocationTemplateService {

    @Autowired
    private ShippingAllocationTemplateMapper mapper;

    @Resource
    private ShippingWayMapper shippingWayMapper;

    @Resource
    private IStockService stockService;

    @Resource
    private IStorehouseService storehouseService;

    public List<ShippingAllocationTemplate> queryAssignTemplate(List<Integer> shippingWayIds, Integer wmsId) {
        return mapper.queryAssignTemplate(shippingWayIds, wmsId);
    }

    public int count(ShippingAllocationTemplate allocationTemplate) {
        return mapper.count(allocationTemplate);
    }

    public List<ShippingAllocationTemplateVo> queryList(ShippingAllocationTemplate allocationTemplate) {
        final List<ShippingAllocationTemplate> allocationTemplates = mapper.queryList(allocationTemplate);

        //物流方式数据
        final Set<Integer> shippingWayIds = allocationTemplates.stream().map(ShippingAllocationTemplate::getShippingWayId).collect(Collectors.toSet());
        final List<ShippingWay> shippingWays = shippingWayMapper.queryByIds(shippingWayIds);
        final Map<Integer, List<ShippingWay>> shippingWayMapById = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getId));

        final List<Integer> wmsIdList = new ArrayList<>(allocationTemplates.stream().map(ShippingAllocationTemplate::getWmsId).collect(Collectors.toSet()));

        final List<Wms> wmss = storehouseService.findWmsByIds(wmsIdList);
        final Map<Integer, List<Wms>> wmsMapById = wmss.stream().collect(Collectors.groupingBy(Wms::getId));

        final List<ShippingAllocationTemplateVo> allocationTemplateVos = BeanMapper.mapList(allocationTemplates, ShippingAllocationTemplateVo.class);

        allocationTemplateVos.forEach(e -> {
            final Integer shippingWayId = e.getShippingWayId();
            final List<ShippingWay> shippingWayList = shippingWayMapById.get(shippingWayId);
            if (CollectionUtils.isNotNullAndEmpty(shippingWayList)) {
                e.setShippingWayName(shippingWayList.get(0).getShippingWayName());
            }

            final Integer wmsId = e.getWmsId();
            final List<Wms> wmsList = wmsMapById.get(wmsId);
            if (CollectionUtils.isNotNullAndEmpty(wmsList)) {
                e.setWmsName(wmsList.get(0).getName());
            }
        });

        return allocationTemplateVos;
    }


    public void add(ShippingAllocationTemplate allocationTemplate) {
        ShippingAllocationTemplate paramBean = new ShippingAllocationTemplate();
        paramBean.setShippingWayId(allocationTemplate.getShippingWayId());
        paramBean.setWmsId(allocationTemplate.getWmsId());

        if (CollectionUtils.isNotNullAndEmpty(mapper.queryList(paramBean))) {
            throw new RuntimeException("该物流方式与仓库的物流配已存在!");
        }
        Assert.isTrue(mapper.add(allocationTemplate) > 0, "物流配额保存失败!");
    }


    /**
     * 更新状态
     */
    public void updateEnable(ShippingAllocationTemplate allocationTemplate) {
        Assert.isTrue(mapper.update(allocationTemplate) > 0, "更新物流配额状态失败!");
    }


    public void update(ShippingAllocationTemplate allocationTemplate) {
        ShippingAllocationTemplate paramBean = new ShippingAllocationTemplate();
        paramBean.setShippingWayId(allocationTemplate.getShippingWayId());
        paramBean.setWmsId(allocationTemplate.getWmsId());

        final List<ShippingAllocationTemplate> templates = mapper.queryList(paramBean);
        if (CollectionUtils.isNotNullAndEmpty(templates) && !templates.get(0).getId().equals(allocationTemplate.getId())) {
            throw new RuntimeException("该物流方式与仓库的物流配已存在!");
        }
        Assert.isTrue(mapper.update(allocationTemplate) > 0, "更新物流配额失败!");
    }


}
