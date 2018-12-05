package com.stosz.plat.model;


import com.stosz.fsm.model.IFsmHistory;

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

    @DBColumn
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     */
    @DBColumn
    private LocalDateTime createAt;

    /**
     *
     */
    @DBColumn
    private String fsmName;

    /**
     * 来源网点id
     */
    @DBColumn
    private Integer objectId;

    /**
     * 目标网点
     */
    @DBColumn
    private String eventName;

    private String eventNameDisplay;

    /**
     * 数量
     */
    @DBColumn
    private String srcState;
    private String srcStateDisplay;
    /**
     * 物品单价
     */
    @DBColumn
    private String dstState;
    private String dstStateDisplay;

    /**
     * 金额
     */
    @DBColumn
    private Integer parentId;

    /**
     * int
     */
    @DBColumn
    private String optUid;

    /**
     * 状态时间
     */
    @DBColumn
    private LocalDateTime stateTime;

    public static final String _memo = "memo";
    /**
     * 描述
     */
    @DBColumn
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
        return createAt;
    }

    /**
     *
     */
    public void setCreateAt(LocalDateTime createTime) {
        this.createAt = createTime;
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