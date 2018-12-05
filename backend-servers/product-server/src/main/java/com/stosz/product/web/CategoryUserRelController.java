package com.stosz.product.web;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.model.CategoryUserRel;
import com.stosz.product.service.CategoryService;
import com.stosz.product.service.CategoryUserRelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户品类关系设置表
 * 2017-08-19
 * @author Administrator
 */
@Controller
@RequestMapping(value="/product/base/categoryUserRel")
public class CategoryUserRelController extends AbstractController {

	@Resource
	private CategoryUserRelService categoryUserRelService;
	
	@Resource
	private IUserService iUserService;
	
	@Resource
	private CategoryService categoryService;
	
	/**
     * 用于跳转到列表页面
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @ResponseBody
    public ModelAndView list() {
    	ModelAndView mav = new ModelAndView("pc/base/categoryUserRel/list");
		mav.addObject("keyword", MenuKeyword.USER_FIRST_CATEGORY_RELATION);
		return mav;
    }

    /**
     * 用户与一级品类关系删除
     * 			he_guitang
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
	public RestResult delete(@RequestParam Integer id) {
    	RestResult result = new RestResult();
		categoryUserRelService.delete(id);
		result.setCode(RestResult.NOTICE);
    	result.setDesc("用户与一级品类关系删除成功!");
		return result;
	}
    
    
    
    /**
     * 页面查询调用方法
     * @param categoryUserRel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/find")
    @ResponseBody
	public RestResult query(CategoryUserRel categoryUserRel, HttpServletRequest request, HttpServletResponse response, Model model) {
    	RestResult result = new RestResult();

		//获取总记录数
		int counts = categoryUserRelService.queryCount(categoryUserRel);
		result.setCode(RestResult.OK);
		//返回总记录数
		result.setTotal(counts);
		if (counts == 0) {
			return result;
		}

		//获取当前页面需要显示的数据
		List<CategoryUserRel> datalist = categoryUserRelService.queryByPage(categoryUserRel);
		if(datalist != null && datalist.isEmpty()){
			return result;
		}
		//获取符合当前条件的所有的userid集合
		Assert.notNull(datalist);
		Set<Integer> useSet = datalist.stream().map(CategoryUserRel::getUserId).collect(Collectors.toSet());
		List<User> userList = iUserService.findByIds(useSet);
 		for (CategoryUserRel categoryUser : datalist) {
			//填充用户名和部门信息
			for (User user : userList) {
				if(categoryUser.getUserId().intValue() == user.getId().intValue()){
					categoryUser.setUserName(user.getLastname());
					categoryUser.setDepartmentName(user.getDepartmentName());
					break;
				}
			}
		}

		//返回当前页面的记录数
		result.setItem(datalist);
  		return result;
    }

	
    /**
     * 查看或者编辑调用方法
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
	public RestResult get(Integer id) {
		Assert.notNull(id,"Id不允许为空");
		CategoryUserRel categoryUserRel = categoryUserRelService.get(id);
    	RestResult result = new RestResult();
		result.setItem(categoryUserRel);
		return result;
	}
	
    /**
     * 新增调用方法
     * @param categoryUserRel
     * @param model
     * @param redirectAttributes
     * @return
     */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(CategoryUserRel categoryUserRel, Model model, RedirectAttributes redirectAttributes) {
    	RestResult result = new RestResult();
		Assert.notNull(categoryUserRel,"保存失败,传入参数异常!");
		Assert.notNull(categoryUserRel.getCategoryId(),"品类信息不能为空!");
		Assert.notNull(categoryUserRel.getUserId(),"用户信息不能为空!");
		Assert.notNull(categoryUserRel.getUserType(),"用户分类不能为空!");
        User user = iUserService.getByUserId(categoryUserRel.getUserId());
        categoryUserRel.setUsable(true);
        categoryUserRelService.add(categoryUserRel, user);
        result.setCode(RestResult.NOTICE);
		result.setDesc("操作成功!");
        return result;
	}

	/**
	 * 修改调用方法(开启和关闭可以调用次方法)
	 * @param request
	 * @param categoryUserRel
	 * @return
	 */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    @ResponseBody
    public RestResult update(CategoryUserRel categoryUserRel, HttpServletRequest request){
    	RestResult result = new RestResult();
		Assert.notNull(categoryUserRel,"保存失败,传入参数异常!");
		Assert.notNull(categoryUserRel.getId(),"id不能为空!");
		Assert.notNull(categoryUserRel.getUsable(),"状态不能为空!");
		categoryUserRelService.update(categoryUserRel);		
		result.setCode(RestResult.NOTICE);
		result.setDesc("操作成功!");
        return result;
    }
}
