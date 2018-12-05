package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品分类实体类
 * @author he_guitang
 *
 */
public class Category extends AbstractParamEntity implements Serializable , ITable,Comparable<Category> {

	private static final long serialVersionUID = -8861024700372252655L;

	public static Category ROOT(){
        Category root = new Category();
        root.setNo("");
        root.setParentId(-1);
        root.setId(0);
        root.setLeaf(false);
        root.setName("根");
        root.setUsable(true);
        return root;
    }

	private Integer id;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String name;

    private Integer parentId;

    private String no;

    private Integer sortNo;

    private Boolean usable;

    private Integer creatorId;

    private String creator;

    private Boolean leaf;

    //产品分类的孩子
    private List<Category> children;

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public void addChild(Category category){
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(category);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
        this.no = no == null ? null : no.trim();
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean  usable) {
        this.usable = usable;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    @Override
    public String getTable() {
        return "category";
    }

    @Override
    public int compareTo(Category o) {
        return this.getNo().compareTo(o.getNo());
    }

}
