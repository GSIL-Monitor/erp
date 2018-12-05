package com.stosz.order.ext.dto;

import java.io.Serializable;

/**
 * @auther carter
 * create time    2017-12-20
 * 部门分配sku的流水
 */
public class DeptAssignQtyDto implements Serializable{

    /**部门id*************************/
    private Integer deptId  =  0 ;
    /**数量*************************/
    private Integer qty = 0;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
