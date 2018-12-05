package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.model.Attribute;
import com.stosz.product.ext.model.CategoryAttributeRel;
import com.stosz.product.ext.model.ProductAttributeRel;
import com.stosz.product.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 属性Controller
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping(value = "/product/base/attribute")
public class AttributeController extends AbstractController {

	@Autowired
	private AttributeService attributeService;

	/**
	 * 属性页面跳转
	 * 
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("/pc/base/attribute");
		model.addObject("keyword", MenuKeyword.ATTRIBUTE_MANAGEMENT);
		return model;
	}

	/**
	 * 属性增加(属性列表和品类属性)
	 * 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(Attribute attribute) {
		RestResult result = new RestResult();
		Assert.notNull(attribute, "保存失败,传入参数异常");
		MBox.assertLenth(attribute.getTitle(), "属性名称", 2, 255);
		attributeService.addAttribute(attribute);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加属性" + attribute.getTitle() + "成功");
		return result;
	}
	
	/**
	 * 属性增加(产品属性的添加)
	 * 		--直接新建属性
	 */
	@RequestMapping(value = "/addByProduct", method = RequestMethod.POST)
	@ResponseBody
	public RestResult addByProduct(Attribute attribute, ProductAttributeRel rel, @RequestParam Integer categoryId) {
//		RestResult result = new RestResult();
		Assert.notNull(attribute, "增加失败,传入参数异常");
		Assert.notNull(rel.getProductId(), "增加失败,未选定产品");
		MBox.assertLenth(attribute.getTitle(), "属性名称", 2, 255);
		rel.setCreatorId(MBox.getLoginUserId());
		rel.setCreator(MBox.getLoginUserName());
		//增加操作
		return attributeService.addByProduct(attribute,rel,categoryId);
	}
	
	/**
	 * 属性删除
	 * 		属性列表/品类属性列表
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam Integer id) {
		RestResult result = new RestResult();
		attributeService.delete(id);
		result.setCode(RestResult.NOTICE);
		result.setDesc("删除成功");
		return result;
	}

	/**
	 * 属性修改
	 * 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(Attribute attribute) {
		RestResult result = new RestResult();
		Assert.notNull(attribute, "修改失败,参数有误");
		Assert.notNull(attribute.getId(), "请选择一项作为修改项");
		MBox.assertLenth(attribute.getTitle(), "属性名称", 2, 255);
		attributeService.update(attribute);
		result.setCode(RestResult.NOTICE);
		result.setDesc("修改成功");
		return result;
	}

	/**
	 * 属性与品类的解绑
	 */
	@RequestMapping(value = "/unBindingCategory", method = RequestMethod.POST)
	@ResponseBody
	public RestResult unBindingCategory(CategoryAttributeRel car) {
		RestResult result = new RestResult();
		UserDto dto = ThreadLocalUtils.getUser();
		Assert.notNull(dto, "获取用户登录信息失败,请登录!");
		Assert.notNull(car, "解绑与绑定失败,传入参数异常");
		Assert.notNull(car.getCategoryId(), "解绑失败,未指定产品类别id");
		Assert.notNull(car.getAttributeId(), "解绑失败,未指定属性id");
		Assert.notNull(car.getBindIs(), "请先选定操作");
		Assert.isTrue(!car.getBindIs(), "该操作不是解绑操作");
		car.setCreatorId(dto.getId());
		car.setCreator(dto.getLastName());
		result = attributeService.unBindingCategory(car);
		return result;
	}

	/**
	 * 属性与品类的绑定
	 */
	@RequestMapping(value = "/bindingCategory", method = RequestMethod.POST)
	@ResponseBody
	public RestResult bindingCategory(CategoryAttributeRel car) {
		RestResult result = new RestResult();
		UserDto dto = ThreadLocalUtils.getUser();
		Assert.notNull(dto, "获取用户登录信息失败,请登录!");
		Assert.notNull(car, "解绑与绑定失败,传入参数异常");
		Assert.notNull(car.getCategoryId(), "绑定失败,未指定产品类别id");
		Assert.notNull(car.getAttributeId(), "绑定失败,未指定属性id");
		Assert.notNull(car.getBindIs(), "请先选定操作");
		Assert.isTrue(car.getBindIs(), "该操作不是绑定操作");
		car.setCreatorId(dto.getId());
		car.setCreator(dto.getLastName());
		result = attributeService.bindingCategory(car);
		return result;
	}
	
