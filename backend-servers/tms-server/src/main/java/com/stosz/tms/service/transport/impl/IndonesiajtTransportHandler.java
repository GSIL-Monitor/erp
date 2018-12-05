package com.stosz.tms.service.transport.impl;

import com.stosz.plat.utils.DateUtils;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.indonesiajt.Item;
import com.stosz.tms.model.indonesiajt.LogisticsDto;
import com.stosz.tms.model.indonesiajt.Receiver;
import com.stosz.tms.model.indonesiajt.Sender;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.Base64;
import com.stosz.tms.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预先导入运单号
 */
@Component("indonesiajtTransportHandler")
public class IndonesiajtTransportHandler extends AbstractPlaceOrderTransportHandler {

    private Logger logger = LoggerFactory.getLogger(IndonesiajtTransportHandler.class);

    //消息类型
    private static final String MSG_TYPE = "ORDERCREATE";

    @Override
    public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
        String requestBody = null;
        String responseBody = null;
        TransportOrderResponse response = new TransportOrderResponse(true);
        try {
            String logisticsDtostr = this.getLogisticsDto(orderRequest,extendInfo);
            String data_digest = this.getDatadigest(logisticsDtostr,extendInfo);
            String msg_type = MSG_TYPE;

            Map<String, Object> param = new HashMap();
            param.put("logistics_interface",logisticsDtostr);
            param.put("data_digest",data_digest);
            param.put("msg_type",msg_type);
            param.put("eccompanyid","SZCUCKOO");
            requestBody = JsonUtils.toJson(param);

            responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), param, "UTF-8");
            Map resultMap = JsonUtils.jsonToMap(responseBody, LinkedHashMap.class);
            List resultList = (List)resultMap.get("responseitems");
            Map reMap = (Map) resultList.get(0);

            if ("true".equals(reMap.get("success"))) {
                response.setCode(TransportOrderResponse.SUCCESS);
                String trackingNo = reMap.get("mailno")+"";
                response.setTrackingNo(trackingNo);
            } else {
                response.setCode(TransportOrderResponse.FAIL);
                String error =  reMap.get("reason")+"";
                response.setErrorMsg(error);
            }
        } catch (Exception e) {
            logger.info("indonesiajtTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
        } finally {
            logService.addLog(getCode(),orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
        }
        return response;
    }

    @Override
    public HandlerTypeEnum getCode() {
        return HandlerTypeEnum.INDONESIAJT;
    }

    /**
     * 获取LogisticsDto
     * @param orderRequest
     * @param extendInfo
     * @return
     * @throws JAXBException
     */
    private String getLogisticsDto(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws JAXBException {
        OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
        List<TransportOrderDetail> detailList = orderRequest.getOrderDetails();

        LogisticsDto logisticsDto = new LogisticsDto();
        logisticsDto.setEccompanyid("SZCUCKOO");//电商标识
        logisticsDto.setLogisticproviderid("JNT");//物流公司编码
        logisticsDto.setCustomerid("SZCUCKOO");//客户标识
        logisticsDto.setTxlogisticid(orderRequest.getOrderNo());
        logisticsDto.setMailno(orderRequest.getTrackNo());//运单号
        logisticsDto.setOrdertype("4");//订单类型
        logisticsDto.setServicetype("6");//服务类型
        Sender sender = new Sender();
        sender.setName(extendInfo.getSenderContactName());//发件人
        sender.setPostcode(extendInfo.getSenderZipcode());//发件人邮编
        sender.setMobile(extendInfo.getSenderTelphone());//发件人电话
        sender.setPhone(extendInfo.getSenderTelphone());
        sender.setProv(extendInfo.getSenderProvince());
        sender.setCity(extendInfo.getSenderCity());
        sender.setAddress(extendInfo.getSenderAddress());
        Assert.notNull(extendInfo.getInterfaceUrl(),"下单请求接口地址不能为空");

        Receiver receiver = new Receiver();
        receiver.setAddress(orderLinkDto.getAddress());
        receiver.setArea(orderLinkDto.getArea());
        receiver.setCity(orderLinkDto.getCity());
        receiver.setProv(orderLinkDto.getProvince());
        receiver.setMobile(orderLinkDto.getTelphone());
        receiver.setName(orderLinkDto.getFirstName()+orderLinkDto.getLastName());
        receiver.setPhone(orderLinkDto.getTelphone());
        receiver.setPostcode(orderLinkDto.getZipcode());

        logisticsDto.setSender(sender);
        logisticsDto.setReceiver(receiver);

        logisticsDto.setCreateordertime(DateUtils.format(orderRequest.getOrderDate(),"yyyy-MM-dd HH:mm:ss"));
        logisticsDto.setSendstarttime(DateUtils.format(orderRequest.getOrderDate(),"yyyy-MM-dd HH:mm:ss"));
        logisticsDto.setSendendtime(DateUtils.format(DateUtils.getNlateDate(orderRequest.getOrderDate(),365),"yyyy-MM-dd HH:mm:ss"));
        logisticsDto.setGoodsvalue(DecimalUtils.formateDecimal(orderRequest.getOrderAmount(),"#.##"));//订单金额
        logisticsDto.setItemsvalue(DecimalUtils.formateDecimal(orderRequest.getOrderAmount(),"#.##"));

        List<Item> items = new ArrayList<>();

        items = detailList.stream().map(detail ->{
            Item item = new Item();
            item.setItemname(detail.getProductTitle());
            item.setItemvalue(DecimalUtils.formateDecimal(detail.getTotalAmount(),"#.#"));
            item.setNumber(detail.getOrderDetailQty()+"");
            return item;
        }).collect(Collectors.toList());

        logisticsDto.setItems(items);
        logisticsDto.setSpecial("");
        logisticsDto.setPaytype("1");
        logisticsDto.setWeight("1");

        return JsonUtils.toJson(logisticsDto);
    }


    private String getDatadigest(String jsonstr,ShippingExtend shippingExtend){
        String md5key = shippingExtend.getMd5Key();
        String param = jsonstr + md5key;
        String datadigest = Base64.encode(MD5Util.MD5Encode(param,"UTF-8").getBytes());
        return datadigest;
    }

    @Override
    protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
        Assert.notNull(extendInfo, "寄件信息不能为空");
        Assert.notNull(orderRequest, "订单信息不能为空");
        OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
        Assert.notNull(orderLinkDto, "收件人信息不能为空");

        Assert.notNull(orderRequest.getOrderNo(),"订单号不能为空");
        Assert.notNull(orderRequest.getOrderTypeEnum(),"订单类型不能为空");
        Assert.notNull(orderRequest.getOrderAmount(),"订单金额不能为空");
//        Assert.notNull(orderRequest.getSendstarttime(),"物流上门取货开始时间不能为空");
//        Assert.notNull(orderRequest.getSendendtime(),"物流上门取货结束时间不能为空");
        Assert.notNull(orderRequest.getOrderDate(),"订单时间不能为空");
        Assert.notNull(orderRequest.getTrackNo(),"运单号不能为空");

        Assert.notNull(extendInfo.getSenderContactName(), "发件人姓名不能为空");
        Assert.notNull(extendInfo.getSenderZipcode(), "发件人邮编不能为空");
        Assert.notNull(extendInfo.getSenderTelphone(), "发件人电话不能为空");
        Assert.notNull(extendInfo.getSenderProvince(), "发件人所在省不能为空");
        Assert.notNull(extendInfo.getSenderCity(), "发件人所在城市不能为空");
        Assert.notNull(extendInfo.getSenderAddress(), "发件人地址不能为空");
        Assert.notNull(extendInfo.getMd5Key(), "发件人MD5key不能为空");

        Assert.notNull(orderLinkDto.getCity(), "收件人城市不能为空");
        Assert.notNull(orderLinkDto.getProvince(), "收件人所在省不能为空");
        Assert.notNull(orderLinkDto.getArea(),"收件人所在地区不能为空");
        Assert.hasText(orderLinkDto.getAddress(), "收件人地址不能为空");
        String name=null;
        if(orderLinkDto.getFirstName()!=null)
        {
            name+=orderLinkDto.getFirstName();
        }
        if(orderLinkDto.getLastName()!=null)
        {
            name+=orderLinkDto.getLastName();
        }
        Assert.notNull(name, "收件人姓名能为空");
        Assert.notNull(orderLinkDto.getTelphone(), "收件人电话不能为空");
        Assert.notNull(orderLinkDto.getZipcode(), "收件邮编不能为空");

        Assert.notNull(orderRequest.getOrderDate(), "订单创建时间不能为空");
        Assert.notNull(orderRequest.getOrderAmount(), "商品金额不能为空");

        List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
        // 多品名信息(中文品名,英文品名,数量,单位,单价,海关编码;)用英文逗号隔开，用封号结尾
        for (TransportOrderDetail orderDetail : orderDetails) {
            Assert.hasText(orderDetail.getProductTitle(),"商品名称不能为空");//产品标题
            Assert.notNull(orderDetail.getTotalAmount(),"明细总价不能为空");//
            Assert.notNull(orderDetail.getOrderDetailQty(),"明细数量不能为空");
        }
    }


//    public static void main(String[] args) {
//        TransportOrderRequest orderRequest = new TransportOrderRequest();
//        ShippingExtend extendInfo = new ShippingExtend();
//        OrderLinkDto orderLinkDto = new OrderLinkDto();
//
//        orderRequest.setOrderNo("testsf1289991109");//客户订单
//        orderRequest.setOrderTypeEnum(OrderTypeEnum.normal);
//        orderRequest.setOrderAmount(new BigDecimal("2.2699"));//订单金额
//        orderRequest.setPayState(0);//未付款
//        orderRequest.setGoodsQty(1);
//        orderRequest.setSendstarttime(new Date());
//        orderRequest.setSendendtime(DateUtils.getNlateDate(365));
//        orderRequest.setOrderDate(new Date());
//        orderRequest.setTrackNo("testbn5ao569910");
//
//        extendInfo.setSenderContactName("testname000");//发件人
//        extendInfo.setSenderZipcode("14442");
//        extendInfo.setSenderTelphone("13333333333");
//        extendInfo.setSenderProvince("GUANDONG");
//        extendInfo.setSenderCity("shenzhen");
//        extendInfo.setSenderAddress("南山区动漫园address");
//        //生产地址
//        extendInfo.setMd5Key("779a66a12c8aba0fd87e1173c32a3899");//秘钥
//        //测试秘钥
//        //extendInfo.setMd5Key("a620da4a08f8da77090b45ebea823f63");//秘钥
//
//        orderLinkDto.setCity("JAKARTA");
//        orderLinkDto.setProvince("DKI JAKARTA");
//        orderLinkDto.setAddress("TEST ALAMAT PENGIRIM");//
//        orderLinkDto.setFirstName("cuckoo");//
//        orderLinkDto.setTelphone("13333333333");//收件人电话
//        orderLinkDto.setZipcode("15555");//收件人邮编
//        orderLinkDto.setArea("南山区");
//
//        TransportOrderDetail transportOrderDetail = new TransportOrderDetail();
//        transportOrderDetail.setProductTitle("testname");
//        transportOrderDetail.setTotalAmount(new BigDecimal(1.111));
//        transportOrderDetail.setOrderDetailQty(1);//数量
//
//        List<TransportOrderDetail> list = new ArrayList<>();
//        list.add(transportOrderDetail);
//
//        orderRequest.setOrderLinkDto(orderLinkDto);
//        orderRequest.setOrderDetails(list);
//
//        // TODO 测试地址
//        //extendInfo.setInterfaceUrl("http://202.159.30.42:22223/jandt_web/szcuckoo/orderAction!createOrder.action");
//        // TODO 生产地址
//        extendInfo.setInterfaceUrl("http://jk.jet.co.id:22261/jant_szcuckoo_web/szcuckoo/orderAction!createOrder.action");
//        try {
//            IndonesiajtTransportHandler transportHandler = new IndonesiajtTransportHandler();
//            transportHandler.transportPlaceOrder(orderRequest,extendInfo);
//        } catch (Exception e) {
//        }
//    }
}
