package com.stosz.product.ext.model;

import com.google.common.collect.Lists;
import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.common.MBox;
import com.stosz.plat.enums.ClassifyEnum;
import com.stosz.plat.enums.CustomEnum;
import com.stosz.plat.enums.SourceEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.product.ext.enums.ProductState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 产品实体类
 *
 * @author he_guitang
 */
public class Product extends AbstractParamEntity implements IFsmInstance, ITable {
	private static final long serialVersionUID = -2757211637760582080L;

	@DBColumn
	private Integer id;
	@DBColumn
	private String spu;
	@DBColumn
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	@DBColumn
	private Integer creatorId;
	@DBColumn
	private String creator;
	@DBColumn
	private String title;
	@DBColumn
	private Integer categoryId;
	@DBColumn
	private ClassifyEnum classifyEnum;
	@DBColumn
	private String innerName;
	@DBColumn
	private String mainImageUrl;
	@DBColumn
	private SourceEnum sourceEnum;
	@DBColumn
	private String sourceUrl;
	@DBColumn
	private String purchaseUrl;
	@DBColumn
	private BigDecimal purchasePrice;
	@DBColumn
	private BigDecimal longness;
	@DBColumn
	private BigDecimal width;
	@DBColumn
	private BigDecimal height;
	@DBColumn
	private BigDecimal weight;
	@DBColumn
	private String state;
	@DBColumn
	private LocalDateTime stateTime;
	@DBColumn
	private Integer totalStock;
	@DBColumn
	private String memo;
	@DBColumn
	private Integer checkerId;
	@DBColumn
	private String checker;
	@DBColumn
	private Integer productNewId;
	@DBColumn
	private String attributeDesc; // 多语言描述
	@DBColumn
	private CustomEnum customEnum;

	private String enCategory;
	private Integer days;
    private List<String> productStates;

	// 品类名
	private String name;
	// 品类编码
	private String no;
	// 产品语言包
	private List<ProductLang> productLangList;
	/**
	 * 产品区域表相关字段
	 */
	private String departmentName; // 所属部门名称
	private String departmentNo;// 部门编号
	private String zoneName; // 区域名
	private String zoneCode; // 区域名
	private String zoneState; // 产品状态
	private Integer stock; // 产品区域库存
	private LocalDateTime lastOrderAt; // 产品区域最后下单时间
	private List<String> departmentNos = Lists.newArrayList(); // 用户部门编码
	private List<Integer> departmentIds = Lists.newArrayList(); // 用户部门编码
	// 所属分类实体类
	private Category category;
	private List<Integer> categoryIds;// 品类集合
	// 产品,部门,区域
	private List<ProductZone> productZoneList;
	// 属性
	private List<Attribute> attributeList;
	private Integer departmentId;
	private Integer zoneId;
	private String attributeName;
	private List<ProductSku> skuList;
	private String sku;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public List<ProductSku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<ProductSku> skuList) {
		this.skuList = skuList;
	}

	public String getEnCategory() {
		return enCategory;
	}

	public void setEnCategory(String enCategory) {
		this.enCategory = enCategory;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

	/**
	 * 与其余产品的图片相似度
     */
    private String siftValue;

	public String getSpu() {
		return spu;
	}

	public void setSpu(String spu) {
		this.spu = spu;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInnerName() {
		return innerName;
	}

	public void setInnerName(String innerName) {
		this.innerName = innerName;
	}

	public String getMainImageUrl() {
		return mainImageUrl;
	}

	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getPurchaseUrl() {
		return purchaseUrl;
	}

	public void setPurchaseUrl(String purchaseUrl) {
		this.purchaseUrl = purchaseUrl;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return MBox.getDisplayByIEnum(ProductState.fromName(state));
	}

	public ProductState getStateEnum() {
		return state == null ? null : ProductState.fromName(state);
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
		this.memo = memo;
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
		this.checker = checker;
	}

	public Integer getProductNewId() {
		return productNewId;
	}

	public void setProductNewId(Integer productNewId) {
		this.productNewId = productNewId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public Integer getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(Integer totalStock) {
		this.totalStock = totalStock;
	}

	@Override
	public String getTable() {
		return "product";
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Integer getParentId() {
		return null;
	}

	@Override
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
		this.creator = creator;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public List<ProductLang> getProductLangList() {
		return productLangList;
	}

	public void setProductLangList(List<ProductLang> productLangList) {
		this.productLangList = productLangList;
	}

	public ClassifyEnum getClassifyEnum() {
		return classifyEnum;
	}

	public void setClassifyEnum(ClassifyEnum classifyEnum) {
		this.classifyEnum = classifyEnum;
	}

	public SourceEnum getSourceEnum() {
		return sourceEnum;
	}

	public void setSourceEnum(SourceEnum sourceEnum) {
		this.sourceEnum = sourceEnum;
	}

	public String getClassifyEnumName() {
		return MBox.getDisplayByIEnum(classifyEnum);
	}

	public String getSourceEnumName() {
		return MBox.getDisplayByIEnum(sourceEnum);
	}

	public List<ProductZone> getProductZoneList() {
		return productZoneList;
	}

	public void setProductZoneList(List<ProductZone> productZoneList) {
		this.productZoneList = productZoneList;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public List<Attribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<Attribute> attributeList) {
		this.attributeList = attributeList;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no == null ? "" : no.trim();
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

	public String getZoneState() {
		return zoneState;
	}

	public void setZoneState(String zoneState) {
		this.zoneState = zoneState;
	}

	public List<String> getDepartmentNos() {
		return departmentNos;
	}

	public void setDepartmentNos(List<String> departmentNos) {
		this.departmentNos = departmentNos;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
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

	public List<String> getProductStates() {
		return productStates;
	}

	public void setProductStates(List<String> productStates) {
		this.productStates = productStates;
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
	
	public String getCustomEnumName() {
		return MBox.getDisplayByIEnum(customEnum);
    }

    public String getSiftValue() {
        return siftValue;
    }

    public void setSiftValue(String siftValue) {
        this.siftValue = siftValue;
    }

	public BigDecimal getLongness() {
		return longness;
	}

	public void setLongness(BigDecimal longness) {
		this.longness = longness;
	}
}