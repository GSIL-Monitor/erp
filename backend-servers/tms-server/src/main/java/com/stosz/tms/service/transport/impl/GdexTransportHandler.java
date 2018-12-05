package com.stosz.tms.service.transport.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.DateUtils;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.gdex.GdexParamDTO;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.service.transport.UpdateWeight;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

/**
 * GDEX 物流接口 预先导入运单号
 * @author xiepengcheng
 * @version [1.0,2017年12月28日]
 */
@Component
public class GdexTransportHandler extends AbstractPlaceOrderTransportHandler implements UpdateWeight {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend shippingExtend) throws Exception {
        TransportOrderResponse response = new TransportOrderResponse(true);
        Map<String,Object> paramMap = new HashMap();
        String responseBody = null;
        try{
            String paramstr = this.getTransportContent(orderRequest,shippingExtend);
            Map<String, String> headMap = new LinkedHashMap();
            headMap.put("ApiKey",shippingExtend.getMd5Key());
            responseBody = HttpClientUtils.doPost(shippingExtend.getInterfaceUrl(), paramstr, ContentType.APPLICATION_JSON,"UTF-8",headMap);
            Map<String,String> responseMap = JsonUtils.jsonToLinkedMap(responseBody,LinkedHashMap.class);
            if (("success").equals(responseMap.get("s"))) {
                response.setCode(TransportOrderResponse.SUCCESS);
                response.setTrackingNo(orderRequest.getTrackNo());
            } else {
                response.setCode(TransportOrderResponse.FAIL);
                response.setErrorMsg(responseMap.get("e"));
            }
        }catch (Exception e){
            logger.info("GdexTransportHandler code={},intefaceUrl={},Exception={}", getCode(), shippingExtend.getInterfaceUrl(), e);
        }finally {
            logService.addLog(getCode(),orderRequest.getOrderNo(), shippingExtend.getInterfaceUrl(),new ObjectMapper().writeValueAsString(paramMap), responseBody);
        }