	//------------------------------------------------------------------


	/**
	 * 属性与产品的解绑
	 */
	@RequestMapping(value = "/unBindingProduct", method = RequestMethod.POST)
	@ResponseBody
	public RestResult unBindingProduct(ProductAttributeRel param) {
		RestResult result = new RestResult();
		UserDto dto = ThreadLocalUtils.getUser();
		Assert.notNull(dto, "获取用户登录信息失败,请登录!");
		Assert.notNull(param, "解绑与绑定失败,传入参数异常");
		Assert.notNull(param.getProductId(), "解绑失败,未指定产品id");
		Assert.notNull(param.getAttributeId(), "解绑失败,未指定属性id");
		Assert.notNull(param.getBindIs(), "请先选定操作");
		Assert.isTrue(!param.getBindIs(), "该操作不是解绑操作");
		param.setCreatorId(dto.getId());
		param.setCreator(dto.getLastName());
		result = attributeService.unBindingProduct(param);
		result.setDesc("属性与产品的解绑成功!");
		return result;
	}

	/**
	 * 属性与产品的绑定
	 */
	@RequestMapping(value = "/bindingProduct", method = RequestMethod.POST)
	@ResponseBody
	public RestResult bindingProduct(ProductAttributeRel param) {
		RestResult result = new RestResult();
		UserDto dto = ThreadLocalUtils.getUser();
		Assert.notNull(dto, "获取用户登录信息失败,请登录!");
		Assert.notNull(param, "解绑与绑定失败,传入参数异常");
		Assert.notNull(param.getProductId(), "绑定失败,未指定产品id");
		Assert.notNull(param.getAttributeId(), "绑定失败,未指定属性id");
		Assert.notNull(param.getBindIs(), "请先选定操作");
		Assert.isTrue(param.getBindIs(), "该操作不是绑定操作");
		param.setCreatorId(dto.getId());
		param.setCreator(dto.getLastName());
		result = attributeService.bindingProduct(param);
		result.setDesc("属性与产品的绑定成功!");
		return result;
	}


	/**
	 * 获取单条属性
	 *
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public RestResult get(@RequestParam Integer id) {
		RestResult result = new RestResult();
		Attribute attribute = attributeService.get(id);
		result.setItem(attribute);
		result.setDesc("属性查询成功!");
		return result;
	}

	/**
	 * 属性列表 属性的页面
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(Attribute attribute) {
		attribute.setVersion(MBox.ERP_ATTRIBUTE_VERSION);
		return attributeService.findAttribute(attribute);
	}

	/**
	 * 属性列表 品类下的属性页面
	 */
	@RequestMapping(value = "/findList")
	@ResponseBody
	public RestResult findList(Attribute attribute) {
		Assert.notNull(attribute.getCategoryId(), "获取属性页面失败,请先选定品类!");
		return attributeService.findListAttribute(attribute);
	}

	/**
	 * 通过品类获取属性列表
	 */
	@RequestMapping(value = "/findByCategoryId")
	@ResponseBody
	public RestResult findByCategoryId(@RequestParam Integer categoryId) {
		RestResult result = new RestResult();
		List<Attribute> list = attributeService.findAttributeByCategoryId(categoryId);
		result.setItem(list);
		result.setDesc("根据品类获取属性成功");
		return result;
	}

	/**
	 * 通过品类和产品获取属性列表
	 * 		-获取品类下的属性,获取产品下的属性
	 */
	@RequestMapping(value = "/findByCateProId")
	@ResponseBody
	public RestResult findByCateProId(@RequestParam Integer categoryId, @RequestParam Integer productId) {
		RestResult result = new RestResult();
		List<Attribute> list = attributeService.findByCateProId(categoryId, productId);
		result.setItem(list);
		result.setDesc("根据品类和产品ID获取属性成功");
		return result;
	}
	

	@RequestMapping(value = "/findAttr")
	@ResponseBody
	public RestResult findAttr(@RequestParam Integer categoryId) {
		RestResult result = new RestResult();
		List<Attribute> list = attributeService
				.findAttributeByCategoryId(categoryId);
		result.setItem(list);
		result.setDesc("查询成功");
		return result;
	}

}
