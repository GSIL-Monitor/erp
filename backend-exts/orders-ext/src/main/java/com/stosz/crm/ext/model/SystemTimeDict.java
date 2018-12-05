package com.stosz.crm.ext.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 
 */
public class SystemTimeDict implements Serializable {

    private Integer id;

    /**
     * 项目
     */
    private String system;

    /**
     * 类型
     */
    private String type;

    /**
     * 上次操作时间
     */
    private LocalDateTime lastTime;

    private String memo;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "SystemTimeDict{" +
                "id=" + id +
                ", system='" + system + '\'' +
                ", type='" + type + '\'' +
                ", lastTime=" + lastTime +
                ", memo='" + memo + '\'' +
                '}';
    }
}