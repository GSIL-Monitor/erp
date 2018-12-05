package com.stosz.tms.parse.startegy;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class AbstractParseStartegy {

	public static final Logger logger = LoggerFactory.getLogger(AbstractParseStartegy.class);

	protected final String ONLINE_STATE = "已上线";

	protected final String INTRANSIT_STATE = "转运中心";

	protected final String RECEIVE_STATE = "已签收";

	protected final String PENDING_STATE = "派送中";

	protected final String UNKNOWN_STATE = "未知状态";

	protected static final String FORMATE_1 = "yyyy-MM-dd HH:mm:ss";
	
	protected static final String FORMATE_2 = "yyyy-MM-dd'T'HH:mm:ssXXX";

	public  List<TrackingTaskDetail> parseTrackContent(String response){ throw new RuntimeException("未实现");}

	protected Date parseDate(String dateStr) {
		return this.parseDate(dateStr, FORMATE_1);
	}

	protected Date parseDate(String dateStr, String format) {
		SimpleDateFormat sdfFormat = new SimpleDateFormat(format);
		try {
			return sdfFormat.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	protected Date tryParseDate(String dateStr, String... formates) throws Exception {
		SimpleDateFormat sdfFormat = new SimpleDateFormat();
		for (String format : formates) {
			try {
				sdfFormat.applyPattern(format);
				return sdfFormat.parse(dateStr);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return null;
	}

	/**
	 * 根据状态时间 升序
	 * @param trackDetails
	 */
	protected void sortTrackByTrackTimeAsc(List<TrackingTaskDetail> trackDetails) {
		if (ArrayUtils.isNotEmpty(trackDetails)) {
			trackDetails.sort((src, dest) -> {
				if (src.getTrackTime() != null && dest.getTrackTime() != null) {
					return src.getTrackTime().compareTo(dest.getTrackTime());
				} else if (src.getTrackTime() != null) {
					return 1;
				} else if (dest.getTrackTime() != null) {
					return -1;
				}
				return 0;
			});
		}
	}

}
