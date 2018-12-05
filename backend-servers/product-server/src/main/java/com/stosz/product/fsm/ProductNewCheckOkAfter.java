package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.common.MBox;
import com.stosz.product.ext.enums.ProductEvent;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.enums.ProductZoneEvent;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.*;
import com.stosz.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductNewCheckOkAfter extends IFsmHandler<ProductNew>{
    @Autowired
    private ProductNewService productNewService;
    @Autowired
    private ProductNewZoneService productNewZoneService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductZoneService productZoneService;

    @Autowired
    private ZoneService zoneService;
    @Resource
    private ProductNewSiftImageService productNewSiftImageService;


    @Override
    public void execute(ProductNew productNew, EventModel event) {
        //删除对比图片的记录
        productNewSiftImageService.deleteByProductNewId(productNew.getId());

        productNewService.updateCheckUserInfo(productNew.getId());
        Product p = null;
        if (productNew.getSpu() == null || productNew.getSpu().equals("")) {
            p = new Product();
            p.setProductNewId(productNew.getId());
            p.setCategoryId(productNew.getCategoryId());
            p.setChecker(MBox.getLoginUserName());
            p.setCheckerId(MBox.getLoginUserId());
            p.setCreatorId(productNew.getCreatorId());
            p.setCreator(productNew.getCreator());
            p.setClassifyEnum(productNew.getClassifyEnum());
            p.setTitle(productNew.getTitle());
            p.setInnerName(productNew.getInnerName());
            p.setSourceEnum(productNew.getSourceEnum());
            p.setSourceUrl(productNew.getSourceUrl());
            p.setMainImageUrl(productNew.getMainImageUrl());
            p.setPurchaseUrl(productNew.getPurchaseUrl());
            p.setTotalStock(0);
            p.setMemo(productNew.getMemo());
            p.setAttributeDesc(productNew.getAttributeDesc());
            p.setCustomEnum(productNew.getCustomEnum());
            productService.createProduct(p);
            productNew.setSpu( p.getSpu() );
            productNewService.updateSpu(productNew.getId() , p.getSpu());
        }else{
            p = productService.loadBySpu(productNew.getSpu());
            ProductState productState =p.getStateEnum();
            if (productState == ProductState.waitFill || productState == ProductState.archiving || productState == ProductState.onsale) {
                // 不做任何处理
            }else
            if (productState == ProductState.disappeared || productState == ProductState.offsale) {
                productService.processEvent(p, ProductEvent.archiving , "此产品曾经被下架或者销档，再次通过排重，重新准备上架.",MBox.getLoginUserName());
            }

        }


        Assert.notNull(p,"spu:" + productNew.getSpu() + " 在数据库中不存在！");

        List<ProductZone> productZones= productZoneService.findByProductId(p.getId());

        Integer productNewDepartmentId = productNew.getDepartmentId();

        //找出本部门未消档的区域
        List<ProductZone> departProductZones = productZones.stream().filter(e->
                e.getDepartmentId().equals(productNewDepartmentId) ).collect(Collectors.toList());
        logger.info("在新品审核通过时 ，检查对应的产品的本部门的区域，新品id:{} spu:{} 产品id:{} 本部门的区域:{}" , productNew.getId() , p.getId() , p.getSpu() , departProductZones);

        List<ProductNewZone> productNewZones = productNewZoneService.findByProductNewId(productNew.getId());

        Assert.notEmpty(productNewZones, "新品完成审核通过，由于没有区域！新品id:" + productNew.getId());

        //将新品的区域信息插入到产品区域表中，但是要检查如果已经存在，则不能插入，忽略即可

        List<Integer> newZoneIds = productNewZones.stream().map(ProductNewZone::getZoneId).collect(Collectors.toList());

        Map<Integer , Zone> zoneMap= zoneService.findByIds(newZoneIds);

        for (ProductNewZone newZone : productNewZones) {
            Optional<ProductZone> pzone =  departProductZones.stream().filter(e-> e.getZoneId().equals( newZone.getZoneId() ) ).findAny();
            logger.info(String.valueOf(pzone.isPresent()));
            if (pzone.isPresent()) {
                ProductZoneState pzoneState = pzone.get().getStateEnum();
                logger.warn("新品审核通过插入到产品区域表中时，发现已经存在，应该将产品区域的当前状态:{} 跳转到 {} ！ 新品id:{} spu:{} 产品id:{} 产品区域id:{} 区域id:{} 区域名称:{} ",
                        pzone.get().getState() , ProductZoneState.archiving,
                        newZone.getProductNewId() , p.getSpu() , p.getId() , pzone.get().getId() , pzone.get().getZoneId(),pzone.get().getZoneName());

                if (pzoneState == ProductZoneState.disappeared || pzoneState == ProductZoneState.offsale) {
                    productZoneService.processEvent(pzone.get(), ProductZoneEvent.create,"之前排重过，再一次通过排重，进入建档中状态。",MBox.getLoginUserName());
                    productZoneService.updateCreator(pzone.get().getId(), productNew.getCreatorId(), productNew.getCreator(), LocalDateTime.now());
                }
                if (pzoneState == ProductZoneState.waitoffsale) {
                    productZoneService.processEvent(pzone.get(), ProductZoneEvent.archive, "之前消档,停留在待下架,再一次通过排重,重新直接上架。", MBox.getLoginUserName());
                    productZoneService.updateCreator(pzone.get().getId(), productNew.getCreatorId(), productNew.getCreator(), LocalDateTime.now());
                }
                continue;
            }

            Zone z = zoneMap.get(newZone.getZoneId());
            Assert.notNull(z, "查询不到对应的区域信息！");
            ProductZone productZoneTmp = new ProductZone();

            productZoneTmp.setZoneName(z.getTitle());
            productZoneTmp.setZoneCode(z.getCode());
            productZoneTmp.setZoneId(z.getId());
            productZoneTmp.setDepartmentId(productNewDepartmentId);
            productZoneTmp.setDepartmentNo(productNew.getDepartmentNo());
            productZoneTmp.setDepartmentName(productNew.getDepartmentName());
            productZoneTmp.setCreatorId(productNew.getCreatorId());
            productZoneTmp.setCreator(productNew.getCreator());
            productZoneTmp.setProductNewId(productNew.getId());
            productZoneTmp.setProductId(p.getId());
            productZoneTmp.setState(ProductZoneState.archiving.name());
            productZoneTmp.setStateTime(LocalDateTime.now());
            productZoneTmp.setLastOrderAt(LocalDateTime.now());

            productZoneTmp.setStock(0);
            productZoneService.create(productZoneTmp);

        }


    }
}
