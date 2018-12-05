package com.stosz.fsm.exception;

@SuppressWarnings("serial")
public class ConfigInvalidException extends FsmException {
	private ConfigInvalidType type;
	public ConfigInvalidException(ConfigInvalidType type, String msg ) {
		super(msg);
		this.type = type;
	}	
	public ConfigInvalidException(ConfigInvalidType type, String msg, Throwable ex ) {
		super(msg,ex);
		this.type = type;
	}
	public ConfigInvalidType getType(){
		return type;
	}
}
