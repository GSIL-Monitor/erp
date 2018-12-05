package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.CategoryLang;
import com.stosz.product.service.CategoryLangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 品类语言包
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping("/product/base/categoryLang")
public class CategoryLangController extends AbstractController {

	@Autowired
	private CategoryLangService service;

	/**
	 * 品类语言包的添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(CategoryLang param) {
		RestResult result = new RestResult();
		Assert.notNull(param, "添加品类语言包失败,传入参数异常");
		Assert.notNull(param.getCategoryId(), "添加品类语言包失败,未选定品类!");
		MBox.assertLenth(param.getName(), "品类语言包名称", 2, 255);
		MBox.assertLenth(param.getLangCode(), "品类语言包编码", 1, 8);
		service.add(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加品类语言包" + param.getName() + "成功");
		return result;
	}

	/**
	 * 品类语言包的删除
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam Integer id) {
		RestResult result = new RestResult();
		service.delete(id);
		result.setDesc("删除品类语言包id:" + id + "成功！");
		result.setCode(RestResult.NOTICE);
		return result;
	}

	/**
	 * 品类语言包的修改
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(CategoryLang param) {
		RestResult result = new RestResult();
		Assert.notNull(param.getId(), "请先选择符合修改的选项");
		MBox.assertLenth(param.getName(), "品类语言包名称", 2, 255);
		MBox.assertLenth(param.getLangCode(), "品类语言包编码", 1, 8);
		service.update(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("品类语言包更新成功！");
		return result;
	}

	/**
	 * 品类语言包列表
	 */
	@RequestMapping(value = "/findByCategoryId")
	@ResponseBody
	public RestResult find(@RequestParam Integer categoryId) {
		RestResult result = new RestResult();
		List<CategoryLang> list = service.findByCategoryId(categoryId);
		result.setItem(list);
		result.setDesc("品类语言包查询成功");
		return result;
	}

}
