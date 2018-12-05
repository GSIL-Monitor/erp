package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.model.SensitiveWord;
import com.stosz.product.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 敏感词controller
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
@RequestMapping("/product/base/sensitiveWord")
@Controller
public class SensitiveWordController extends AbstractController {
	
	@Autowired
	private SensitiveWordService sensitiveWordService;
	
	/**
	 * 敏感词页面
	 */
	@RequestMapping(value = "")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView("/pc/base/sensitiveWord");
		model.addObject("keyword", MenuKeyword.SENSITIVEWORDS_SETTING);
		return model;
	}
	
	/**
	 * 敏感词增加
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(SensitiveWord param){
		RestResult result = new RestResult();
		Assert.notNull(param, "敏感词添加失败,参数有误");
		MBox.assertLenth(param.getName(), "敏感词名称", 1, 255);
		sensitiveWordService.insert(param);
		result.setCode("敏感词添加成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}
	
	/**
	 * 敏感词删除
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(@RequestParam Integer id){
		RestResult result = new RestResult();
		sensitiveWordService.delete(id);
		result.setDesc("敏感词删除成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}
	
	/**
	 * 敏感词修改
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(SensitiveWord param){
		RestResult result = new RestResult();
		Assert.notNull(param, "敏感词添加失败,参数有误");
		Assert.notNull(param.getId(), "请先选择要修改的敏感词");
		MBox.assertLenth(param.getName(), "敏感词名称", 1, 255);
		sensitiveWordService.update(param);
		result.setDesc("敏感词修改成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}
	
	/**
	 * 敏感词单条查询
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public RestResult get(@RequestParam Integer id){
		RestResult result = new RestResult();
		SensitiveWord entity = sensitiveWordService.getById(id);
		result.setItem(entity);
		result.setDesc("敏感词查询成功");
		return result;
	}
	
	/**
	 * 敏感词列表查询
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(SensitiveWord param){
		return sensitiveWordService.find(param);
	}
}
