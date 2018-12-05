package com.stosz.product.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.Category;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductSku;
import com.stosz.product.ext.model.ProductSkuAttributeValueRel;
import com.stosz.product.service.ProductService;
import com.stosz.product.service.ProductSkuAsyncService;
import com.stosz.product.service.ProductSkuService;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品Controller
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping(value = "/product/manage")
public class ProductController extends AbstractController {
	@Resource
	private ProductService service;
	@Resource
	private ProductSkuService productSkuService;
	@Resource
	private ProductSkuAsyncService productSkuAsyncService;

	/**
	 * 产品删除的方法
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam Integer productId) {
		RestResult result = new RestResult();
		service.deleteProduct(productId);
		result.setCode(RestResult.NOTICE);
		result.setDesc("删除成功");
		return result;
	}

	/**
	 * 产品修改的方法
	 * 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(Product product) {
		RestResult result = new RestResult();
		Assert.notNull(product.getId(), "请先选择一项作为修改项");
		service.updateProduct(product);
		result.setCode(RestResult.NOTICE);
		result.setDesc("修改成功");
		return result;
	}

	/**
	 * 根据产品id修改产品内部名
	 * 
	 */
	@RequestMapping(value = "/updateById", method = RequestMethod.POST)
	@ResponseBody
	public RestResult updateById(@RequestParam(required = true) String innerName, @RequestParam(required = true) Integer id) {
		RestResult result = new RestResult();
		MBox.assertLenth(innerName, "内部名", 100);
		service.updateById(innerName,id);
		result.setCode(RestResult.NOTICE);
		result.setDesc("修改产品内部名成功");
		return result;
	}

	/**
	 * 根据产品id修改产品特性与产品内部名,并推送到老erp和wms
	 *
	 */
	@RequestMapping(value = "/updatePushProduct", method = RequestMethod.POST)
	@ResponseBody
	public RestResult updatePushProduct(Product param) {
		RestResult result = new RestResult();
		service.updatePushProduct(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("修改产品内部名成功");
		return result;
	}


	/**
	 * sku单个生成
	 */
	@RequestMapping(value = "/productSkuGenerate",method = RequestMethod.POST)
	@ResponseBody
	public RestResult productSkuGenerate(@RequestParam String attrInfo) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) JSONUtils.parse(attrInfo);
		Assert.isTrue(list.size() != 0, "传入参数有误");
		List<ProductSkuAttributeValueRel> relList = new ArrayList<ProductSkuAttributeValueRel>();
		for (Map<String, Object> map : list) {
			ProductSkuAttributeValueRel rel = new ProductSkuAttributeValueRel();
			rel.setProductId(Integer.parseInt(map.get("productId").toString()));
			List<Integer> attributeValueIds = (List<Integer>) map.get("attributeValueIds");
			rel.setAttributeValueIds(attributeValueIds);
			relList.add(rel);
		}
		Integer id = relList.get(0).getProductId();
		RestResult rst = productSkuService.productSkuGenerate(id, relList);
		return rst;
	}

	/**
	 * sku批量生成
	 */
	@RequestMapping(value = "/productSkuBatchGenerate",method = RequestMethod.POST)
	@ResponseBody
	public RestResult productSkuBatchGenerate(@RequestParam Integer id) {
		RestResult result = new RestResult();
		productSkuAsyncService.asyncGenerateAllSku(id);
		result.setCode(RestResult.NOTICE);
		result.setDesc("SKU批量生成成功!");
		return result;
	}

	/**
	 * 产品列表(关联表)
	 * 		--新品查重
	 */
