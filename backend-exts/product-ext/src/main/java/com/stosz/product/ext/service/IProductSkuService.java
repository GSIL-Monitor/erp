package com.stosz.product.ext.service;


import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductSku;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 何贵堂 on 2017/11/9.
 * <p>
 * 产品sku相关接口
 */
public interface IProductSkuService {

    String url="/product/remote/IProductSkuService";

    /**
     * 批量通过sku获取属性值的title
     * 速度慢  不建议使用,建议使用findAttrValueTitleBySku()代替
     */
    Map<String, String> getAttrValueCombinations(List<String> skuList);

    /**
     * 批量通过sku获取属性值的title
     */
    Map<String, String> findAttrValueTitleBySku(List<String> skuList);

    /**
     * 通过sku获取属性值的title
     */
    String getAttrValueTitleBySku(String sku);

    /**
     * 通过sku获取spu
     */
    String getSpuBySku(String sku);

    /**
     * 通过产品id和属性值id集合返回sku
     */
    String getSkuByProductAttrVal(Integer id, List<Integer> attValIds);

    /**
     * 通过产品id和产品属性值关系表id集合返回sku
     */
    String getSkuByProductRelId(Integer id, List<Integer> relIds);

    /**
     * 通过sku获取ProductSku
     */
    ProductSku getBySku(String sku);

    /**
     * 通过sku返回List<ProductSku>
     */
    List<ProductSku> findBySkuList(List<String> skuList);

    /**
     * 通过spu得到sku
     */
    List<ProductSku> findBySpu(String spu);
}
