package com.stosz.fsm.model;

import com.stosz.fsm.IFsmInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * EventEntity
 *
 * @ClassName EventEntity
 * @author shrek
 * @version 1.0
 */
public class AliasModel<T extends IFsmInstance> {

	
	private String fsmName;
	private String srcState;
	private String dstState;
	private String eventName;
	private List<String> beforeHandler = new ArrayList<String>();
	private List<String> afterHandler = new ArrayList<String>();
	private List<String> triggerHandler = new ArrayList<String>();
	
	
	public AliasModel(String fsmName, String srcState, String dstState, String eventName) {
		this.fsmName = fsmName;
		this.srcState = srcState;
		this.dstState = dstState;
		this.eventName = eventName;
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



}

