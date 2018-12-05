package com.stosz.tms.service.track.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.EncryptUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.russia.OrderStatus;
import com.stosz.tms.model.russia.StatusReport;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.XStreamUtils;

/**
 * 俄罗斯物流 轨迹抓取
 * @author feiheping
 * @version [1.0,2017年12月12日]
 */
@Component
public class RussiaTrackHandler extends AbstractSingleTrackHandler {

	private Logger logger = LoggerFactory.getLogger(RussiaTrackHandler.class);

	public RussiaTrackHandler(@Qualifier("russiaParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse transportTrackResponse = new TransportTrackResponse();
		try {
			StatusReport statusReport = new StatusReport();
			statusReport.setDate(new Date());
			String secure = this.getSecure(statusReport, shippingExtend);
			statusReport.setSecure(secure);
			statusReport.setAccount(shippingExtend.getAccount());
			statusReport.setShowHistory(1);

			OrderStatus orderStatus = new OrderStatus();
			orderStatus.setDispatchNumber(request.getTrackingNo());
			statusReport.setOrder(orderStatus);

			String content = XStreamUtils.jaxbToXml(statusReport);
			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("xml_request", content);
			long t2=System.currentTimeMillis();
			String response = HttpClientUtils.doPost(shippingExtend.getWaybilltrackUrl(), paramMap, "UTF-8");
			long t3=System.currentTimeMillis();
			if((t3-t2)/1000>15){
				logger.info("EXTREMELY CALL OVER TIMEtime{} seconds",(t3-t2)/1000);
			}
			if((t3-t2)/1000>3){
				logger.info(" CALL OVER TIME seconds",(t3-t2)/1000);
			}
			List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
			transportTrackResponse.setCode(TransportTrackResponse.SUCCESS);
			transportTrackResponse.setTrackDetails(trackDetails);
		} catch (Exception e) {
			logger.info("Russia 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
			transportTrackResponse.fail("俄罗斯物流轨迹抓取失败");
		}
		return transportTrackResponse;
	}

	public String getSecure(StatusReport statusReport, ShippingExtend shippingExtend) {
		String date = DateUtils.format(statusReport.getDate(), "yyyy-MM-dd HH:mm:ss");
		String original = StringUtil.concat(date, "&", shippingExtend.getMd5Key());
		return EncryptUtils.md5(original);
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.RUSSIA;
	}

	public static void main(String[] args) {
		ShippingExtend extend = new ShippingExtend();
		extend.setAccount("3e37c656688b3d17f828bfefe573fe83");
		extend.setMd5Key("b6486cc41b2abacfe6d9116ddc747d0e");
		extend.setWaybilltrackUrl("http://integration.cdek-asia.cn/status_report_h.php");
		new RussiaTrackHandler(null).captureTransTrack(new TrackRequest("1067012539"), extend);
	}

}
