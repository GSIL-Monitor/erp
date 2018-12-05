package com.stosz.tms.service.track;

import com.stosz.plat.utils.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TrackHandlerFactory implements InitializingBean {

	/**
	 * key handler key
	 */
	public Map<String, AbstractSingleTrackHandler> trackHandlerMap;

	@Autowired
	private List<AbstractSingleTrackHandler> list;

	@Override
	public void afterPropertiesSet() throws Exception {
		trackHandlerMap = new HashMap<>();
		if (ArrayUtils.isNotEmpty(list)) {
			for (AbstractSingleTrackHandler abstractTransportHandler : list) {
				trackHandlerMap.put(abstractTransportHandler.getCode().code(), abstractTransportHandler);
			}
		}
	}

	public AbstractSingleTrackHandler getHandler(String type) {
		return trackHandlerMap.get(type);
	}
}
