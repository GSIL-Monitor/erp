package com.stosz.fsm;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 状态机Instance接口
 *
 * @ClassName IFsmInstance
 * @author shrek
 * @version 1.0
 */
public interface IFsmInstance extends Serializable {

	/**
	 * id
	 */
	abstract public Integer getId();

	/**
	 * parentId
	 */
	abstract public Integer getParentId();
	
	/**
	 * state
	 */
	abstract public String getState();

	abstract public void setState(String state);

	/**
	 * stateTime
	 */
	abstract public LocalDateTime getStateTime();

	abstract public void setStateTime(LocalDateTime stateTime);
	
	
}
