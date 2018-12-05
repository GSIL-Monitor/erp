package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.AttributeLang;
import com.stosz.product.service.AttributeLangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 属性语言包
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping("/product/base/attributeLang")
public class AttributeLangController extends AbstractController {

	@Autowired
	private AttributeLangService attributeLangService;

	/**
	 * 属性语言包的添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(AttributeLang attributeLang) {
		RestResult result = new RestResult();
		Assert.notNull(attributeLang, "添加属性语言包失败,传入参数异常");
		Assert.notNull(attributeLang.getAttributeId(), "添加属性语言包失败,未选定属性!");
		MBox.assertLenth(attributeLang.getName(), "属性语言包名称", 2, 255);
		MBox.assertLenth(attributeLang.getLangCode(), "属性语言包编码", 1, 8);
		attributeLangService.addAttributeLang(attributeLang);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加属性语言包" + attributeLang.getName() + "成功");
		return result;
	}

	/**
	 * 属性语言包的删除
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam Integer id) {
		RestResult result = new RestResult();
		attributeLangService.deleteAttributeLang(id);
		result.setDesc("删除属性语言包id:" + id + "成功！");
		result.setCode(RestResult.NOTICE);
		return result;
	}

	/**
	 * 属性语言包的修改
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(AttributeLang attributeLang) {
		RestResult result = new RestResult();
		Assert.notNull(attributeLang.getId(), "请先选择符合修改的选项");
		MBox.assertLenth(attributeLang.getName(), "属性语言包名称", 2, 255);
		MBox.assertLenth(attributeLang.getLangCode(), "属性语言包编码", 1, 8);
		attributeLangService.updateAttributeLang(attributeLang);
		result.setCode(RestResult.NOTICE);
		result.setDesc("属性语言包更新成功！");
		return result;
	}

	/**
	 * 属性语言包列表
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(@RequestParam Integer attributeId) {
		RestResult result = new RestResult();
		List<AttributeLang> list = attributeLangService
				.findByAttributeId(attributeId);
		result.setItem(list);
		result.setDesc("属性语言包查询成功");
		return result;
	}

}
