package com.stosz.fsm.model;

import java.time.LocalDateTime;

/**
 * 状态机历史实体类
 *
 * @ClassName FsmHistoryEntity
 * @author shiqiangguo
 * @version 1.0
 */
public interface IFsmHistory  {

	public Integer getId() ;
	public void setId(Integer id) ;

	public LocalDateTime getCreateAt();

	public void setCreateAt(LocalDateTime createAt);
	/**
	 * 状态机类型,实体的Class名称
	 */
	public String getFsmName() ;
	/**
	 * 状态机类型,实体的Class名称
	 */
	public void setFsmName(String fsmName);
	
	/**
	 * 关联实体对象的entity_id
	 */
	public Integer getObjectId() ;
	/**
	 * 关联实体对象的entity_id
	 */
	public void setObjectId(Integer entityId) ;
	
	/**
	 * 事件名称
	 */
	public String getEventName() ;
	/**
	 * 事件名称
	 */
	public void setEventName(String eventName);
	
	/**
	 * 原始状态
	 */
	public String getSrcState();
	/**
	 * 原始状态
	 */
	public void setSrcState(String srcState) ;
	
	/**
	 * 目标状态
	 */
	public String getDstState() ;
	/**
	 * 目标状态
	 */
	public void setDstState(String dstState) ;
	
	/**
	 * 父状态机实体Id
	 */
	public Integer getParentId();
	/**
	 * 父状态机实体Id
	 */
	public void setParentId(Integer parentId);
	
	/**
	 * 操作人的uid
	 */
	public String getOptUid() ;
	/**
	 * 操作人的uid
	 */
	public void setOptUid(String optUid) ;
	
	/**
	 * 状态机信息描述
	 */
	public String getMemo() ;
	/**
	 * 状态机信息描述
	 */
	public void setMemo(String memo);
	


	/**
	 * 状态机类型,实体的Class名称
	 */
	public static final String _fsmName = "fsmName";
	
	/**
	 * 关联实体对象的entity_id
	 */
	public static final String _entityId = "entityId";
	
	/**
	 * 事件名称
	 */
	public static final String _eventName = "eventName";
	
	/**
	 * 原始状态
	 */
	public static final String _srcState = "srcState";
	
	/**
	 * 目标状态
	 */
	public static final String _dstState = "dstState";
	
	/**
	 * 父状态机Id
	 */
	public static final String _parentId = "parentId";
	
	/**
	 * 操作人的uid
	 */
	public static final String _optUid = "optUid";
	
	/**
	 * 状态机信息描述
	 */
	public static final String _remark = "remark";
	
}
