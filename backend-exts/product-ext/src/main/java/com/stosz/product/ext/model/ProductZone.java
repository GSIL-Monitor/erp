package com.stosz.product.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.common.MBox;
import com.stosz.plat.enums.GoalEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.product.ext.enums.ProductZoneState;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 产品,部门,区域实体类
 * 
 * @author he_guitang
 *
 */
public class ProductZone extends AbstractParamEntity implements ITable, IFsmInstance, Comparable<ProductZone> {
	private static final long serialVersionUID = 5593430076907144696L;
	@DBColumn
	private Integer id;
	@DBColumn
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	@DBColumn
	private Integer productId;
	@DBColumn
	private String zoneCode;
	@DBColumn
	private String zoneName;
	@DBColumn
	private String currencyCode;
	@DBColumn
	private Integer departmentId;
	@DBColumn
	private String departmentName;
	@DBColumn
	private String departmentNo;
	@DBColumn
	private Integer creatorId;
	@DBColumn
	private String creator;
	@DBColumn
	private String state;
	@DBColumn
	private LocalDateTime stateTime;
	@DBColumn
	private String langCodeStr;
	@DBColumn
	private Integer productNewId;
	@DBColumn
	private Integer stock;
	@DBColumn
	private Integer qtyPreout;
	@DBColumn
	private Integer qtyRoad;
	@DBColumn
	private LocalDateTime lastOrderAt;
	@DBColumn
	private LocalDateTime archiveAt;
	@DBColumn
	private String archiveBy;
	@DBColumn
	private GoalEnum goalEnum;
	// 参数
	private Integer zoneId;
	private List<Integer> departmentIds;
	private String  departmentOldId;
	private Boolean checked;

	public Boolean getChecked() {
		return checked == null ? false : checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getProductNewId() {
		return productNewId;
	}

	public void setProductNewId(Integer productNewId) {
		this.productNewId = productNewId;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public String getArchiveBy() {
		return archiveBy;
	}

	public void setArchiveBy(String archiveBy) {
		this.archiveBy = archiveBy;
	}

	public LocalDateTime getArchiveAt() {
		return archiveAt;
	}

	public void setArchiveAt(LocalDateTime archiveAt) {
		this.archiveAt = archiveAt;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public Integer getParentId() {
		return null;
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName == null ? null : zoneName.trim();
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
		this.departmentName = departmentName == null ? null : departmentName.trim();
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getStateName() {
		return MBox.getDisplayByIEnum(ProductZoneState.fromName(state));
	}

	public ProductZoneState getStateEnum() {
		return state == null ? null : ProductZoneState.fromName(state);
	}

	public LocalDateTime getStateTime() {
		return stateTime;
	}

	public void setStateTime(LocalDateTime stateTime) {
		this.stateTime = stateTime;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public String getLangCodeStr() {
		return langCodeStr;
	}

	public void setLangCodeStr(String langCodeStr) {
		this.langCodeStr = langCodeStr;
	}

	@Override
	public String getTable() {
		return "product_zone";
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public LocalDateTime getLastOrderAt() {
		return lastOrderAt;
	}

	public void setLastOrderAt(LocalDateTime lastOrderAt) {
		this.lastOrderAt = lastOrderAt;
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

	public List<Integer> getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(List<Integer> departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getDepartmentOldId() {
		return departmentOldId;
	}

	public void setDepartmentOldId(String departmentOldId) {
		this.departmentOldId = departmentOldId;
	}

	public Integer getQtyPreout() {
		return qtyPreout;
	}

	public void setQtyPreout(Integer qtyPreout) {
		this.qtyPreout = qtyPreout;
	}

	public Integer getQtyRoad() {
		return qtyRoad;
	}

	public void setQtyRoad(Integer qtyRoad) {
		this.qtyRoad = qtyRoad;
	}

	@Override
	public int compareTo(ProductZone o) {
		return o.getChecked().compareTo(this.getChecked());
	}
}