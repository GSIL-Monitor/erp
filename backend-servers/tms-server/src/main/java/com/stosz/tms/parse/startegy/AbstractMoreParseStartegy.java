package com.stosz.tms.parse.startegy;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

import java.util.*;

public abstract class AbstractMoreParseStartegy extends AbstractParseStartegy{

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
