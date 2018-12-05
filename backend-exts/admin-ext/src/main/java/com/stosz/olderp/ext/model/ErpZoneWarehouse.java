package com.stosz.olderp.ext.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 
 */
public class ErpZoneWarehouse implements Serializable {

    public static final Logger logger = LoggerFactory.getLogger(ErpZoneWarehouse.class);

    private Integer zwId;

    /**
     * 关联仓库id
     */
    private Integer idWarehouse;

    /**
     * 关联区域表id
     */
    private Integer idZone;

    /**
     * 优先级
     */
    private Integer level;

    /**
     * 启用状态:0未启用，1已启用
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    private Integer createUserId;

    private static final long serialVersionUID = 1L;

    public Integer getZwId() {
        return zwId;
    }

    public void setZwId(Integer zwId) {
        this.zwId = zwId;
    }

    public Integer getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(Integer idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 勿删！同步数据去重使用
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErpZoneWarehouse that = (ErpZoneWarehouse) o;
        return  Objects.equals(idWarehouse, that.idWarehouse) &&
                Objects.equals(idZone, that.idZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWarehouse, idZone);
    }

    @Override
    public String toString() {
        return "ErpZoneWarehouse{" +
                "zwId=" + zwId +
                ", idWarehouse=" + idWarehouse +
                ", idZone=" + idZone +
                ", level=" + level +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUserId=" + createUserId +
                '}';
    }

    public static void main(String[] args) {
        ErpZoneWarehouse a = new ErpZoneWarehouse();
        ErpZoneWarehouse b = new ErpZoneWarehouse();
        a.setIdZone(1);
        a.setIdWarehouse(2);
        b.setIdZone(1);
        b.setIdWarehouse(2);
        b.setLevel(1);
        List<ErpZoneWarehouse> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        logger.info(""+a);
        Set set = list.stream().collect(Collectors.toSet());
        Set set1 = new HashSet(list);
        logger.info(""+set.size());
        logger.info(""+set1.size());
        logger.info(""+a.equals(b));
    }
}