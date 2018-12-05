package com.stosz.admin.ext.model;

import com.stosz.fsm.model.IFsmHistory;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;

/**
 * 状态机历史实体类
 *
 * @ClassName FsmHistory
 * @author shiqiangguo
 * @version 1.0
 */
public class FsmHistory extends AbstractParamEntity implements ITable, IFsmHistory {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1L;

	/**
	 * 空构造函数
	 */
	public FsmHistory() {
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static final String _create_time="create_time";
	public static final String _createTime="createTime";
	/**
	 * 
	 */
	private LocalDateTime createTime;

	public static final String _fsm_name = "fsm_name";
	public static final String _fsmName = "fsmName";
	/**
	 * 
	 */
	private String fsmName;
	
	public static final String _object_id="object_id";
	public static final String _objectId="objectId";
	/**
	 * 来源网点id
	 */
	private Integer objectId;
	
	public static final String _event_name="event_name";
	public static final String _eventName="eventName";
	/**
	 * 目标网点
	 */
	private String eventName;

	private String eventNameDisplay;
	
	public static final String _src_state="src_state";
	public static final String _srcState="srcState";
	/**
	 * 数量
	 */
	private String srcState;
	private String srcStateDisplay;
	public static final String _dst_state="dst_state";
	public static final String _dstState="dstState";
	/**
	 * 物品单价
	 */
	private String dstState;
	private String dstStateDisplay;
	
	public static final String _parent_id="parent_id";
	public static final String _parentId="parentId";
	/**
	 * 金额
	 */
	private Integer parentId;
	
	public static final String _opt_uid="opt_uid";
	public static final String _optUid="optUid";
	/**
	 * int
	 */
	private String optUid;
	
	public static final String _state_time="state_time";
	public static final String _stateTime="stateTime";
	/**
	 * 状态时间
	 */
	private LocalDateTime stateTime;

	public static final String _memo = "memo";
	/**
	 * 描述
	 */
	private String memo;

	public String getEventNameDisplay() {
		return eventNameDisplay;
	}

	public void setEventNameDisplay(String eventNameDisplay) {
		this.eventNameDisplay = eventNameDisplay;
	}

	public String getSrcStateDisplay() {
		return srcStateDisplay;
	}

	public void setSrcStateDisplay(String srcStateDisplay) {
		this.srcStateDisplay = srcStateDisplay;
	}

	public String getDstStateDisplay() {
		return dstStateDisplay;
	}

	public void setDstStateDisplay(String dstStateDisplay) {
		this.dstStateDisplay = dstStateDisplay;
	}

	/**
	 * 
	 */
	public LocalDateTime getCreateAt() {
		return createTime;
	}

	/**
	 * 
	 */
	public void setCreateAt(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 
	 */
	public String getFsmName() {
		return fsmName;
	}
	/**
	 * 
	 */
	public void setFsmName(String fsmName) {
		this.fsmName = fsmName;
	}
	
	/**
	 * 来源网点id
	 */
	public Integer getObjectId() {
		return objectId;
	}
	/**
	 * 来源网点id
	 */
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	
	/**
	 * 目标网点
	 */
	public String getEventName() {
		return eventName;
	}
	/**
	 * 目标网点
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	/**
	 * 数量
	 */
	public String getSrcState() {
		return srcState;
	}
	/**
	 * 数量
	 */
	public void setSrcState(String srcState) {
		this.srcState = srcState;
	}
	
	/**
	 * 物品单价
	 */
	public String getDstState() {
		return dstState;
	}
	/**
	 * 物品单价
	 */
	public void setDstState(String dstState) {
		this.dstState = dstState;
	}
	
	/**
	 * 金额
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 金额
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * int
	 */
	public String getOptUid() {
		return optUid;
	}
	/**
	 * int
	 */
	public void setOptUid(String optUid) {
		this.optUid = optUid;
	}
	
	/**
	 * 状态时间
	 */
	public LocalDateTime getStateTime() {
		return stateTime;
	}

	/**
	 * 状态时间
	 */
	public void setStateTime(LocalDateTime stateTime) {
		this.stateTime = stateTime;
	}
	
	/**
	 * 描述
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * 描述
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public String getTable() {
		return "fsm_history";
	}
	
}
