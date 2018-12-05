package com.stosz.fsm.exception;

@SuppressWarnings("serial")
public class FsmException extends RuntimeException {
	public FsmException( String msg )
	{
		super( msg );
	}
	public FsmException( String msg, Throwable ex )
	{
		super( msg, ex );
	}
}
