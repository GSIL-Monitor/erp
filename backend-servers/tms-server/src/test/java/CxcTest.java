import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.CxcTrackHandler;
import com.stosz.tms.service.transport.impl.CxcTransportHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产下单 查询轨迹调通
 */
public class CxcTest  {

    public static final Logger logger = LoggerFactory.getLogger(CxcTest.class);

    CxcTransportHandler transportHandler;
    CxcTrackHandler cxcTrackHandler;

    @Test
    public void testAdd() {
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();

        orderRequest.setOrderNo("测试订单号123123");//订单号
        orderRequest.setTrackNo("test111");//
        orderRequest.setOrderDate(new Date());//时间

        orderLinkDto.setProvince("广东省");
        orderLinkDto.setCity("深圳市");
        orderLinkDto.setArea("南山区");
        orderLinkDto.setAddress("动漫园");
        orderLinkDto.setFirstName("li");
        orderLinkDto.setLastName("si");
        orderLinkDto.setTelphone("13333333333");
        orderRequest.setOrderAmount(new BigDecimal(1));
        orderRequest.setOrderLinkDto(orderLinkDto);
        extendInfo.setAccount("CBXGCN1684945");
        extendInfo.setMd5Key("C3625XCBG48N4");
        extendInfo.setInterfaceUrl("http://42.3.224.83:7028/BGNCXCInterface/add.do"); //TODO 生产环境
        try {
        	CxcTransportHandler cxcTransportHandler=new CxcTransportHandler();
        	cxcTransportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

    }

    @Test
    public void testQuery(){
//        String trackNo = "1171121075838156863";
        String trackNo = "1171222155839202492";
        ShippingExtend shippingExtend = new ShippingExtend();
        shippingExtend.setWaybilltrackUrl("http://42.3.224.83:7028/BGNCXCInterfaceNew/searchScanInfo.do");//TODO 生产环境
        cxcTrackHandler.captureTransTrack(new TrackRequest(trackNo),shippingExtend);
    }

}
