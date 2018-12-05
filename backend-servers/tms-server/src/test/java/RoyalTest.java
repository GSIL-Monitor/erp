import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.PakistanTrackHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoyalTest extends JUnitBaseTest {
    @Autowired
    PakistanTrackHandler trackHandler;
    @Test
    public  void  qq(){
        ShippingExtend extendInfo = new ShippingExtend();
        extendInfo.setWaybilltrackUrl("http://www.pfcexpress.com/webservice/APIWebService.asmx");//TODO 生产环境
        String OrderNo = "1171130085750804566";
        trackHandler.captureTransTrack(new TrackRequest(OrderNo),extendInfo);
    }
    @Test
    public  void  tt(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=simpleDateFormat.parse("2014-06-09 15:00:00");
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
    }
}
