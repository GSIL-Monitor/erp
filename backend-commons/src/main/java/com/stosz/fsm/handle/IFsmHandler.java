package com.stosz.fsm.handle;

import com.stosz.fsm.IFsmInstance;
import com.stosz.fsm.model.EventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 状态机的Handler基类
 * BeforeHandler, AfterHandler, TriggerHandler
 *
 * @ClassName IFsmHandler
 * @author shrek
 * @version 1.0
 */
public abstract class IFsmHandler<T extends IFsmInstance> {

	

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	abstract public void execute(T t, EventModel event);
	
}
