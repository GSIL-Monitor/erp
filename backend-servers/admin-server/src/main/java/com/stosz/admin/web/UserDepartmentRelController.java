package com.stosz.admin.web;

import com.stosz.admin.service.admin.UserDepartmentRelService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.UserDepartmentRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * pc数据权限
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
@RequestMapping("/admin/userDepartmentRel")
@Controller
public class UserDepartmentRelController extends AbstractController {
	
	@Autowired
	private UserDepartmentRelService service;
	
	/**
	 * pc数据权限页面
	 */
	@RequestMapping(value = "")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView("/pc/base/userDepartmentRel");
		return model;
	}

	/**
	 * pc数据权限增加
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(UserDepartmentRel param){
		RestResult result = new RestResult();
		Assert.notNull(param, "产品数据权限增加失败,参数有误");
		Assert.notNull(param.getUserId(), "请选择用户");
		Assert.notNull(param.getDepartmentId(), "请选择部门");
		service.add(param);
		result.setDesc("产品数据权限添加成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}
	
	/**
	 * pc数据权限删除
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam Integer id){
		RestResult result = new RestResult();
		service.delete(id);
		result.setDesc("产品数据权限删除成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}
	
	/**
	 * pc数据权限修改
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(UserDepartmentRel param){
		RestResult result = new RestResult();
		Assert.notNull(param, "产品数据权限修改失败,参数有误");
		service.update(param);
		result.setDesc("产品数据权限修改成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}

	/**
	 * pc数据权限查询
	 */
	@RequestMapping(value = "/find",method = RequestMethod.POST)
	@ResponseBody
	public RestResult find(UserDepartmentRel param){
		if(param.getStart()!=0){
			Integer start = param.getStart();
			param.setStart(start-1);
		}
		return service.find(param);
	}

	
}