//	@RequestMapping(value = "/findList")
//	@ResponseBody
//    public RestResult findList(Product product, boolean isSystemMatch) {
//		List<Integer> departmentIds = service.getDepartmentViewByCurrentUser();
//		product.setDepartmentIds(departmentIds);
//		if ("".equals(product.getSpu())) {
//			product.setSpu(null);
//		}
//		return service.findListing(product, isSystemMatch);    //产品,品类
//    }

	/**
	 *查重列表的产品图片信息
	 */
	@RequestMapping(value = "/findImage")
	@ResponseBody
	public RestResult findImage(Product product, boolean isSystemMatch) {
		if ("".equals(product.getSpu())) {
			product.setSpu(null);
		}
		return service.findImage(product, isSystemMatch);    //产品,品类
	}

	/**
	 *查重列表的产品图片信息
	 */
	@RequestMapping(value = "/countImage")
	@ResponseBody
	public RestResult countImage(Product product, boolean isSystemMatch) {
		if ("".equals(product.getSpu())) {
			product.setSpu(null);
		}
		return service.countImage(product, isSystemMatch);    //产品,品类
	}


	/**
	 *查重列表的产品信息
	 */
	@RequestMapping(value = "/getInfo")
	@ResponseBody
	public RestResult getInfo(@RequestParam Integer id) {
		return service.getInfo(id);    //产品,品类
	}


	/**
	 * 产品列表(关联表)
	 * 		--广告产品列表
	 */
	@RequestMapping(value = "/product_list")
	@ResponseBody
	public RestResult queryList(Product product) {
		product.setCreatorId(MBox.getLoginUserId());
		return service.queryList(product);        //产品,产品区域
	}
	
	/**
	 * 产品列表(关联表)
	 * 		--(查重)开发产品列表
	 */
	@RequestMapping(value = "/dev_list")
	@ResponseBody
	public RestResult queryListing(Product product) {
		List<Integer> departmentIds = service.getDepartmentViewByCurrentUser();
		product.setDepartmentIds(departmentIds);
		return service.queryList(product);//产品,产品区域
	}
	
	/**
	 * 产品列表(关联表)
	 * 		--助理,主管产品列表
	 */
	@RequestMapping(value = "/supervisor_assistant")
	@ResponseBody
	public RestResult queryApproveList(Product product) {
		return service.queryApproveList(product);//产品,产品区域
	}
	
	/**
	 * 产品信息查询 (个人)
	 */
	@RequestMapping(value = "/getDetails")
	@ResponseBody
	public RestResult getDetails(@RequestParam Integer id) {
		RestResult result = new RestResult();
        Product product = service.getDetails(id);
        result.setItem(product);
		result.setDesc("个人产品信息查询成功!");
		return result;
	}
	
	/**
	 * 产品信息查询 (主管,助理)
	 */
	@RequestMapping(value = "/getApproveDetails")
	@ResponseBody
	public RestResult getApproveDetails(@RequestParam Integer id) {
		RestResult result = new RestResult();
        Product product = service.getApproveDetails(id);
        result.setItem(product);
        result.setDesc("主管/助理产品信息查询成功!");
		return result;
	}
	
	/**
	 * 产品信息查询 (开发)
	 */
	@RequestMapping(value = "/getAllDetails")
	@ResponseBody
	public RestResult getAllDetails(@RequestParam Integer id) {
		RestResult result = new RestResult();
        Product product = service.getAllDetails(id);
        result.setItem(product);
        result.setDesc("开发产品信息查询成功!");
		return result;
	}
	
	/**
	 * sku列表
	 */
	@RequestMapping(value = "/productSkuList")
	@ResponseBody
	public RestResult productSkuList(@RequestParam Integer productId) {
		RestResult result = new RestResult();
        List<ProductSku> list = service.productSkuList(productId);
        result.setItem(list);
        result.setDesc("sku列表查询成功!");
        return result;
	}

	/**
	 * 在产品的属性填充完成后将产品的待填充状态改为建档中
	 */
	@RequestMapping(value = "/processEvent", method = RequestMethod.POST)
	@ResponseBody
	public RestResult processEvent(Integer id) {
		RestResult restResult = new RestResult();
		service.updateWaitFillFsm(id);
		restResult.setCode(RestResult.NOTICE);
		restResult.setDesc("产品状态改为建档中");
		return restResult;
	}

	@RequestMapping(value = "revokeProduct", method = RequestMethod.POST)
	@ResponseBody
	public RestResult revokeProduct(Integer id){
		return service.revokeProduct(id);
	}


	@ResponseBody
	@RequestMapping("/getWaitDivideProduct")
	public RestResult getWaitDivideProduct(Product product) {
		RestResult result = new RestResult();
		Integer count = service.countByDivide(product);
		Assert.notNull(count, "查询待细化分类的产品总数失败！");
		result.setTotal(count);
		if (count == 0) {
			return result;
		}
		List<Product> productList = service.findByDivide(product);
		result.setItem(productList);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getWaitDivideCategory")
	public RestResult getWaitDivideCategory() {
		RestResult result = new RestResult();
		List<Category> categoryList = service.findCategoryByDivide();
		Assert.notNull(categoryList, "查询需要待细化分类产品的分类失败！");
		result.setItem(categoryList);
		return result;
	}


	@ResponseBody
	@RequestMapping("/updateCategory")
	public RestResult updateCategory(Integer categoryId, Integer id) {
		RestResult result = new RestResult();
		service.updateCategoryById(categoryId, id);
		result.setCode(RestResult.NOTICE);
		result.setDesc("产品品类细化成功！");
		return result;
	}

	@ResponseBody
	@RequestMapping("/updateCategoryBatch")
	/**
	 * id:产品id的拼接
	 * categoryId:品类id
	 */
	public RestResult updateCategoryBatch(String ids, Integer categoryId) {
		RestResult result = new RestResult();
		Assert.hasLength(ids, "传入的产品id集合为空！");
		//List<Integer> productIdList = (List<Integer>) JSONUtils.parse(id);
		String[] idsArray = ids.split(",");
		List<Integer> productIdList = new ArrayList<>();
		for (String id : idsArray){
			productIdList.add(Integer.valueOf(id.trim()));
		}
		service.updateCategoryBatchById(productIdList, categoryId);
        result.setCode(RestResult.NOTICE);
		result.setDesc("产品细化分类成功！");
		return result;
	}

	/**
	 * 根据skuID获取对应的产品来源链接
	 */
	@RequestMapping("/getProductSourceUrl")
	public void getProductSourceUrl(@RequestParam Integer skuId, HttpServletResponse resp) throws Exception {
		String url = service.getProductSourceUrl(skuId);
		resp.sendRedirect(url);
	}

	/**
	 * 根据spu获取产品信息
	 */
	@RequestMapping("/getBySpu")
	public RestResult getBySpu(@RequestParam String spu){
		RestResult result = new RestResult();
		Product product = service.getBySpu(spu);
		result.setItem(product);
		result.setDesc("通过SPU获取产品信息成功!");
		return result;
	}

}
