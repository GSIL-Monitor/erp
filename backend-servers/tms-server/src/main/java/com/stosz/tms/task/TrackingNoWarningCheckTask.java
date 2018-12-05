package com.stosz.tms.task;

import com.google.common.collect.Lists;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.FreemarkerUtils;
import com.stosz.plat.utils.MailSendUtils;
import com.stosz.tms.enums.DictionaryTypeEnum;
import com.stosz.tms.mapper.ShippingMapper;
import com.stosz.tms.mapper.ShippingStoreRelationMapper;
import com.stosz.tms.mapper.ShippingTrackingNoListMapper;
import com.stosz.tms.model.*;
import com.stosz.tms.service.BaseDictionaryService;
import com.stosz.tms.service.ShippingWayService;
import com.stosz.tms.vo.TrackingNoWarningListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 运单号数量检测任务
 */
@Component
@EnableScheduling
public class TrackingNoWarningCheckTask {

    private Logger logger = LoggerFactory.getLogger(BlackCatTrackTask.class);

    @Resource
    private ShippingWayService shippingWayService;

    @Resource
    private ShippingTrackingNoListMapper trackingNoListMapper;

    @Resource
    private ShippingMapper shippingMapper;

    @Resource
    private ShippingStoreRelationMapper storeRelationMapper;

    @Resource
    private MailSendUtils sendUtils;

