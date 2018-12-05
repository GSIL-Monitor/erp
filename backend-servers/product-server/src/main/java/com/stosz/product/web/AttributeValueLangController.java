package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.AttributeValueLang;
import com.stosz.product.service.AttributeValueLangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 属性值语言包
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping("/product/base/attributeValueLang")
public class AttributeValueLangController extends AbstractController {

	@Autowired
	private AttributeValueLangService attributeValueLangService;

	/**
	 * 属性值语言包的添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(AttributeValueLang param) {
		RestResult result = new RestResult();
		Assert.notNull(param, "添加失败,传入参数异常");
		Assert.notNull(param.getAttributeValueId(),"添加失败,未选定属性值");
		MBox.assertLenth(param.getName(), "属性值语言包名称", 2, 255);
		MBox.assertLenth(param.getLangCode(), "属性值语言包编码", 1, 8);
		//添加
		attributeValueLangService.addAttributeValueLang(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加属性值语言包" + param.getName() + "成功");
		return result;
	}

	/**
	 * 属性值语言包的删除
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam Integer id) {
		RestResult result = new RestResult();
		attributeValueLangService.deleteAttributeValueLang(id);
		result.setDesc("删除属性值语言包id:" + id + "成功！");
		result.setCode(RestResult.NOTICE);
		return result;
	}

	/**
	 * 属性值语言包的修改
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(AttributeValueLang param) {
		RestResult result = new RestResult();
		Assert.notNull(param.getId(), "请先选择符合修改的选项");
		Assert.notNull(param.getAttributeValueId(),"未选定属性值");
		MBox.assertLenth(param.getName(), "属性值语言包名称", 2, 255);
		MBox.assertLenth(param.getLangCode(), "属性值语言包编码", 1, 8);
		//修改
		attributeValueLangService.updateAttributeValueLang(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("属性值语言包修改成功!");
		return result;
	}

	/**
	 * 属性值语言包列表
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(@RequestParam Integer attributeValueId) {
		RestResult result = new RestResult();
		List<AttributeValueLang> list = attributeValueLangService
				.findByAttributeValueId(attributeValueId);
		result.setItem(list);
		result.setDesc("属性值语言包查询成功");
		return result;
	}

}
