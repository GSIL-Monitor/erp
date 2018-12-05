package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.enums.CategoryUserTypeEnum;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.model.Attribute;
import com.stosz.product.ext.model.Category;
import com.stosz.product.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 产品类别controller
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping(value = "/product/base/category")
public class CategoryController extends AbstractController {

	@Resource
	private CategoryService categoryService;

	/**
	 * 品类列表界面显示
	 * 
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public ModelAndView asList() {
		ModelAndView model = new ModelAndView("/pc/base/category");
		model.addObject("keyword", MenuKeyword.CATEGORY_MANAGEMENT);
		return model;
	}

	/**
	 * 品类的添加
	 * 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(Category category) {
		RestResult result = new RestResult();
		UserDto dto = ThreadLocalUtils.getUser();
	    Assert.notNull(dto, "获取用户登录信息失败,请先登录!");
		Assert.notNull(category, "产品类别删除失败,参数不正确");
		MBox.assertLenth(category.getName(), "品类名", 2, 255);
//		Assert.notNull(category.getNo(), "品类编码不能为NULL");
		Assert.notNull(category.getParentId(), "上级菜单选择失败");
		if(category.getNo() == null)
			category.setNo("");
		// 设置排序
		if(category.getSortNo() == null)
			category.setSortNo(10);
		category.setCreatorId(dto.getId());
		category.setCreator(dto.getLastName());
		//添加
		categoryService.addCategory(category);
		result.setCode(RestResult.NOTICE);
		result.setDesc("品类添加成功！");
		return result;
	}

	/**
	 * 品类的删除
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam Integer id) {
		RestResult result = new RestResult();
		if (id == 0) {
			result.setCode(RestResult.FAIL);
			result.setDesc("根节点不允许删除");
			return result;
		}
		categoryService.deleteCategory(id);
		result.setDesc("删除品类id:" + id + "成功！");
		result.setCode(RestResult.NOTICE);
		return result;
	}

	/**
	 * 品类的更新
	 * 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(Category category) {
		RestResult result = new RestResult();
		Assert.notNull(category, "产品类别更新失败,参数不正确");
		UserDto dto = ThreadLocalUtils.getUser();
	    Assert.notNull(dto, "获取用户登录信息失败,请先登录!");
		Assert.notNull(category.getId(), "请先选择一项要修改的产品类别");
		Assert.notNull(category.getParentId(), "请先选择所属分类");
		MBox.assertLenth(category.getName(), "品类名", 2, 255);
		category.setCreatorId(dto.getId());
		category.setSortNo(100);
		category.setCreator(dto.getLastName());
		if (category.getId() == 0) {
			result.setCode(RestResult.FAIL);
			result.setDesc("根节点不允许修改");
			return result;
		}
		//修改
		categoryService.updateCategory(category);
		result.setCode(RestResult.NOTICE);
		result.setDesc("品类更新成功！");
		return result;
	}

	/**
	 * 品类的移动
	 * 
	 */
	@RequestMapping(value = "/move", method = RequestMethod.POST)
	@ResponseBody
	public RestResult move(
			@RequestParam("currentCategoryId") Integer currentCategoryId,
			@RequestParam("targetCategoryId") Integer targetCategoryId) {
		RestResult result = new RestResult();
		categoryService.moveCategory(currentCategoryId, targetCategoryId);
		result.setItem(RestResult.NOTICE);
		result.setDesc("品类移动成功！");
		return result;
	}

	/**
	 * 品类树的显示
	 * 
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public RestResult tree(
			@RequestParam(required = false, defaultValue = "") String no) {
		RestResult result = new RestResult();
		Category tree = categoryService.buildTreeByCategoryNo(no);
		result.setItem(tree);
		result.setDesc("品类树查询成功!");
		return result;
	}

	/**
	 * 品类列表的显示 品类列表显示，应该是输入一个id和名称，名称可以模糊搜索
	 * 
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(Category category) {
		return categoryService.find(category);
	}

	/**
	 * 返回所有有效的一级分类
	 * @return
	 */
	@RequestMapping(value = "/findAllFirstLevel")
	@ResponseBody
	public RestResult findAllFirstLevel() {
		Category category = new Category();
		category.setParentId(0);
		category.setUsable(true);
		return categoryService.find(category);
	}

	/**
	 * 返回当前登录用户的一级分类
	 * @return
	 */
    @RequestMapping(value = "/findSelfFirstLevel")
    @ResponseBody
	public RestResult findSelfFirstLevel(@RequestParam CategoryUserTypeEnum userType){
    	Assert.notNull(userType, "用户类型不明确");
		List<Category> categories = categoryService.findFirstLevelByUserId(MBox.getLoginUserId(),userType);
		RestResult result = new RestResult();
		result.setItem(categories);
		return result;
	}

	/**
	 * 根据品类id获取品类对应的属性
	 * 
	 */
	@RequestMapping("/findAttribute")
	@ResponseBody
	public RestResult findAttribute(Integer categoryId) {
		RestResult result = new RestResult();
		Assert.notNull(categoryId, "请先选择所属品类");
		List<Attribute> attList = categoryService
				.findAttribvuteByCategoryId(categoryId);
		result.setItem(attList);
		result.setDesc("获取属性成功!");
		return result;
	}
	
	/**
	 * 某节点下的名称模糊
	 */
	@RequestMapping("/nodeNameLike")
	@ResponseBody
	public RestResult nodeNameLike(Category param){
		Assert.notNull(param.getParentId(), "未选择父级ID");
		return categoryService.nodeNameLike(param);
	}
	
	/**
	 * 某节点下的名称模糊
	 */
	@RequestMapping("/leafSearch")
	@ResponseBody
	public RestResult leafSearch(Category param){
		return categoryService.leafSearch(param);
	}

	@RequestMapping("/query")
	@ResponseBody
	public RestResult query(){
		RestResult result = new RestResult();
		result.setItem(categoryService.query());
		return result;
	}
	

}