        return response;
    }

    /**
     * 封装请求物流接口参数
     * @param orderRequest
     * @param extendInfo
     * @return
     */
    private String getTransportContent(TransportOrderRequest orderRequest,ShippingExtend extendInfo) {
        OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
        GdexParamDTO gdexParamDTO = new GdexParamDTO();
        gdexParamDTO.setGdexCN(orderRequest.getTrackNo());//运单号
        gdexParamDTO.setSendingAgentItemNumber(orderRequest.getTrackNo());
        gdexParamDTO.setProduct("00008");
        gdexParamDTO.setAccountNumber("1660504");
        gdexParamDTO.setReferenceID("");
        gdexParamDTO.setConsignmentDate(DateUtils.format(new Date(),"yyyy-MM-dd"));
        BigDecimal amount = orderRequest.getOrderAmount().setScale(0, BigDecimal.ROUND_HALF_DOWN);
        gdexParamDTO.setPieces(orderRequest.getGoodsQty());//商品数量
        gdexParamDTO.setWeight("0.500");
        gdexParamDTO.setConsigneeCompany(orderLinkDto.getFirstName()+orderLinkDto.getLastName());
        gdexParamDTO.setConsigneeName(orderLinkDto.getFirstName()+orderLinkDto.getLastName());//收件人姓名
        gdexParamDTO.setConsigneeAddress1(orderLinkDto.getAddress());//收件人地址
        gdexParamDTO.setConsigneeAddress2("");
        gdexParamDTO.setConsigneeAddress3("");
        gdexParamDTO.setTown(orderLinkDto.getCity());
        gdexParamDTO.setState(orderLinkDto.getProvince());
        gdexParamDTO.setPostcode(orderLinkDto.getZipcode());
        gdexParamDTO.setConsigneeContactNumber1(orderLinkDto.getTelphone());
        gdexParamDTO.setConsigneeContactNumber2(orderLinkDto.getTelphone());
        gdexParamDTO.setShipmentType("P");
        gdexParamDTO.setCODPayment(amount.toString());
        gdexParamDTO.setRemarks("");
        gdexParamDTO.setHeight("0.00");
        gdexParamDTO.setWidth("0.00");
        gdexParamDTO.setLength("0.00");
        gdexParamDTO.setPickupType("LODGE-IN");
        gdexParamDTO.setProductDesc("");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(gdexParamDTO);
        } catch (JsonProcessingException e) {
            logger.trace("GdexTransportHandler getTransportContent方法错误，{}",e);
        }
        return null;
    }

    public void validate(TransportOrderRequest orderRequest,ShippingExtend extendInfo) {
        Assert.notNull(extendInfo, "寄件信息不能为空");
        Assert.notNull(orderRequest, "订单信息不能为空");
        OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

        Assert.hasText(orderRequest.getTrackNo(), "运单编号不能为空");
        if(orderRequest.getTrackNo().length()<1 || orderRequest.getTrackNo().length()>15){
            throw new IllegalArgumentException("运单号长度为1-15");
        }
        Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");
        Assert.notNull(orderRequest.getGoodsQty(), "订单数量不能为空");
        if(orderRequest.getGoodsQty()<1 || orderRequest.getGoodsQty()>999){
          throw new IllegalArgumentException("订单数量为1-999");
        }

        Assert.notNull(orderLinkDto.getProvince(), "收件人省不能为空");
        Assert.notNull(orderLinkDto.getCity(), "收件人市不能为空");
        Assert.notNull(orderLinkDto.getAddress(), "收件人详细地址不能为空");
        Assert.notNull(orderLinkDto.getFirstName(), "收件人姓不能为空");
        Assert.notNull(orderLinkDto.getLastName(), "收件人名不能为空");
        Assert.notNull(orderLinkDto.getTelphone(), "收件人电话不能为空");
        Assert.notNull(orderLinkDto.getZipcode(), "收件人城市邮编不能为空");

        Assert.notNull(extendInfo.getMd5Key(), "寄件公司密码key不能为空");
        Assert.notNull(extendInfo.getInterfaceUrl(), "下单请求地址不能为空");
    }

    @Override
    public HandlerTypeEnum getCode() {
        return HandlerTypeEnum.GDEX;
    }

    /**
     * 更新重量
     * @param trackNo
     * @param url
     * @param weight
     * @return
     */
    @Override
    public String updateWeight(String trackNo, String url, Double weight) {
        try{
            Map<String,Object> backMap = new LinkedHashMap<>();
            String paramstr = this.getUpdateWeightParam(trackNo,weight);
            Map<String, String> headMap = new LinkedHashMap();
            headMap.put("ApiKey","vV9d4gBNZUiWxvX2Cn5P5ObzdxpCqMgIIzyFNWm7ZQY=");
            String responseStr = HttpClientUtils.doPost(url, paramstr, ContentType.APPLICATION_JSON,"UTF-8",headMap);
            if(("success").equals(JsonUtils.jsonToMap(responseStr,LinkedHashMap.class).get("s").toString())){
                backMap.put("success",true);
                backMap.put("msg","");
            }else {
                backMap.put("success",false);
                backMap.put("msg",JsonUtils.jsonToMap(responseStr,LinkedHashMap.class).get("e"));
            }
            return JsonUtils.toJson(backMap);
        }catch (Exception e){
            logger.info("GdexUpdateWeight gdex更新重量错误，{}",e);
        }
        return null;
    }

    private String getUpdateWeightParam(String trackNo,Double weight){
        List<Map> list = new ArrayList<>();
        Map<String,String> paramMap = new LinkedHashMap<>();
        paramMap.put("CN",trackNo);
        paramMap.put("Weight",weight+"");
        list.add(paramMap);
        return JsonUtils.toJson(list);
    }
}
