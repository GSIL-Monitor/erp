package com.stosz.tms.service.track;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;

/**
 * 物流轨迹抓取
 * @author feiheping
 * @version [1.0,2017年12月8日]
 */
public abstract class AbstractSingleTrackHandler extends AbstractTrackHandler {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public AbstractSingleTrackHandler(AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	/**
	 * 根据运单号抓取物流轨迹
	 * @param trackingNo
	 * @return
	 */
	public abstract TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend);


	protected String getRequestAction(String requestAction) {
		if (requestAction.endsWith("?")) {
			return requestAction.substring(0, requestAction.length() - 1);
		}
		if (requestAction.endsWith("/")) {
			return requestAction.substring(0, requestAction.length() - 1);
		}
		if (requestAction.endsWith("//")) {
			return requestAction.substring(0, requestAction.length() - 2);
		}
		return requestAction;
	}
}
