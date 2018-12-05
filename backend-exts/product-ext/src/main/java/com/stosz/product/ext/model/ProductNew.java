package com.stosz.product.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.common.MBox;
import com.stosz.plat.enums.ClassifyEnum;
import com.stosz.plat.enums.CustomEnum;
import com.stosz.plat.enums.GoalEnum;
import com.stosz.plat.enums.SourceEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.product.ext.enums.ProductNewState;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 查重分类公共model
 *
 * @author Administrator
 * 
 */
public class ProductNew extends AbstractParamEntity implements IFsmInstance, ITable {

	private static final long serialVersionUID = 1L;
	@DBColumn
	private Integer id;
	@DBColumn
	private LocalDateTime createAt;
	@DBColumn
	private String title;
	@DBColumn
	private Integer topCategoryId;
	@DBColumn
	private Integer categoryId;
	@DBColumn
	private ClassifyEnum classifyEnum;
	@DBColumn
	private String mainImageUrl;
	@DBColumn
	private SourceEnum sourceEnum;
	@DBColumn
	private String sourceUrl;
	@DBColumn
	private String purchaseUrl;
	@DBColumn
	private String state;
	@DBColumn
	private LocalDateTime stateTime;
	@DBColumn
	private String memo;
	@DBColumn
	private Integer creatorId;
	@DBColumn
	private String creator;
	@DBColumn
	private String departmentNo;
	@DBColumn
	private Integer departmentId;
	@DBColumn
	private String departmentName;
	@DBColumn
	private Integer checkerId;
	@DBColumn
	private String checker;
	@DBColumn
	private String spu;
	@DBColumn
	private String attributeDesc;// 多语言描述
	@DBColumn
	private CustomEnum customEnum;
	@DBColumn
	private GoalEnum goalEnum;
	@DBColumn
	private String innerName;
	@DBColumn
	private LocalDateTime submitTime;
//	    @DBColumn
    private Integer advertTopCategoryId;

	@DBColumn
	private String disableMarkup;
	private Integer auditorId;//审核人id
	private String ids;
    private List<Integer> categoryIds;
    List<ProductNewZone> productNewZones;
    private Boolean userLabel;
	/**
	 * 国家Code
	 */
	private String zoneCode;
	/**
	 * 新品国家名次
	 */
	private String zoneName;
	/**
	 * 新品分类名称
	 */
	private String categoryName;
	private String categoryNo;
	private String topCategoryName;
	/**
	 * 当前新品一级分类36进制
	 */
	private String topCategoryNo;
	private Collection<Integer> topCategories;
	private List<Integer> departmentIds;

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

    public Integer getAdvertTopCategoryId() {
        return advertTopCategoryId;
    }

    public void setAdvertTopCategoryId(Integer advertTopCategoryId) {
        this.advertTopCategoryId = advertTopCategoryId;
    }

	public Boolean getUserLabel() {
		return userLabel == null? false : userLabel;
	}

	public void setUserLabel(Boolean userLabel) {
		this.userLabel = userLabel;
	}

	public String getDisableMarkup() {
		return disableMarkup;
	}

	public void setDisableMarkup(String disableMarkup) {
		this.disableMarkup = disableMarkup;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getTable() {
		return "product_new";
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getTopCategoryName() {
		return topCategoryName;
	}

	public void setTopCategoryName(String topCategoryName) {
		this.topCategoryName = topCategoryName;
	}

	public String getTopCategoryNo() {
		return topCategoryNo;
	}

	public void setTopCategoryNo(String topCategoryNo) {
		this.topCategoryNo = topCategoryNo;
	}

	@Override
	public Integer getCreatorId() {
		return creatorId;
	}

	@Override
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public Collection<Integer> getTopCategories() {
		return topCategories;
	}

	public void setTopCategories(Collection<Integer> topCategories) {
		this.topCategories = topCategories;
	}

	public String getClassifyEnumName() {
		return MBox.getDisplayByIEnum(classifyEnum);
	}

	public String getSourceEnumName() {
		return MBox.getDisplayByIEnum(sourceEnum);
	}

	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public List<ProductNewZone> getProductNewZones() {
		return productNewZones;
	}

	public void setProductNewZones(List<ProductNewZone> productNewZones) {
		this.productNewZones = productNewZones;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getTopCategoryId() {
		return topCategoryId;
	}

	public void setTopCategoryId(Integer topCategoryId) {
		this.topCategoryId = topCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public ClassifyEnum getClassifyEnum() {
		return classifyEnum;
	}

	public void setClassifyEnum(ClassifyEnum classifyEnum) {
		this.classifyEnum = classifyEnum;
	}

	public String getMainImageUrl() {
		return mainImageUrl;
	}

	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl == null ? null : mainImageUrl.trim();
	}

	public SourceEnum getSourceEnum() {
		return sourceEnum;
	}

	public void setSourceEnum(SourceEnum sourceEnum) {
		this.sourceEnum = sourceEnum;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
	}

	public String getPurchaseUrl() {
		return purchaseUrl;
	}

	public void setPurchaseUrl(String purchaseUrl) {
		this.purchaseUrl = purchaseUrl == null ? null : purchaseUrl.trim();
	}

	@Override
	public Integer getParentId() {
		return null;
	}

	public String getStateName() {
		return MBox.getDisplayByIEnum(ProductNewState.fromName(state));
	}

	public ProductNewState getStateEnum() {
		return state == null ? null : ProductNewState.fromName(state);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public LocalDateTime getStateTime() {
		return stateTime;
	}

	public void setStateTime(LocalDateTime stateTime) {
		this.stateTime = stateTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public Integer getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(Integer checkerId) {
		this.checkerId = checkerId;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker == null ? null : checker.trim();
	}

	public String getSpu() {
		return spu;
	}

	public void setSpu(String spu) {
		this.spu = spu;
	}

	public String getAttributeDesc() {
		return attributeDesc;
	}

	public void setAttributeDesc(String attributeDesc) {
		this.attributeDesc = attributeDesc;
	}

	public CustomEnum getCustomEnum() {
		return customEnum;
	}

	public void setCustomEnum(CustomEnum customEnum) {
		this.customEnum = customEnum;
	}

	public GoalEnum getGoalEnum() {
		return goalEnum;
	}

	public void setGoalEnum(GoalEnum goalEnum) {
		this.goalEnum = goalEnum;
	}

	public String getGoalEnumName() {
		return MBox.getDisplayByIEnum(goalEnum);
	}

	public String getCustomEnumName() {
		return MBox.getDisplayByIEnum(customEnum);
	}

	public List<Integer> getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(List<Integer> departmentIds) {
		this.departmentIds = departmentIds;
	}

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getInnerName() {
		return innerName;
	}

	public void setInnerName(String innerName) {
		this.innerName = innerName;
	}

	public LocalDateTime getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(LocalDateTime submitTime) {
		this.submitTime = submitTime;
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

}