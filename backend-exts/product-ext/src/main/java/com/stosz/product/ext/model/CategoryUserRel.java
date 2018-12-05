package com.stosz.product.ext.model;

import com.stosz.plat.enums.CategoryUserTypeEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class CategoryUserRel extends AbstractParamEntity implements ITable {

	@DBColumn
	private Integer id;

	@DBColumn
	private Integer userId;

	@DBColumn
    private Integer categoryId;

    private String userName;

    private String categoryName;

    @DBColumn
    private CategoryUserTypeEnum userType;

    @DBColumn
    private String departmentNo;

    private String departmentName;

    private String userTypeName;

    @DBColumn
    private Boolean usable;
    
    public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUserTypeName() {
    	userTypeName = (userType == null?"":userType.name());
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public Boolean getUsable() {
		return usable;
	}

	public void setUsable(Boolean usable) {
		this.usable = usable;
	}
// boolean值，交给前端去处理
//	public String getStatusStr() {
//		if(usable == 1){
//			this.statusStr =  "有效";
//		}else{
//			this.statusStr =  "无效";
//		}
//		return statusStr;
//	}

//	public void setStatusStr(String statusStr) {
//		this.statusStr = statusStr;
//	}

	public CategoryUserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(CategoryUserTypeEnum userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;
	}

	@Override
	public String getTable() {
		return "category_user_rel";
	}
}