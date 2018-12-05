package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * 老erp的产品属性实体
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
public class OldErpAttribute extends AbstractParamEntity implements Serializable, ITable {

    /**
     * 主键id
     */
    @DBColumn
    private Integer idProductOption;
    /**
     * 产品id
     */
    @DBColumn
    private Integer idProduct;
    /**
     * 属性名称
     */
    @DBColumn
    private String title;

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTable() {
        return "erp_product_option";
    }

    @Override
    public Integer getId() {
        return idProductOption;
    }

    @Override
    public void setId(Integer id) {
        this.idProductOption = id;
    }
}
