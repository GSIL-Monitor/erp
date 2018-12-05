package com.stosz;

import com.stosz.plat.utils.JsonUtils;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.impl.HkcoeParseStartegy;
import com.stosz.tms.service.track.impl.HkcoeTrackHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 香港coe
 */
public class HkcoeTest{

    public static final Logger logger = LoggerFactory.getLogger(HkcoeTest.class);
//    @Autowired
//    HkcoeTrackHandler trackHandler;

    @Test
    public void testQueryRoute(){
        //1790014195  1790036612  1790014194
        ShippingExtend shippingExtend = new ShippingExtend();
        String trackNo = "1790014194";
        //TODO 生产环境
        shippingExtend.setWaybilltrackUrl("http://www.coe.com.hk/order/trackNew");
        HkcoeTrackHandler hkcoeTrackHandler=new HkcoeTrackHandler(new HkcoeParseStartegy());
        TransportTrackResponse trackResponse=hkcoeTrackHandler.captureTransTrack(new TrackRequest(trackNo),shippingExtend);
        logger.info(JsonUtils.toJson(trackResponse));
    }

}
