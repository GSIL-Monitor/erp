package com.stosz.fsm;

import com.stosz.fsm.model.EventModel;
import com.stosz.fsm.model.FsmTemplateModel;
import com.stosz.fsm.xml.FsmXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * FsmStateInfo
 *
 * @ClassName FsmStateInfo
 * @author shrek
 * @version 1.0
 */
public class FsmStateContext implements InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, FsmTemplateModel> fsmTemplateMap = new LinkedHashMap<String, FsmTemplateModel>();

	private String xmlFile;

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	/**
	 * 加载FsmStateContent, 通过XML文件
	 *	
	 * @return void	
	 * @throws
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("初始化状态机Content开始");
		
		FsmXmlLoader loader = new FsmXmlLoader();
		loader.loadFsmStateContent(this,xmlFile);
		
		logger.info("初始化状态机Content结束");
	}
	

	/**
	 * addFsmTemplate
	 *
	 * @param fsmTemplate	
	 * @return void	
	 * @throws
	 */
	public void addFsmTemplate(FsmTemplateModel fsmTemplate) {
		this.fsmTemplateMap.put(fsmTemplate.getFsmName(), fsmTemplate);
	}
	/**
	 * 获取FsmTemplate
	 *
	 * @param fsmName
	 * @return	
	 * @return FsmTemplateModel	
	 * @throws
	 */
	public FsmTemplateModel getFsmTemplate(String fsmName) {
		return fsmTemplateMap.get(fsmName);
	}

	/**
	 * getCurrEvent
	 *
	 * @param fsmName
	 * @param srcState
	 * @param eventName
	 * @return	
	 * @return EventEntity	
	 * @throws
	 */
	public EventModel getCurrEvent(String fsmName, String srcState, String eventName) {
		FsmTemplateModel fsmTemplate = getFsmTemplate(fsmName);
		
		if (fsmTemplate == null) {
			return null;
		}
		return fsmTemplate.getEvent(srcState, eventName);
	}
	
	/**
	 * 是否定义了eventName
	 *
	 * @param eventName
	 * @return	
	 * @return boolean	
	 * @throws
	 */
	public boolean containsEvent(String fsmName, String eventName) {
		FsmTemplateModel fsmTemplate = getFsmTemplate(fsmName);
		
		if (fsmTemplate == null) {
			return false;
		}
		return fsmTemplate.containsEvent(fsmName, eventName);
	}
	
	/**
	 * 是否定义了stateName
	 *
	 * @param stateName
	 * @return	
	 * @return boolean	
	 * @throws
	 */
	public boolean containsState(String fsmName, String stateName) {
		FsmTemplateModel fsmTemplate = getFsmTemplate(fsmName);
		
		if (fsmTemplate == null) {
			return false;
		}
		return fsmTemplate.containsState(fsmName, stateName);
	}
	
	/**
	 * getParentFsmName
	 *
	 * @return	
	 * @return String	
	 * @throws
	 */
	public String getParentFsmName(String fsmName) {
		FsmTemplateModel fsmTemplate = getFsmTemplate(fsmName);
		return fsmTemplate.getParentFsmName();
	}
	
	/**
	 * 查询StateGroup包含的Stae列表
	 * 
	 * @param fsmName
	 * @param stateGroupName
	 * @return List<String> 包含的订单状态列表
	 * @see
	 */
	public List<String> getStateGroupDetail(String fsmName, String stateGroupName) {
		FsmTemplateModel fsmTemplate = getFsmTemplate(fsmName);
		Map<String, List<String>> groupStateMapping = fsmTemplate.getStateGroupMapping();
		if (groupStateMapping.containsKey(stateGroupName)) {
			return groupStateMapping.get(stateGroupName);
		} else {
			return null;
		}
	}
	
	
	

//	private void loadByCode() {
//		logger.info("通过代码方式初始化");
//		/**
//		 * eventStart
//		 */
//		EventModel eventStart = addEvent("OrdersEntity", OrdersFsmStateEnum.start, OrdersFsmStateEnum.draft, OrdersFsmEventEnum.start);
//	
//		/**
//		 * eventPaymentReceived
//		 */
//		EventModel eventPaymentReceived = addEvent("OrdersEntity", OrdersFsmStateEnum.draft, OrdersFsmStateEnum.payment_received, OrdersFsmEventEnum.received_payment);
//		eventPaymentReceived.getAfterHandler().add("stateChangeNotityFrontHandler");
//		eventPaymentReceived.getAfterHandler().add("matchingGoodsHandler");
//	
//		/**
//		 * eventMatching
//		 */
//		EventModel eventMatching = addEvent("OrdersEntity", OrdersFsmStateEnum.payment_received, OrdersFsmStateEnum.all_matched, OrdersFsmEventEnum.matching);
//	
//		/**
//		 * eventSendToWH
//		 */
//		EventModel eventSendToWH = addEvent("OrdersEntity", OrdersFsmStateEnum.all_matched, OrdersFsmStateEnum.send_to_wh, OrdersFsmEventEnum.send_to_wh);
//		// 测试无法到达路径
//	//	addEvent("OrdersEntity", OrdersFsmStateEnum.payment_received, OrdersFsmStateEnum.payment_received, OrdersFsmEventEnum.send_to_wh);
//	//	addEvent("OrdersEntity", OrdersFsmStateEnum.all_matched, OrdersFsmStateEnum.send_to_wh, OrdersFsmEventEnum.matching);
//	}
//	
//
//	public EventModel addEvent(String fsmName, OrdersFsmStateEnum src, OrdersFsmStateEnum dst, OrdersFsmEventEnum eventName) {
//		EventModel event = new EventModel(fsmName, src.name(), dst.name(), eventName.name());
//		eventList.add(event);
//		eventMap.put(getEventKey(event), event);
//		addEvent(fsmName, eventName.name().toLowerCase());
//		addState(fsmName, src.name().toLowerCase());
//		addState(fsmName, dst.name().toLowerCase());
//		return event;
//	}





}
