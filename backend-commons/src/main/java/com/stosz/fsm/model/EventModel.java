package com.stosz.fsm.model;

import com.stosz.fsm.xml.EventNode;

import java.util.ArrayList;
import java.util.List;

/**
 * EventModel
 *
 * @ClassName EventModel
 * @author shrek
 * @version 1.0
 */
public class EventModel {

	
	private String fsmName;
	private String srcState;
	private String dstState;
	private String eventName;
	private List<String> beforeHandler = new ArrayList<String>();
	private List<String> afterHandler = new ArrayList<String>();
	private List<String> triggerHandler = new ArrayList<String>();
	
	
	public EventModel(String fsmName, String srcState, String dstState, String eventName) {
		this.fsmName = fsmName;
		this.srcState = srcState;
		this.dstState = dstState;
		this.eventName = eventName;
	}

	/**
	 * 构造函数
	 */
	public EventModel() {
		// do nothing
	}
	
	/**
	 * @param event
	 */
	public EventModel(String fsmName, EventNode event) {
		
		this.fsmName = fsmName;
		this.srcState = event.getSrcState();
		this.dstState = event.getDstState();
		this.eventName = event.getName();
		
		this.beforeHandler = str2List(event.getBefore(), ",");
		this.afterHandler = str2List(event.getAfter(), ",");
		this.triggerHandler = str2List(event.getTrigger(), ",");
		
	}
	
	private List<String> str2List(String str, String delimiter) {
		String[] buf = str.split(delimiter);
		List<String> result = new ArrayList<String>();
		for (String cell : buf) {
			if (cell == null || cell.trim().length() <= 0) {
				continue;
			}
			result.add(cell);
		}
		return result;
	}
	
	private String list2Str(List<String> list, String delimiter) {
		if (list == null || list.size() <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String cell : list) {
			if (cell == null || cell.trim().length() <= 0) {
				continue;
			}
			sb.append(cell);
		}
		return sb.toString();
	}

	public String getFsmName() {
		return fsmName;
	}
	public void setFsmName(String fsmName) {
		this.fsmName = fsmName;
	}
	public String getSrcState() {
		return srcState;
	}
	public void setSrcState(String srcState) {
		this.srcState = srcState;
	}
	public String getDstState() {
		return dstState;
	}
	public void setDstState(String dstState) {
		this.dstState = dstState;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public List<String> getBeforeHandler() {
		return beforeHandler;
	}
	public void setBeforeHandler(List<String> beforeHandler) {
		this.beforeHandler = beforeHandler;
	}

	public List<String> getAfterHandler() {
		return afterHandler;
	}
	public void setAfterHandler(List<String> afterHandler) {
		this.afterHandler = afterHandler;
	}

	public List<String> getTriggerHandler() {
		return triggerHandler;
	}
	public void setTriggerHandler(List<String> triggerHandler) {
		this.triggerHandler = triggerHandler;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EventModel {");
		sb.append("  fsmName=" + fsmName);
		sb.append(", srcState=" + srcState);
		sb.append(", dstState=" + dstState);
		sb.append(", eventName=" + eventName);
		sb.append(", beforeHandler=" + list2Str(this.beforeHandler, ","));
		sb.append(", afterHandler=" + list2Str(this.afterHandler, ","));
		sb.append(", triggerHandler=" + list2Str(this.triggerHandler, ","));
		sb.append("}");
		
		return sb.toString();
	}

}

