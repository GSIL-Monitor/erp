package com.stosz.order.service;

import com.google.common.base.Strings;
import com.stosz.order.ext.model.OrdersWebInterfaceRecord;
import com.stosz.order.ext.model.OrdersWebInterfaceRecordDetail;
import com.stosz.order.mapper.order.OrdersWebInterfaceRecordDetailMapper;
import com.stosz.order.mapper.order.OrdersWebInterfaceRecordMapper;
import com.stosz.plat.enums.InterfaceNameEnum;
import com.stosz.plat.enums.InterfaceTypeEnum;
import com.stosz.plat.enums.ResponseResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @auther carter
 * create time    2018-01-17
 */
@Service
public class OrdersWebInterfaceRecordService {

    private  static final  Logger logger = LoggerFactory.getLogger(OrdersWebInterfaceRecordService.class);

    @Autowired
    private OrdersWebInterfaceRecordMapper ordersWebInterfaceRecordMapper;

    @Autowired
    private OrdersWebInterfaceRecordDetailMapper ordersWebInterfaceRecordDetailMapper;


    /**
     * 按照条件查询推送记录；
     * @param interfaceTypeEnum
     * @param interfaceNameEnum
     * @param objectId
     * @param responseResultEnum
     * @param createAtStart
     * @param createAtEnd
     * @param start
     * @param limit
     * @return
     */
    public List<OrdersWebInterfaceRecord> findByCondition(InterfaceTypeEnum interfaceTypeEnum, InterfaceNameEnum interfaceNameEnum, Integer objectId, ResponseResultEnum responseResultEnum, LocalDateTime createAtStart, LocalDateTime createAtEnd,Integer start, Integer limit) {

        if (InterfaceTypeEnum.others.equals(interfaceTypeEnum)) interfaceTypeEnum=null;
        if (InterfaceNameEnum.others.equals(interfaceNameEnum)) interfaceNameEnum=null;
        if(null==objectId || objectId.intValue() <1) objectId = null;
        if (ResponseResultEnum.others.equals(responseResultEnum)) responseResultEnum=null;

        return ordersWebInterfaceRecordMapper.findByCondition( interfaceTypeEnum,  interfaceNameEnum,  objectId,  responseResultEnum,  createAtStart,createAtEnd,start,limit);
    }


    /**
     * 保存请求信息
     * @param interfaceTypeEnum 接口类型 比如订单，其它等；
     * @param interfaceNameEnum 接口名称 属于某种接口之下的接口名称
     * @param objectId     接口对应的主键ID,可以唯一确定该请求业务数据的id
     * @param requestUrl   推送或者请求的url
     * @param requestParam 推送或者请求的参数
     * @return   对应的详细记录的ID,后续根据这个id，对响应结果进行更新
     */
    public Integer addOrUpdateRequest(InterfaceTypeEnum interfaceTypeEnum, InterfaceNameEnum interfaceNameEnum , Integer objectId, String requestUrl, String requestParam )
    {
        Assert.isTrue(null!=objectId && objectId.intValue()> 0, "唯一确定该请求业务数据的id必须为正整数");
        Assert.isTrue(!Strings.isNullOrEmpty(requestUrl), "唯一确定该请求业务数据的id必须为正整数");

        interfaceTypeEnum = Optional.ofNullable(interfaceTypeEnum).orElse(InterfaceTypeEnum.others);
        interfaceNameEnum = Optional.ofNullable(interfaceNameEnum).orElse(interfaceNameEnum.others);
        requestParam = Optional.ofNullable(requestParam).orElse("");
        String extParam = "";

        //保存主记录信息
        OrdersWebInterfaceRecord record = ordersWebInterfaceRecordMapper.findRecordByTypeNameAndObjectId(interfaceTypeEnum,interfaceNameEnum,objectId);
        if (null == record || null == record.getId() || record.getId() < 1)
        {//不存在，则创建实体
            record = new OrdersWebInterfaceRecord();
            record.setInterfaceTypeEnum(interfaceTypeEnum);
            record.setInterfaceNameEnum(interfaceNameEnum);
            record.setObjectId(objectId);
        }

        record.setRequestUrl(requestUrl);
        record.setRequestParam(requestParam);
        record.setExtParam(extParam);
        record.setRequestTimes(record.getRequestTimes()+1);

        if(record.getId()> 0)
        {
            ordersWebInterfaceRecordMapper.update(record);
        }else
        {
            ordersWebInterfaceRecordMapper.insert(record);
        }

       //保存详细数据
        OrdersWebInterfaceRecordDetail recordDetail = new OrdersWebInterfaceRecordDetail();
        recordDetail.setRecordId(record.getId());
        recordDetail.setObjectId(objectId);
        recordDetail.setInterfaceNameEnum(interfaceNameEnum);
        recordDetail.setRequestUrl(requestUrl);
        recordDetail.setRequestParam(requestParam);

        ordersWebInterfaceRecordDetailMapper.insert(recordDetail);

        return recordDetail.getId();
    }

    /**
     * 保存响应信息
     * @param recordDetailId  发送记录详情ID
     * @param responseDesc        响应内容，也可以放异常信息
     * @param responseResultEnum  响应的结果类型
     * @return
     */
    public boolean saveResponse(Integer recordDetailId,String responseDesc, ResponseResultEnum responseResultEnum)
    {
        Assert.isTrue(null != recordDetailId && recordDetailId.intValue() > 0, "详细记录的ID为正整数");
        Assert.isTrue(Arrays.asList(ResponseResultEnum.success,ResponseResultEnum.fail,ResponseResultEnum.exception).contains(responseResultEnum), "结果类型只能为成功，失败或者异常");
        try {

            String responseStatusCode = "";
            responseDesc = Optional.ofNullable(responseDesc).orElse("");

            OrdersWebInterfaceRecordDetail ordersWebInterfaceRecordDetail = ordersWebInterfaceRecordDetailMapper.findById(new Long(recordDetailId));

            Assert.isTrue(null != ordersWebInterfaceRecordDetail && null != ordersWebInterfaceRecordDetail.getId() && ordersWebInterfaceRecordDetail.getId() > 0, "详细记录id" + recordDetailId + "对应的记录不存在");

            //更新详细记录的结果
            ordersWebInterfaceRecordDetailMapper.updateResponse(recordDetailId, responseStatusCode, responseDesc,responseResultEnum.ordinal());

            //更新概览记录的结果
            ordersWebInterfaceRecordMapper.updateResponse(ordersWebInterfaceRecordDetail.getRecordId(), responseStatusCode, responseDesc,responseResultEnum.ordinal());

            return true;

        }catch (Exception ex)
        {
            logger.error("保存响应信息出错",ex);
            return false;
        }


    }



}
