package com.stosz.admin.ext.common;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuNode implements Serializable {

    private Integer id;

    private String name;

    private String keyword;

    private Integer parentId;

    private String url;

    private String no;

    private String icon;

    private Long sort;

    private String remark;

    private List<MenuNode> children;

    private Boolean isChecked = false;

    //默认为叶子节点
    private Boolean leaf = true;

    public List<MenuNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }

    public void addChild(MenuNode node){
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
        this.setLeaf(false);
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf=leaf;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "MenuNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", keyword='" + keyword + '\'' +
                ", parentId=" + parentId +
                ", url='" + url + '\'' +
                ", no='" + no + '\'' +
                ", icon='" + icon + '\'' +
                ", sort=" + sort +
                ", remark='" + remark + '\'' +
                ", children=" + children +
                ", isChecked=" + isChecked +
                ", leaf=" + leaf +
                '}';
    }

    public static MenuNode createRootNode() {
        MenuNode node = new MenuNode();
        node.setId(0);
        node.setParentId(null);
        node.setKeyword("");
        node.setNo("00");
        node.setSort(1L);
        node.setName("根");
        node.setChecked(true);
        node.setRemark("根节点");
        node.setLeaf(false);
        return node;
    }

}
