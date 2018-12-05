package com.stosz.tms.model;

import java.util.Date;
import java.util.List;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.tms.dto.ShippingStoreRelLableAddDto;
import com.stosz.tms.enums.AllowedProductTypeEnum;

/**
 * 物流仓库关系
 * @author feiheping
 * @version [1.0,2017年12月16日]
 */
public class ShippingStoreRel extends AbstractParamEntity implements ITable {

	@DBColumn
	private Integer id;

	@DBColumn
	private Integer shippingWayId;

	@DBColumn
	private String wmsName;

	@DBColumn
	private Integer wmsId;

	@DBColumn
	private Integer expressSheetType;

	@DBColumn
	private Integer allowedProductType;

	@DBColumn
	private String shippingGeneralName;

	@DBColumn
	private String shippingSpeciaName;

	@DBColumn
	private String shippingGeneralCode;

	@DBColumn
	private String shippingSpeciaCode;

	@DBColumn
	private Integer enable;

	@DBColumn
	private Date createAt;

	@DBColumn
	private Date updateAt;

	@DBColumn
	private String creator;

	@DBColumn
	private Integer creatorId;

	@DBColumn
	private String modifier;

	@DBColumn
	private Integer modifierId;

	@DBColumn
	private Integer isBackChoice;

	private String aliasName;

	private String aliasCode;

	private Integer general;

	private Integer specia;

	private List<Integer> shippingWayIdList;

	private List<ShippingStoreRelLableAddDto> lableDataList; // 选择的标签集合

	private int matchWay = -1;// 0 仓库+排程 1 仓库+排程模板 2 任意仓+排程 3 任意仓+排程模板

	private int sorted;// 排序

	private int eachCount;// 已经排上的次数

	private ShippingAllocationTemplate template;// 排程模板

	private ShippingSchedule schedule;// 排程

	private AllowedProductTypeEnum productType;

	private ShippingWay shippingWay;

	private boolean lackTrackNo = false;// 是否缺少运单号

	public Integer getIsBackChoice() {
		return isBackChoice;
	}

	public void setIsBackChoice(Integer isBackChoice) {
		this.isBackChoice = isBackChoice;
	}

	public boolean isLackTrackNo() {
		return lackTrackNo;
	}

	public void setLackTrackNo(boolean lackTrackNo) {
		this.lackTrackNo = lackTrackNo;
	}

	public int getEachCount() {
		return eachCount;
	}

	public void setEachCount(int eachCount) {
		this.eachCount = eachCount;
	}

	public List<Integer> getShippingWayIdList() {
		return shippingWayIdList;
	}

	public void setShippingWayIdList(List<Integer> shippingWayIdList) {
		this.shippingWayIdList = shippingWayIdList;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getAliasCode() {
		return aliasCode;
	}

	public void setAliasCode(String aliasCode) {
		this.aliasCode = aliasCode;
	}

	public Integer getGeneral() {
		return general;
	}

	public void setGeneral(Integer general) {
		this.general = general;
	}

	public Integer getSpecia() {
		return specia;
	}

	public void setSpecia(Integer specia) {
		this.specia = specia;
	}

	public List<ShippingStoreRelLableAddDto> getLableDataList() {
		return lableDataList;
	}

	public void setLableDataList(List<ShippingStoreRelLableAddDto> lableDataList) {
		this.lableDataList = lableDataList;
	}

	public ShippingWay getShippingWay() {
		return shippingWay;
	}

	public void setShippingWay(ShippingWay shippingWay) {
		this.shippingWay = shippingWay;
	}

	public String getShippingGeneralName() {
		return shippingGeneralName;
	}

	public void setShippingGeneralName(String shippingGeneralName) {
		this.shippingGeneralName = shippingGeneralName;
	}

	public String getShippingSpeciaName() {
		return shippingSpeciaName;
	}

	public void setShippingSpeciaName(String shippingSpeciaName) {
		this.shippingSpeciaName = shippingSpeciaName;
	}

	public String getShippingGeneralCode() {
		return shippingGeneralCode;
	}

	public void setShippingGeneralCode(String shippingGeneralCode) {
		this.shippingGeneralCode = shippingGeneralCode;
	}

	public String getShippingSpeciaCode() {
		return shippingSpeciaCode;
	}

	public void setShippingSpeciaCode(String shippingSpeciaCode) {
		this.shippingSpeciaCode = shippingSpeciaCode;
	}

	public int getMatchWay() {
		return matchWay;
	}

	public void setMatchWay(int matchWay) {
		this.matchWay = matchWay;
	}

	public int getSorted() {
		return sorted;
	}

	public void setSorted(int sorted) {
		this.sorted = sorted;
	}

	public ShippingAllocationTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ShippingAllocationTemplate template) {
		this.template = template;
	}

	public ShippingSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(ShippingSchedule schedule) {
		this.schedule = schedule;
	}

	public AllowedProductTypeEnum getProductType() {
		return productType;
	}

	public void setProductType(AllowedProductTypeEnum productType) {
		this.productType = productType;
	}

	public Integer getExpressSheetType() {
		return expressSheetType;
	}

	public void setExpressSheetType(Integer expressSheetType) {
		this.expressSheetType = expressSheetType;
	}

	public Integer getAllowedProductType() {
		return allowedProductType;
	}

	public void setAllowedProductType(Integer allowedProductType) {
		this.allowedProductType = allowedProductType;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Integer getModifierId() {
		return modifierId;
	}

	public void setModifierId(Integer modifierId) {
		this.modifierId = modifierId;
	}

	public Integer getShippingWayId() {
		return shippingWayId;
	}

	public void setShippingWayId(Integer shippingWayId) {
		this.shippingWayId = shippingWayId;
	}

	public String getWmsName() {
		return wmsName;
	}

	public void setWmsName(String wmsName) {
		this.wmsName = wmsName == null ? null : wmsName.trim();
	}

	public Integer getWmsId() {
		return wmsId;
	}

	public void setWmsId(Integer wmsId) {
		this.wmsId = wmsId;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getTable() {
		return "shipping_store_rel";
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}
}