package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 新品图片相似度对比的实体
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/26]
 */
public class ProductNewSiftImage extends AbstractParamEntity implements ITable, Serializable {

    @DBColumn
    private Integer id;
    @DBColumn
    private Integer productNewId;
    @DBColumn
    private Integer productId;
    @DBColumn
    private Integer siftValue;
    @DBColumn
    private LocalDateTime createAt;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductNewId() {
        return productNewId;
    }

    public void setProductNewId(Integer productNewId) {
        this.productNewId = productNewId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSiftValue() {
        return siftValue;
    }

    public void setSiftValue(Integer siftValue) {
        this.siftValue = siftValue;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String getTable() {
        return "product_new_sift_image";
    }
}
