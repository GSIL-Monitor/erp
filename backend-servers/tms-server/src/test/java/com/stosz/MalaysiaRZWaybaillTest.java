package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.service.waybill.ShippingBillHandler;
import com.stosz.tms.service.waybill.ShippingBillHandlerFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class MalaysiaRZWaybaillTest extends JUnitBaseTest {

    @Autowired
    private ShippingBillHandlerFactory handlerFactory;


    @Test
    public void test() throws Exception {
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

        malaysiaRzHandler.get(1).createShippingBill(shippingParcel,new FileOutputStream("G://HelloWorld.pdf"));

        logger.info(""+malaysiaRzHandler);
//        malaysiaRZWaybaill.createWaybaill();;
    }


//    public static void main(String[] args) {
//        MalaysiaRZWaybaill malaysiaRZWaybaill = new MalaysiaRZWaybaill();
//        malaysiaRZWaybaill.createWaybaill();;
//    }

}
