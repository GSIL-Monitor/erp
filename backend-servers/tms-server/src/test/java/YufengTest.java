import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.TrackingTaskService;
import com.stosz.tms.service.track.impl.YufengTrackHandler;
import com.stosz.tms.service.transport.impl.YufengTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 下单 查询调通
 */
public class YufengTest extends JUnitBaseTest {

    @Autowired
    YufengTrackHandler yufengTrackHandler;

    @Autowired
    TrackingTaskService service;

    @Test
    public void testAdd() {
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();

        orderLinkDto.setLastName("测试");
        orderLinkDto.setFirstName("测试");
        orderLinkDto.setProvince("测试");
        orderLinkDto.setCity("测试深圳市");
        orderLinkDto.setArea("测试南山区");
        orderLinkDto.setAddress("测试南海大道");
        orderLinkDto.setTelphone("13333333333");

        orderRequest.setOrderLinkDto(orderLinkDto);

        orderRequest.setOrderNo("测试123");
        orderRequest.setGoodsQty(1);//数量
        orderRequest.setOrderAmount(new BigDecimal(1));//金额
        orderRequest.setRemark("测试备注");
//        orderRequest.setName("测试主单名称");

        extendInfo.setInterfaceUrl("http://218.16.117.83:8666/Api.aspx");//TODO 生产环境

        YufengTransportHandler yufengTransportHandler = new YufengTransportHandler();

        try {
            yufengTransportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testQuery(){
        ShippingExtend extendInfo = new ShippingExtend();
        extendInfo.setWaybilltrackUrl("http://218.16.117.83:8033/APIQuery.aspx");//TODO 生产环境
        String trackingNo = "571100030299";
        yufengTrackHandler.captureTransTrack(new TrackRequest(trackingNo),extendInfo);
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

}
