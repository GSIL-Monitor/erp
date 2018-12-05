package com.stosz.fsm;

import com.google.common.base.Strings;
import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.fsm.exception.FsmException;
import com.stosz.fsm.handle.FsmHandleExecutor;
import com.stosz.fsm.model.EventModel;
import com.stosz.fsm.model.IFsmHistory;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.SpringContextHolder;
import com.stosz.plat.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * FsmProxyService FSM（Finite State Machine）
 *
 * @ClassName FsmProxyService
 * @author shrek
 * @version 1.0
 */
@Transactional(propagation = Propagation.REQUIRED)
public class FsmProxyService<T extends IFsmInstance> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

//	@Autowired
	private FsmHistoryService fsmHistoryService;
//	@Autowired
	private FsmStateContext fsmStateContent;

//	@Autowired
	private FsmHandleExecutor<T> fsmHandleExecutor;

	public void setFsmHistoryService(FsmHistoryService fsmHistoryService) {
		this.fsmHistoryService = fsmHistoryService;
	}

	public void setFsmStateContent(FsmStateContext fsmStateContent) {
		this.fsmStateContent = fsmStateContent;
	}

	public void setFsmHandleExecutor(FsmHandleExecutor<T> fsmHandleExecutor) {
		this.fsmHandleExecutor = fsmHandleExecutor;
	}

	/**
	 * processEvent srcState ==event==> dstState
	 *
	 * @param instance @param eventName @return void @throws
	 */
	public void processEvent(T instance, FsmEventEnum event, String memo) {
		this.processEvent(instance, event, null, null, memo);
	}

	/**
	 * processEvent srcState ==event==> dstState
	 *
	 * @param instance @param eventName @return void @throws
	 */
	public void processEvent(T instance, FsmEventEnum event, String optUid, LocalDateTime optTime, String description) {
		Assert.notNull(instance, "状态机事件处理异常，fsmInstance 为空");
		Assert.notNull(instance.getId(), "状态机事件处理异常，fsmInstance.id 为空");
		Assert.notNull(instance.getState(), "状态机事件处理异常，fsmInstance.state 为空");
		// init
		if (optUid == null) {
            optUid = MBox.getLoginUserName();
			if(Strings.isNullOrEmpty(optUid)) optUid="系统";
		}
		if (optTime == null) {
			optTime = LocalDateTime.now();
		}

		// log format util
		FsmLog fmsLog = new FsmLog(FsmUtils.getFsmName(instance), instance, event.name());

		try {
			logger.info("processEvent start, fsmInstanceId={} state:{} eventName={}", instance.getId(),
					instance.getState(), event.name());

			/**
			 * 初始化
			 */
			// 获取状态机类型, 每一个Entity只能有一个状态机
			String fsmName = FsmUtils.getFsmName(instance);

			// 原始状态以Instance的state为基准
			String srcState = instance.getState();

			/**
			 * 获取使用的Event, 并校验
			 */
			if (!fsmStateContent.containsEvent(fsmName, event.name())) {
				String msg = fmsLog.format("状态机[%s]状态流转异常, 未找到对应的event[%s], 当前状态[%s]", fsmName, event, srcState);
				logger.error(msg);
				throw new FsmException(msg);
			}
			if (!fsmStateContent.containsState(fsmName, srcState)) {
				String msg = fmsLog.format("状态机[%s]状态流转异常, 未找到对应的state[%s], 当前Event[%s]", fsmName, srcState, event);
				logger.error(msg);
				throw new FsmException(msg);
			}
			EventModel eventModel = fsmStateContent.getCurrEvent(fsmName, srcState, event.name());
			if (eventModel == null) {
				String msg = fmsLog.format("状态机[%s]状态无法跳转, 请确认当前状态[%s]是否允许执行[%s]事件", fsmName, srcState,
						event);
				logger.error(msg);
				throw new FsmException(msg);
			}

			/**
			 * 子状态机 关联逻辑
			 */
			// 查询子状态机
			// 确定子状态机情况
			// break
			// ######

			// before event 事务内
			int beforeHandlerSize = eventModel.getBeforeHandler() == null?0:eventModel.getBeforeHandler().size();
			int afterHandlerSize = eventModel.getAfterHandler() == null?0:eventModel.getAfterHandler().size();
			logger.trace(fmsLog.format("触发事务内的before事件, handler.size=%s, handler[]=%s", beforeHandlerSize,
					StringUtil.toString(eventModel.getBeforeHandler(), ", ")));
			invokeEventHandler(eventModel.getBeforeHandler(), instance, eventModel);

			/**
			 * 更新state
			 */
			logger.trace(
					fmsLog.format("更新状态 from [%s] to [%s], 涉及FsmInstance, FsmHistory ", srcState, eventModel.getDstState()));
			this.updateState(eventModel, instance, optUid, description);

			// after event 事务内
			logger.trace(fmsLog.format("触发事务内的after事件, handler.size=%s, handler[]=%s", afterHandlerSize,
					StringUtil.toString(eventModel.getBeforeHandler(), ", ")));
			invokeEventHandler(eventModel.getAfterHandler(), instance, eventModel);

			// 在EventHandler中不能包含修改实体状态的逻辑, 请检查配置
			String msg = "在EventHandler[Before, After]中不能包含修改实体状态的逻辑, 请检查配置";
			Assert.notNull(instance.getState(), msg +" current state is null!");
			Assert.isTrue(instance.getState().equalsIgnoreCase(eventModel.getDstState()), msg+" 当前状态:" + instance.getState() + " 应该是:" + eventModel.getDstState());
			// event trigger 事务外
			logger.trace(fmsLog.format("触发事务外的trigger事件, handler.size=%s, handler[]=%s",
					eventModel.getTriggerHandler().size(), StringUtil.toString(eventModel.getTriggerHandler(), ", ")));
			invokeTriggerHandler(eventModel.getTriggerHandler(), instance, eventModel);

			logger.trace(fmsLog.format("processEvent end, fsmInstanceId=%s", instance.getId()));
		} catch (Exception e) {
			String msg = fmsLog.format("processEvent error, %s", e.getMessage());
			logger.error(msg, e);
			throw new FsmException(msg);
		}
	}

	/**
	 * invoke event 事务内
	 *
	 * @param handlerList @param fsmInstance @param event @return void @throws
	 */
	private void invokeEventHandler(List<String> handlerList, T fsmInstance, EventModel event) {

		if (handlerList != null && handlerList.size() > 0) {
			for (String handler : handlerList) {
				logger.trace("--------------------------------------------------------------------");
				logger.trace("-- Event:{}  state:{} instanceId:{} handler:{}", event, fsmInstance.getState(),
						fsmInstance.getId(), handler);
				logger.trace("--------------------------------------------------------------------");
				fsmHandleExecutor.execute(handler.trim(), fsmInstance, event);
			}
		}

	}

	/**
	 * invoke event 事务外 通过ThreadLocal把数据传递出事务范围, 由XikeTransactionManager处理改数据
	 *
	 * @param handlerList @param fsmInstance @param event @return void @throws
	 */
	private void invokeTriggerHandler(List<String> handlerList, T fsmInstance, EventModel event) {

		if (handlerList != null && handlerList.size() > 0) {
			for (String trigger : handlerList) {
				logger.trace("--------------------------------------------------------------------");
				logger.trace("-- :Event:{} state:{} instanceId:{} Trigger:{}", trigger, event, fsmInstance.getState(),
						fsmInstance.getId());
				logger.trace("--------------------------------------------------------------------");
				TransationEventThreadLocal.addTrigger(trigger.trim(), fsmInstance, event);
			}
		}
	}

	/**
	 * updateState
	 *
	 * @param event @param fsmInstance @param description @return void @throws
	 */
	private void updateState(EventModel event, T fsmInstance, String optUid, String description) {

		LocalDateTime optTime = LocalDateTime.now();
		// log format util
		FsmLog fsmLog = new FsmLog(FsmUtils.getFsmName(fsmInstance), fsmInstance, event.getEventName());

		/**
		 * fsmInstance更新state
		 */
		String dstState = event.getDstState();
		String fsmName = event.getFsmName();

		/**
		 * 获取EntityDao
		 */
		String daoName = fsmName + "Mapper";
		daoName = StringUtil.toLowerFirstChar(daoName);
		Object bean = null;
		try {
			bean = SpringContextHolder.getBean(daoName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (bean == null) {
			String msg = fsmLog.format("状态机实体%s 没有对应的bean[%s], 请定义相应名字的bean或确认配置是否正确", fsmName, daoName);
			logger.error(msg);
			throw new FsmException(msg);
		}
		if (!(bean instanceof IFsmDao)) {
			String msg = fsmLog.format("bean[%s]必须实现IFsmDao, 请修改", daoName);
			logger.error(msg);
			throw new FsmException(msg);
		}

		@SuppressWarnings("unchecked")
		IFsmDao<T> dao = (IFsmDao<T>) bean;

		// 更新实体
		logger.trace("状态机更新状态:{}", fsmLog);
		fsmInstance.setState(dstState);
		fsmInstance.setStateTime(optTime);
		dao.updateState(fsmInstance.getId(), fsmInstance.getState(), optTime);

		/**
		 * FsmHistory增加记录
		 */
		logger.trace(fsmLog.format("save history with dstState=%s", dstState));
		Integer fsmHistoryId = fsmHistoryService.add(fsmInstance, event, optUid, optTime, description);
		logger.trace(fsmLog.format("save history, fsmHistoryId=%s", fsmHistoryId));

	}

	/**
	 * 增加状态机历史记录 空 ==save==> start
	 *
	 * @param fsmInstance @param description @throws
	 */
    public void processNew(T fsmInstance, String memo) {
        this.processNew(fsmInstance, null, memo);
    }

	/**
	 * 增加状态机历史记录 空 ==save==> start
	 *
	 * @param fsmInstance @param description @throws
	 */
    public void processNew(T fsmInstance, String optUid, String memo) {
        Assert.notNull(fsmInstance, "状态机实体不允许为空");

		// init
		if (optUid == null) {
			optUid = MBox.getLoginid();
		}

		// 获取新建状态机的Event
		EventModel eventNew = this.createEventNew(fsmInstance);

		// log format util

		FsmLog fmsLog = new FsmLog(FsmUtils.getFsmName(fsmInstance), fsmInstance, eventNew.getEventName());
		try {

			logger.info(fmsLog.format("processNew start, save new FSM Instance", fsmInstance.getId(),
					eventNew.getEventName()));

            this.updateState(eventNew, fsmInstance, optUid, memo);

			logger.trace(fmsLog.format("processNew end", fsmInstance.getId()));
		} catch (Exception e) {
			String msg = fmsLog.format("processNew error, %s", e.getMessage());
			logger.error(msg, e);
			throw new FsmException(msg);
		}
	}

	/**
	 * 获取默认的 创建状态机Event
	 *
	 * @param fsmInstance @return EventModel @throws
	 */
	private EventModel createEventNew(T fsmInstance) {
		logger.trace("save default EventNew, fsmInstanceId={}", fsmInstance.getId());
		EventModel eventNew = new EventModel(FsmUtils.getFsmName(fsmInstance), FsmConstant.STATE_START,
				FsmConstant.STATE_START, FsmConstant.EVENT_START);
		return eventNew;
	}

	/**
	 * FsmHistory增加日志记录 {src} ==log==> {src}
	 *
	 * @param fsmInstance
	 * @param description
	 */
	public void processLog(T fsmInstance, String description) {

		processLog(fsmInstance, FsmConstant.EVENT_LOG, description);
	}

	/**
	 * FsmHistory增加日志记录 {src} ==log==> {src}
	 *
	 * @param fsmInstance
	 * @param description
	 */
	public void processLog(T fsmInstance, String eventName, String description) {
		this.processLog(fsmInstance, eventName, null, null, description);
	}

	/**
	 * FsmHistory增加日志记录 {src} ==log==> {src}
	 *
	 * @param fsmInstance
	 * @param description
	 */
	public void processLog(T fsmInstance, String eventName, String optUid, LocalDateTime optTime, String description) {
		Assert.notNull(fsmInstance, "状态机实体不允许为空");
		Assert.notNull(fsmInstance, "状态机事件不允许为空");
		if (description == null) {
			description = "";
		}
		// init
		if (optUid == null) {
			optUid = MBox.getLoginid();
		}
		if (optTime == null) {
			optTime = LocalDateTime.now();
		}

		// 构建状态机日志记录Event
		EventModel eventLog = new EventModel(FsmUtils.getFsmName(fsmInstance), fsmInstance.getState(),
				fsmInstance.getState(), eventName);

		// log format util
		FsmLog fmsLog = new FsmLog(FsmUtils.getFsmName(fsmInstance), fsmInstance, eventLog.getEventName());

		try {
			logger.debug(fmsLog.format("processLog start", fsmInstance.getState()));
			Integer fsmHistoryId = fsmHistoryService.add(fsmInstance, eventLog, optUid, optTime, description);
			logger.trace(fmsLog.format("processLog end, fsmHistoryId=%s", fsmHistoryId));
		} catch (Exception e) {
			String msg = fmsLog.format("processLog error, %s", e.getMessage());
			logger.error(msg, e);
			throw new FsmException(msg);
		}
	}

	public int deleteFsmHistory(String fsmName , Integer objectId){
        int j = fsmHistoryService.deleteHistory(fsmName,objectId);
        logger.info("删除状态机历史成功，状态机:{} 状态机id:{} ，删除历史记录行数:{} " , fsmName , objectId , j);
        return j;

    }

	/**
	 * 查询状态机历史列表
	 * 
	 * @param fsmInstance
	 *            状态机实体
	 * @return List<FsmHistoryEntity> 状态机历史列表
	 * @see
	 */
	public List<IFsmHistory> queryFsmHistory(IFsmInstance fsmInstance) {
		try {
			logger.debug("queryFsmHistory start");

			List<IFsmHistory> result = this.queryFsmHistory(FsmUtils.getFsmName(fsmInstance), fsmInstance.getId());

			logger.trace("queryFsmHistory end");
			return result;
		} catch (Exception e) {
			String msg = String.format("queryFsmHistory, fsmInstance=%s", fsmInstance);
			logger.error(msg, e);
			throw new FsmException(msg);
		}
	}

	/**
	 * 查询父状态机历史列表
	 * 
	 * @param fsmInstance
	 *            状态机实体
	 * @return List<FsmHistoryEntity> 状态机历史列表
	 * @see
	 */
	public List<IFsmHistory> queryParentFsmHistory(IFsmInstance fsmInstance) {

		Assert.notNull(fsmInstance,"状态机实体不允许为空！");
		Assert.notNull(fsmInstance.getId(),"状态机实体Id不允许为空！");

		try {
			logger.trace("queryParentFsmHistory start, fsmInstance.parentId={}", fsmInstance.getParentId());

			String fsmName = FsmUtils.getFsmName(fsmInstance);
			String parentFsmName = fsmStateContent.getParentFsmName(fsmName);
			Integer parentEntityId = fsmInstance.getParentId();
			logger.trace("queryParentFsmHistory parentFsmName={} parentEntityId={}", parentFsmName, parentEntityId);

			if (parentFsmName == null || parentEntityId == null) {
				logger.trace("queryParentFsmHistory end parentFsmName is null  or parentEntityId is null,so result is empty!");
				return new ArrayList<IFsmHistory>();
			} else {
				List<IFsmHistory> result = this.queryFsmHistory(parentFsmName, parentEntityId);
				logger.trace("queryParentFsmHistory end parentFsmName is {}  , parentEntityId is {},result size is {}" , parentFsmName,parentEntityId , result.size());
				return result;
			}
		} catch (Exception e) {
			String msg = String.format("queryParentFsmHistory, fsmInstance=%s", fsmInstance);
			logger.error(msg, e);
			throw new FsmException(msg);
		}
	}

	public List<IFsmHistory> queryChildrenFsmHistory(String fsmName, Integer parentId) {
		Assert.isTrue(fsmName!=null && fsmName.length()!=0,"状态机名不允许为空");
		Assert.notNull(parentId,"父状态机Id不允许为空！");

		try {
			logger.trace("queryChildrenFsmHistory start, fsmName={}, parentId={}", fsmName, parentId);
			List<IFsmHistory> list = this.queryFsmHistory(fsmName , parentId);
			logger.trace("queryChildrenFsmHistory end");
			return list;
		} catch (Exception e) {
			String msg = String.format("queryChildrenFsmHistory, fsmName={}, parentId={}", fsmName, parentId);
			logger.error(msg, e);
			throw new FsmException(msg);
		}
	}

	/**
	 * 查询状态机历史列表
	 * 
	 * @return PageResult<FsmHistoryEntity> 状态机历史列表
	 * @see
	 */
	public List<IFsmHistory> queryFsmHistory(String fsmName, Integer entityId) {
		Assert.notNull(fsmName, "状态机名称不允许为空");
		Assert.notNull(entityId, "状态机实体id不允许为空");
		logger.trace("queryFsmHistory start fsmName:{} , entityId:{}", fsmName, entityId);
        List<IFsmHistory> result = fsmHistoryService.queryHistory(fsmName, entityId);
        logger.trace("queryFsmHistory end fsmName:{} , entityId:{}, history.size:{}", fsmName, entityId, result.size());

		return result;
	}

	/**
	 * 查询StateGroup包含的Stae列表
	 * 
	 * @param fsmName
	 * @param stateGroupName
	 * @return List<String> 包含的订单状态列表
	 * @see
	 */
	public List<String> getStateGroupDetail(String fsmName, String stateGroupName) {
		Assert.notNull(fsmName ,"状态机名称不允许为空");
		Assert.notNull(stateGroupName ,"分组名称不允许为空");

		try {
			logger.trace("getStateGroupDetail start");
			List<String> result = this.fsmStateContent.getStateGroupDetail(fsmName, stateGroupName);
			if (result == null) {
				String msg = String.format("状态机[%s]查询StateGroup包含的State列表, 未找到对应的StateList,请确认参数[%s]", fsmName, fsmName,
						stateGroupName);
				logger.error(msg);
				throw new FsmException(msg);
			}
			logger.trace("getStateGroupDetail end");
			return result;
		} catch (Exception e) {
			String msg = String.format("getStateGroupDetail, fsmName=%s, stateGroupName=%s", fsmName, stateGroupName);
			logger.error(msg, e);
			throw new FsmException(msg);
		}
	}

	/**
	 * 统一状态机日志格式的内部类
	 *
	 * @ClassName FsmLog
	 * @author kelvem
	 * @version 1.0
	 */
	private class FsmLog {
		private String fsmName;
		private T fsmInstance;
		private String eventName;

		public FsmLog(String fsmName, T fsmInstance, String eventName) {
			this.fsmName = fsmName;
			this.fsmInstance = fsmInstance;
			this.eventName = eventName;
		}

		public String format(String format, Object... args) {

			String msg = String.format(format, args);
			String str = String.format("状态机:%s instanceId:%s state:%s event:%s - %s", this.fsmName,
					this.fsmInstance.getId(), this.fsmInstance.getState(), this.eventName, msg);
			return str;
		}

		public String toString(){
			return format("");
		}
	}
}
