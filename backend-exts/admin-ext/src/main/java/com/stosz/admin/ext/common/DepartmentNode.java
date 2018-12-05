package com.stosz.admin.ext.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XCY on 2017/8/17.
 * desc:
 */
public class DepartmentNode implements Serializable {
    private Integer id;
    private String name;
    private String remark;
    private Integer tlevel;
    private String masterId;
    private String masterName;
    private String pySearch;
    private String status;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private Integer parentId;
    private String no;
    private List<DepartmentNode> children;
    //默认为叶子节点
    private Boolean leaf = true;

    public void addChild(DepartmentNode node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
        this.setLeaf(false);
    }

    public static DepartmentNode createRootNode() {
        DepartmentNode node = new DepartmentNode();
        node.setId(0);
        node.setParentId(null);
        node.setName("根");
        node.setLeaf(false);
        node.setNo("0");
        node.setTlevel(1);
        return node;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTlevel() {
        return tlevel;
    }

    public void setTlevel(Integer tlevel) {
        this.tlevel = tlevel;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getPySearch() {
        return pySearch;
    }

    public void setPySearch(String pySearch) {
        this.pySearch = pySearch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<DepartmentNode> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentNode> children) {
        this.children = children;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }


    @Override
    public String toString() {
        return "DepartmentNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", tlevel=" + tlevel +
                ", masterId=" + masterId +
                ", masterName='" + masterName + '\'' +
                ", pySearch='" + pySearch + '\'' +
                ", status='" + status + '\'' +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                ", parentId=" + parentId +
                ", no='" + no + '\'' +
                ", children=" + children +
                ", leaf=" + leaf +
                '}';
    }
}
