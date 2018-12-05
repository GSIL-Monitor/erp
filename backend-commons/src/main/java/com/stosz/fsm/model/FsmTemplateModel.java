package com.stosz.fsm.model;

import com.stosz.plat.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * FsmTemplateModel
 *
 * @ClassName FsmTemplateModel
 * @author shrek
 * @version 1.0
 */
public class FsmTemplateModel {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	

	private List<EventModel> eventList = new ArrayList<EventModel>();
	private Map<String, EventModel> eventMap = new LinkedHashMap<String, EventModel>();
	
	private Set<String> stateNameSet = new LinkedHashSet<String>();
	private Set<String> eventNameSet = new LinkedHashSet<String>();
	private Map<String, List<String>> groupStateMapping = new HashMap<String, List<String>>();
	
	private String parentFsmName;
	private String fsmName;
	
	

	/**
	 * 无参数构造函数
	 */
	public FsmTemplateModel() {
	}
	
	/**
	 * @param entityName
	 */
	public FsmTemplateModel(String fsmName, String parentFsmName) {
		this.fsmName = fsmName;
		this.setParentFsmName(parentFsmName);
	}


	//-----------------------------------------------------------
	//        方法
	//-----------------------------------------------------------

	/**
	 * getEvent
	 *
	 * @param fsmName
	 * @param srcState
	 * @param eventName
	 * @return	
	 * @return EventEntity	
	 * @throws
	 */
	public EventModel getEvent(String srcState, String eventName) {
		
		String eventKey = getEventKey(this.fsmName, srcState, eventName);
		if (eventMap.containsKey(eventKey)) {
			EventModel event = eventMap.get(eventKey);
			return event;
		} else {
			return null;
		}
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
		
		if (eventNameSet.contains(eventName.toLowerCase())) {
			return true;
		} else {
			return false;
		}
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
		
		if (stateNameSet.contains(stateName.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
	
	//-----------------------------------------------------------
	//        构造
	//-----------------------------------------------------------

	/**
	 * addEvent
	 *
	 * @param entityName
	 * @param model	
	 * @return void	
	 * @throws
	 */
	public EventModel addEvent(EventModel event) {
		
		eventList.add(event);
		eventMap.put(getEventKey(event), event);
		addEvent(event.getEventName().toLowerCase());
		addState(event.getSrcState().toLowerCase());
		addState(event.getDstState().toLowerCase());
		return event;
	}
	public void addState(String stateName) {
		if (stateName != null) {
			stateNameSet.add(stateName);
		}
	}
	public void addEvent(String eventName) {
		if (eventName != null) {
			eventNameSet.add(eventName);
		}
	}
	
	
	//-----------------------------------------------------------
	//        log
	//-----------------------------------------------------------
	public void printLog() {
		logger.info("状态机[{}] 信息如下 -------------------------------------", fsmName);
		if (parentFsmName != null && parentFsmName.trim().length() > 0) {
			logger.info("状态机[{}] 的父状态机[{}] ", fsmName, parentFsmName);
		}
		
		StringBuilder sb = new StringBuilder();
		for (String name : stateNameSet) {
			sb.append(name).append(", ");
		}
		logger.info("状态机[{}] 包含的State : {}", fsmName, sb.toString());
		
		sb = new StringBuilder();
		for (String name : eventNameSet) {
			sb.append(name).append(", ");
		}
		logger.info("状态机[{}] 包含的Event : {}", fsmName, sb.toString());
		
		logger.info("状态机[{}] Event信息如下", fsmName);
		for (EventModel event : eventList) {
			logger.info("状态机[{}] Event[{}] : {}", fsmName, event.getEventName(), event);
		}
		
		logger.info("状态机[{}] GroupState信息如下", fsmName);
		for (String groupName : groupStateMapping.keySet()) {
			List<String> stateNameList = groupStateMapping.get(groupName);
			logger.info("状态机[{}] GroupState[{}] : {}", fsmName, groupName, StringUtil.toString(stateNameList, ","));
		}
	}
	
	
	//-----------------------------------------------------------
	//        private
	//-----------------------------------------------------------
	private String getEventKey(String fsmName, String srcState, String eventName) {
		String key = fsmName + "-" + srcState.toLowerCase() + "-" + eventName.toLowerCase();
		return key;
	}
	
	private String getEventKey(EventModel event) {
		String key = event.getFsmName() + "-" + event.getSrcState().toLowerCase() + "-" + event.getEventName().toLowerCase();
		return key;
	}


	//-----------------------------------------------------------
	//        get / set
	//-----------------------------------------------------------
	public String getParentFsmName() {
		return parentFsmName;
	}
	public void setParentFsmName(String parentFsmName) {
		if (parentFsmName == null || parentFsmName.trim().length() <= 0) {
			this.parentFsmName = null;
		} else {
			this.parentFsmName = parentFsmName;
		}
	}

	public String getFsmName() {
		return fsmName;
	}
	public void setFsmName(String fsmName) {
		this.fsmName = fsmName;
	}

	public Map<String, List<String>> getStateGroupMapping() {
		return groupStateMapping;
	}

	public void setGroupStateMapping(Map<String, List<String>> groupStateMapping) {
		this.groupStateMapping = groupStateMapping;
	}
	
	
	
}

