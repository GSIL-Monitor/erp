package com.stosz.fsm.exception;

@SuppressWarnings("serial")
public class StateChangedException extends FsmException {
	private String eventName;
	private String currentState;
	public StateChangedException( String eventName, String currentState)
	{
		super( "状态流转异常, 未找到对应的event, 提交Event["+eventName+"]当前状态["+currentState+"]" );
		this.eventName = eventName;
		this.currentState = currentState;
	}
	public String getCommitState(){
		return eventName;
	}
	public String getCurrentState(){
		return currentState;
	}
}
