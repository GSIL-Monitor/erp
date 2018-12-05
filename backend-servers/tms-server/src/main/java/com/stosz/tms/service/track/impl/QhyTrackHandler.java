package com.stosz.tms.service.track.impl;

import java.util.List;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.CollectionUtils;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;

/**
 * QHY 查询物流轨迹
 * @version [1.0,2018年1月11日]
 */
@Component
public class QhyTrackHandler extends AbstractSingleTrackHandler {
    private static  final Logger logger= LoggerFactory.getLogger(QhyTrackHandler.class);
    private static final String METHOD = "getCargoTrack";//获取轨迹

	public QhyTrackHandler(@Qualifier("qhyParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
        TransportTrackResponse transportTrackResponse = new TransportTrackResponse();
		try {
            String requestStr = this.getRequestStr(request,shippingExtend);
            long t2=System.currentTimeMillis();
            String response = HttpClientUtils.doPost(shippingExtend.getWaybilltrackUrl(),requestStr, ContentType.APPLICATION_XML,"UTF-8");
            long t3=System.currentTimeMillis();
            if((t3-t2)/1000>15){
                logger.info("EXTREMELY CALL OVER TIMEtime{} seconds",(t3-t2)/1000);
            }
            if((t3-t2)/1000>3){
                logger.info(" CALL OVER TIME seconds",(t3-t2)/1000);
            }

            List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
            if(!CollectionUtils.isNullOrEmpty(trackDetails)){
                transportTrackResponse.setCode(TransportTrackResponse.SUCCESS);
                transportTrackResponse.setTrackDetails(trackDetails);
            }else {
                transportTrackResponse.setCode(TransportTrackResponse.FAIL);
            }
		} catch (Exception e) {
			logger.trace("QhyTrackHandler 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
		}
		return transportTrackResponse;
	}

	private String getRequestStr(TrackRequest request,ShippingExtend shippingExtend){
	    String jsonstr = "{\"codes\":[\""+request.getTrackingNo()+"\"]}";
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "xmlns:ns1=\"http://www.example.org/Ec/\"><SOAP-ENV:Body><ns1:callService><paramsJson>");
        sb.append(jsonstr);
        sb.append("</paramsJson><appToken>");
        sb.append(shippingExtend.getAccount());
        sb.append("</appToken><appKey>");
        sb.append(shippingExtend.getMd5Key());
        sb.append("</appKey><service>");
        sb.append(METHOD);
        sb.append("</service></ns1:callService></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        return sb.toString();

    }

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.QHY;
	}

}
