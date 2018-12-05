package com.stosz.tms.service.transport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.stosz.plat.common.SpringContextHolder;
import com.stosz.plat.utils.ArrayUtils;

@Component
public class TransportHandlerFactory implements InitializingBean {

	/*
	 * key handler key
	 */
	public Map<String, AbstractTransportHandler> transportHandlers;

	@Autowired
	private List<AbstractTransportHandler> list;

	@Override
	public void afterPropertiesSet() throws Exception {
		transportHandlers = new HashMap<>();
		if (ArrayUtils.isNotEmpty(list)) {
			for (AbstractTransportHandler transportHandler : list) {
				String className = transportHandler.getClass().getName();
				Assert.notNull(transportHandler.getCode(), String.format("%s,code不能为空", className));
				Assert.isTrue(!transportHandlers.containsKey(transportHandler.getCode().code()),
						String.format("%s,code=%s,已经存在,请检查", className, transportHandler.getCode()));
				transportHandlers.put(transportHandler.getCode().code(), transportHandler);
			}
		}
	}

	public AbstractTransportHandler getHandler(String type) {
		return transportHandlers.get(type);
	}

	public AbstractTransportHandler getNewHandler(String type) {
		AbstractTransportHandler handler = transportHandlers.get(type);
		if (handler != null) {
			return SpringContextHolder.getBean(handler.getClass());
		}
		return null;
	}

}