    @Resource
    private BaseDictionaryService dictionaryService;

//    @Scheduled(cron = "0 0 0 0/1 * ?")
    public void trackingNoCheck(){
        ShippingWay paramBean = new ShippingWay();
        paramBean.setStart(0);
        paramBean.setLimit(1000000);
        paramBean.setState(1);

        List<ShippingWay> shippingWays = shippingWayService.queryList(paramBean);


        if (CollectionUtils.isNullOrEmpty(shippingWays))
             return;

         //过滤掉不需要预警的物流方式
        shippingWays =  shippingWays.stream().filter(e -> {
            if (e.getNeedTrackNum() == 0)
                return false;
            if (e.getTrackNoWaringEnable() == 0)
                return false;
            return true;
        }).collect(Collectors.toList());

        if (CollectionUtils.isNullOrEmpty(shippingWays))
            return;


        final Set<Integer> shippingIds = shippingWays.stream().map(ShippingWay::getShippingId).collect(Collectors.toSet());
        final List<Integer> shippingWayIds = new ArrayList<Integer>(shippingWays.stream().map(ShippingWay::getId).collect(Collectors.toSet()));


        //运单号列表
        ShippingTrackingNoList trackingNoParamBean = new ShippingTrackingNoList();
        trackingNoParamBean.setShippingWayIdList(shippingWayIds);
        trackingNoParamBean.setTrackStatus(0);

        final List<ShippingTrackingNoList> trackingNoLists = trackingNoListMapper.queryList(trackingNoParamBean);
        final Map<Integer, List<ShippingTrackingNoList>> trackingNoMapByShippingWayId = trackingNoLists.stream().collect(Collectors.groupingBy(ShippingTrackingNoList::getShippingWayId));


        //物流公司
        final List<Shipping> shippings = shippingMapper.queryByIds(shippingIds);
        final Map<Integer, List<Shipping>> shippingMapById  = shippings.stream().collect(Collectors.groupingBy(Shipping::getId));


        //物流方式与仓库关联
        ShippingStoreRel storeRelParamBean = new ShippingStoreRel();
        storeRelParamBean.setEnable(1);
        storeRelParamBean.setShippingWayIdList(shippingWayIds);

        final List<ShippingStoreRel> shippingStoreRels = storeRelationMapper.select(storeRelParamBean);
        final Map<Integer, List<ShippingStoreRel>> storeRelMapByShippingWayId = shippingStoreRels.stream().collect(Collectors.groupingBy(ShippingStoreRel::getShippingWayId));
        final Map<Integer, List<ShippingStoreRel>> storeRelMapByWmsId = shippingStoreRels.stream().collect(Collectors.groupingBy(ShippingStoreRel::getWmsId));


        List<TrackingNoWarningListVo> warningListVos = Lists.newArrayList();

        shippingWays.forEach(e -> {
            final Integer shippingWayId = e.getId();
            final Integer trackNoWarningNum = e.getTrackNoWarningNum();
            final Integer shippingId = e.getShippingId();

            final List<Shipping> shippingList = shippingMapById.get(shippingId);
            if (CollectionUtils.isNullOrEmpty(shippingList))
                return;

            final List<ShippingStoreRel> storeRelList = storeRelMapByShippingWayId.get(shippingWayId);
            if (CollectionUtils.isNullOrEmpty(storeRelList))
                return;

            final Shipping shipping = shippingList.get(0);

             List<ShippingTrackingNoList> trackingNoListList = trackingNoMapByShippingWayId.get(shippingWayId);

            if (CollectionUtils.isNullOrEmpty(trackingNoListList))
                trackingNoListList = Collections.emptyList();

            final Map<Integer, List<ShippingTrackingNoList>> trackingNoMapByWmsId = trackingNoListList.stream().collect(Collectors.groupingBy(ShippingTrackingNoList::getWmsId));


            final Set<Integer> wmsIds = storeRelList.stream().map(ShippingStoreRel::getWmsId).collect(Collectors.toSet());


            for (Integer wmsId: wmsIds
                 ) {
                final List<ShippingStoreRel> shippingStoreRelsList = storeRelMapByWmsId.get(wmsId);

                final List<ShippingTrackingNoList> trackingNoListListByWmsId = trackingNoMapByWmsId.get(wmsId);

                final String wmsName = shippingStoreRelsList.get(0).getWmsName();

                if (CollectionUtils.isNullOrEmpty(trackingNoListListByWmsId)){

                    for(int i = 0 ; i < 3 ; i++){
                        TrackingNoWarningListVo warningListVo = new TrackingNoWarningListVo();
                        warningListVo.setWmsName(wmsName);
                        warningListVo.setWarningNo(e.getTrackNoWarningNum().toString());
                        warningListVo.setShippingWayName(e.getShippingWayName());
                        warningListVo.setShippingWayCode(e.getShippingWayCode());
                        warningListVo.setShippingName(shipping.getShippingName());
                        warningListVo.setShippingCode(shipping.getShippingCode());
                        warningListVo.setNowExistNo("0");
                        switch (i){
                            case 0:
                                warningListVo.setProductType("所有");
                                break;
                            case 1:
                                warningListVo.setProductType("普货");
                                break;
                            case 2:
                                warningListVo.setProductType("特货");
                                break;
                        }
                        warningListVos.add(warningListVo);
                    }
                }else{
                    final Map<Integer, List<ShippingTrackingNoList>> trackingNoMapByType = trackingNoListListByWmsId.stream().collect(Collectors.groupingBy(ShippingTrackingNoList::getProductType));

                    for (Integer type: trackingNoMapByType.keySet()
                            ) {
                        final List<ShippingTrackingNoList> trackingNoListListByStatus = trackingNoMapByType.get(type);

                        if (trackingNoListListByStatus.size() < trackNoWarningNum){
                            TrackingNoWarningListVo warningListVo = new TrackingNoWarningListVo();
                            warningListVo.setWmsName(wmsName);
                            warningListVo.setWarningNo(e.getTrackNoWarningNum().toString());
                            warningListVo.setShippingWayName(e.getShippingWayName());
                            warningListVo.setShippingWayCode(e.getShippingWayCode());
                            warningListVo.setShippingName(shipping.getShippingName());
                            warningListVo.setShippingCode(shipping.getShippingCode());
                            warningListVo.setNowExistNo(trackingNoListListByStatus.size()+"");
                            switch (type){
                                case 0:
                                    warningListVo.setProductType("所有");
                                    break;
                                case 1:
                                    warningListVo.setProductType("普货");
                                    break;
                                case 2:
                                    warningListVo.setProductType("特货");
                                    break;
                            }
                            warningListVos.add(warningListVo);
                        }
                    }
                }
             }
        });

        if (CollectionUtils.isNullOrEmpty(warningListVos))
            return;

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("warningDataList",warningListVos);

        final String htmlData = FreemarkerUtils.format("/freemarker/trackNoWarning.ftl", dataMap);

        final List<BaseDictionary> dictionaries = dictionaryService.query(DictionaryTypeEnum.MAIL_NOTIFY_NAME.name());

        if (CollectionUtils.isNullOrEmpty(dictionaries))
            return;

        dictionaries.forEach(e -> {
            try {
                sendUtils.sendHtmlMail(e.getDicValue(),"运单号数值告警",htmlData );
            } catch (MessagingException ex) {
                logger.error("发送运单号告警邮件异常!",ex);
            };
        });

        System.out.print(warningListVos);
    }
}
