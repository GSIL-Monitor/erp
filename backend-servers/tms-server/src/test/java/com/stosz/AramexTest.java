package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.external.aramex.*;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.AramexTrackHandler;
import com.stosz.tms.service.transport.impl.AramexTransportHandler;
import com.stosz.tms.utils.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 下单 轨迹通
 */
public class AramexTest extends JUnitBaseTest {
    @Autowired
    AramexTransportHandler aramexTransportHandler;

    @Autowired
    AramexTrackHandler aramexTrackHandler;
    /**
     * 下单通
     */
    @Test
    public void testAdd(){
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend shippingExtend = new ShippingExtend();
        orderRequest.setOrderNo("testorderno110");
        orderRequest.setWeight(0.2D);
        orderRequest.setUnit("BOX");
//        orderRequest.setName("test test aramex");
        orderRequest.setOrderAmount(new BigDecimal(1));
        orderRequest.setGoodsQty(1);
        orderRequest.setCurrencyCode("USD");

        TransportOrderDetail transportOrderDetail = new TransportOrderDetail();
        transportOrderDetail.setUnit("BOX");
        transportOrderDetail.setTotalWeight(new BigDecimal(0.2));
        transportOrderDetail.setTotalAmount(new BigDecimal(8));
        transportOrderDetail.setProductTitle("booktest");
        transportOrderDetail.setWeight(new BigDecimal(2));

        OrderLinkDto orderLinkDto = new OrderLinkDto();
        orderLinkDto.setFirstName("zhang");
        orderLinkDto.setLastName("shan");
        orderLinkDto.setTelphone("1111");
        orderLinkDto.setAddress("test address test");
        orderLinkDto.setEmail("test2213@stosz.com");
        orderLinkDto.setZipcode("821551");
        orderLinkDto.setCountryCode("SA");
        orderLinkDto.setCity("Riyadh");
        orderLinkDto.setCompanyName("my company");//收件人公司 必传

        List<TransportOrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(transportOrderDetail);
        orderRequest.setOrderLinkDto(orderLinkDto);
        orderRequest.setOrderDetails(orderDetails);

        shippingExtend.setSenderContactName("zhangshan");
        shippingExtend.setSenderTelphone("133333333333");
        shippingExtend.setSender("test company");
        shippingExtend.setSenderEmail("test@189.com");
        //TODO 测试地址
        shippingExtend.setInterfaceUrl("https://ws.dev.aramex.net/ShippingAPI.V2/Shipping/Service_1_0.svc?singleWsdl");
        //TODO 生产地址
//        shippingExtend.setInterfaceUrl("https://ws.aramex.net/ShippingAPI.V2/Shipping/Service_1_0.svc?wsdl");

        try{
            aramexTransportHandler.transportPlaceOrder(orderRequest,shippingExtend);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testRest() throws RemoteException, MalformedURLException {

        javax.xml.rpc.Service service = new Service_1_0_ServiceLocator();
        BasicHttpBinding_Service_1_0Stub stub = new BasicHttpBinding_Service_1_0Stub(new URL("https://ws.dev.aramex.net/ShippingAPI.V2/Shipping/Service_1_0.svc?singleWsdl"),service);
        ShipmentCreationRequest shipmentCreationRequest = new ShipmentCreationRequest();

        //1.shipments
        Shipment shipment = new Shipment();
        //Number
        shipment.setNumber("");//运单号
        Attachment attachment = new Attachment();
        attachment.setFileName("");
        attachment.setFileExtension("");
        attachment.setFileExtension("");
        Attachment[] attachments = new Attachment[]{attachment};
        //Attachments
        shipment.setAttachments(attachments);
        Party shipper = new Party();
        shipper.setReference1("Shipper Reference1");
        shipper.setReference2("Shipper Reference2");
        shipper.setAccountNumber("70546291");
        Address partyAddress = new Address();
        partyAddress.setLine1("Shipper Address Line11");
        partyAddress.setLine2("Shipper Address Line22");
        partyAddress.setLine3("Shipper Address Line33");
        partyAddress.setCity("Hong Kong");
        partyAddress.setStateOrProvinceCode("");
        partyAddress.setPostCode("");
        partyAddress.setCountryCode("HK");
        //PartyAddress
        shipper.setPartyAddress(partyAddress);

        Contact contact = new Contact();
        contact.setDepartment("");
        contact.setPersonName("lisi");//todo notempty
        contact.setTitle("");
        contact.setCompanyName("Shipper Company");
        contact.setPhoneNumber1("999999");
        contact.setPhoneNumber1Ext("1111");
        contact.setPhoneNumber2("");
        contact.setPhoneNumber2Ext("");
        contact.setFaxNumber("");
        contact.setCellPhone("999999");
        contact.setEmailAddress("all_hkg_it@aramex.com");
        contact.setType("");

        //contact
        shipper.setContact(contact);
        //shipper
        shipment.setShipper(shipper);

        Party consignee = new Party();
        consignee.setReference1("Cnee Reference1");
        consignee.setReference2("Shipper Reference2");
        consignee.setAccountNumber("");

        Address cneeAddress = new Address();
        cneeAddress.setLine1("Cnee Address Line1");
        cneeAddress.setLine2("Cnee Address Line2");
        cneeAddress.setLine3("Cnee Address Line3");
        cneeAddress.setCity("Riyadh");
        cneeAddress.setStateOrProvinceCode("");
        cneeAddress.setPostCode("");
        cneeAddress.setCountryCode("SA");

        //cnee--Address
        consignee.setPartyAddress(cneeAddress);

        Contact cneeConcat = new Contact();
        cneeConcat.setDepartment("");
        cneeConcat.setPersonName("Cnee Name");
        cneeConcat.setTitle("");
        cneeConcat.setCompanyName("Cnee Company");
        cneeConcat.setPhoneNumber1("999999");
        cneeConcat.setPhoneNumber1Ext("1111");
        cneeConcat.setPhoneNumber2("");
        cneeConcat.setPhoneNumber2Ext("");
        cneeConcat.setFaxNumber("");
        cneeConcat.setCellPhone("999999");
        cneeConcat.setEmailAddress("test@aramex.com");
        cneeConcat.setType("");

        //conee--contact
        consignee.setContact(cneeConcat);

        //consignee
        shipment.setConsignee(consignee);

        Party thirdParty = new Party();
        thirdParty.setReference1("");
        thirdParty.setReference2("");
        thirdParty.setAccountNumber("");

        Address thirpartyAddress = new Address();
        thirpartyAddress.setLine1("");
        thirpartyAddress.setLine2("");
        thirpartyAddress.setLine3("");
        thirpartyAddress.setCity("");
        thirpartyAddress.setStateOrProvinceCode("");
        thirpartyAddress.setPostCode("");
        thirpartyAddress.setCountryCode("");
        thirdParty.setPartyAddress(thirpartyAddress);

        Contact thirdConcat = new Contact();
        thirdConcat.setDepartment("");
        thirdConcat.setPersonName("");
        thirdConcat.setTitle("");
        thirdConcat.setCompanyName("");
        thirdConcat.setPhoneNumber1("");
        thirdConcat.setPhoneNumber1Ext("");
        thirdConcat.setPhoneNumber2("");
        thirdConcat.setPhoneNumber2Ext("");
        thirdConcat.setFaxNumber("");
        thirdConcat.setCellPhone("");
        thirdConcat.setEmailAddress("");
        thirdConcat.setType("");

        thirdParty.setContact(thirdConcat);
        //thirdPaty
        shipment.setThirdParty(thirdParty);
        shipment.setReference1("");
        shipment.setReference2("");
        shipment.setReference3("");
        shipment.setForeignHAWB("");
        shipment.setTransportType_x0020_(0);
        shipment.setShippingDateTime(Calendar.getInstance());
        shipment.setDueDate(Calendar.getInstance());
        shipment.setPickupLocation("");
        shipment.setPickupGUID("");
        shipment.setComments("");
        shipment.setAccountingInstrcutions("");
        shipment.setOperationsInstructions("OperationsInstructions");

        ShipmentDetails details = new ShipmentDetails();

        Dimensions dimensions = new Dimensions();
        dimensions.setHeight(2);
        dimensions.setLength(2);
        dimensions.setWidth(2);
        dimensions.setUnit("cm");

        //dimensions
        details.setDimensions(dimensions);

        Weight weight = new Weight();
        weight.setValue(0.5);
        weight.setUnit("KG");

        //weight
        details.setActualWeight(weight);
        details.setProductGroup("EXP");
        details.setProductType("EPX");
        details.setPaymentType("P");
        details.setPaymentOptions("");
        details.setServices("CODS");
        details.setNumberOfPieces(1);
        details.setDescriptionOfGoods("BOOK");
        details.setGoodsOriginCountry("HK");

        Money money = new Money();
        money.setValue(10);
        money.setCurrencyCode("USD");

        //money
        details.setCashOnDeliveryAmount(money);

        Money insuMoney = new Money();
        insuMoney.setValue(0);
        insuMoney.setCurrencyCode("");

        //insu-money
        details.setInsuranceAmount(insuMoney);

        Money collmoney = new Money();
        collmoney.setValue(0);
        collmoney.setCurrencyCode("");
        //collec-money
        details.setCollectAmount(collmoney);


        Money cashmoney = new Money();
        cashmoney.setValue(0);
        cashmoney.setCurrencyCode("");

        //cash-money
        details.setCashAdditionalAmount(cashmoney);

        details.setCashAdditionalAmountDescription("");

        Money cusmoney = new Money();
        cusmoney.setValue(10);
        cusmoney.setCurrencyCode("USD");
        //cus-Money
        details.setCustomsValueAmount(cusmoney);

        //########### FOR(){]
        ShipmentItem shipmentItem = new ShipmentItem();

        shipmentItem.setPackageType("box set");
        Weight itemWeight1 = new Weight();
        itemWeight1.setUnit("KG");
        itemWeight1.setValue(3.33);
        //item-weight
        shipmentItem.setWeight(itemWeight1);
        shipmentItem.setComments("new try1");
        shipmentItem.setReference("new try1");
        Dimensions itemDimensions = new Dimensions();
        itemDimensions.setLength(1);
        itemDimensions.setWidth(2);
        itemDimensions.setHeight(3);
        itemDimensions.setUnit("cm");

        Dimensions[] piecesDimensions = new Dimensions[]{itemDimensions};
        //piecesDimensions
        shipmentItem.setPiecesDimensions(piecesDimensions);
        shipmentItem.setCommodityCode("hs123456");
        shipmentItem.setGoodsDescription("new test desc");
        shipmentItem.setCountryOfOrigin("JO");

        Money itemcusval = new Money();
        itemcusval.setCurrencyCode("USD");
        itemcusval.setValue(8);

        //item-customsValue
        shipmentItem.setCustomsValue(itemcusval);

        //shipmentItem
        ShipmentItem[] items = new ShipmentItem[]{shipmentItem};

        //items
        details.setItems(items);

        //details
        shipment.setDetails(details);

        Shipment[] shipments = new Shipment[]{shipment};

        //1.shipments
        shipmentCreationRequest.setShipments(shipments);

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setAccountCountryCode("HK");
        clientInfo.setAccountEntity("HKG");
        clientInfo.setAccountNumber("70546291");
        clientInfo.setAccountPin("123123");
        clientInfo.setUserName("testingapi@aramex.com");
        clientInfo.setPassword("R123456789$r");
        clientInfo.setVersion("v1");
        //2.ClientInfo
        shipmentCreationRequest.setClientInfo(clientInfo);
        Transaction transaction = new Transaction();
        transaction.setReference1("");
        transaction.setReference2("");
        transaction.setReference3("");
        transaction.setReference4("");
        //3.Transaction
        shipmentCreationRequest.setTransaction(transaction);
        LabelInfo labelInfo = new LabelInfo();
        labelInfo.setReportID(9201);
        labelInfo.setReportType("URL");
        //4.LabelInfo
        shipmentCreationRequest.setLabelInfo(labelInfo);

        logger.info("requeststr========"+ JsonUtils.toJson(shipmentCreationRequest));

        ShipmentCreationResponse reponse = stub.createShipments(shipmentCreationRequest);

        logger.info("responsestr======="+ JsonUtils.toJson(reponse));

    }


    @Test
    public void testTrace(){
        //31542387483  31043484452
        String trackingNo = "31542387483";
        ShippingExtend shippingExtend = new ShippingExtend();
        //TODO 生产地址
        shippingExtend.setWaybilltrackUrl("https://ws.aramex.net/ShippingAPI.V2/Tracking/Service_1_0.svc?singleWsdl");
        try{
            aramexTrackHandler.captureTransTrack(new TrackRequest(trackingNo),shippingExtend);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

}
