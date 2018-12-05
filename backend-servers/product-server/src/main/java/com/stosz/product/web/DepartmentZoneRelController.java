package com.stosz.product.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.model.DepartmentZoneRel;
import com.stosz.product.service.DepartmentZoneRelService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 部门区域关系表
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
@Controller
@RequestMapping(value="/product/base/departmentZoneRel")
public class DepartmentZoneRelController extends AbstractController {

	@Resource
	private DepartmentZoneRelService service;
	
	/**
     * 用于跳转到列表页面
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @ResponseBody
    public ModelAndView list() {
    	ModelAndView mav = new ModelAndView("pc/base/departmentZoneRel/list");
		mav.addObject("keyword", MenuKeyword.USER_FIRST_CATEGORY_RELATION);
		return mav;
    }

  /**
  * 新增调用方法
  */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(@RequestParam Integer departmentId, @RequestParam Integer zoneId) {
		RestResult result = new RestResult();
		service.insert(departmentId, zoneId);
	    result.setCode(RestResult.NOTICE);
		result.setDesc("部门区域关系添加成功!");
		return result;
	}

    
    /**
     * 部门区域关系表删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
	public RestResult delete(@RequestParam Integer id) {
    	RestResult result = new RestResult();
		service.delete(id);
		result.setCode(RestResult.NOTICE);
    	result.setDesc("部门区域关系删除成功!");
		return result;
	}
    
	/**
	 * 开启和关闭可以调用方法
	 */
   @RequestMapping(value="/updateUsable", method = RequestMethod.POST)
   @ResponseBody
   public RestResult updateUsable(Integer id, Boolean usable){
   		RestResult result = new RestResult();
		Assert.notNull(id,"请选择要修改的项");
		Assert.notNull(usable, "未指明状态[开启或关闭]");
		service.updateUsable(id, usable);
		result.setCode(RestResult.NOTICE);
		result.setDesc("部门区域关系状态修改成功!");
       return result;
   }
    
    /**
     * 页面查询调用方法
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
	public RestResult query(DepartmentZoneRel param) {
    	return service.findList(param);
    }

}
