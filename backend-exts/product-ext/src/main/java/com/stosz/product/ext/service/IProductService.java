package com.stosz.product.ext.service;

import com.stosz.product.ext.model.AttributeValue;
import com.stosz.product.ext.model.LabelObject;
import com.stosz.product.ext.model.Product;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 产品数据对外接口
 * @author xiongchenyang
 * @version [1.0 , 2017/11/13]
 */
public interface IProductService {

	String url = "/product/remote/IProductService";

	Product getBySpu(String spu);

	/**
	 * 
	 * 根据sku获取属性信息
	 */
	List<AttributeValue> getValueListBySku(String sku);

	/**
	 * 根据产品id获取产品信息
	 *      -产品绑定的属性,属性上的属性值
	 */
	Product getProductInfoById(Integer id);

	List<Product> findProductBySpuList(Set<String> spuList);

	Product getById(Integer productId);

	List<Product> getByIds(List<Integer> productIds);

	Product getProductById(Integer productId);

	/**
	 * 通过二级域名返回产品信息
	 *      -返回产品信息,产品的sku,sku对应的属性值组合
	 */
	List<Product> findByWebDirectory(String webDirectory);

	/**
	 *通过产品id返回产品信息+产品sku+sku对应的属性值组合
	 */
	Product getProductAssemble(Integer id);

	/**
	 * 通过sku获取对应的产品信息
	 */
	Map<String, Product> findBySkuList(List<String> skuList);

	/**
	 * 通过SKU获取产品的所有标签(未实现)
	 * @param skuList
	 * @return
	 */
	Map<String, List<LabelObject>> queryLabelBySkuList(List<String> skuList);

    /**
     * 通过spu查询批量查询产品信息
     *      产品信息中需要skuList
     */
    List<Product> findBySpuSet(Set<String> spuList);


}
