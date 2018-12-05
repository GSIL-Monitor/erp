import com.stosz.plat.utils.JsonUtils;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.impl.RussiaParseStartegy;
import com.stosz.tms.service.track.impl.RussiaTrackHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 下单 查询轨迹调通
 */
public class RussiaTest{

    public static final Logger logger = LoggerFactory.getLogger(RussiaTest.class);
    @Test
    public void testQuery(){
//        String trackNo = "1171121075838156863";  1171122000334371722  1171222155839202492
        String trackNo = "1171122000334371722";
        ShippingExtend shippingExtend = new ShippingExtend();
        shippingExtend.setWaybilltrackUrl("http://integration.cdek-asia.cn/status_report_h.php");//TODO 生产环境
        shippingExtend.setMd5Key("b6486cc41b2abacfe6d9116ddc747d0e");
        shippingExtend.setAccount("3e37c656688b3d17f828bfefe573fe83");
        RussiaTrackHandler russiaTrackHandler=new RussiaTrackHandler(new RussiaParseStartegy());
        TransportTrackResponse trackResponse=russiaTrackHandler.captureTransTrack(new TrackRequest(trackNo),shippingExtend);
        logger.info(JsonUtils.toJson(trackResponse));
    }

}
