package com.stosz.fsm;

import com.stosz.fsm.model.EventModel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 存储状态机Trigger数据
 * 实现trigger事务外
 *
 * @ClassName TransationEventThreadLocal
 * @author shrek
 * @version 1.0
 */
public class TransationEventThreadLocal {

	/**
	 * 状态机Trigger数据
	 */
	private static ThreadLocal<Set<Triple<String, IFsmInstance, EventModel>>> triggerSet = new ThreadLocal<Set<Triple<String, IFsmInstance, EventModel>>>();

	/**
	 * 获取Trigger的数据
	 *
	 * @return Set<Triple<trigger, IFsmInstance, EventModel>>
	 * @throws
	 */
	public static Set<Triple<String, IFsmInstance, EventModel>> getTriggerMap() {

		Set<Triple<String, IFsmInstance, EventModel>> result = triggerSet.get();
		if (result == null) {
			return new LinkedHashSet<Triple<String, IFsmInstance, EventModel>>();
		} else {
			return result;
		}
	}

	/**
	 * 添加Trigger
	 *
	 * @param trigger
	 * @param fsmInstance
	 * @param event	
	 * @return void	
	 * @throws
	 */
	public static void addTrigger(String trigger, IFsmInstance fsmInstance, EventModel event) {
		Set<Triple<String, IFsmInstance, EventModel>> buf = triggerSet.get();
		if (buf == null) {
			buf = new LinkedHashSet<Triple<String, IFsmInstance, EventModel>>();
		}
		buf.add(Triple.of(trigger, fsmInstance, event));

		triggerSet.set(buf);
	}

	/**
	 * 删除Trigger的数据
	 *	
	 * @return void	
	 * @throws
	 */
	public static void removeTriggerSet() {
		triggerSet.remove();
	}

}
