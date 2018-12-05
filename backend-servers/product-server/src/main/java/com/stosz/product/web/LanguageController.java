package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.model.Language;
import com.stosz.product.service.LanguageService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 语言设置Controller
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping("/product/base/language")
public class LanguageController extends AbstractController {

	@Resource
	private LanguageService languageService;

	/**
	 * 语言添加方法
	 * 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(Language language) {
		RestResult result = new RestResult();
		Assert.notNull(language, "增加失败,参数错误");
		MBox.assertLenth(language.getName(), "语言名称", 2, 255);
		MBox.assertLenth(language.getLangCode(), "语言编码",1,8);
		//添加
		languageService.addLanguage(language);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加成功");
		return result;
	}

	/**
	 * 语言删除方法
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@Param("languageId") Integer id) {
		RestResult result = new RestResult();
		languageService.deleteLanguage(id);
		result.setCode(RestResult.NOTICE);
		result.setDesc("删除成功");
		return result;
	}

	/**
	 * 语言修改方法
	 * 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(Language language) {
		RestResult result = new RestResult();
		Assert.notNull(language, "修改失败,参数错误");
		Assert.notNull(language.getId(), "请先选择修改项");
		MBox.assertLenth(language.getName(), "语言名称", 2, 255);
		MBox.assertLenth(language.getLangCode(), "语言编码",1,8);
		languageService.updateLanguage(language);
		result.setCode(RestResult.NOTICE);
		result.setDesc("修改成功");
		return result;
	}

	/**
	 * 语言列表
	 * 
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(Language language) {
		return languageService.findLanguage(language);
	}

}
