package com.stosz.tms.web;

import com.google.common.collect.Lists;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.MailSendUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.mapper.ShippingMapper;
import com.stosz.tms.model.Shipping;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.impl.AramexTransportHandler;
import com.stosz.tms.service.waybill.ShippingBillHandler;
import com.stosz.tms.service.waybill.ShippingBillHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/tms/testshiper")
public class TestController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    private AramexTransportHandler aramexTransportHandler;

    @Resource
    private MailSendUtils mailSendUtils;


    @Resource
    private ShippingMapper shippingMapper;

    @Autowired
    private ShippingBillHandlerFactory handlerFactory;

    /**
     *  aramex 下单
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "aramexAdd")
    public RestResult getShippingCodeList() throws Exception {

        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend shippingExtend = new ShippingExtend();

        aramexTransportHandler.transportPlaceOrder(orderRequest,shippingExtend);
        return null;
    }

    @RequestMapping(method = RequestMethod.GET,value = "test")
    public String test() throws Exception {
//        List<Shipping> shippingLis  = Lists.newArrayList();
//
//        Shipping shipping = new Shipping();
//        shipping.setShippingName("test1");
//        shipping.setShippingCode("ssss");
//        shippingLis.add(shipping);
//
//        Shipping shipping1 = new Shipping();
//        shipping1.setShippingName("test2");
//        shipping1.setShippingCode("ssss1");
//        shippingLis.add(shipping1);
//
//        shippingMapper.batchInsert(shippingLis);

        final List<ShippingBillHandler> malaysiaRzHandler = handlerFactory.getHandler("malaysia_gdex");


        ShippingParcel shippingParcel = new ShippingParcel();
        shippingParcel.setTrackNo("454564454566");
        shippingParcel.setFirstName("费");
        shippingParcel.setLastName("和平");
        shippingParcel.setCountry("孟加拉");
        shippingParcel.setProvince("哈哈哈省");
        shippingParcel.setCity("呵呵呵市");
        shippingParcel.setAddress("哈哈哈哈哈 asfaaaa 哈哈号");
        shippingParcel.setTelphone("12345678912");
        shippingParcel.setZipcode("1547");
        shippingParcel.setOrderAmount(new BigDecimal(1558.00));
        shippingParcel.setProductType(2);

        for (int i = 0 ; i <malaysiaRzHandler.size();i++ ){
            malaysiaRzHandler.get(i).createShippingBill(shippingParcel,new FileOutputStream("G://test"+i+".pdf"));
        }

        return "test";
    }
    
    public void testAddList() {
//    	TrackingTask trackingTask = new TrackingTask();
//		trackingTask.setShippingCode(HandlerTypeEnum.CXC.code());
//		trackingTask.setTrackNo(erpOrderShipping.getTrackNumber());
//		trackingTask.setFetchCount(0);
//		trackingTask.setComplete(0);
//		trackingTask.setCreateAt(erpOrderShipping.getCreatedAt());
//		trackingTask.setIdOrderShipping(erpOrderShipping.getIdOrderShipping());
//		trackingTask.setOldIdShipping(erpOrderShipping.getIdShipping());
//		trackingTasks.add(trackingTask);
    }
    
    public static void main(String[] args) {
    	Stream.of(AopProxyUtils.class.getDeclaredMethods()).forEach(item -> {
			logger.info(item.getName());
		});
	}

}
