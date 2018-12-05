package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.AttributeValue;
import com.stosz.product.ext.model.ProductAttributeValueRel;
import com.stosz.product.service.AttributeValueService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 属性值controller
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping(value = "/product/base/attributeValue")
public class AttributeValueController extends AbstractController {

	@Autowired
	private AttributeValueService attributeValueService;

	/**
	 * 属性值添加(属性列表)
	 * 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(AttributeValue attributeValue) {
		RestResult result = new RestResult();
		Assert.notNull(attributeValue, "添加失败,传入参数异常");
		MBox.assertLenth(attributeValue.getTitle(), "属性值标题", 1, 50);
		attributeValueService.addAttributeValue(attributeValue);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加成功");
		return result;
	}

	/**
	 * 属性值添加(产品属性值关联关系)
	 * 
	 */
	@RequestMapping(value = "/addByProduct", method = RequestMethod.POST)
	@ResponseBody
	public RestResult addByProduct(AttributeValue attributeValue, @RequestParam Integer productId) {
		RestResult result = new RestResult();
		Assert.notNull(attributeValue, "添加失败,传入参数异常");
		Assert.notNull(attributeValue.getProductId(), "添加失败,未指明当前产品");
		Assert.notNull(attributeValue.getAttributeId(), "添加失败,未指明当前属性");
		MBox.assertLenth(attributeValue.getTitle(), "属性值标题", 1, 50);
		attributeValueService.addByProduct(attributeValue, productId);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加成功");
		return result;
	}

	/**
	 * 属性值删除 属性值列表/品类属性属性值列表
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam(required = true) Integer attributeValueId) {
		RestResult result = new RestResult();
		attributeValueService.delete(attributeValueId);
		result.setCode(RestResult.NOTICE);
		result.setDesc("删除成功");
		return result;
	}

	/**
	 * 属性值修改
	 * 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(AttributeValue attributeValue) {
		RestResult result = new RestResult();
		Assert.notNull(attributeValue, "修改失败,传入参数异常");
		Assert.notNull(attributeValue.getId(), "请选择一项作为修改项");
		MBox.assertLenth(attributeValue.getTitle(), "属性值名称", 1, 50);
		attributeValueService.updateAttributeValue(attributeValue);
		result.setCode(RestResult.NOTICE);
		result.setDesc("修改成功");
		return result;
	}

	/**
	 * 属性值的解绑
	 */
	@RequestMapping(value = "/unBind", method = RequestMethod.POST)
	@ResponseBody
	public RestResult unBind(ProductAttributeValueRel param, @RequestParam Integer attributeId) {
		RestResult result = new RestResult();
		Assert.notNull(param, "属性值解绑失败,传入参数异常");
		Assert.notNull(param.getProductId(), "解绑失败,未指定产品id");
		Assert.notNull(param.getAttributeValueId(), "解绑失败,未指定属性值id");
		Assert.notNull(param.getBindIs(), "请先选定操作");
		Assert.isTrue(!param.getBindIs(), "该操作不是解绑操作");
		result = attributeValueService.unBind(param, attributeId);
		return result;
	}

	/**
	 * 属性值的绑定
	 */
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public RestResult bind(ProductAttributeValueRel param, @RequestParam Integer attributeId) {
		RestResult result = new RestResult();
		UserDto dto = ThreadLocalUtils.getUser();
		Assert.notNull(param, "属性值绑定失败,传入参数异常");
		Assert.notNull(dto, "获取用户登录信息失败,请登录!");
		Assert.notNull(param.getBindIs(), "请先选定操作");
		Assert.notNull(param.getProductId(), "请先选定产品");
		Assert.notNull(param.getAttributeValueId(), "请先选定属性值");
		Assert.isTrue(param.getBindIs(), "该操作不是绑定操作");
		param.setCreatorId(MBox.getLoginUserId());
		param.setCreator(MBox.getLoginUserName());
		result = attributeValueService.bind(param, attributeId);
		result.setDesc("属性值绑定成功!");
		return result;
	}

	/**
	 * 属性值列表
	 * 		产品属性值的绑定和解绑
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(@RequestParam Integer productId, @RequestParam Integer attributeId) {
		RestResult result = new RestResult();
		List<AttributeValue> attributeValueList = attributeValueService.findByAttributeId(productId,attributeId);
		result.setItem(attributeValueList);
		result.setDesc("属性值列表查询成功");
		return result;
	}
	
	/**
	 * 属性值列表
	 * 		属性/品类
	 */
	@RequestMapping(value = "/findByAttributeId")
	@ResponseBody
	public RestResult findByAttributeId(@RequestParam(required = true) Integer attributeId,@RequestParam Integer start, @RequestParam Integer limit) {
		RestResult result = new RestResult();
		Assert.notNull(attributeId,"请先选定属性!");
		AttributeValue value = new AttributeValue();
		value.setAttributeId(attributeId);
		if (start != null) value.setStart(start);
		if (limit != null) value.setLimit(limit);
		return attributeValueService.findByAttrId(value);
	}
	
}
