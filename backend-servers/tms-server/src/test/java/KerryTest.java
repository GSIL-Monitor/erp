import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.TrackingTaskService;
import com.stosz.tms.service.track.impl.KerryTrackHandler;
import com.stosz.tms.service.transport.impl.KerryTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 下单 查询调通
 */
public class KerryTest extends JUnitBaseTest {

    @Autowired
    KerryTrackHandler kerryTrackHandler;
    @Autowired
    TrackingTaskService service;

    @Test
    public void testAdd() {
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();

        extendInfo.setSenderAddress("shenzhen");
        extendInfo.setSenderZipcode("103102");
        extendInfo.setSender("qq");
        extendInfo.setSenderTelphone("0819990012");
        extendInfo.setSenderContactName("afsfdsf");

        orderLinkDto.setLastName("测试");
        orderLinkDto.setFirstName("测试");
        orderLinkDto.setZipcode("1031");
        orderLinkDto.setProvince("测试");
        orderLinkDto.setCity("测试深圳市");
        orderLinkDto.setArea("测试南山区");
        orderLinkDto.setAddress("测试南海大道");
        orderLinkDto.setTelphone("0819990012");

        orderRequest.setOrderLinkDto(orderLinkDto);

        orderRequest.setOrderNo("测试123");
        orderRequest.setTrackNo("12354787");
        orderRequest.setGoodsQty(1);//数量
        orderRequest.setOrderAmount(new BigDecimal(1));//金额
        orderRequest.setRemark("测试备注");
//        orderRequest.setName("测试主单名称");

        extendInfo.setInterfaceUrl("http://exch.th.kerryexpress.com/ediwebapi_uat/SmartEDI/shipment_info");//TODO 生产环境

        KerryTransportHandler kerryTransportHandler=new KerryTransportHandler();

        try {
            kerryTransportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testQuery(){
        ShippingExtend extendInfo = new ShippingExtend();
        extendInfo.setWaybilltrackUrl("http://szerp.stosz.com:8080/admin/public/login");//TODO 生产环境
        String trackingNo = "1171130085750804566";
        kerryTrackHandler.captureTransTrack(new TrackRequest(trackingNo),extendInfo);
    }


//    @Test
//    public void testImport(){
//
//        TrackingTask trackingTask = new TrackingTask();
//
//        String str = FileUtils.readFile("D:\\workResource\\泰国BJT.txt");
//        String[] arr = str.split("\r\n");
//        for (int i =0;i<arr.length;i++){
//            trackingTask.setShippingCode(HandlerTypeEnum.THAILANDBJX.code());
//            trackingTask.setTrackNo(arr[i].trim());
//            trackingTask.setFetchCount(0);
//            trackingTask.setComplete(0);
//            service.inserSelective(trackingTask);
//        }
//
//
//    }
    @Test
    public void qq(){
        String s="sfsf\\\"";
        logger.info("sf");
    }

}
