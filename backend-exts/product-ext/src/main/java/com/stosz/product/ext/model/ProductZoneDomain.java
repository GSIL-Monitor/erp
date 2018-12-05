package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;

public class ProductZoneDomain extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;

	@DBColumn
	private LocalDateTime createAt;

	@DBColumn
	private String domain;

	@DBColumn
	private String webDirectory;

	@DBColumn
	private String loginid;

    /**
     * 去掉了该字段，但是为了兼容程序，该字段不去掉；
     */
	private Integer productZoneId = new Integer(0);

	@DBColumn
	private Integer productId = new Integer(0);

    @DBColumn
    private Integer zoneId= new Integer(0);

    @DBColumn
    private Integer departmentId= new Integer(0);

    @DBColumn
    private String seoLoginid="";

    @DBColumn
    private String siteProductId="";

	@Override
	public String getTable() {
		return "product_zone_domain";
	}

	@Override
	public Integer getId() {
		return id;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWebDirectory() {
        return webDirectory;
    }

    public void setWebDirectory(String webDirectory) {
        this.webDirectory = webDirectory;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public Integer getProductZoneId() {
        return productZoneId;
    }

    public void setProductZoneId(Integer productZoneId) {
        this.productZoneId = productZoneId;
    }

    public String getSiteProductId() {
        return siteProductId;
    }

    public void setSiteProductId(String siteProductId) {
        this.siteProductId = siteProductId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getSeoLoginid() {
        return seoLoginid;
    }

    public void setSeoLoginid(String seoLogind) {
        this.seoLoginid = seoLogind;
    }
}
