package com.stosz.fsm.handle;

import com.stosz.fsm.IFsmInstance;
import com.stosz.fsm.exception.FsmException;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.common.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 状态机Handle的Executor
 *
 * @ClassName FsmHandleExecutor
 * @author shrek
 * @version 1.0
 */
public class FsmHandleExecutor<T extends IFsmInstance> {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 执行Handle
	 *
	 * @param handler
	 * @param fsmInstance	
	 * @return void	
	 * @throws
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void execute(String handler, T fsmInstance, EventModel event) {
		
		IFsmHandler<T> bean = null;
		try {
			bean = SpringContextHolder.getBean(handler);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		if (bean == null) {
			String msg = String.format("获取Bean[%s]失败, 请确认该Bean的配置", handler);
			logger.error(msg);
			throw new FsmException(msg);
		}
		
		// 执行Handle
		logger.info("执行Handler[{}] start, event={}, fsmInstanceId:{} ,parentId:{}, state:{}, stateTime:{}", handler, event, fsmInstance.getId() ,fsmInstance.getParentId() , fsmInstance.getState() , fsmInstance.getStateTime() );
		bean.execute(fsmInstance, event);
		logger.info("执行Handler[{}] end, event={}, fsmInstanceId:{} ,parentId:{}, state:{} , stateTime:{}", handler, event, fsmInstance.getId() ,fsmInstance.getParentId() , fsmInstance.getState() , fsmInstance.getStateTime() );
	}

}
